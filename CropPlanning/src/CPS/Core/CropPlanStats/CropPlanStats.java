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
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.FunctionList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.calculation.Calculation;
import ca.odell.glazedlists.calculation.Calculations;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.MatcherEditor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author crcarter
 */
public class CropPlanStats extends CPSDisplayableDataUserModule implements ActionListener {

  JPanel jplContents, jplChart;
  JButton updateButton;
  JLabel labelInfo;
  private BasicEventList<CPSPlanting> dataList;
  private SortedList<CPSPlanting> dataSorted;
  private FilterList<CPSPlanting> dataFiltered;

  public CropPlanStats() {
    setModuleName("VisualCalendar");
    setModuleType("Core");
    setModuleVersion( CPSGlobalSettings.getVersion() );
  }

  private void doIt() {

    Calendar cal = Calendar.getInstance();

//****************************************************************************//
//  Create the lists and setup the sort/filter/function chain
//****************************************************************************//
    dataList = new BasicEventList<CPSPlanting>();
    dataFiltered = new FilterList<CPSPlanting>( dataList );
    dataSorted = new SortedList<CPSPlanting>( dataFiltered, null );
    FunctionList<CPSPlanting, Float> bedsList =
          new FunctionList<CPSPlanting, Float>( dataFiltered,
                                              new FunctionList.Function<CPSPlanting, Float>() {
                                                 public Float evaluate( CPSPlanting p ) {
                                                    return p.getBedsToPlant();
                                                 }} );
    Calculation<Float> summaryBeds = Calculations.sumFloats( bedsList );

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
    Date first = dataSorted.get(0).getDateToPlant();

    // ... and the last
    // this sorts the list based on harvest date + num of weeks to yield
    dataSorted.setComparator( new Comparator<CPSPlanting>() {

                Calendar compareCal = Calendar.getInstance();

                public int compare( CPSPlanting o1, CPSPlanting o2) {
                  Date d1 = o1.getDateToHarvest();
                  Date d2 = o2.getDateToHarvest();

                  compareCal.setTime( d1 );
                  compareCal.add( Calendar.WEEK_OF_YEAR,
                                  o1.getYieldNumWeeks() );
                  d1 = compareCal.getTime();

                  compareCal.setTime( d2 );
                  compareCal.add( Calendar.WEEK_OF_YEAR,
                                  o2.getYieldNumWeeks() );
                  d2 = compareCal.getTime();

                  return d1.compareTo(d2);
                }
              }
            );
    Date last = dataSorted.get( dataSorted.size()-1 ).getDateToHarvest();
    cal.setTime( last );
    cal.add( Calendar.WEEK_OF_YEAR, 
             dataSorted.get( dataSorted.size()-1 ).getYieldNumWeeks() );
    last = cal.getTime();
    labelString += "<br>Last harvest is " + dataSorted.get( dataSorted.size()-1 ).getCropName() +
                        " on " + CPSDateValidator.format( last );


//****************************************************************************//
//    Create the filters depending on how it was planted
//****************************************************************************//
    CPSInTheFieldFilter dsFilter = new CPSInTheFieldFilter();
    dsFilter.setViewLimited(true);
    dsFilter.setFilterOnPlantingMethod(true);
    dsFilter.setFilterMethodDirectSeed(true);

    CPSInTheFieldFilter tpFilter = new CPSInTheFieldFilter();
    tpFilter.setViewLimited(true);
    tpFilter.setFilterOnPlantingMethod(true);
    tpFilter.setFilterMethodDirectSeed(false);

    // put them together as an OR matcher
    BasicEventList<MatcherEditor<CPSPlanting>> dstpFilters =
            new BasicEventList<MatcherEditor<CPSPlanting>>();
    dstpFilters.add( dsFilter );
    dstpFilters.add( tpFilter );
    CompositeMatcherEditor<CPSPlanting> dstpFilter =
            new CompositeMatcherEditor<CPSPlanting>( dstpFilters );
    dstpFilter.setMode( CompositeMatcherEditor.OR );

//****************************************************************************//
//    Loop over the field names
//****************************************************************************//
    fieldNames.clear();
    fieldNames.add("");
    List<String> weeks = new ArrayList<String>();
    List<Float> bedCount = new ArrayList<Float>();
    for ( String field : fieldNames ) {

//      System.out.println("Field name: " + field );
      dsFilter.setFieldName( field );
      tpFilter.setFieldName( field );

      cal.setTime( first );
      int weekNum = cal.get( Calendar.WEEK_OF_YEAR );
      cal.setTime( last );
      int endWeek = cal.get( Calendar.WEEK_OF_YEAR );
      
      while ( weekNum <= endWeek ) {

        cal.set( Calendar.WEEK_OF_YEAR, weekNum );
        dsFilter.setPlantingInTheFieldOn( cal.getTime() );
        tpFilter.setPlantingInTheFieldOn( cal.getTime() );

        // update the filter? is this needed?
        dataFiltered.setMatcherEditor(dstpFilter);


        float beds = CPSCalculations.roundQuarter( summaryBeds.getValue() );

//        weeks.add( )
        System.out.print( CPSDateValidator.format( cal.getTime(),
                                                    CPSDateValidator.DATE_FORMAT_SHORT ) +
                            ", " + dataFiltered.size() + ", " + beds + ", " );
        for ( int i = 1; i <= beds; i++ )
          System.out.print("|");
        System.out.println("");

        weekNum++;
      }
    }

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
      jplContents = new JPanel( new MigLayout( "insets 2px, gap 0px!" ));

    jplContents.removeAll();

    if ( labelInfo == null )
      labelInfo = new JLabel( "Nothing to display; try pressing \"Update\"" );
    jplContents.add( labelInfo, "align left, fill, grow" );

    if ( updateButton == null ) {
      updateButton = new JButton("Update");
      updateButton.addActionListener(this);
    }
    jplContents.add( updateButton, "wrap" );

    if ( jplChart != null )
      jplContents.add( jplChart, "span 2, grow, fill" );
    
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


  /**
   * Given a date (through the {@link setPlantingInTheFieldOn( Date d )} method)
   * this will match any planting that has been planted before the date and whose
   * "harvest window" (defined as Harvest Date + Num Weeks of Harvest) has not
   * passed.
   */
  class CPSInTheFieldFilter extends CPSComplexPlantingFilter {

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

}
