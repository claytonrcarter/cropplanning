/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.CropPlanStats;

import CPS.Data.CPSCalculations;
import CPS.Data.CPSComplexPlantingFilter;
import CPS.Data.CPSDateValidator;
import CPS.Data.CPSPlanting;
import CPS.Data.CPSPlantingComparator;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSDisplayableDataUserModule;
import CPS.Module.CPSGlobalSettings;
import CPS.UI.Swing.CPSCardPanel;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.FunctionList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.calculation.AbstractEventListCalculation;
import ca.odell.glazedlists.calculation.Calculation;
import ca.odell.glazedlists.calculation.Calculations;
import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.matchers.Matchers;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author crcarter
 */
public class CropPlanStats extends CPSDisplayableDataUserModule implements ActionListener {

  JPanel jplContents, jplBedsCharts, jplFlatsChart, jplReq, jplFlats;
  
  JButton updateButton;
  JLabel labelInfo;
  private BasicEventList<CPSPlanting> dataList;
  private SortedList<CPSPlanting> dataSorted;
  private FilterList<CPSPlanting> dataFiltered;

  public CropPlanStats() {
    setModuleName("PlanStats");
    setModuleType("Core");
    setModuleVersion( CPSGlobalSettings.getVersion() );
  }

//****************************************************************************//
//  Called when the user pressed "Update"
//****************************************************************************//
  private void doIt() {

    updateButton.setEnabled(false);
    jplContents.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    Calendar cal = Calendar.getInstance();

//****************************************************************************//
//  Create the lists and setup the sort/filter/function chain
//****************************************************************************//
    dataList = new BasicEventList<CPSPlanting>();
    dataFiltered = new FilterList<CPSPlanting>( dataList );
    dataSorted = new SortedList<CPSPlanting>( dataFiltered, null );

    SumBedsRowftFlats statSums = new SumBedsRowftFlats( dataFiltered );


//****************************************************************************//
//    Fill up the lists
//****************************************************************************//
    dataList.addAll( getDataSource().getCropPlan( getMediator().getCropPlan() ) );

    List<String> fieldNames = getDataSource().getFieldNameList( getMediator().getCropPlan() );
    List<String> flatSizes = getDataSource().getFlatSizeList( getMediator().getCropPlan() );
    List<String> requirements = getDataSource().getRequirementsForPlan( getMediator().getCropPlan() );

    // make sure we include blank field names
    if ( ! fieldNames.contains("") )
      fieldNames.add("");
    
    // and include "blank" flat size as "total flats"
    if ( ! flatSizes.contains("") )
      flatSizes.add("");

    // and include "blank" requirements as "total beds"
    if ( ! requirements.contains("") )
      requirements.add("");


//****************************************************************************//
//    Find the first and last weeks to worry about
//****************************************************************************//
    // Find the first planting
    dataSorted.setComparator( new CPSPlantingComparator( CPSDataModelConstants.PROP_DATE_PLANT ));
    String labelString = "First planting is " + dataSorted.get(0).getCropName() +
                         " on " + dataSorted.get(0).getDateToPlantString();
    Date dateFirstPlanting = dataSorted.get(0).getDateToPlant();

    // ... and the last
    // this sorts the list based on harvest date + num of weeks to yield
    dataSorted.setComparator( new Comparator<CPSPlanting>() {
                public int compare( CPSPlanting o1, CPSPlanting o2) {
                  return o1.getDateHarvestEnd().compareTo(o2.getDateHarvestEnd());
                }
              }
            );
    Date dateLastHarvest = dataSorted.get( dataSorted.size()-1 ).getDateHarvestEnd();
    labelString += "<br>Last harvest is " + dataSorted.get( dataSorted.size()-1 ).getCropName() +
                        " on " + CPSDateValidator.format( dateLastHarvest );


    int weekNum;
    cal.setTime( dateLastHarvest );
    int lastWeekNum = cal.get( Calendar.WEEK_OF_YEAR );


//****************************************************************************//
//    Create the filters depending on how it was planted
//****************************************************************************//
    CPSInTheFieldMatcherEditor inFieldMatcher = new CPSInTheFieldMatcherEditor();
    CPSInTheGreenhouseMatcher inGHMatcher = new CPSInTheGreenhouseMatcher();


//****************************************************************************//
//    Create our data structures
//****************************************************************************//
    // eg. 128 => 78.25, 72 => 105.5, 606 => ...
    Map<String, Float> flatssTotalMap = new HashMap<String, Float>();

    // eg 128 => flats => 34.25
    //           flatsdate  => 4/12
    Map<String, WeeklyMaxStruct> flatsMaxMap = new HashMap<String, WeeklyMaxStruct>();
    for (String f : flatSizes)
      flatsMaxMap.put( f, new WeeklyMaxStruct () );


    // eg. black-plastic => beds => 68.5, ... rowlen => 1243.11
    Map<String, WeeklyMaxStruct> reqsTotalMap = new HashMap<String, WeeklyMaxStruct>();
    for (String r : requirements )
      reqsTotalMap.put( r, new WeeklyMaxStruct () );

    // eg black-plastic => beds     => 34.25
    //                     bedsDate => 6/12
    //                     rowLen   => 102.5
    //                     rowLenDate => 7/2
    Map<String, WeeklyMaxStruct> reqsMaxMap = new HashMap<String, WeeklyMaxStruct>();
    for (String r : requirements )
      reqsMaxMap.put( r, new WeeklyMaxStruct () );

    // weekNum => field name (or "flats" or "all") => usage#
    Map<Integer, Map<String, Double>> bedFlatUsageMap = new HashMap<Integer, Map<String, Double>>();


//****************************************************************************//
//    get total bed/flat count for requirements and flats
//****************************************************************************//
    for ( String r : requirements ) {
      inFieldMatcher.setRequirement( r );
      dataFiltered.setMatcher(inFieldMatcher);
      reqsTotalMap.get( r ).beds = statSums.beds;
      reqsTotalMap.get( r ).rowLen = statSums.rowUnitLengthes;
    }

    for ( String f : flatSizes) {
      inGHMatcher.setFlatSize( f );
      dataFiltered.setMatcher( inGHMatcher );
      flatssTotalMap.put( f, statSums.flats );
    }

    // unfilter the list
    dataFiltered.setMatcher( Matchers.trueMatcher() );
    inFieldMatcher.setRequirement( null );
    inGHMatcher.setFlatSize( null );


//**********************************************************************//
// Loop over the weeks
//**********************************************************************//
    cal.setTime( dateFirstPlanting );
    weekNum = cal.get( Calendar.WEEK_OF_YEAR );

    for (; weekNum <= lastWeekNum; weekNum++ ) {

      // init this weeks' usage Map
      bedFlatUsageMap.put( weekNum, new HashMap<String, Double> () );

      // set the calendar to this week
      cal.set( Calendar.WEEK_OF_YEAR, weekNum );

      //*************************************//
      // Check total trays in GH this week
      //*************************************//
      inGHMatcher.setInTheGreenhouseOn( cal.getTime() );
      inGHMatcher.setFlatSize( null );
      dataFiltered.setMatcher( inGHMatcher );

      bedFlatUsageMap.get( weekNum )
                     .put( "flats",
                           1.0 * CPSCalculations.roundQuarter( statSums.flats ));


      //*************************************//
      // Check to see if this week is the maximum tray usage (by size)
      //*************************************//
      for ( String f : flatSizes ) {
        inGHMatcher.setFlatSize( f );
        dataFiltered.setMatcher( inGHMatcher );
        if ( statSums.flats > flatsMaxMap.get( f ).flats ) {
          flatsMaxMap.get( f ).flats = statSums.flats;
          flatsMaxMap.get( f ).flatsDate = cal.getTime();
        }
      }


      //*************************************//
      // Check total beds in use this week
      //*************************************//
      inFieldMatcher.setInTheFieldOn( cal.getTime() );
      inFieldMatcher.setFieldName( null );
      inFieldMatcher.setRequirement( null );
      dataFiltered.setMatcher( inFieldMatcher );

      bedFlatUsageMap.get( weekNum )
                     .put( "all",
                           1.0 * CPSCalculations.roundQuarter( statSums.beds ));


      //*************************************//
      // Check to see about max usage of requirements
      //*************************************//
      for ( String r : requirements ) {
        inFieldMatcher.setRequirement( r );
        dataFiltered.setMatcher( inFieldMatcher );
        if ( statSums.beds > reqsMaxMap.get(r).beds ) {
          reqsMaxMap.get(r).beds = statSums.beds;
          reqsMaxMap.get(r).bedsDate = cal.getTime();
        }
        if ( statSums.rowUnitLengthes > reqsMaxMap.get( r).rowLen ) {
          reqsMaxMap.get(r).rowLen = statSums.rowUnitLengthes;
          reqsMaxMap.get(r).rowLenDate = cal.getTime();
        }
      }


      //*************************************//
      // now ignore requirements and just look at field names for bed usage
      //*************************************//
      inFieldMatcher.setRequirement( null );
      for ( String f : fieldNames ) {
        inFieldMatcher.setFieldName( f );
        dataFiltered.setMatcher( inFieldMatcher );
        bedFlatUsageMap.get( weekNum )
                       .put( f,
                             1.0 * CPSCalculations.roundQuarter( statSums.beds ));
      }

    }


//****************************************************************************//
//    Done collecting data, now put to together into the charts
//****************************************************************************//

    List<String> bedChartTitles = new ArrayList<String>();
    List<JPanel> bedCharts = new ArrayList<JPanel>();

    JPanel ghChart = null;

    List<String> chartsToMake = new ArrayList<String>( fieldNames );
    chartsToMake.add( "all" );
    chartsToMake.add( "flats" );

    //*************************************//
    // Loop over fields to build charts for bed usage (and flats)
    //*************************************//
    for ( String f : chartsToMake ) {

      List<String> chartLabels = new ArrayList<String>();
      List<Double> chartValues = new ArrayList<Double>();

      cal.setTime( dateFirstPlanting );
      weekNum = cal.get( Calendar.WEEK_OF_YEAR );
      for (; weekNum <= lastWeekNum; weekNum++ ) {

        cal.set( Calendar.WEEK_OF_YEAR, weekNum );
        chartLabels.add( CPSDateValidator.format( cal.getTime(),
                                          CPSDateValidator.DATE_FORMAT_SHORT));
        chartValues.add( bedFlatUsageMap.get(weekNum).get(f) );

      }

      //*************************************//
      // Catch special case where we're working with flats
      //*************************************//
      if ( f.equals( "flats") ) {
        ghChart = new ChartPanel( chartValues, chartLabels, "Trays in the Greenhouse" );
      }
      else {
        // and special cases for specific field names
        if ( f.equals("all") )
          bedChartTitles.add( "Total beds in use" );
        else if ( f.equals("") )
          bedChartTitles.add( "Beds in use in field" );
        else
          bedChartTitles.add( "Beds in use: " + f );

        bedCharts.add( new ChartPanel( chartValues, chartLabels, "" ) );
      }
    }


//****************************************************************************//
//    Now build all of the displayable components
//****************************************************************************//
    JLabel tempLabel;

    //*************************************//
    // Charts
    //*************************************//
    labelInfo = new JLabel( "<html>" + labelString + "</html>" );
    jplBedsCharts = new CPSCardPanel( bedChartTitles, bedCharts, bedChartTitles.indexOf( "Total beds in use" ) );
    jplFlatsChart = ghChart;

    //*************************************//
    // Total and Max Usage of Beds/Requirements
    //*************************************//
    jplReq = new JPanel( new MigLayout( "",
                                        "[align right, 15%:][align center, 2%:][align right, 15%:]" ));
    jplReq.add( new JLabel( "Requirement" ), "align center" );
    jplReq.add( new JLabel( "<html><center>" + "Total<br>Beds" + "</center></html>" ), "skip 1" );
    jplReq.add( new JLabel( "<html><center>" + "Max<br>Beds" + "</center></html>" ) );
    if ( CPSGlobalSettings.useMetric() ) {
      jplReq.add( new JLabel( "<html><center>" + "Total<br>Row Meters" + "</center></html>" ) );
      jplReq.add( new JLabel( "<html><center>" + "Max<br>Row Meters" + "</center></html>" ), "wrap" );
    } else {
      jplReq.add( new JLabel( "<html><center>" + "Total<br>RowFt" + "</center></html>" ) );
      jplReq.add( new JLabel( "<html><center>" + "Max<br>RowFt" + "</center></html>" ), "wrap" );
    }
    jplReq.add( new JSeparator(), "growx, span, wrap" );
    boolean firstRow = true;
    for ( String req : requirements ) {
      if ( req.equals("") )
        continue;
      jplReq.add( new JLabel( req ) );
      if ( firstRow ) {
        jplReq.add( new JSeparator( SwingConstants.VERTICAL ), "growy, spany" );
        firstRow = false;
      }
      jplReq.add( new JLabel( "" + CPSCalculations.roundQuarter( reqsTotalMap.get(req).beds ) ));
      tempLabel = new JLabel( "" + CPSCalculations.roundQuarter( reqsMaxMap.get(req).beds ) );
      tempLabel.setToolTipText( "on " + CPSDateValidator.format( reqsMaxMap.get(req).bedsDate,
                                                                 CPSDateValidator.DATE_FORMAT_SHORT ));
      jplReq.add( tempLabel );
      jplReq.add( new JLabel( "" + reqsTotalMap.get(req).rowLen ));
      tempLabel = new JLabel( "" + reqsMaxMap.get(req).rowLen );
      tempLabel.setToolTipText( "on " + CPSDateValidator.format( reqsMaxMap.get(req).rowLenDate,
                                                                 CPSDateValidator.DATE_FORMAT_SHORT ));
      jplReq.add( tempLabel, "wrap" );
    }
    tempLabel = new JLabel("Total:");
    tempLabel.setToolTipText( "for all beds in this plan, regardless of 'requirements' needed" );
    jplReq.add( tempLabel );
    // still have to add the separator if we're on the first row
    if ( firstRow )
      jplReq.add( new JSeparator( SwingConstants.VERTICAL ), "growy, spany" );
    jplReq.add( new JLabel( "" + CPSCalculations.roundQuarter( reqsTotalMap.get("").beds ) ));
    tempLabel = new JLabel( "" + CPSCalculations.roundQuarter( reqsMaxMap.get("").beds ) );
    tempLabel.setToolTipText( "on " + CPSDateValidator.format( reqsMaxMap.get("").bedsDate,
                                                               CPSDateValidator.DATE_FORMAT_SHORT ));
    jplReq.add( tempLabel );
    jplReq.add( new JLabel( "" + reqsTotalMap.get("").rowLen ));
    tempLabel = new JLabel( "" + reqsMaxMap.get("").rowLen );
    tempLabel.setToolTipText( "on " + CPSDateValidator.format( reqsMaxMap.get("").rowLenDate,
                                                               CPSDateValidator.DATE_FORMAT_SHORT ));
    jplReq.add( tempLabel, "wrap" );


    //*************************************//
    // Total/Max usage of flats
    //*************************************//
    jplFlats = new JPanel( new MigLayout( "",
                                          "[align right, 15%:][align center, 2%:][align right, 15%:]" ));
    jplFlats.add( new JLabel( "Flat Size" ), "align center" );
    jplFlats.add( new JLabel( "<html><center>" + "Total<br>Flats" + "</center></html>" ), "skip 1" );
    jplFlats.add( new JLabel( "<html><center>" + "Max<br>Flats" + "</center></html>" ), "wrap" );
    jplFlats.add( new JSeparator(), "growx, span, wrap" );

    firstRow = true;
    for ( String flat : flatSizes ) {
      if ( flat.equals( "" ) )
        continue;
      jplFlats.add( new JLabel( flat ) );
      if ( firstRow ) {
        jplFlats.add( new JSeparator( SwingConstants.VERTICAL ), "growy, spany" );
        firstRow = false;
      }
      jplFlats.add( new JLabel( flatssTotalMap.get(flat).toString() ));
      tempLabel = new JLabel( "" + CPSCalculations.roundQuarter( flatsMaxMap.get(flat).flats ));
      tempLabel.setToolTipText( "on " + CPSDateValidator.format( flatsMaxMap.get(flat).flatsDate,
                                                                 CPSDateValidator.DATE_FORMAT_SHORT ));
      jplFlats.add( tempLabel, "wrap" );
    }
    tempLabel = new JLabel("Total:");
    tempLabel.setToolTipText( "for all flats in this plan, regardless of size" );
    jplFlats.add( tempLabel );
    if ( firstRow )
      jplFlats.add( new JSeparator( SwingConstants.VERTICAL ), "growy, spany" );
    jplFlats.add( new JLabel( flatssTotalMap.get("").toString() ));
    tempLabel = new JLabel( "" + CPSCalculations.roundQuarter( flatsMaxMap.get("").flats ));
    tempLabel.setToolTipText( "on " + CPSDateValidator.format( flatsMaxMap.get("").flatsDate,
                                                               CPSDateValidator.DATE_FORMAT_SHORT ));
    jplFlats.add( tempLabel, "wrap" );


    //*************************************//
    // all done
    //*************************************//
    rebuildContentPanel();

    updateButton.setEnabled(true);
    jplContents.setCursor(Cursor.getDefaultCursor());

  }


