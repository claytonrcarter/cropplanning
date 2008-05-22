/* CPSCrop.java 
 * Copyright (C) 2007, 2008 Clayton Carter
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
 */

package CPS.Data;

import CPS.Data.CPSDatum.CPSDatumState;
import CPS.Module.CPSDataModelConstants;
import java.util.*;

/**
 * Crop - data structure to hold information about a crop
 *        NOT for a planting of a crop, but just for the crop
 */
public class CPSCrop extends CPSRecord {

   public final int PROP_CROP_NAME = CPSDataModelConstants.PROP_CROP_NAME;
   public final int PROP_CROP_DESC = CPSDataModelConstants.PROP_CROP_DESC;
   public final int PROP_VAR_NAME = CPSDataModelConstants.PROP_VAR_NAME;
   public final int PROP_FAM_NAME = CPSDataModelConstants.PROP_FAM_NAME;
   public final int PROP_BOT_NAME = CPSDataModelConstants.PROP_BOT_NAME;

   public final int PROP_MATURITY = CPSDataModelConstants.PROP_MATURITY;
   public final int PROP_GROUPS = CPSDataModelConstants.PROP_GROUPS;
   public final int PROP_KEYWORDS = CPSDataModelConstants.PROP_KEYWORDS;
   public final int PROP_OTHER_REQ = CPSDataModelConstants.PROP_OTHER_REQ;
   public final int PROP_NOTES = CPSDataModelConstants.PROP_NOTES;

   public final int PROP_FROST_HARDY = CPSDataModelConstants.PROP_FROST_HARDY;
   
   public final int PROP_DIRECT_SEED = CPSDataModelConstants.PROP_DIRECT_SEED;
   public final int PROP_DS_MAT_ADJUST = CPSDataModelConstants.PROP_DS_MAT_ADJUST;
   public final int PROP_DS_ROWS_P_BED = CPSDataModelConstants.PROP_DS_ROWS_P_BED;
   public final int PROP_DS_SPACE_BETROW = CPSDataModelConstants.PROP_DS_SPACE_BETROW;
   public final int PROP_DS_PLANT_NOTES = CPSDataModelConstants.PROP_DS_PLANT_NOTES;
   
   public final int PROP_TRANSPLANT = CPSDataModelConstants.PROP_TRANSPLANT;
   public final int PROP_TP_MAT_ADJUST = CPSDataModelConstants.PROP_TP_MAT_ADJUST;
   public final int PROP_TP_ROWS_BED = CPSDataModelConstants.PROP_TP_ROWS_P_BED;
   public final int PROP_TP_SPACE_BETROW = CPSDataModelConstants.PROP_TP_SPACE_BETROW;
   public final int PROP_TP_SPACE_INROW = CPSDataModelConstants.PROP_TP_SPACE_INROW;
   public final int PROP_TP_TIME_IN_GH = CPSDataModelConstants.PROP_TP_TIME_IN_GH;
   public final int PROP_FLAT_SIZE = CPSDataModelConstants.PROP_FLAT_SIZE;
   public final int PROP_TP_PLANT_NOTES = CPSDataModelConstants.PROP_TP_PLANT_NOTES;
   public final int PROP_POT_UP = CPSDataModelConstants.PROP_TP_POT_UP;
   public final int PROP_POT_UP_NOTES = CPSDataModelConstants.PROP_TP_POT_UP_NOTES;
   
   public final int PROP_YIELD_FOOT = CPSDataModelConstants.PROP_YIELD_P_FOOT;
   public final int PROP_YIELD_WEEKS = CPSDataModelConstants.PROP_YIELD_NUM_WEEKS;
   public final int PROP_YIELD_PER_WEEK = CPSDataModelConstants.PROP_YIELD_P_WEEK;
   public final int PROP_CROP_UNIT = CPSDataModelConstants.PROP_CROP_UNIT;
   public final int PROP_CROP_UNIT_VALUE = CPSDataModelConstants.PROP_CROP_UNIT_VALUE;
   
