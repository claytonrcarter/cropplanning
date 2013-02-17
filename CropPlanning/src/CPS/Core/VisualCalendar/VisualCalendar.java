/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.VisualCalendar;

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
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.calculation.Calculation;
import ca.odell.glazedlists.calculation.Calculations;
import ca.odell.glazedlists.impl.filter.TextMatcher;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author crcarter
 */
public class VisualCalendar extends CPSDisplayableDataUserModule implements ActionListener {

  JButton run;
  private BasicEventList<CPSPlanting> data;
  private SortedList<CPSPlanting> dataSorted;
  private FilterList<CPSPlanting> dataFiltered;

  public VisualCalendar() {
    setModuleName("VisualCalendar");
    setModuleType("Core");
    setModuleVersion( CPSGlobalSettings.getVersion() );
  }

  private void doIt() {

    data = new BasicEventList<CPSPlanting>();
    dataFiltered = new FilterList<CPSPlanting>( data );

    dataSorted = new SortedList<CPSPlanting>( dataFiltered, null );

    FunctionList<CPSPlanting, Float> bedsList =
          new FunctionList<CPSPlanting, Float>( dataFiltered,
                                              new FunctionList.Function<CPSPlanting, Float>() {
                                                 public Float evaluate( CPSPlanting p ) {
                                                    return p.getBedsToPlant();
                                                 }} );

    Calculation<Float> summaryBeds = Calculations.sumFloats( bedsList );



    data.addAll( getDataSource().getCropPlan( getMediator().getCropPlan() ) );

    List<String> fieldNames = getDataSource().getFieldNameList( getMediator().getCropPlan() );


    int sortProp = CPSDataModelConstants.PROP_DATE_PLANT;

    dataSorted.setComparator( new CPSPlantingComparator( CPSDataModelConstants.PROP_DATE_PLANT ));
    System.out.println( "First planting is " + dataSorted.get(0).getCropName() +
                        " on " + dataSorted.get(0).getDateToPlantString() );
    Date first = dataSorted.get(0).getDateToPlant();

    dataSorted.setComparator( new CPSPlantingComparator( CPSDataModelConstants.PROP_DATE_HARVEST ));
    System.out.println( "Last harvest is " + dataSorted.get( dataSorted.size()-1 ).getCropName() +
                        " on " + dataSorted.get( dataSorted.size()-1 ).getDateToHarvestString() );
    Date last = dataSorted.get( dataSorted.size()-1 ).getDateToHarvest();


    CPSComplexPlantingFilter dsFilter = new CPSComplexPlantingFilter();
    CPSComplexPlantingFilter tpFilter = new CPSComplexPlantingFilter();

    // Create the OR filter for DS and TP in the field
    BasicEventList<MatcherEditor<CPSPlanting>> dstpFilters = new BasicEventList<MatcherEditor<CPSPlanting>>();
    dstpFilters.add( dsFilter );
    dstpFilters.add( tpFilter );
    CompositeMatcherEditor<CPSPlanting> dstpFilter =
            new CompositeMatcherEditor<CPSPlanting>( dstpFilters );
    dstpFilter.setMode( CompositeMatcherEditor.OR );

    // Now AND filter the DS/TP filter with the field name
    TextMatcherEditor<CPSPlanting> fieldFilter =
            new TextMatcherEditor<CPSPlanting> (
              new TextFilterator<CPSPlanting>() {

                public void getFilterStrings(List<String> baseList, CPSPlanting element) {
                  baseList.add( element.getLocation() );
                }

              }
            );
    BasicEventList<MatcherEditor<CPSPlanting>> filters = new BasicEventList<MatcherEditor<CPSPlanting>>();
    filters.add( fieldFilter );
    filters.add( dstpFilter );
    CompositeMatcherEditor<CPSPlanting> compositeFilter =
            new CompositeMatcherEditor<CPSPlanting>( filters );
    compositeFilter.setMode( CompositeMatcherEditor.AND );


    Calendar cal = Calendar.getInstance();

    for ( String field : fieldNames ) {

      fieldFilter.setFilterText( new String[] { field } );
      System.out.println("Field name: " + field);

      cal.setTime( first );
      int weekNum = cal.get( Calendar.WEEK_OF_YEAR );
      cal.setTime( last );
      int endWeek = cal.get( Calendar.WEEK_OF_YEAR );
      while ( weekNum <= endWeek ) {

        cal.set( Calendar.WEEK_OF_YEAR, weekNum );

        // add filter for location

        dsFilter.setViewLimited(true);

        dsFilter.setFilterOnPlantingMethod(true);
        dsFilter.setFilterMethodDirectSeed(true);

        // a planting must be planted before this week and not yet harvested
        dsFilter.setFilterOnPlantingDate(true);
        dsFilter.setPlantingRangeEnd( cal.getTime() );

        dsFilter.setFilterOnHarvestDate(true);
        dsFilter.setHarvestRangeStart(cal.getTime());

        tpFilter.setViewLimited(true);

        tpFilter.setFilterOnPlantingMethod(true);
        tpFilter.setFilterMethodDirectSeed(false);

        // a planting must be tp before this week and not yet harvested
        tpFilter.setFilterOnTPDate(true);
        tpFilter.setTpRangeEnd( cal.getTime() );

        tpFilter.setFilterOnHarvestDate(true);
        tpFilter.setHarvestRangeStart(cal.getTime());

        // update the filter?
//        dataFiltered.setMatcherEditor(dstpFilter);
        dataFiltered.setMatcherEditor(compositeFilter);

        System.out.print( CPSDateValidator.format( cal.getTime(),
                                                     CPSDateValidator.DATE_FORMAT_SHORT ) +
                            ", " +
                            dataFiltered.size() +
                            ", " +
                            CPSCalculations.precision2( summaryBeds.getValue() ) +
                            ", " );
        float beds = CPSCalculations.precision2( summaryBeds.getValue() );
        for ( int i = 1; i < beds; i++ )
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

  public JPanel display() {

    JPanel jpl = new JPanel();
    run = new JButton("Run");
    run.addActionListener(this);
    jpl.add(run);
    return jpl;

  }

  public Dimension getSize() {
    return new Dimension(100, 100);
  }

  public void actionPerformed(ActionEvent e) {

    if ( e.getActionCommand().equalsIgnoreCase( run.getActionCommand() ) )
      doIt();

  }



}