  @Override
  public void dataUpdated() {}

  @Override
  public int init() { return 0; }

  @Override
  protected int saveState() { return 0; }

  @Override
  public int shutdown() { return 0; }


  protected void rebuildContentPanel() {

    if ( jplContents == null )
      jplContents = new JPanel( new MigLayout( "insets 2px, gap 0px!",
                                               "[50%][50%]",
                                               "[][60%][]" ));
//                                               "[][grow, fill][]" ));

    jplContents.removeAll();

    if ( labelInfo == null )
      labelInfo = new JLabel( "Nothing to display; try pressing \"Update\"" );
    jplContents.add( labelInfo, "align left" );

    if ( updateButton == null ) {
      updateButton = new JButton("Update");
      updateButton.addActionListener(this);
    }
    jplContents.add( updateButton, "align right, wrap" );

    if ( jplBedsCharts != null ) {
      jplContents.add( jplBedsCharts, "grow" );
      jplContents.add( jplFlatsChart, "grow, wrap" );
    }

    if ( jplReq != null ) {
      jplContents.add( jplReq, "align left, grow" );
      jplContents.add( jplFlats, "align left, grow, wrap" );
    }

    
  }

  public JPanel display() {

    if ( jplContents == null )
      rebuildContentPanel();
    
    return jplContents;

  }

