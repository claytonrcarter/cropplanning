/* CPSTextFilter.java - created: Jan 31, 2008
 * Copyright (C) 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package CPS.Data;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.text.JTextComponent;

/**
 * A class to hold/represent information about a "view" or filter of a data
 * set, in this case, of crop plans.  There are 7 aspects to a crop plan filter:
 * 
 * Filtering on "planting status": Has the record been planted, transplanted or 
 * harvested.  Each of these "status" data can be stored as "show only yes",
 * "show only no" and "show all".  (getDoneXXX for the first two, filterOnXXX 
 * for the last.)
 * 
 * Filtering on dates: Does the planting/transplanting/harvest date for a record
 * fall within a certain range.
 *
 * <b>Note</b>: this does not support or represent a free form text filter
 *
 */
public class CPSComplexPlantingFilter extends AbstractMatcherEditor<CPSPlanting> implements Matcher<CPSPlanting> {

    private boolean metaViewLimited;
    
    private boolean filterOnPlanting, donePlanting;
    private boolean filterOnTransplanting, doneTransplanting;
    private boolean filterOnHarvest, doneHarvesting;
    
    private boolean filterOnPlantingMeth, directSeeded;
    
    private boolean filterOnPlantingDate;
    private Date plantingRangeStart, plantingRageEnd;
    
    private boolean filterOnTPDate;
    private Date tpRangeStart, tpRangeEnd;
    
    private boolean filterOnHarvestDate;
    private Date harvestRangeStart, harvestRangeEnd;
    
    private boolean filterOnAnyDate;
    private Date anyDateRangeStart, anyDateRangeEnd;

    public CPSComplexPlantingFilter() {
       
       reset();

    }

    public void reset() {
       setFilterOnPlantingMethod( false );

       setFilterOnPlanting( false );
       setFilterOnTransplanting( false );
       setFilterOnHarvest( false );

       setFilterOnPlantingDate( false );
       setFilterOnTPDate( false );
       setFilterOnHarvestDate( false );
       setFilterOnAnyDate( false );

       setViewLimited( false );
    }

    public void changed() { fireChanged( this ); }

   public MatcherEditor<CPSPlanting> getMatcherEditor() {
      return this;
   }

   @Override
   // TODO ideally this would create a new, immutable matcher each time it's called
   public Matcher getMatcher() {
      return this;
   }

   public boolean matches( CPSPlanting item ) {


      if ( item.getIgnore() )
         return false;

      if ( ! isViewLimited() )
         return true;

      return ( filterOnPlantingMethod() ? (( filterMethodDirectSeed() ? item.isDirectSeeded() : true ) &&
                                           ( filterMethodTransplant() ? item.isTransplanted() : true ))
                                        : true ) &&
             ( filterOnPlanting() ? isDonePlanting() == item.getDonePlanting() : true ) &&
             ( filterOnTransplanting() ? isDoneTransplanting() == item.getDoneTP() : true ) &&
             ( filterOnHarvest() ? isDoneHarvesting() == item.getDoneHarvest() : true );

//           if ( filterOnAnyDate() ) {
//               if      ( getAnyDateRangeEnd() == null )
//                   filterString += " ( date_plant   >= " + escapeValue( getAnyDateRangeStart() ) + " OR " +
//                                   "   date_tp      >= " + escapeValue( getAnyDateRangeStart() ) + " OR " +
//                                   "   date_harvest >= " + escapeValue( getAnyDateRangeStart() ) + " ) ";
//               else if ( getAnyDateRangeStart() == null )
//                   filterString += " ( date_plant   <= " + escapeValue( getAnyDateRangeEnd() ) + " OR " +
//                                   "   date_tp      <= " + escapeValue( getAnyDateRangeEnd() ) + " OR " +
//                                   "   date_harvest <= " + escapeValue( getAnyDateRangeEnd() ) + " ) ";
//               else { // both != null
//                   filterString += " ( date_plant   BETWEEN " + escapeValue( getAnyDateRangeStart() ) + " AND " +
//                                                                escapeValue( getAnyDateRangeEnd() )   + " OR ";
//                   filterString += "   date_tp      BETWEEN " + escapeValue( getAnyDateRangeStart() ) + " AND " +
//                                                                escapeValue( getAnyDateRangeEnd() )   + " OR ";
//                   filterString += "   date_harvest BETWEEN " + escapeValue( getAnyDateRangeStart() ) + " AND " +
//                                                                escapeValue( getAnyDateRangeEnd() )   + " ) ";
//               }
//              filterString += " AND ";
//           }
//           else {
//              if ( filterOnPlantingDate() ) {
//                 if ( getPlantingRangeEnd() == null )
//                    filterString += "date_plant >= " + escapeValue( getPlantingRangeStart() );
//                 else if ( getPlantingRangeStart() == null )
//                    filterString += "date_plant <= " + escapeValue( getPlantingRangeEnd() );
//                 else // both != null
//                    filterString += "date_plant BETWEEN " + escapeValue( getPlantingRangeStart() ) + " AND " +
//                                                            escapeValue( getPlantingRangeEnd() );
//                 filterString += " AND ";
//              }
//
//              if ( filterOnTPDate() ) {
//                 if ( getTpRangeEnd() == null )
//                    filterString += "date_tp >= " + escapeValue( getTpRangeStart() );
//                 else if ( getTpRangeStart() == null )
//                    filterString += "date_tp <= " + escapeValue( getTpRangeEnd() );
//                 else // both != null
//                    filterString += "date_tp BETWEEN " + escapeValue( getTpRangeStart() ) + " AND " +
//                                                         escapeValue( getTpRangeEnd() );
//                 filterString += " AND ";
//              }
//
//              if ( filterOnHarvestDate() ) {
//                 if ( getHarvestDateEnd() == null )
//                    filterString += "date_harvest >= " + escapeValue( getHarvestDateStart() );
//                 else if ( getHarvestDateStart() == null )
//                    filterString += "date_harvest <= " + escapeValue( getHarvestDateEnd() );
//                 else // both != null
//                    filterString += "date_harvest BETWEEN " + escapeValue( getHarvestDateStart() ) + " AND " +
//                                                              escapeValue( getHarvestDateEnd() );
//                 filterString += " AND ";
//              }
   }



