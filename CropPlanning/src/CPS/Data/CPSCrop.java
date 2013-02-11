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
public final class CPSCrop extends CPSRecord {

   public final static int PROP_CROP_NAME = CPSDataModelConstants.PROP_CROP_NAME;
   public final static int PROP_CROP_DESC = CPSDataModelConstants.PROP_CROP_DESC;
   public final static int PROP_VAR_NAME = CPSDataModelConstants.PROP_VAR_NAME;
   public final static int PROP_FAM_NAME = CPSDataModelConstants.PROP_FAM_NAME;
   public final static int PROP_BOT_NAME = CPSDataModelConstants.PROP_BOT_NAME;

   public final static int PROP_MATURITY = CPSDataModelConstants.PROP_MATURITY;
   public final static int PROP_GROUPS = CPSDataModelConstants.PROP_GROUPS;
   public final static int PROP_KEYWORDS = CPSDataModelConstants.PROP_KEYWORDS;
   public final static int PROP_OTHER_REQ = CPSDataModelConstants.PROP_OTHER_REQ;
   public final static int PROP_NOTES = CPSDataModelConstants.PROP_NOTES;

   public final static int PROP_FROST_HARDY = CPSDataModelConstants.PROP_FROST_HARDY;
   
   public final static int PROP_DIRECT_SEED = CPSDataModelConstants.PROP_DIRECT_SEED;
   public final static int PROP_DS_MAT_ADJUST = CPSDataModelConstants.PROP_DS_MAT_ADJUST;
   public final static int PROP_DS_ROWS_P_BED = CPSDataModelConstants.PROP_DS_ROWS_P_BED;
   public final static int PROP_DS_SPACE_BETROW = CPSDataModelConstants.PROP_DS_SPACE_BETROW;
   public final static int PROP_DS_PLANT_NOTES = CPSDataModelConstants.PROP_DS_PLANT_NOTES;
   
   public final static int PROP_TRANSPLANT = CPSDataModelConstants.PROP_TRANSPLANT;
   public final static int PROP_TP_MAT_ADJUST = CPSDataModelConstants.PROP_TP_MAT_ADJUST;
   public final static int PROP_TP_ROWS_BED = CPSDataModelConstants.PROP_TP_ROWS_P_BED;
   public final static int PROP_TP_SPACE_BETROW = CPSDataModelConstants.PROP_TP_SPACE_BETROW;
   public final static int PROP_TP_SPACE_INROW = CPSDataModelConstants.PROP_TP_SPACE_INROW;
   public final static int PROP_TP_TIME_IN_GH = CPSDataModelConstants.PROP_TP_TIME_IN_GH;
   public final static int PROP_FLAT_SIZE = CPSDataModelConstants.PROP_FLAT_SIZE;
   public final static int PROP_TP_PLANT_NOTES = CPSDataModelConstants.PROP_TP_PLANT_NOTES;
   public final static int PROP_POT_UP = CPSDataModelConstants.PROP_TP_POT_UP;
   public final static int PROP_POT_UP_NOTES = CPSDataModelConstants.PROP_TP_POT_UP_NOTES;
   
   public final static int PROP_YIELD_FOOT = CPSDataModelConstants.PROP_YIELD_P_FOOT;
   public final static int PROP_YIELD_WEEKS = CPSDataModelConstants.PROP_YIELD_NUM_WEEKS;
   public final static int PROP_YIELD_PER_WEEK = CPSDataModelConstants.PROP_YIELD_P_WEEK;
   public final static int PROP_CROP_UNIT = CPSDataModelConstants.PROP_CROP_UNIT;
   public final static int PROP_CROP_UNIT_VALUE = CPSDataModelConstants.PROP_CROP_UNIT_VALUE;

   public static final int PROP_SEEDS_PER_UNIT   = CPSDataModelConstants.PROP_SEEDS_PER_UNIT;
   public static final int PROP_SEED_UNIT   = CPSDataModelConstants.PROP_SEED_UNIT;
   public static final int PROP_SEEDS_PER_DS = CPSDataModelConstants.PROP_SEEDS_PER_DS;
   public static final int PROP_SEEDS_PER_TP = CPSDataModelConstants.PROP_SEEDS_PER_TP;