  public Dimension getSize() {
    return new Dimension(100, 100);
  }

  public void actionPerformed(ActionEvent e) {

    if ( e.getActionCommand().equalsIgnoreCase( updateButton.getActionCommand() ) )
      doIt();

  }



//****************************************************************************//
// Internal Classes
//****************************************************************************//


//****************************************************************************//
// Filters
//****************************************************************************//
  /**
   * Given a date (through the {@link setPlantingInTheFieldOn( Date d )} method)
   * this will match any planting that has been planted before the date and whose
   * "harvest window" (defined as Harvest Date + Num Weeks of Harvest) has not
   * passed.
   */
  class CPSInTheFieldMatcherEditor extends CPSComplexPlantingFilter {

    private Date dateInTheField;
    private String fieldName;
    private String requirement = null;

    @Override
    public boolean matches(CPSPlanting p) {
      boolean m = super.matches(p);

      if ( dateInTheField != null ) {
        if ( p.isDirectSeeded() )
          m &= dateInTheField.after( bumpDateByDay( p.getDateToPlant(), -1 )) &&
               dateInTheField.before( p.getDateHarvestEnd() );
        else
          m &= dateInTheField.after( bumpDateByDay( p.getDateToTP(), -1 )) &&
               dateInTheField.before( p.getDateHarvestEnd() );
      }

      // if fieldName is null, then match any field name
      // if it's blank, then match blank
      // otherwise match that it "starts with"
      if ( fieldName != null ) {
        if ( fieldName.equals( "") )
          m &= p.getLocation().equals( "" );
        else
          m &= p.getLocation().startsWith(fieldName);
      }

      if ( requirement != null && ! requirement.equals("") )
        m &= p.getOtherRequirements().contains( requirement );

      return m;
    }