    public boolean isViewLimited() { return metaViewLimited; }
    public void setViewLimited( boolean b ) { metaViewLimited = b; }

    /* ***************************************************************** */
    /* PLANTING METHOD
    /* ***************************************************************** */
    public boolean filterOnPlantingMethod() { return filterOnPlantingMeth; }
    public boolean setFilterOnPlantingMethod( boolean b ) { return filterOnPlantingMeth = b; }
    
    public boolean filterMethodDirectSeed() { return directSeeded; }
    public boolean setFilterMethodDirectSeed( boolean b ) { return directSeeded = b; }
    
    public boolean filterMethodTransplant() { return ! filterMethodDirectSeed(); }
    public boolean setFilterMethodTransplant( boolean b ) { return setFilterMethodDirectSeed( ! b ); }
    
    /* ***************************************************************** */
    /* PLANTING/TP/HARVEST COMPLETED
    /* ***************************************************************** */
    public boolean setFilterOnPlanting( boolean filterOnPlanting ) { return this.filterOnPlanting = filterOnPlanting; }
    public boolean setFilterOnTransplanting( boolean filterOnTransplanting ) { return this.filterOnTransplanting = filterOnTransplanting; }
    public boolean setFilterOnHarvest( boolean filterOnHarvest ) { return this.filterOnHarvest = filterOnHarvest; }
    
    public boolean filterOnPlanting() { return filterOnPlanting; }
    public boolean filterOnTransplanting() { return filterOnTransplanting; }
    public boolean filterOnHarvest() { return filterOnHarvest; }
    
    public void setDonePlanting( boolean donePlanting ) { this.donePlanting = donePlanting; }
    public void setDoneTransplanting( boolean doneTransplanting ) { this.doneTransplanting = doneTransplanting; }
    public void setDoneHarvesting( boolean doneHarvesting ) { this.doneHarvesting = doneHarvesting; }
    
    public boolean isDonePlanting() { return donePlanting; }
    public boolean isDoneTransplanting() { return doneTransplanting; }
    public boolean isDoneHarvesting() { return doneHarvesting; }
    

    /* ***************************************************************** */
    /* DATES
    /* ***************************************************************** */
    public boolean setFilterOnPlantingDate( boolean filterOnPlantingDate ) { return this.filterOnPlantingDate = filterOnPlantingDate; }
    public boolean setFilterOnTPDate( boolean filterOnTPDate ) { return this.filterOnTPDate = filterOnTPDate; }
    public boolean setFilterOnHarvestDate( boolean filterOnHarvestDate ) { return this.filterOnHarvestDate = filterOnHarvestDate; }
    public boolean setFilterOnAnyDate( boolean filterOnAnyDate ) { return this.filterOnAnyDate = filterOnAnyDate; }
    
    public void setPlantingRangeStart( Date plantingRangeStart ) { this.plantingRangeStart = plantingRangeStart; }
    public void setPlantingRangeEnd( Date plantingRangeEnd ) { this.plantingRageEnd = plantingRangeEnd; }
    public void setTpRangeStart( Date tpRangeStart ) { this.tpRangeStart = tpRangeStart; }
    public void setTpRangeEnd( Date tpRangeEnd ) { this.tpRangeEnd = tpRangeEnd; }
    public void setHarvestDateStart( Date harvestDateStart ) { this.harvestRangeStart = harvestDateStart; }
    public void setHarvestDateEnd( Date harvestDateEnd ) { this.harvestRangeEnd = harvestDateEnd; }
    public void setAnyDateRangeStart( Date anyDateRangeStart ) { this.anyDateRangeStart = anyDateRangeStart; }
    public void setAnyDateRangeEnd( Date anyDateRangeEnd ) { this.anyDateRangeEnd = anyDateRangeEnd; }

    public boolean filterOnPlantingDate() { return filterOnPlantingDate; }
    public boolean filterOnTPDate() { return filterOnTPDate; }
    public boolean filterOnHarvestDate() { return filterOnHarvestDate; }
    public boolean filterOnAnyDate() { return filterOnAnyDate; }