   public static final String[] SEED_UNIT_STRINGS = { "", "oz", "lb",
                                                      "g", "kg",
                                                      "ea", "M (1k)" };


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
   
   private CPSDatum<Boolean> frostHardy;
   
   private CPSDatum<Boolean> directSeed;
   private CPSDatum<Integer> dsMatAdjust;
   private CPSDatum<Integer> dsRowsPerBed;
   private CPSDatum<Integer> dsRowSpace;
   private CPSDatum<String>  dsPlantNotes;
   
   private CPSDatum<Boolean> transplant;
   private CPSDatum<Integer> tpMatAdjust;
   private CPSDatum<Integer> tpRowsPerBed;
   private CPSDatum<Integer> tpRowSpace;
   private CPSDatum<Integer> tpInRowSpace;
   private CPSDatum<Integer> tpTimeInGH;
   private CPSDatum<String> tpFlatSize;
   private CPSDatum<String> tpPlantNotes;
   private CPSDatum<Boolean> tpPotUp;
   private CPSDatum<String> tpPotUpNotes;
   
   private CPSDatum<Float> yieldPerFoot;
   private CPSDatum<Integer> yieldNumWeeks;
   private CPSDatum<Integer> yieldPerWeek;
   private CPSDatum<String> cropYieldUnit;
   private CPSDatum<Float> cropUnitValue;

   private CPSDatum<Integer> seedsPerUnit;
   private CPSDatum<String>  seedUnit;
   private CPSDatum<Float>   seedsPerDS;
   private CPSDatum<Float>   seedsPerTP;
   

