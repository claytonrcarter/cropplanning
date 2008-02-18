/* CPSComplexFilter.java - created: Jan 31, 2008
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

import java.util.Date;

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
 * A general, user defined, free form filter string.
 *
 */
public class CPSComplexPlantingFilter extends CPSComplexFilter {

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
    
    
    public CPSComplexPlantingFilter() {
        super();
        setFilterOnPlanting(false);
        setFilterOnTransplanting(false);
        setFilterOnHarvest(false);
        
        setFilterOnPlantingDate(false);
        setFilterOnTPDate(false);
        setFilterOnHarvest(false);
        
        setViewLimited(false);
    }
    
    public CPSComplexPlantingFilter( String f ) {
        this();
        setFilterString( f );
    }
    
    
    public boolean isViewLimited() { return metaViewLimited; }
    public void setViewLimited( boolean b ) { metaViewLimited = b; }
    
    /*
     * PLANTING METHOD
     */
    public boolean filterOnPlantingMethod() { return filterOnPlantingMeth; }
    public boolean setFilterOnPlantingMethod( boolean b ) { return filterOnPlantingMeth = b; }
    
    public boolean isDirectSeeded() { return directSeeded; }
    public boolean setDirectSeeded( boolean b ) { return directSeeded = b; }
    
    public boolean isTransplanted() { return ! isDirectSeeded(); }
    public boolean setTransplanted( boolean b ) { return setDirectSeeded( ! b ); }
    
    /*
     * PLANTING/TP/HARVEST COMPLETED
     */
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
    

    /*
     * DATES
     */
    public boolean setFilterOnPlantingDate( boolean filterOnPlantingDate ) { return this.filterOnPlantingDate = filterOnPlantingDate; }
    public boolean setFilterOnTPDate( boolean filterOnTPDate ) { return this.filterOnTPDate = filterOnTPDate; }
    public boolean setFilterOnHarvestDate( boolean filterOnHarvestDate ) { return this.filterOnHarvestDate = filterOnHarvestDate; }

    public void setPlantingRangeStart( Date plantingRangeStart ) { this.plantingRangeStart = plantingRangeStart; }
    public void setPlantingRangeEnd( Date plantingRangeEnd ) { this.plantingRageEnd = plantingRangeEnd; }
    public void setTpRangeStart( Date tpRangeStart ) { this.tpRangeStart = tpRangeStart; }
    public void setTpRangeEnd( Date tpRangeEnd ) { this.tpRangeEnd = tpRangeEnd; }
    public void setHarvestDateStart( Date harvestDateStart ) { this.harvestRangeStart = harvestDateStart; }
    public void setHarvestDateEnd( Date harvestDateEnd ) { this.harvestRangeEnd = harvestDateEnd; }

    public boolean filterOnPlantingDate() { return filterOnPlantingDate; }
    public boolean filterOnTPDate() { return filterOnTPDate; }
    public boolean filterOnHarvestDate() { return filterOnHarvestDate; }

    public Date getPlantingRangeStart() { return plantingRangeStart; }
    public Date getPlantingRangeEnd() { return plantingRageEnd; }
    public Date getTpRangeStart() { return tpRangeStart; }
    public Date getTpRangeEnd() { return tpRangeEnd; }
    public Date getHarvestDateStart() { return harvestRangeStart; }
    public Date getHarvestDateEnd() { return harvestRangeEnd; }
    
    
    /*
     * STATIC methods for default "filters"
     * 
     */
    public static CPSComplexPlantingFilter directSeededFilter() {
        CPSComplexPlantingFilter ds = new CPSComplexPlantingFilter();
        ds.setViewLimited(true);
        ds.setFilterOnPlantingMethod(true);
        ds.setDirectSeeded(true);
        return ds;
    }
    
    public static CPSComplexPlantingFilter transplantedFilter() {
        CPSComplexPlantingFilter tp = new CPSComplexPlantingFilter();
        tp.setViewLimited(true);
        tp.setFilterOnPlantingMethod(true);
        tp.setTransplanted(true);
        return tp;
    }
}