    public Date getPlantingRangeStart() { return plantingRangeStart; }
    public Date getPlantingRangeEnd() { return plantingRageEnd; }
    public Date getTpRangeStart() { return tpRangeStart; }
    public Date getTpRangeEnd() { return tpRangeEnd; }
    public Date getHarvestDateStart() { return harvestRangeStart; }
    public Date getHarvestDateEnd() { return harvestRangeEnd; }
    public Date getAnyDateRangeStart() { return anyDateRangeStart; }
    public Date getAnyDateRangeEnd() { return anyDateRangeEnd; }


    /* ***************************************************************** */
    /* methods to alter the filter in place
    /* ***************************************************************** */
    public void setAsDirectSeededFilter() {
       reset();
       setViewLimited(true);
       setFilterOnPlantingMethod(true);
       setFilterMethodDirectSeed(true);
       changed();
    }

    public void setAsTransplatedFilter() {
       reset();
       setViewLimited(true);
       setFilterOnPlantingMethod( true );
       setFilterMethodTransplant(true);
       changed();
    }

    public void setAsAllNotPlantedFilter() {
       reset();
       setViewLimited(true);
       setFilterOnPlanting(true);
       setDonePlanting(false);
       changed();
    }

    public void setAsDSNotPlantedFilter() {
       reset();
       setAsAllNotPlantedFilter();

       setFilterOnPlantingMethod(true);
       setFilterMethodDirectSeed(true);
       changed();
    }

    public void setAsTPNotSeededFilter() {
       reset();
       setAsAllNotPlantedFilter();

       setFilterOnPlantingMethod(true);
       setFilterMethodTransplant(true);
       changed();
    }

    public void setAsTPSeededNotPlantedFilter() {
       reset();
       setAsTPNotSeededFilter();

       setFilterOnPlanting(true);
       setDonePlanting(true);

       setFilterOnTransplanting(true);
       setDoneTransplanting(false);
       changed();
    }

    public void setAsThisWeekFilter() {
       reset();

       GregorianCalendar temp = new GregorianCalendar();
       temp.setTime( new Date() );

       setViewLimited(true);
       setFilterOnAnyDate(true);

       temp.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY );
       setAnyDateRangeStart( temp.getTime() );

       temp.set( Calendar.DAY_OF_WEEK, Calendar.SATURDAY );
       setAnyDateRangeEnd( temp.getTime() );
       changed();
    }

    public void setAsNextWeekFilter() {
       reset();
       setAsThisWeekFilter();

       GregorianCalendar temp = new GregorianCalendar();
       temp.setTime( new Date() );

       // start day should be this Sunday ...
       temp.setTime( getAnyDateRangeStart() );
       // ... so we add a week
       temp.add( GregorianCalendar.WEEK_OF_YEAR, 1 );
       setAnyDateRangeStart( temp.getTime() );

       temp.set( GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.SATURDAY );
       setAnyDateRangeEnd( temp.getTime() );
       changed();
    }

    public void setAsThisAndNextWeekFilter() {
       reset();
       setAsThisWeekFilter();

       GregorianCalendar temp = new GregorianCalendar();

       // leave start date alone, since it should be OK already
       // end day should be this Saturday ...
       temp.setTime( getAnyDateRangeEnd() );
       // ... so we add a week
       temp.add( GregorianCalendar.WEEK_OF_YEAR, 1 );
       setAnyDateRangeEnd( temp.getTime() );
       changed();
   }
    
    /* ***************************************************************** */
    /* STATIC factory methods for default "filters"
    /* ***************************************************************** */
    public static CPSComplexPlantingFilter directSeededFilter() {
        CPSComplexPlantingFilter ds = new CPSComplexPlantingFilter();
        ds.setAsDirectSeededFilter();
        return ds;
    }
    
    public static CPSComplexPlantingFilter transplantedFilter() {
        CPSComplexPlantingFilter tp = new CPSComplexPlantingFilter();
        tp.setAsTransplatedFilter();
        return tp;
    }
    
    public static CPSComplexPlantingFilter allNotPlantedFilter() {
       CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
       f.setAsAllNotPlantedFilter();
       return f;
    }
    
    public static CPSComplexPlantingFilter DSNotPlantedFilter() {
       CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
       f.setAsDSNotPlantedFilter();
       return f;
    }
    
    public static CPSComplexPlantingFilter TPNotSeededFilter() {
       CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
       f.setAsTPNotSeededFilter();
       return f;
    }
    
    public static CPSComplexPlantingFilter TPSeededNotPlantedFilter() {
       CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
       f.setAsTPSeededNotPlantedFilter();
       return f;
    }
    
    public static CPSComplexPlantingFilter thisWeekFilter() {
       CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
       f.setAsThisWeekFilter();
       return f;
    }
    
    public static CPSComplexPlantingFilter nextWeekFilter() {
       CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
       f.setAsNextWeekFilter();
       return f;
    }
    
    public static CPSComplexPlantingFilter thisAndNextWeekFilter() {
       CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
       f.setAsThisAndNextWeekFilter();
       return f;
    }
    
}