   /**
    * CPSCrop - create a new, blank CPSCrop object
    */
   public CPSCrop() {
       
      setRepresentsSingleRecord();
      recordID = new CPSDatum<Integer>( "Unique ID", new Integer( -1 ), PROP_ID );
      commonIDs = new CPSDatum<ArrayList<Integer>>( "Crop IDs represented", new ArrayList<Integer>(), PROP_COMMON_ID );
       
      cropName = new CPSDatum<String>( "Crop", "Crop name", "", PROP_CROP_NAME );
      cropDescription = new CPSDatum<String>( "Description", "Description of crop or variety", "", PROP_CROP_DESC );
      varName = new CPSDatum<String>( "Variety", "Variety Name", "", PROP_VAR_NAME );
      familyName = new CPSDatum<String>( "Family", "Family name", "", PROP_FAM_NAME );
      botanicalName = new CPSDatum<String>( "Botanical name", "" , PROP_BOT_NAME );
        
      maturityDays = new CPSDatum<Integer>( "Days to maturity", new Integer( -1 ), PROP_MATURITY );
        
      groups = new CPSDatum<String>( "Groups", "", PROP_GROUPS );
      keywords = new CPSDatum<String>( "Keywords", "", PROP_KEYWORDS );
      otherRequirements = new CPSDatum<String>( "Other Requirements", "", PROP_OTHER_REQ );
      notes = new CPSDatum<String>( "Notes", "" , PROP_NOTES );
        
      frostHardy = new CPSDatum<Boolean>( "Frost hardy?", new Boolean( null ), PROP_FROST_HARDY );
       
      directSeed = new CPSDatum<Boolean>( "Direct Seeded", new Boolean( null ), PROP_DIRECT_SEED );
      dsMatAdjust = new CPSDatum<Integer>( "Adjust Mat. (DS)", new Integer( -1 ), PROP_DS_MAT_ADJUST );
      dsRowsPerBed = new CPSDatum<Integer>( "Rows/Bed (DS)", new Integer( -1 ), PROP_DS_ROWS_P_BED );
      dsRowSpace = new CPSDatum<Integer>( "Row Spacing (DS)", new Integer( -1 ), PROP_DS_SPACE_BETROW );
      dsPlantNotes = new CPSDatum<String>( "Notes for DS", ""  , PROP_DS_PLANT_NOTES );

      transplant = new CPSDatum<Boolean>( "Transplant", new Boolean( null ), PROP_TRANSPLANT );
      tpMatAdjust = new CPSDatum<Integer>( "Adjust Mat. (TP)", new Integer( -1 ), PROP_TP_MAT_ADJUST );
      tpRowsPerBed = new CPSDatum<Integer>( "Rows/Bed (TP)", new Integer( -1 ), PROP_TP_ROWS_BED );
      tpRowSpace = new CPSDatum<Integer>( "Row Spacing (TP)", new Integer( -1 ), PROP_TP_SPACE_BETROW );
      tpInRowSpace = new CPSDatum<Integer>( "In-Row Spacing (TP)", new Integer( -1 ), PROP_TP_SPACE_INROW );
      tpTimeInGH = new CPSDatum<Integer>( "Time to TP", new Integer( -1 ), PROP_TP_TIME_IN_GH );
      tpFlatSize = new CPSDatum<String>( "Flat Size", "", PROP_FLAT_SIZE );
      tpPlantNotes = new CPSDatum<String>( "Notes for TP", "", PROP_TP_PLANT_NOTES );
      tpPotUp = new CPSDatum<Boolean>( "Pot Up", new Boolean( null ), PROP_POT_UP );
      tpPotUpNotes = new CPSDatum<String>( "Notes for Potting Up", "" , PROP_POT_UP_NOTES );
       
//      yieldPerFoot = new CPSDatum<Float>( "Yield/Foot", new Float( -1.0 ), PROP_YIELD_FOOT );
      yieldPerFoot = new CPSDatum<Float>( "Yield/Foot", new Float( -1.0 ), PROP_YIELD_FOOT );
      yieldNumWeeks = new CPSDatum<Integer>( "Yield for Weeks", new Integer( -1 ), PROP_YIELD_WEEKS );
      yieldPerWeek = new CPSDatum<Integer>( "Yield/Week", new Integer( -1 ), PROP_YIELD_PER_WEEK );
      cropYieldUnit = new CPSDatum<String>( "Yield Unit", "", PROP_CROP_UNIT );
      cropUnitValue = new CPSDatum<Float>( "Value/Unit", new Float(-1.0) , PROP_CROP_UNIT_VALUE );

      seedsPerUnit = new CPSDatum<Integer>( "Seeds/Unit", new Integer(-1), PROP_SEEDS_PER_UNIT );
      seedUnit = new CPSDatum<String>( "Units", "", PROP_SEED_UNIT );
      seedsPerDS = new CPSDatum<Float>( "Seeds/RowFt (DS)", new Float( -1.0 ), PROP_SEEDS_PER_DS );
      seedsPerTP = new CPSDatum<Float>( "Seeds/Plant (TP)", new Float( -1.0 ), PROP_SEEDS_PER_TP );

    }

    public CPSDatum getDatum( int prop ) {
       
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
            
          case PROP_SEEDS_PER_UNIT:  return seedsPerUnit;
          case PROP_SEED_UNIT:       return seedUnit;
          case PROP_SEEDS_PER_DS:    return seedsPerDS;
          case PROP_SEEDS_PER_TP:    return seedsPerTP;
          
          default:                   return super.getDatum( prop );
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
       
       a.add( PROP_SEEDS_PER_UNIT );
       a.add( PROP_SEED_UNIT );
       a.add( PROP_SEEDS_PER_DS );
       a.add( PROP_SEEDS_PER_TP );

       return a;
   }

   @Override
   public void finishUp () { /* nothing to do here */ }
   @Override
   public void updateCalculations() { /* nothing to do here */ }

    
    public String getCropName() { return get( PROP_CROP_NAME ); }
    public CPSDatumState getCropNameState() { return getStateOf( PROP_CROP_NAME ); }
    public void setCropName( String s ) { set( cropName, s ); }

    public String getCropDescription() { return get( PROP_CROP_DESC ); }
    public CPSDatumState getCropDescriptionState() { return getStateOf( PROP_CROP_DESC ); }
    public void setCropDescription( String s ) { set( cropDescription, s ); }

    public String getVarietyName() { return get( PROP_VAR_NAME ); }
    public CPSDatumState getVarietyNameState() { return getStateOf( PROP_VAR_NAME ); }
    public void setVarietyName( String s ) { set( varName, s ); }
   