   /** 
    * from CPSDataModelConstants: this is the highest value defined there
    * @return the last (ie highest) property supported by this CPSRecords iterator
    */
   protected int lastValidProperty() { return PROP_YIELD_PER_WEEK; }
   
   private CPSDatum<String> cropName;
   private CPSDatum<String> cropDescription;
   private CPSDatum<String> varName;    
   private CPSDatum<String> familyName;
   private CPSDatum<String> botanicalName;

   // private CPSDatum<Boolean> successions;
   private CPSDatum<Integer> maturityDays;    
   
   private CPSDatum<String> groups;
   private CPSDatum<String> keywords;
   private CPSDatum<String> otherRequirements;
   private CPSDatum<String> notes;
   
   private CPSDatum<CPSBoolean> frostHardy;
   
   private CPSDatum<CPSBoolean> directSeed;
   private CPSDatum<Integer> dsMatAdjust;
   private CPSDatum<Integer> dsRowsPerBed;
   private CPSDatum<Integer> dsRowSpace;
   private CPSDatum<String>  dsPlantNotes;
   
   private CPSDatum<CPSBoolean> transplant;
   private CPSDatum<Integer> tpMatAdjust;
   private CPSDatum<Integer> tpRowsPerBed;
   private CPSDatum<Integer> tpRowSpace;
   private CPSDatum<Integer> tpInRowSpace;
   private CPSDatum<Integer> tpTimeInGH;
   private CPSDatum<String> tpFlatSize;
   private CPSDatum<String> tpPlantNotes;
   private CPSDatum<CPSBoolean> tpPotUp;
   private CPSDatum<String> tpPotUpNotes;
   
   private CPSDatum<Float> yieldPerFoot;
   private CPSDatum<Integer> yieldNumWeeks;
   private CPSDatum<Integer> yieldPerWeek;
   private CPSDatum<String> cropYieldUnit;
   private CPSDatum<Float> cropUnitValue;
   