    public void setInTheFieldOn(Date d) {
      dateInTheField = d;
      fireChanged(this);
    }

    public void setFieldName(String s) {
      fieldName = s;
      fireChanged(this);
    }

    public void setRequirement( String f ) {
      requirement = f;
      fireChanged(this);
    }

  }


    /**
   * Given a date (through the {@link setSeededInTheGreenhouseOn( Date d )} method)
   * this will match any planting that has been planted before the date and whose
   * transplant date has not passed.
   * It will also match on a flat size, if given via {@link setFlatSize( String f )}.
   * Set either to null stop attempting to match that property.  (ie match any
   * value of that property)
   */
  class CPSInTheGreenhouseMatcher extends CPSComplexPlantingFilter {

    private Date seededInTheGreenhouse = null;
    private String flatSize = null;


    public CPSInTheGreenhouseMatcher() {
      setAsTransplatedFilter();
    }

    @Override
    public boolean matches(CPSPlanting p) {
      boolean m = super.matches(p);

      if ( seededInTheGreenhouse != null )
        m &= seededInTheGreenhouse.after( bumpDateByDay( p.getDateToPlant(), -1 )) &&
             seededInTheGreenhouse.before( p.getDateToTP() );

      if ( flatSize != null && ! flatSize.equals("") )
        m &= p.getFlatSize().equals(flatSize);

      return m;
    }