    public String getFamilyName() { return get( PROP_FAM_NAME ); }
    public CPSDatumState getFamilyNameState() { return getStateOf( PROP_FAM_NAME ); }
    public void setFamilyName( String e ) { set( familyName, e ); }

    public String getBotanicalName() { return get( PROP_BOT_NAME ); }
    public CPSDatumState getBotanicalNameState() { return getStateOf( PROP_BOT_NAME ); }
    public void setBotanicalName( String e ) { set( botanicalName, e ); }

    public Integer getMaturityDays() { return getInt( PROP_MATURITY ); }
    public String getMaturityDaysString() { return getString( PROP_MATURITY ); }
    public CPSDatumState getMaturityDaysState() { return getStateOf( PROP_MATURITY ); }
    public void setMaturityDays( Integer i ) { set( maturityDays, i ); }
    public void setMaturityDays( int i ) { setMaturityDays( new Integer( i ) ); }
    public void setMaturityDays( String s ) { setMaturityDays( parseInteger( s ) ); }

    public String getGroups() { return get( PROP_GROUPS ); }
    public CPSDatumState getGroupsState() { return getStateOf( PROP_GROUPS ); }
    public void setGroups( String e ) { set( groups, e ); }
    
    public String getKeywords() { return get( PROP_KEYWORDS ); }
    public CPSDatumState getKeywordsState() { return getStateOf( PROP_KEYWORDS ); }
    public void setKeywords( String e ) { set( keywords, e ); }

    public String getNotes() { return get( PROP_NOTES ); }
    public CPSDatumState getNotesState() { return getStateOf( PROP_NOTES ); }
    public void setNotes( String e ) { set( notes, e ); }

    public String getOtherRequirements() { return get( PROP_OTHER_REQ ); }
    public CPSDatumState getOtherRequirementsState() { return getStateOf( PROP_OTHER_REQ ); }
    public void setOtherRequirements( String e ) { set( otherRequirements, e ); }
    
    public Boolean isFrostHardy() { return getBoolean( PROP_FROST_HARDY ); }
    public Boolean isFrostTender() { return ! isFrostHardy().booleanValue(); }
    public CPSDatumState getFrostHardyState() { return getStateOf( PROP_FROST_HARDY ); }   
    public void setFrostHardy( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setFrostHardy( new Boolean( true ));
       else
          setFrostHardy( new Boolean( false ));
    }
    public void setFrostHardy( Boolean b ) { set( frostHardy, b ); }
    public void setFrostHardy( boolean b ) { setFrostHardy( new Boolean( b )); }
    
    /*
     * DIRECT SEEDING STATS
     */
    public Boolean isDirectSeeded() { return getBoolean( PROP_DIRECT_SEED ); }
    public CPSDatumState getDirectSeededState() { return getStateOf( PROP_DIRECT_SEED ); }   
    public void setDirectSeeded( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setDirectSeeded( new Boolean( true ));
       else
          setDirectSeeded( new Boolean( false ));
    }
    public void setDirectSeeded( Boolean b ) { set( directSeed, b ); }
    public void setDirectSeeded( boolean b ) { setDirectSeeded( new Boolean( b )); }
    
    public Integer getDSMaturityAdjust() { return getInt( PROP_DS_MAT_ADJUST ); }
    public String getDSMaturityAdjustString() { return getString( PROP_DS_MAT_ADJUST ); }
    public CPSDatumState getDSMaturityAdjustState() { return getStateOf( PROP_DS_MAT_ADJUST  ); }
    public void setDSMaturityAdjust( Integer i ) { set( dsMatAdjust, i ); }
    public void setDSMaturityAdjust( int i ) { set( dsMatAdjust, new Integer( i ) ); }
    public void setDSMaturityAdjust( String s ) { setDSMaturityAdjust( parseInteger(s) ); }
   