   /**
    * CPSCrop - create a new, blank CPSCrop object
    */
   public CPSCrop() {
       
       setRepresentsSingleRecord();
       recordID = new CPSDatum<Integer>( "Unique ID", PROP_ID, new Integer(-1) );
       commonIDs = new CPSDatum<ArrayList<Integer>>( "Crop IDs represented", PROP_COMMON_ID, new ArrayList() );
       
       cropName = new CPSDatum<String>( "Crop name", PROP_CROP_NAME, "" );
       cropDescription = new CPSDatum<String>( "Crop description", PROP_CROP_DESC, "" );
       varName = new CPSDatum<String>( "Variety", PROP_VAR_NAME, "" );
       familyName = new CPSDatum<String>( "Family name", PROP_FAM_NAME, "" );
       botanicalName = new CPSDatum<String>( "Botanical name", PROP_BOT_NAME, "" );
        
       maturityDays = new CPSDatum<Integer>( "Days to maturity", PROP_MATURITY, new Integer(-1) );
        
       groups = new CPSDatum<String>( "Groups", PROP_GROUPS, "" );
       keywords = new CPSDatum<String>( "Keywords", PROP_KEYWORDS, "" );
       otherRequirements = new CPSDatum<String>( "Other Requirements", PROP_OTHER_REQ, "" );
       notes = new CPSDatum<String>( "Notes", PROP_NOTES, "" );
        
       frostHardy = new CPSDatum<CPSBoolean>( "Frost hardy?", PROP_FROST_HARDY, new CPSBoolean( null ) );
       
       directSeed = new CPSDatum<CPSBoolean>( "Direct Seeded", PROP_DIRECT_SEED, new CPSBoolean( null ) );
       dsMatAdjust = new CPSDatum<Integer>( "Adjust Mat. (DS)", PROP_DS_MAT_ADJUST, new Integer(-1));
       dsRowsPerBed = new CPSDatum<Integer>( "Rows/Bed (DS)", PROP_DS_ROWS_P_BED, new Integer(-1)  );
       dsRowSpace = new CPSDatum<Integer>( "Row Spacing (DS)", PROP_DS_SPACE_BETROW, new Integer(-1));
       dsPlantNotes = new CPSDatum<String>( "Notes for DS", PROP_DS_PLANT_NOTES, ""  );

       transplant = new CPSDatum<CPSBoolean>( "Transplant", PROP_TRANSPLANT, new CPSBoolean( null ) );
       tpMatAdjust = new CPSDatum<Integer>( "Adjust Mat. (TP)", PROP_TP_MAT_ADJUST, new Integer(-1));
       tpRowsPerBed = new CPSDatum<Integer>( "Rows/Bed (TP)", PROP_TP_ROWS_BED, new Integer(-1)  );
       tpRowSpace = new CPSDatum<Integer>( "Row Spacing (TP)", PROP_TP_SPACE_BETROW, new Integer(-1));
       tpInRowSpace = new CPSDatum<Integer>( "In-Row Spacing (TP)", PROP_TP_SPACE_INROW, new Integer(-1)  );
       tpTimeInGH = new CPSDatum<Integer>( "Time to TP", PROP_TP_TIME_IN_GH, new Integer(-1)  );
       tpFlatSize = new CPSDatum<String>( "Flat Size", PROP_FLAT_SIZE, "" );
       tpPlantNotes = new CPSDatum<String>( "Notes for TP", PROP_TP_PLANT_NOTES, ""  );
       tpPotUp = new CPSDatum<CPSBoolean>( "Pot Up", PROP_POT_UP, new CPSBoolean( null ) );
       tpPotUpNotes = new CPSDatum<String>( "Notes for Potting Up", PROP_POT_UP_NOTES, "" );
       
       yieldPerFoot = new CPSDatum<Float>( "Yield/Foot", PROP_YIELD_FOOT, new Float(-1.0) );
       yieldNumWeeks = new CPSDatum<Integer>( "Yield for Weeks", PROP_YIELD_WEEKS, new Integer(-1) );
       yieldPerWeek = new CPSDatum<Integer>( "Yield/Week", PROP_YIELD_PER_WEEK, new Integer(-1) );
       cropYieldUnit = new CPSDatum<String>( "Yield Unit", PROP_CROP_UNIT, "" );
       cropUnitValue = new CPSDatum<Float>( "Value/Unit", PROP_CROP_UNIT_VALUE, new Float(-1.0) );       
        
    }

    protected CPSDatum getDatum( int prop ) {
       
       /* very ugly, but this allows us to use the hierarchy of Crop properties */
       switch ( prop ) {
          case PROP_CROP_NAME:       return cropName;
          case PROP_CROP_DESC:       return cropDescription;
          case PROP_VAR_NAME:        return varName;
          case PROP_FAM_NAME:        return familyName;
          case PROP_BOT_NAME:        return botanicalName;
          
          case PROP_MATURITY:        return maturityDays;
          
          case PROP_GROUPS:          return groups;
          case PROP_KEYWORDS:        return keywords;
          case PROP_OTHER_REQ:       return otherRequirements;
          case PROP_NOTES:           return notes;
          
          case PROP_FROST_HARDY:     return frostHardy;
          
          case PROP_DIRECT_SEED:     return directSeed;
          case PROP_DS_MAT_ADJUST:   return dsMatAdjust;
          case PROP_DS_ROWS_P_BED:   return dsRowsPerBed;
          case PROP_DS_SPACE_BETROW: return dsRowSpace;
          case PROP_DS_PLANT_NOTES:  return dsPlantNotes;
          
          case PROP_TRANSPLANT:      return transplant;
          case PROP_TP_MAT_ADJUST:   return tpMatAdjust;
          case PROP_TP_ROWS_BED:     return tpRowsPerBed;
          case PROP_TP_SPACE_BETROW: return tpRowSpace;
          case PROP_TP_SPACE_INROW:  return tpInRowSpace;
          case PROP_TP_TIME_IN_GH:   return tpTimeInGH;
          case PROP_FLAT_SIZE:       return tpFlatSize;
          case PROP_TP_PLANT_NOTES:  return tpPlantNotes;
          case PROP_POT_UP:          return tpPotUp;
          case PROP_POT_UP_NOTES:    return tpPotUpNotes;
          
          case PROP_YIELD_FOOT:      return yieldPerFoot;
          case PROP_YIELD_WEEKS:     return yieldNumWeeks;
          case PROP_YIELD_PER_WEEK:  return yieldPerWeek;
          case PROP_CROP_UNIT:       return cropYieldUnit;
          case PROP_CROP_UNIT_VALUE: return cropUnitValue;
          
          default:
             return null;
       }

    }

