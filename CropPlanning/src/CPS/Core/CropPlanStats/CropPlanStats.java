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
  
  Map<String, Float[]> reqMap = new HashMap<String, Float[]>();
  Map<String, Float[]> flatMap = new HashMap<String, Float[]>();


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
    FunctionList<CPSPlanting, Float> functionList =
          new FunctionList<CPSPlanting, Float>( dataFiltered,
                                              new FunctionList.Function<CPSPlanting, Float>() {
                                                 public Float evaluate( CPSPlanting p ) {
                                                    return p.getBedsToPlant();
                                                 }} );
    Calculation<Float> sumOfFloats = Calculations.sumFloats( functionList );
    
//****************************************************************************//
//    Fill up the lists
//****************************************************************************//
    dataList.addAll( getDataSource().getCropPlan( getMediator().getCropPlan() ) );

    List<String> fieldNames = 
            getDataSource().getFieldNameList( getMediator().getCropPlan() );
    // make sure we include blank field names
    if ( ! fieldNames.contains("") )
      fieldNames.add("");

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
    Date dateLastHarvest = dataSorted.get( dataSorted.size()-1 ).getDateToHarvest();
    cal.setTime( dateLastHarvest );
    cal.add( Calendar.WEEK_OF_YEAR, 
             dataSorted.get( dataSorted.size()-1 ).getYieldNumWeeks() );
    dateLastHarvest = cal.getTime();
    labelString += "<br>Last harvest is " + dataSorted.get( dataSorted.size()-1 ).getCropName() +
                        " on " + CPSDateValidator.format( dateLastHarvest );


    int weekNum;
    cal.setTime( dateLastHarvest );
    int endWeek = cal.get( Calendar.WEEK_OF_YEAR );

//****************************************************************************//
//    Create the filters depending on how it was planted
//****************************************************************************//
    CPSInTheFieldMatcherEditor dsInFieldMatcher = new CPSInTheFieldMatcherEditor();
    dsInFieldMatcher.setViewLimited(true);
    dsInFieldMatcher.setFilterOnPlantingMethod(true);
    dsInFieldMatcher.setFilterMethodDirectSeed(true);

    CPSInTheFieldMatcherEditor tpInFieldMatcher = new CPSInTheFieldMatcherEditor();
    tpInFieldMatcher.setViewLimited(true);
    tpInFieldMatcher.setFilterOnPlantingMethod(true);
    tpInFieldMatcher.setFilterMethodDirectSeed(false);

    // put them together as an OR matcher
    BasicEventList<MatcherEditor<CPSPlanting>> inFieldMatchers =
            new BasicEventList<MatcherEditor<CPSPlanting>>();
    inFieldMatchers.add( dsInFieldMatcher );
    inFieldMatchers.add( tpInFieldMatcher );
    CompositeMatcherEditor<CPSPlanting> inFieldFilter =
            new CompositeMatcherEditor<CPSPlanting>( inFieldMatchers );
    inFieldFilter.setMode( CompositeMatcherEditor.OR );

    List<String> chartWeeks;
    List<Double> chartValues;
//****************************************************************************//
//    Loop over the field names
//****************************************************************************//
    List<String> titles = new ArrayList<String>();
    List<JPanel> charts = new ArrayList<JPanel>();
    for ( String field : fieldNames ) {

      chartWeeks = new ArrayList<String>();
      chartValues = new ArrayList<Double>();

      dsInFieldMatcher.setFieldName( field );
      tpInFieldMatcher.setFieldName( field );

      cal.setTime( dateFirstPlanting );
      weekNum = cal.get( Calendar.WEEK_OF_YEAR );

      //**********************************************************************//
      // Loop over the weeks
      //**********************************************************************//
      while ( weekNum <= endWeek ) {

        cal.set( Calendar.WEEK_OF_YEAR, weekNum );
        dsInFieldMatcher.setPlantingInTheFieldOn( cal.getTime() );
        tpInFieldMatcher.setPlantingInTheFieldOn( cal.getTime() );

        // update the filter? is this needed?
        dataFiltered.setMatcherEditor(inFieldFilter);

        double beds = CPSCalculations.roundQuarter( sumOfFloats.getValue() );

        chartWeeks.add( CPSDateValidator.format( cal.getTime(),
                                            CPSDateValidator.DATE_FORMAT_SHORT));
        chartValues.add( beds );

        weekNum++;
      }

      if ( field.equals("") )
        titles.add( "Beds in use in field" );
      else
        titles.add( "Beds in use: " + field );

      charts.add( new ChartPanel( chartValues, chartWeeks, "" ) );

    }