    public Integer getDSRowsPerBed() { return getInt( PROP_DS_ROWS_P_BED  ); }
    public String getDSRowsPerBedString() { return getString( PROP_DS_ROWS_P_BED ); }
    public CPSDatumState getDSRowsPerBedState() { return getStateOf( PROP_DS_ROWS_P_BED  ); }
    public void setDSRowsPerBed( Integer i ) { set( dsRowsPerBed, i ); }
    public void setDSRowsPerBed( int i ) { set( dsRowsPerBed, new Integer( i )); }
    public void setDSRowsPerBed( String s ) { setDSRowsPerBed( parseInteger(s) ); }

    public Integer getDSSpaceBetweenRow() { return getInt( PROP_DS_SPACE_BETROW  ); }
    public String getDSSpaceBetweenRowString() { return getString( PROP_DS_SPACE_BETROW ); }
    public CPSDatumState getDSSpaceBetweenRowState() { return getStateOf( PROP_DS_SPACE_BETROW  ); }
    public void setDSSpaceBetweenRow( Integer i ) { set( dsRowSpace, i ); }
    public void setDSSpaceBetweenRow( int i ) { set( dsRowSpace, new Integer( i ) ); }
    public void setDSSpaceBetweenRow( String s ) { setDSSpaceBetweenRow( parseInteger(s) ); }
   
    public String getDSPlantNotes() { return get( PROP_DS_PLANT_NOTES  ); }
    public CPSDatumState getDSPlantNotesState() { return getStateOf( PROP_DS_PLANT_NOTES  ); }
    public void setDSPlantNotes( String s ) { set( dsPlantNotes, s ); }
    
    /* 
     * TRANSPLANT STATS
     */
    public Boolean isTransplanted() { return getBoolean( PROP_TRANSPLANT ); }
    public CPSDatumState getTransplantedState() { return getStateOf( PROP_TRANSPLANT ); }   
    public void setTransplanted( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setTransplanted( new Boolean( true ));
       else
          setTransplanted( new Boolean( false ));
    }
    public void setTransplanted( Boolean b ) { set( transplant, b ); }
    public void setTransplanted( boolean b ) { setTransplanted( new Boolean( b )); }

    public Integer getTPMaturityAdjust() { return getInt( PROP_TP_MAT_ADJUST  ); }
    public String getTPMaturityAdjustString() { return getString( PROP_TP_MAT_ADJUST ); }
    public CPSDatumState getTPMaturityAdjustState() { return getStateOf( PROP_TP_MAT_ADJUST  ); }
    public void setTPMaturityAdjust( Integer i ) { set( tpMatAdjust, i ); }
    public void setTPMaturityAdjust( int i ) { setTPMaturityAdjust( new Integer( i ) ); }
    public void setTPMaturityAdjust( String s ) { setTPMaturityAdjust( parseInteger(s) ); }
   
    public Integer getTPRowsPerBed() { return getInt( PROP_TP_ROWS_BED  ); }
    public String getTPRowsPerBedString() { return getString( PROP_TP_ROWS_BED ); }
    public CPSDatumState getTPRowsPerBedState() { return getStateOf( PROP_TP_ROWS_BED  ); }
    public void setTPRowsPerBed( Integer i ) { set( tpRowsPerBed, i ); }
    public void setTPRowsPerBed( int i ) { setTPRowsPerBed( new Integer( i ) ); }
    public void setTPRowsPerBed( String s ) { setTPRowsPerBed( parseInteger(s) ); }
   
    public Integer getTPSpaceBetweenRow() { return getInt( PROP_TP_SPACE_BETROW  ); }
    public String getTPSpaceBetweenRowString() { return getString( PROP_TP_SPACE_BETROW ); }
    public CPSDatumState getTPSpaceBetweenRowState() { return getStateOf( PROP_TP_SPACE_BETROW  ); }
    public void setTPSpaceBetweenRow( Integer i ) { set( tpRowSpace, i ); }
    public void setTPSpaceBetweenRow( int i ) { setTPSpaceBetweenRow( new Integer( i ) ); }
    public void setTPSpaceBetweenRow( String s ) { setTPSpaceBetweenRow( parseInteger(s) ); }
    