    public ArrayList<Integer> getListOfInheritableProperties() {
       ArrayList<Integer> a = new ArrayList<Integer>();

       a.add( PROP_VAR_NAME );
       a.add( PROP_FAM_NAME );
       a.add( PROP_BOT_NAME );
       a.add( PROP_MATURITY );
       a.add( PROP_GROUPS );
       a.add( PROP_OTHER_REQ );
       a.add( PROP_FROST_HARDY );
       
       a.add( PROP_DIRECT_SEED );
       a.add( PROP_DS_MAT_ADJUST  );
       a.add( PROP_DS_ROWS_P_BED  );
       a.add( PROP_DS_SPACE_BETROW  );
       a.add( PROP_DS_PLANT_NOTES  );
       
       a.add( PROP_TRANSPLANT );
       a.add( PROP_TP_MAT_ADJUST  );
       a.add( PROP_TP_ROWS_BED  );
       a.add( PROP_TP_SPACE_BETROW  );
       a.add( PROP_TP_SPACE_INROW  );
       a.add( PROP_TP_TIME_IN_GH  );
       a.add( PROP_FLAT_SIZE );
       a.add( PROP_TP_PLANT_NOTES  );
       a.add( PROP_POT_UP );
       a.add( PROP_POT_UP_NOTES );
       
       a.add( PROP_YIELD_FOOT );
       a.add( PROP_YIELD_WEEKS );
       a.add( PROP_YIELD_PER_WEEK );
       a.add( PROP_CROP_UNIT );
       a.add( PROP_CROP_UNIT_VALUE );

       return a;
   }
    
    public String getCropName() { return get( PROP_CROP_NAME ); }
    public CPSDatumState getCropNameState() { return getStateOf( PROP_CROP_NAME ); }
    public void setCropName( String s ) { setCropName( s, false ); }
    public void setCropName( String s, boolean force  ) { set( cropName, s, force ); }

    public String getCropDescription() { return get( PROP_CROP_DESC ); }
    public CPSDatumState getCropDescriptionState() { return getStateOf( PROP_CROP_DESC ); }
    public void setCropDescription( String s ) { setCropDescription( s, false ); }
    public void setCropDescription( String s, boolean force  ) { set( cropDescription, s, force ); }

    public String getVarietyName() { return get( PROP_VAR_NAME ); }
    public CPSDatumState getVarietyNameState() { return getStateOf( PROP_VAR_NAME ); }
    public void setVarietyName( String s ) { setVarietyName( s, false ); }
    public void setVarietyName( String s, boolean force ) { set( varName, s, force ); }
   
    public String getFamilyName() { return get( PROP_FAM_NAME ); }
    public CPSDatumState getFamilyNameState() { return getStateOf( PROP_FAM_NAME ); }
    public void setFamilyName( String e) { setFamilyName( e, false ); }
    public void setFamilyName( String e, boolean force ) { set( familyName, e, force ); }

    public String getBotanicalName() { return get( PROP_BOT_NAME ); }
    public CPSDatumState getBotanicalNameState() { return getStateOf( PROP_BOT_NAME ); }
    public void setBotanicalName( String e) { setBotanicalName( e, false ); }
    public void setBotanicalName( String e, boolean force ) { set( botanicalName, e, force ); }

    public int getMaturityDays() { return getInt( PROP_MATURITY ); }
    public String getMaturityDaysString() { return formatInt( getMaturityDays() ); }
    public CPSDatumState getMaturityDaysState() { return getStateOf( PROP_MATURITY ); }
    public void setMaturityDays( int i ) { setMaturityDays( i, false ); }
    public void setMaturityDays( int i, boolean force ) {
       set( maturityDays, new Integer( i ), force );
    }
    public void setMaturityDays( String s, boolean force ) {
       setMaturityDays( parseInt( s ), force );
    }
    public void setMaturityDays( String s ) { setMaturityDays( s, false ); }