//****************************************************************************//
//    Now work on a chart of flats needed
//****************************************************************************//

    CPSInTheGreenhouseMatcher inGHMatcher = new CPSInTheGreenhouseMatcher();
    BasicEventList<MatcherEditor<CPSPlanting>> listOfFilters =
            new BasicEventList<MatcherEditor<CPSPlanting>>();
    listOfFilters.add( inGHMatcher );
    listOfFilters.add( new AbstractMatcherEditor<CPSPlanting>()
                          {
                            @Override
                            public Matcher<CPSPlanting> getMatcher() {
                               return Matchers.trueMatcher();
                            }
                          } );
    CompositeMatcherEditor<CPSPlanting> andFilter =
            new CompositeMatcherEditor<CPSPlanting>( listOfFilters );
    andFilter.setMode( CompositeMatcherEditor.AND );

    SumBedsRowftFlats customSummer = new SumBedsRowftFlats( dataFiltered );

    cal.setTime( dateFirstPlanting );
    weekNum = cal.get( Calendar.WEEK_OF_YEAR );

    chartWeeks = new ArrayList<String>();
    chartValues = new ArrayList<Double>();

    List<String> flatSizes = getDataSource().getFlatSizeList( getMediator().getCropPlan() );
    List<String> requirements = getDataSource().getRequirementsForPlan( getMediator().getCropPlan() );

    reqMap.clear();
    flatMap.clear();

    FlatMatcher flatMatcher = new FlatMatcher();
    RequirementMatcher reqMatcher = new RequirementMatcher();

//****************************************************************************//
//    First loop over the requirements and flats to build the totals
//    and init the arrays
//****************************************************************************//
    for ( String req : requirements ) {
      if ( req.equals("") )
        continue;
      reqMatcher.setRequirement(req);
      dataFiltered.setMatcher( reqMatcher );
      reqMap.put( req, new Float[] { customSummer.sumBeds,   0f,
                                     customSummer.sumRowLen, 0f } );
    }
    for ( String flat : flatSizes ) {
      flatMatcher.setFlatSize(flat);
      dataFiltered.setMatcher( flatMatcher );
      flatMap.put( flat, new Float[] { customSummer.sumFlats, 0f } );
    }

listOfFilters.remove(inGHMatcher);
//****************************************************************************//
//    Now loop over the weeks to add it all up.
//****************************************************************************//
    while ( weekNum <= endWeek ) {
      
      //**********************************************************************//
      // flats in gh this week
      //**********************************************************************//
      cal.set( Calendar.WEEK_OF_YEAR, weekNum );
      inGHMatcher.setSeededInTheGreenhouseOn( cal.getTime() );

      listOfFilters.add( inGHMatcher );
      // update the filter? is this needed?
      dataFiltered.setMatcherEditor( andFilter );

      double flats = CPSCalculations.roundQuarter( customSummer.sumFlats );

      chartWeeks.add( CPSDateValidator.format( cal.getTime(),
                                          CPSDateValidator.DATE_FORMAT_SHORT));
      chartValues.add( flats );

      //**********************************************************************//
      // Max requirements and flats
      //**********************************************************crop************//
      boolean update = false;
      listOfFilters.add(flatMatcher);
      for ( String flat: flatSizes ) {
        flatMatcher.setFlatSize(flat);
        Float[] f = flatMap.get(flat);
        if ( customSummer.sumFlats > f[1] ) {
          f[1] = customSummer.sumFlats;
          update = true;
        }
        if ( update ) {
          reqMap.put( flat, f );
          update = false;
        }
      }
      listOfFilters.remove(flatMatcher);
      listOfFilters.remove(inGHMatcher);

      dsInFieldMatcher.setPlantingInTheFieldOn( cal.getTime() );
      tpInFieldMatcher.setPlantingInTheFieldOn( cal.getTime() );
      listOfFilters.add( inFieldFilter );
      listOfFilters.add( reqMatcher );
      dataFiltered.setMatcherEditor( andFilter );
      for ( String req : requirements ) {
        if ( req.equals("") )
          continue;
        reqMatcher.setRequirement(req);
        Float[] f = reqMap.get( req );
        if ( customSummer.sumBeds > f[1] ) {
          f[1] = customSummer.sumBeds;
          update = true;
        }
        if ( customSummer.sumRowLen > f[3] ) {
          f[3] = customSummer.sumRowLen;
          update = true;
        }
        if ( update ) {
          reqMap.put( req, f );
          update = false;
        }
//        if ( req.startsWith("black"))
//          System.out.println("Beds needing " + req +
//                             " this week: " +
//                             CPSCalculations.roundQuarter( customSummer.sumBeds ));
      }
      listOfFilters.remove(reqMatcher);
      listOfFilters.remove( inFieldFilter );


      weekNum++;
    }


    labelInfo = new JLabel( "<html>" + labelString + "</html>" );
    jplBedsCharts = new CPSCardPanel( titles, charts, titles.indexOf( "Beds in use in field" ) );
    jplFlatsChart = new ChartPanel( chartValues, chartWeeks, "Trays in the Greenhouse" );

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
      jplReq.add( new JLabel( "" + CPSCalculations.roundQuarter( reqMap.get(req)[0] ) ));
      jplReq.add( new JLabel( "" + CPSCalculations.roundQuarter( reqMap.get(req)[1] ) ));
      jplReq.add( new JLabel( reqMap.get(req)[2].toString() ));
      jplReq.add( new JLabel( reqMap.get(req)[3].toString() ), "wrap" );
    }


    jplFlats = new JPanel( new MigLayout( "",
                                          "[align right, 15%:][align center, 2%:][align right, 15%:]" ));
    jplFlats.add( new JLabel( "Flat Size" ), "align center" );
    jplFlats.add( new JLabel( "<html><center>" + "Total<br>Flats" + "</center></html>" ), "skip 1" );
    jplFlats.add( new JLabel( "<html><center>" + "Max<br>Flats" + "</center></html>" ), "wrap" );
    jplFlats.add( new JSeparator(), "growx, span, wrap" );

    firstRow = true;
    for ( String flat : flatSizes ) {
      jplFlats.add( new JLabel( flat ) );
      if ( firstRow ) {
        jplFlats.add( new JSeparator( SwingConstants.VERTICAL ), "growy, spany" );
        firstRow = false;
      }
      jplFlats.add( new JLabel( flatMap.get(flat)[0].toString() ));
      jplFlats.add( new JLabel( "" + CPSCalculations.roundQuarter( flatMap.get(flat)[1] )), "wrap" );
    }


    rebuildContentPanel();