    public Integer getTPSpaceInRow() { return getInt( PROP_TP_SPACE_INROW  ); }
    public String getTPSpaceInRowString() { return getString( PROP_TP_SPACE_INROW ); }
    public CPSDatumState getTPSpaceInRowState() { return getStateOf( PROP_TP_SPACE_INROW  ); }
    public void setTPSpaceInRow( Integer i ) { set( tpInRowSpace, i ); }
    public void setTPSpaceInRow( int i ) { setTPSpaceInRow( new Integer( i ) ); }
    public void setTPSpaceInRow( String s ) { setTPSpaceInRow( parseInteger(s) ); }
   
    public Integer getTPTimeInGH() { return getInt( PROP_TP_TIME_IN_GH  ); }
    public String getTPTimeInGHString() { return getString( PROP_TP_TIME_IN_GH ); }
    public CPSDatumState getTPTimeInGHState() { return getStateOf( PROP_TP_TIME_IN_GH  ); }
    public void setTPTimeInGH( Integer i ) { set( tpTimeInGH, i ); }
    public void setTPTimeInGH( int i ) { setTPTimeInGH( new Integer( i ) ); }
    public void setTPTimeInGH( String s ) { setTPTimeInGH( parseInteger(s) ); }
   
    public String getTPFlatSize() { return get( PROP_FLAT_SIZE ); }
    public CPSDatumState getTPFlatSizeState() { return getStateOf( PROP_FLAT_SIZE ); }
    public void setTPFlatSize( String s ) { set( tpFlatSize, s ); }
   
    public String getTPPlantNotes() { return get( PROP_TP_PLANT_NOTES  ); }
    public CPSDatumState getTPPlantNotesState() { return getStateOf( PROP_TP_PLANT_NOTES  ); }
    public void setTPPlantNotes( String s ) { set( tpPlantNotes, s ); }

    
    public Boolean isPottedUp() { return getBoolean( PROP_POT_UP ); }
    public CPSDatumState getTPPottedUpState() { return getStateOf( PROP_POT_UP); }   
    public void setTPPottedUp( String s ) { 
       if ( s != null && s.equalsIgnoreCase("true") )
          setTPPottedUp( true );
       else
          setTPPottedUp( false );
    }
    public void setTPPottedUp( Boolean b ) { set( tpPotUp, b ); }
    public void setTPPottedUp( boolean b ) { setTPPottedUp( new Boolean(b) ); }
    
    public String getTPPotUpNotes() { return get( PROP_POT_UP_NOTES  ); }
    public CPSDatumState getTPPotUpNotesState() { return getStateOf( PROP_POT_UP_NOTES ); }
    public void setTPPotUpNotes( String s ) { set( tpPotUpNotes, s ); }

   
   
   public Float getYieldPerFoot() { return getFloat( PROP_YIELD_FOOT ); }
   public String getYieldPerFootString() { return formatFloat( (Float) get( PROP_YIELD_FOOT ) ); }
   public CPSDatumState getYieldPerFootState() { return getStateOf( PROP_YIELD_FOOT ); }
    public void setYieldPerFoot( Float f ) { set( yieldPerFoot, f ); }
    public void setYieldPerFoot( float f ) { setYieldPerFoot( new Float( f ) ); }
   public void setYieldPerFoot( String s ) { setYieldPerFoot( parseFloatBigF(s) ); }
   
   public Integer getYieldNumWeeks() { return getInt( PROP_YIELD_WEEKS ); }
   public String getYieldNumWeeksString() { return getString( PROP_YIELD_WEEKS ); }
   public CPSDatumState getYieldNumWeeksState() { return getStateOf( PROP_YIELD_WEEKS ); }
    public void setYieldNumWeeks( Integer i ) { set( yieldNumWeeks, i ); }
    public void setYieldNumWeeks( int i ) { setYieldNumWeeks( new Integer( i )); }
   public void setYieldNumWeeks( String s ) { setYieldNumWeeks( parseInteger(s) ); }
   
   public Integer getYieldPerWeek() { return getInt( PROP_YIELD_PER_WEEK ); }
   public String getYieldPerWeekString() { return getString( PROP_YIELD_PER_WEEK ); }
   public CPSDatumState getYieldPerWeekState() { return getStateOf( PROP_YIELD_PER_WEEK ); }
    public void setYieldPerWeek( Integer i ) { set( yieldPerWeek, i ); }
    public void setYieldPerWeek( int i ) { setYieldPerWeek( new Integer( i )); }
   public void setYieldPerWeek( String s ) { setYieldPerWeek( parseInteger(s) ); }
   