    public String getGroups() { return get( PROP_GROUPS ); }
    public CPSDatumState getGroupsState() { return getStateOf( PROP_GROUPS ); }
    public void setGroups( String e) { setGroups( e, false ); }
    public void setGroups( String e, boolean force ) { set( groups, e, force ); }
    
    public String getKeywords() { return get( PROP_KEYWORDS ); }
    public CPSDatumState getKeywordsState() { return getStateOf( PROP_KEYWORDS ); }
    public void setKeywords( String e) { setKeywords( e, false ); }
    public void setKeywords( String e, boolean force ) { set( keywords, e, force ); }

    public String getNotes() { return get( PROP_NOTES ); }
    public CPSDatumState getNotesState() { return getStateOf( PROP_NOTES ); }
    public void setNotes( String e) { setNotes( e, false ); }
    public void setNotes( String e, boolean force ) { set( notes, e, force ); }

    public String getOtherRequirments() { return get( PROP_OTHER_REQ ); }
    public CPSDatumState getOtherRequirmentsState() { return getStateOf( PROP_OTHER_REQ ); }
    public void setOtherRequirements( String e) { setOtherRequirements( e, false ); }
    public void setOtherRequirements( String e, boolean force ) {
      set( otherRequirements, e, force );
    }
    
    public boolean isFrostHardy() { return get( PROP_FROST_HARDY, new CPSBoolean(false)).booleanValue(); } 
    public boolean isFrostTender() { return ! isFrostHardy(); }
    public CPSDatumState getFrostHardyState() { return getStateOf( PROP_FROST_HARDY ); }   
    public void setFrostHardy( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setFrostHardy( true );
       else
          setFrostHardy( false );
    }
    public void setFrostHardy( Boolean b ) { setFrostHardy( b, false ); }
    public void setFrostHardy( Boolean b, boolean force ) { set( frostHardy, new CPSBoolean(b), force ); }
    
    /*
     * DIRECT SEEDING STATS
     */
    public boolean isDirectSeeded() { return get( PROP_DIRECT_SEED, new CPSBoolean(null)).booleanValue(); } 
    public CPSDatumState getDirectSeededState() { return getStateOf( PROP_DIRECT_SEED ); }   
    public void setDirectSeeded( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setDirectSeeded( true );
       else
          setDirectSeeded( false );
    }
    public void setDirectSeeded( Boolean b ) { setDirectSeeded( b, false ); }
    public void setDirectSeeded( Boolean b, boolean force ) { set( directSeed, new CPSBoolean(b), force ); }
    
    public int getDSMaturityAdjust() { return getInt( PROP_DS_MAT_ADJUST  ); }
    public String getDSMaturityAdjustString() { return formatInt( getDSMaturityAdjust() ); }
    public CPSDatumState getDSMaturityAdjustState() { return getStateOf( PROP_DS_MAT_ADJUST  ); }
    public void setDSMaturityAdjust( int i ) { setDSMaturityAdjust( i, false ); }
    public void setDSMaturityAdjust( String s ) { setDSMaturityAdjust( s, false ); }
    public void setDSMaturityAdjust( String s, boolean force ) { setDSMaturityAdjust( parseInt(s), force ); }
    public void setDSMaturityAdjust( int i, boolean force ) { set( dsMatAdjust, i, force ); }
   
    public int getDSRowsPerBed() { return getInt( PROP_DS_ROWS_P_BED  ); }
    public String getDSRowsPerBedString() { return formatInt( getDSRowsPerBed() ); }
    public CPSDatumState getDSRowsPerBedState() { return getStateOf( PROP_DS_ROWS_P_BED  ); }
    public void setDSRowsPerBed( int i ) { setDSRowsPerBed( i, false ); }
    public void setDSRowsPerBed( String s ) { setTPRowsPerBed( s, false ); }
    public void setDSRowsPerBed( String s, boolean force ) { setDSRowsPerBed( parseInt(s), force ); }
    public void setDSRowsPerBed( int i, boolean force ) { set( dsRowsPerBed, i, force ); }