    public void setInTheGreenhouseOn(Date d) {
      this.seededInTheGreenhouse = d;
      fireChanged(this);
    }

    public void setFlatSize( String f ) {
      flatSize = f;
      fireChanged(this);
    }

  }


//****************************************************************************//
// Advanced Calculation
//****************************************************************************//
  static final class SumBedsRowftFlats extends AbstractEventListCalculation<Float, CPSPlanting> {

    public float beds, rowUnitLengthes, flats;

    public SumBedsRowftFlats(EventList<CPSPlanting> source) {
        super(new Float(0), source);
        beds = rowUnitLengthes = flats = 0;
    }

    protected void inserted( CPSPlanting p ) {
      beds   += p.getBedsToPlant();
      rowUnitLengthes += p.getRowFtToPlant();
      flats  += p.getFlatsNeeded();
    }

    protected void deleted(CPSPlanting p) {
      beds   -= p.getBedsToPlant();
      rowUnitLengthes -= p.getRowFtToPlant();
      flats  -= p.getFlatsNeeded();
    }

    protected void updated( CPSPlanting oldP, CPSPlanting newP ) {
      beds   = beds   - oldP.getBedsToPlant()  + newP.getBedsToPlant();
      rowUnitLengthes = rowUnitLengthes - oldP.getRowFtToPlant() + newP.getRowFtToPlant();
      flats  = flats  - oldP.getFlatsNeeded()  + newP.getFlatsNeeded();
    }

  }

//****************************************************************************//
//  Basic struct to hold max values for each week
//****************************************************************************//
  private final class WeeklyMaxStruct {
    public float beds = 0;
    public float rowLen = 0;
    public float flats = 0;

    public Date bedsDate = null;
    public Date rowLenDate = null;
    public Date flatsDate = null;
  }

}