//    progBar.setVisible(false);
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

    private Date plantingInTheField;
    private String fieldName;

    @Override
    public boolean matches(CPSPlanting p) {
      boolean m = super.matches(p);

      Calendar c = Calendar.getInstance();
      c.setTime( p.getDateToHarvest() );
      c.add( Calendar.WEEK_OF_YEAR, p.getYieldNumWeeks() );
      c.add( Calendar.DAY_OF_YEAR, 1 );
      Date endOfHarvestWindow = c.getTime();

      // if fieldName blank/Null then only match on blank field name only
      if ( fieldName == null || fieldName.equals("") )
        m &= p.getLocation().equals("");
      else
        m &= p.getLocation().startsWith(fieldName);

      if ( filterMethodTransplant() )
        m &= plantingInTheField.after( bumpDateByDay( p.getDateToTP(), -1 )) &&
             plantingInTheField.before( endOfHarvestWindow );
      else // filterMethodDirectSeed()
        m &= plantingInTheField.after( bumpDateByDay( p.getDateToPlant(), -1 )) &&
             plantingInTheField.before( endOfHarvestWindow );

      return m;
    }

    public void setPlantingInTheFieldOn(Date d) { this.plantingInTheField = d; }
    public void setFieldName(String s) { this.fieldName = s; }


  }


    /**
   * Given a date (through the {@link setPlantingInTheFieldOn( Date d )} method)
   * this will match any planting that has been planted before the date and whose
   * "harvest window" (defined as Harvest Date + Num Weeks of Harvest) has not
   * passed.
   */
  class CPSInTheGreenhouseMatcher extends CPSComplexPlantingFilter {

    private Date seededInTheGreenhouse;

    public CPSInTheGreenhouseMatcher() {
      setAsTransplatedFilter();
    }

    @Override
    public boolean matches(CPSPlanting p) {
      boolean m = super.matches(p);

      m &= seededInTheGreenhouse.after( bumpDateByDay( p.getDateToPlant(), -1 )) &&
           seededInTheGreenhouse.before( p.getDateToTP() );

      return m;
    }

    public void setSeededInTheGreenhouseOn(Date d) {
      this.seededInTheGreenhouse = d;
      fireChanged(this);
    }

  }



  @Deprecated
  class CPSHarvestWindowFilterTooComplicatedDontUse extends CPSComplexPlantingFilter {

    private boolean filterOnHarvestable;
    private Date harvestableDate;

    private boolean filterOnHarvestableRange;
    private Date harvestableRangeStart, harvestableRangeEnd;

    @Override
    public boolean matches(CPSPlanting item) {
      boolean m = super.matches(item);

      if ( filterOnHarvestableDate() ) {

        Calendar c = Calendar.getInstance();
        c.setTime( item.getDateToHarvest() );
        c.add( Calendar.WEEK_OF_YEAR, item.getYieldNumWeeks() );
        c.add( Calendar.DAY_OF_YEAR, 1 );
        Date endOfHarvestWindow = c.getTime();

        if ( getHarvestableDate() != null )
          m &= getHarvestableDate().after( bumpDateByDay( item.getDateToHarvest(), -1 ) ) &&
               getHarvestableDate().before( endOfHarvestWindow );

      }

      /*
       * If something is harvestable between Date A and Date B
       *  - then harvest has to start before B AND end after A
       *
       * So if something is harvestable after Date A
       *  - Date A is the "start of harvestable range"
       *  - then harvest has to end after A
       *
       * And if something is harvestable before Date B
       *  - Date B is the "end of harvestable range"
       *  - then harvest has to start before B
       */

      if ( filterOnHarvestableRange() ) {

        Calendar c = Calendar.getInstance();
        c.setTime( item.getDateToHarvest() );
        c.add( Calendar.WEEK_OF_YEAR, item.getYieldNumWeeks() );
        c.add( Calendar.DAY_OF_YEAR, 1 );
        Date endOfHarvestWindow = c.getTime();

        if ( getHarvestableRangeEnd() == null )
          m &= getHarvestableRangeStart().after( bumpDateByDay( endOfHarvestWindow, -1 ));
        else if ( getHarvestableRangeStart() == null )
          m &= getHarvestableRangeEnd().before( bumpDateByDay( item.getDateToHarvest(), 1 ));

      }

      return m;
    }

    public boolean filterOnHarvestableDate() { return filterOnHarvestable; }
    public void setFilterOnHarvestable(boolean f) {
      this.filterOnHarvestable = f;
    }

    public Date getHarvestableDate() { return harvestableDate; }
    public void setHarvestableOn(Date h) {
      this.harvestableDate = h;
    }

    public boolean filterOnHarvestableRange() { return filterOnHarvestableRange; }
    public void setFilterOnHarvestableRange(boolean filterOnHarvestableRange) {
      this.filterOnHarvestableRange = filterOnHarvestableRange;
    }

    public Date getHarvestableRangeStart() { return harvestableRangeStart; }
    public void setHarvestableRangeStart(Date harvestableRangeStart) {
      this.harvestableRangeStart = harvestableRangeStart;
    }

    public Date getHarvestableRangeEnd() { return harvestableRangeEnd; }
    public void setHarvestableRangeEnd(Date harvestableRangeEnd) {
      this.harvestableRangeEnd = harvestableRangeEnd;
    }



  }