    public int getDSSpaceBetweenRow() { return getInt( PROP_DS_SPACE_BETROW  ); }
    public String getDSSpaceBetweenRowString() { return formatInt( getDSSpaceBetweenRow() ); }
    public CPSDatumState getDSSpaceBetweenRowState() { return getStateOf( PROP_DS_SPACE_BETROW  ); }
    public void setDSSpaceBetweenRow( int i ) { setDSSpaceBetweenRow( i, false ); }
    public void setDSSpaceBetweenRow( String s ) { setDSSpaceBetweenRow( s, false ); }
    public void setDSSpaceBetweenRow( String s, boolean force ) { setDSSpaceBetweenRow( parseInt(s), force ); }
    public void setDSSpaceBetweenRow( int i, boolean force ) { set( dsRowSpace, i, force ); }
   
    public String getDSPlantNotes() { return get( PROP_DS_PLANT_NOTES  ); }
    public CPSDatumState getDSPlantNotesState() { return getStateOf( PROP_DS_PLANT_NOTES  ); }
    public void setDSPlantNotes( String s ) { setDSPlantNotes( s, false ); }
    public void setDSPlantNotes( String s, boolean force ) { set( dsPlantNotes, s, force ); }
    
    /* 
     * TRANSPLANT STATS
     */
    public boolean isTransplanted() { return get( PROP_TRANSPLANT, new CPSBoolean(null)).booleanValue(); } 
    public CPSDatumState getTransplantedState() { return getStateOf( PROP_TRANSPLANT ); }   
    public void setTransplanted( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setTransplanted( true );
       else
          setTransplanted( false );
    }
    public void setTransplanted( Boolean b ) { setTransplanted( b, false ); }
    public void setTransplanted( Boolean b, boolean force ) { set( transplant, new CPSBoolean(b), force ); }

    public int getTPMaturityAdjust() { return getInt( PROP_TP_MAT_ADJUST  ); }
    public String getTPMaturityAdjustString() { return formatInt( getTPMaturityAdjust() ); }
    public CPSDatumState getTPMaturityAdjustState() { return getStateOf( PROP_TP_MAT_ADJUST  ); }
    public void setTPMaturityAdjust( int i ) { setTPMaturityAdjust( i, false ); }
    public void setTPMaturityAdjust( String s ) { setTPMaturityAdjust( s, false ); }
    public void setTPMaturityAdjust( String s, boolean force ) { setTPMaturityAdjust( parseInt(s), force ); }
    public void setTPMaturityAdjust( int i, boolean force ) { set( tpMatAdjust, i, force ); }
   
    public int getTPRowsPerBed() { return getInt( PROP_TP_ROWS_BED  ); }
    public String getTPRowsPerBedString() { return formatInt( getTPRowsPerBed() ); }
    public CPSDatumState getTPRowsPerBedState() { return getStateOf( PROP_TP_ROWS_BED  ); }
    public void setTPRowsPerBed( int i ) { setTPRowsPerBed( i, false ); }
    public void setTPRowsPerBed( String s ) { setTPRowsPerBed( s, false ); }
    public void setTPRowsPerBed( String s, boolean force ) { setTPRowsPerBed( parseInt(s), force ); }
    public void setTPRowsPerBed( int i, boolean force ) { set( tpRowsPerBed, i, force ); }
   
    public int getTPSpaceBetweenRow() { return getInt( PROP_TP_SPACE_BETROW  ); }
    public String getTPSpaceBetweenRowString() { return formatInt( getTPSpaceBetweenRow() ); }
    public CPSDatumState getTPSpaceBetweenRowState() { return getStateOf( PROP_TP_SPACE_BETROW  ); }
    public void setTPSpaceBetweenRow( int i ) { setTPSpaceBetweenRow( i, false ); }
    public void setTPSpaceBetweenRow( String s ) { setTPSpaceBetweenRow( s, false ); }
    public void setTPSpaceBetweenRow( String s, boolean force ) { setTPSpaceBetweenRow( parseInt(s), force ); }
    public void setTPSpaceBetweenRow( int i, boolean force ) { set( tpRowSpace, i, force ); }
    