   public String getCropYieldUnit() { return get( PROP_CROP_UNIT ); }
   public CPSDatumState getCropYieldUnitState() { return getStateOf( PROP_CROP_UNIT ); }
    public void setCropYieldUnit( String s ) { set( cropYieldUnit, s ); }
   
   public Float getCropUnitValue() { return getFloat( PROP_CROP_UNIT_VALUE ); }
   public String getCropUnitValueString() { return formatFloat( (Float) get( PROP_CROP_UNIT_VALUE ) ); }
   public CPSDatumState getCropUnitValueState() { return getStateOf( PROP_CROP_UNIT_VALUE ); }
    public void setCropUnitValue( Float f ) { set( cropUnitValue, f ); }
    public void setCropUnitValue( float f ) { setCropUnitValue( new Float( f )); }
   public void setCropUnitValue( String s ) { setCropUnitValue( parseFloatBigF(s) ); }
   
   
    public Integer       getSeedsPerUnit() {            return getInt( seedsPerUnit.getPropertyNum() ); }
    public String        getSeedsPerUnitString() {       return getString( seedsPerUnit.getPropertyNum() ); }
    public CPSDatumState getSeedsPerUnitState() {        return getStateOf( seedsPerUnit.getPropertyNum() ); }
    public void          setSeedsPerUnit( Integer i ) { set( seedsPerUnit, i ); }
    public void          setSeedsPerUnit( int i ) {     setSeedsPerUnit( new Integer( i )); }
    public void          setSeedsPerUnit( String s ) {  setSeedsPerUnit( parseInteger(s) ); }

    public String        getSeedUnit() {           return get( seedUnit.getPropertyNum() ); }
    public CPSDatumState getSeedUnitState() {       return getStateOf( seedUnit.getPropertyNum() ); }
    public void          setSeedUnit( String s ) { set( seedUnit, parseInheritableString(s) ); }

    public Float         getSeedsPerDS() {           return getFloat( seedsPerDS.getPropertyNum() ); }
    public String        getSeedsPerDSString() {      return getString( seedsPerDS.getPropertyNum() ); }
    public CPSDatumState getSeedsPerDSState() {       return getStateOf( seedsPerDS.getPropertyNum() ); }
    public void          setSeedsPerDS( Float i ) {  set( seedsPerDS, i ); }
    public void          setSeedsPerDS( float i ) {  setSeedsPerDS( new Float( i )); }
    public void          setSeedsPerDS( String s ) { setSeedsPerDS( parseFloatBigF(s) ); }
   
    public Float         getSeedsPerTP() {           return getFloat( seedsPerTP.getPropertyNum() ); }
    public String        getSeedsPerTPString() {      return formatFloat( (Float) get( seedsPerTP.getPropertyNum() ) ); }
    public CPSDatumState getSeedsPerTPState() {       return getStateOf( seedsPerTP.getPropertyNum() ); }
    public void          setSeedsPerTP( Float f ) {  set( seedsPerTP, f ); }
    public void          setSeedsPerTP( float f ) {  setSeedsPerTP( new Float( f )); }
    public void          setSeedsPerTP( String s ) { setSeedsPerTP( parseFloatBigF(s) ); }

   
    
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
          if ( c.isNotNull() ) {
             s += c.getName() + " = '" + c.getValue() + "'";
             if ( c.isInherited() )
                s += "(i)";
             if ( c.isCalculated() )
                s += "(c)";
             s += ", ";
          }
       }
       
       return s;
    }
    
    /** Returns true if this CPSCrop object represents a "crop".
     *
     * Returns true if and only if the crop name is valid and the variety name is invalid.
     */
    public boolean isCrop() {
       return cropName.isNotNull() && ! varName.isNotNull();
    }
    
    /** Returns true if this CPSCrop object represents a "variety".
     *
     * Returns true if and only if both the crop name and the variety name are valid.
     */
    public boolean isVariety() {
       return cropName.isNotNull() && varName.isNotNull();
    }
}