//****************************************************************************//
// Advanced Calculation
//****************************************************************************//
  static final class SumBedsRowftFlats extends AbstractEventListCalculation<Float, CPSPlanting> {

    public float sumBeds, sumRowLen, sumFlats;

    public SumBedsRowftFlats(EventList<CPSPlanting> source) {
        super(new Float(0), source);
        sumBeds = sumRowLen = sumFlats = 0;
    }

    protected void inserted( CPSPlanting p ) {
      sumBeds   += p.getBedsToPlant();
      sumRowLen += p.getRowFtToPlant();
      sumFlats  += p.getFlatsNeeded();
    }

    protected void deleted(CPSPlanting p) {
      sumBeds   -= p.getBedsToPlant();
      sumRowLen -= p.getRowFtToPlant();
      sumFlats  -= p.getFlatsNeeded();
    }

    protected void updated( CPSPlanting oldP, CPSPlanting newP ) {
      sumBeds   = sumBeds   - oldP.getBedsToPlant()  + newP.getBedsToPlant();
      sumRowLen = sumRowLen - oldP.getRowFtToPlant() + newP.getRowFtToPlant();
      sumFlats  = sumFlats  - oldP.getFlatsNeeded()  + newP.getFlatsNeeded();
    }

  }


  static final class FlatMatcher extends CPSComplexPlantingFilter {

    String flatSize = "";

    public FlatMatcher() {
      setViewLimited(true);
    }

    public void setFlatSize( String f ) { 
      flatSize = f;
      fireChanged(this);
    }

    public boolean matches( CPSPlanting item ) {
      return super.matches(item) && item.getFlatSize().equals(flatSize);
    }


  }

  static final class RequirementMatcher extends CPSComplexPlantingFilter {

    String req;

    public RequirementMatcher() {
      setViewLimited(true);
    }

    public void setRequirement( String f ) {
      req = f;
      fireChanged(this);
    }
    
    public boolean matches( CPSPlanting item ) {
      return super.matches(item) && item.getOtherRequirements().contains( req );
    }

  }

}