    public int getTPSpaceInRow() { return getInt( PROP_TP_SPACE_INROW  ); }
    public String getTPSpaceInRowString() { return formatInt( getTPSpaceInRow() ); }
    public CPSDatumState getTPSpaceInRowState() { return getStateOf( PROP_TP_SPACE_INROW  ); }
    public void setTPSpaceInRow( int i ) { setTPSpaceInRow( i, false ); }
    public void setTPSpaceInRow( String s ) { setTPSpaceInRow( s, false ); }
    public void setTPSpaceInRow( String s, boolean force ) { setTPSpaceInRow( parseInt(s), force ); }
    public void setTPSpaceInRow( int i, boolean force ) { set( tpInRowSpace, i, force ); }
   
    public int getTPTimeInGH() { return getInt( PROP_TP_TIME_IN_GH  ); }
    public String getTPTimeInGHString() { return formatInt( getTPTimeInGH() ); }
    public CPSDatumState getTPTimeInGHState() { return getStateOf( PROP_TP_TIME_IN_GH  ); }
    public void setTPTimeInGH( int i ) { setTPTimeInGH( i, false ); }
    public void setTPTimeInGH( String s ) { setTPTimeInGH( s, false ); }
    public void setTPTimeInGH( String s, boolean force ) { setTPTimeInGH( parseInt(s), force ); }
    public void setTPTimeInGH( int i, boolean force ) { set( tpTimeInGH, i, force ); }
   
    public String getTPFlatSize() { return get( PROP_FLAT_SIZE ); }
    public CPSDatumState getTPFlatSizeState() { return getStateOf( PROP_FLAT_SIZE ); }
    public void setTPFlatSize( String s ) { setTPFlatSize( s, false ); }
    public void setTPFlatSize( String s, boolean force ) { set( tpFlatSize, s, force ); }
   
    public String getTPPlantNotes() { return get( PROP_TP_PLANT_NOTES  ); }
    public CPSDatumState getTPPlantNotesState() { return getStateOf( PROP_TP_PLANT_NOTES  ); }
    public void setTPPlantNotes( String s ) { setTPPlantNotes( s, false ); }
    public void setTPPlantNotes( String s, boolean force ) { set( tpPlantNotes, s, force ); }
      
    public boolean isPottedUp() { return get( PROP_POT_UP, new CPSBoolean(false)).booleanValue(); } 
    public CPSDatumState getTPPottedUpState() { return getStateOf( PROP_POT_UP); }   
    public void setTPPottedUp( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setTPPottedUp( true );
       else
          setTPPottedUp( false );
    }
    public void setTPPottedUp( Boolean b ) { setTPPottedUp( b, false ); }
    public void setTPPottedUp( Boolean b, boolean force ) { set( tpPotUp, new CPSBoolean(b), force ); }
    
    public String getTPPotUpNotes() { return get( PROP_POT_UP_NOTES  ); }
    public CPSDatumState getTPPotUpNotesState() { return getStateOf( PROP_POT_UP_NOTES ); }
    public void setTPPotUpNotes( String s ) { setTPPotUpNotes( s, false ); }
    public void setTPPotUpNotes( String s, boolean force ) { set( tpPotUpNotes, s, force ); }
      
   
   
   public float getYieldPerFoot() { return getFloat( PROP_YIELD_FOOT ); }
   public String getYieldPerFootString() { return formatFloat( getYieldPerFoot(), 3 ); }
   public CPSDatumState getYieldPerFootState() { return getStateOf( PROP_YIELD_FOOT ); }
    public void setYieldPerFoot( float f ) { setYieldPerFoot( f, false ); }
   public void setYieldPerFoot( String s ) { setYieldPerFoot( s, false ); }
   public void setYieldPerFoot( String s, boolean force ) { setYieldPerFoot( parseFloat(s), force ); }
   public void setYieldPerFoot( float f, boolean force ) { set( yieldPerFoot, f, force ); }
   
   public int getYieldNumWeeks() { return getInt( PROP_YIELD_WEEKS ); }
   public String getYieldNumWeeksString() { return formatInt( getYieldNumWeeks() ); }
   public CPSDatumState getYieldNumWeeksState() { return getStateOf( PROP_YIELD_WEEKS ); }
    public void setYieldNumWeeks( int i ) { setYieldNumWeeks( i, false ); }
   public void setYieldNumWeeks( String s ) { setYieldNumWeeks( s, false ); }
   public void setYieldNumWeeks( String s, boolean force ) { setYieldNumWeeks( parseInt(s), force ); }
   public void setYieldNumWeeks( int i, boolean force ) { set( yieldNumWeeks, i, force ); }
   
   public int getYieldPerWeek() { return getInt( PROP_YIELD_PER_WEEK ); }
   public String getYieldPerWeekString() { return formatInt( getYieldPerWeek() ); }
   public CPSDatumState getYieldPerWeekState() { return getStateOf( PROP_YIELD_PER_WEEK ); }
    public void setYieldPerWeek( int i ) { setYieldPerWeek( i, false ); }
   public void setYieldPerWeek( String s ) { setYieldPerWeek( s, false ); }
   public void setYieldPerWeek( String s, boolean force ) { setYieldPerWeek( parseInt(s), force ); }
   public void setYieldPerWeek( int i, boolean force ) { set( yieldPerWeek, i, force ); }
   
   public String getCropYieldUnit() { return get( PROP_CROP_UNIT ); }
   public CPSDatumState getCropYieldUnitState() { return getStateOf( PROP_CROP_UNIT ); }
    public void setCropYieldUnit( String s ) { setCropYieldUnit( s, false ); }
   public void setCropYieldUnit( String s, boolean force ) { set( cropYieldUnit, s, force ); }
   
   public float getCropUnitValue() { return getFloat( PROP_CROP_UNIT_VALUE ); }
   public String getCropUnitValueString() { return formatFloat( getCropUnitValue() ); }
   public CPSDatumState getCropUnitValueState() { return getStateOf( PROP_CROP_UNIT_VALUE ); }
    public void setCropUnitValue( float f ) { setCropUnitValue( f, false ); }
   public void setCropUnitValue( String s ) { setCropUnitValue( s, false ); }
   public void setCropUnitValue( String s, boolean force ) { setCropUnitValue( parseFloat(s), force ); }
   public void setCropUnitValue( float f, boolean force ) { set( cropUnitValue, f, force ); }
   
   
    
    public CPSRecord diff( CPSRecord thatCrop ) {
       return super.diff( thatCrop, new CPSCrop() );
    }
    
    /* Iterator */
    public CropIterator iterator() { return new CropIterator(); }
    
    public class CropIterator extends CPSRecordIterator {
       
       public  boolean ignoreThisProperty() {
          return this.currentProp == PROP_ID;
       }
       
    }
    
    public String toString() {
       String s = "";
       
       CropIterator i = this.iterator();
       CPSDatum c;
       while ( i.hasNext() ) {
          c = i.next();
          if ( c.isValid() )
             if ( c.getDatum() instanceof CPSCrop )
                s += c.getDescriptor() + " = '" + ((CPSCrop) c.getDatum()).getCropName() + "', ";
             else
                s += c.getDescriptor() + " = '" + c.getDatum() + "', ";
       }
       
       return s;
    }
    
    /** Returns true if this CPSCrop object represents a "crop".
     *
     * Returns true if and only if the crop name is valid and the variety name is invalid.
     */
    public boolean isCrop() {
       return cropName.isValid() && ! varName.isValid();
    }
    
    /** Returns true if this CPSCrop object represents a "variety".
     *
     * Returns true if and only if both the crop name and the variety name are valid.
     */
    public boolean isVariety() {
       return cropName.isValid() && varName.isValid();
    }
}
