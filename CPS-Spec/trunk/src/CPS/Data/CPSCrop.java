package CPS.Data;

import CPS.Data.CPSDatum.CPSDatumState;
import CPS.Module.CPSDataModelConstants;
import java.util.*;

/**
 * Crop - data structure to hold information about a crop
 *        NOT for a planting of a crop, but just for the crop
 */
public class CPSCrop extends CPSRecord {

   // Fields from schema that are as-of-yet unimplemented:
   // "Fudge", "mat_adjust", "misc_adjust", 
   // "seeds_sources","seeds_item_codes","seeds_unit_size"
   
   public final int PROP_CROP_NAME = CPSDataModelConstants.PROP_CROP_NAME;
   public final int PROP_CROP_DESC = CPSDataModelConstants.PROP_CROP_DESC;
   public final int PROP_VAR_NAME = CPSDataModelConstants.PROP_VAR_NAME;
   public final int PROP_FAM_NAME = CPSDataModelConstants.PROP_FAM_NAME;
   public final int PROP_BOT_NAME = CPSDataModelConstants.PROP_BOT_NAME;
   // public final int PROP_SUCCESSIONS = 6;
   public final int PROP_MATURITY = CPSDataModelConstants.PROP_MATURITY;
   public final int PROP_GROUPS = CPSDataModelConstants.PROP_GROUPS;
   public final int PROP_KEYWORDS = CPSDataModelConstants.PROP_KEYWORDS;
   public final int PROP_OTHER_REQ = CPSDataModelConstants.PROP_OTHER_REQ;
   public final int PROP_NOTES = CPSDataModelConstants.PROP_NOTES;
   public final int PROP_SIMILAR = CPSDataModelConstants.PROP_SIMILAR;

   public final int PROP_TIME_TP = CPSDataModelConstants.PROP_TIME_TO_TP;
   public final int PROP_MAT_ADJUST = CPSDataModelConstants.PROP_MAT_ADJUST;
   public final int PROP_ROWS_BED = CPSDataModelConstants.PROP_ROWS_P_BED;
   public final int PROP_SPACE_INROW = CPSDataModelConstants.PROP_SPACE_INROW;
   public final int PROP_SPACE_BETROW = CPSDataModelConstants.PROP_SPACE_BETROW;
   public final int PROP_FLAT_SIZE = CPSDataModelConstants.PROP_FLAT_SIZE;
   public final int PROP_PLANTER = CPSDataModelConstants.PROP_PLANTER;
   public final int PROP_PLANTER_SETTING = CPSDataModelConstants.PROP_PLANTER_SETTING;
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
    
//   private CPSDatum<Integer> cropID;
   // private CPSCrop similar;
   private CPSDatum<String> similar;
   
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
   
   private CPSDatum<Integer> timeToTP;
   private CPSDatum<Integer> maturityAdjust;
   private CPSDatum<Integer> rowsPerBed;
   private CPSDatum<Integer> spaceInRow;
   private CPSDatum<Integer> spaceBetweenRow;
   private CPSDatum<String> flatSize;
   private CPSDatum<String> planter;
   private CPSDatum<String> planterSetting;
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
       recordID = new CPSDatum<Integer>( "Unique ID", PROP_ID, "id", new Integer(-1) );
       commonIDs = new CPSDatum<ArrayList<Integer>>( "Crop IDs represented", PROP_COMMON_ID, "column_DNE", new ArrayList() );
       
       similar = new CPSDatum<String>( "Similar to", PROP_SIMILAR, "similar_to", "" );
       
       cropName = new CPSDatum<String>( "Crop name", PROP_CROP_NAME, "crop_name", "" );
       cropDescription = new CPSDatum<String>( "Crop description", PROP_CROP_DESC, "description", "" );
       varName = new CPSDatum<String>( "Variety", PROP_VAR_NAME, "var_name", "" );
       familyName = new CPSDatum<String>( "Family name", PROP_FAM_NAME, "fam_name", "" );
       botanicalName = new CPSDatum<String>( "Botanical name", PROP_BOT_NAME, "bot_name", "" );
        
       // successions = new CPSDatum<Boolean>( "Succession?", PROP_SUCCESSIONS, "successions", new Boolean(false) );
        
       maturityDays = new CPSDatum<Integer>( "Days to maturity", PROP_MATURITY, "maturity", new Integer(-1) );
        
       groups = new CPSDatum<String>( "Groups", PROP_GROUPS, "groups", "" );
       keywords = new CPSDatum<String>( "Keywords", PROP_KEYWORDS, "keywords", "" );
       otherRequirements = new CPSDatum<String>( "Other Requirements", PROP_OTHER_REQ, "other_req", "" );
       notes = new CPSDatum<String>( "Notes", PROP_NOTES, "notes", "" );
        
       timeToTP = new CPSDatum<Integer>( "Time to TP", PROP_TIME_TP, "time_to_tp", new Integer(-1) );
       maturityAdjust = new CPSDatum<Integer>( "Adjust Mat.", PROP_MAT_ADJUST, "mat_adjust", new Integer(-1));
       rowsPerBed = new CPSDatum<Integer>( "Rows/Bed", PROP_ROWS_BED, "rows_p_bed", new Integer(-1) );
       spaceInRow = new CPSDatum<Integer>( "In-Row Spacing", PROP_SPACE_INROW, "space_inrow", new Integer(-1) );
       spaceBetweenRow = new CPSDatum<Integer>( "Row Spacing", PROP_SPACE_BETROW, "space_betrow", new Integer(-1));
       flatSize = new CPSDatum<String>( "Flat Size", PROP_FLAT_SIZE, "flat_size", "" );
       planter = new CPSDatum<String>( "Use Planter", PROP_PLANTER, "planter", "" );
       planterSetting = new CPSDatum<String>( "Planter Setting", PROP_PLANTER_SETTING, "planter_setting", "" );
       yieldPerFoot = new CPSDatum<Float>( "Yield/Foot", PROP_YIELD_FOOT, "yield_p_foot", new Float(-1.0) );
       yieldNumWeeks = new CPSDatum<Integer>( "Yield for Weeks", PROP_YIELD_WEEKS, "yield_num_weeks", new Integer(-1) );
       yieldPerWeek = new CPSDatum<Integer>( "Yield/Week", PROP_YIELD_PER_WEEK, "yield_p_week", new Integer(-1) );
       cropYieldUnit = new CPSDatum<String>( "Yield Unit", PROP_CROP_UNIT, "crop_unit", "" );
       cropUnitValue = new CPSDatum<Float>( "Value/Unit", PROP_CROP_UNIT_VALUE, "crop_unit_value", new Float(-1.0) );       
        
    }

    protected CPSDatum getDatum( int prop ) {
       
       /* very ugly, but this allows us to use the hierarchy of Crop properties */
       switch ( prop ) {
          case PROP_CROP_NAME:       return cropName;
          case PROP_CROP_DESC:       return cropDescription;
          case PROP_VAR_NAME:        return varName;
          case PROP_FAM_NAME:        return familyName;
          case PROP_BOT_NAME:        return botanicalName;
          // case PROP_SUCCESSIONS:     return successions;
          case PROP_MATURITY:        return maturityDays;
          case PROP_GROUPS:          return groups;
          case PROP_KEYWORDS:        return keywords;
          case PROP_NOTES:           return notes;
          case PROP_OTHER_REQ:       return otherRequirements;
          case PROP_SIMILAR:         return similar;
          case PROP_TIME_TP:         return timeToTP;
          case PROP_MAT_ADJUST:      return maturityAdjust;
          case PROP_ROWS_BED:        return rowsPerBed;
          case PROP_SPACE_INROW:     return spaceInRow;
          case PROP_SPACE_BETROW:    return spaceBetweenRow;
          case PROP_FLAT_SIZE:       return flatSize;
          case PROP_PLANTER:         return planter;
          case PROP_PLANTER_SETTING: return planterSetting;
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
       a.add( PROP_TIME_TP );
       a.add( PROP_MAT_ADJUST );
       a.add( PROP_ROWS_BED );
       a.add( PROP_SPACE_INROW );
       a.add( PROP_SPACE_BETROW );
       a.add( PROP_FLAT_SIZE );
       a.add( PROP_PLANTER );
       a.add( PROP_PLANTER_SETTING );
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

    public String getSimilarCrop() { return get( PROP_SIMILAR ); }
    public CPSDatumState getSimilarCropState() { return getStateOf( PROP_SIMILAR ); }
    public void setSimilarCrop( String c ) { setSimilarCrop( c, false); }
    public void setSimilarCrop( String c, boolean force  ) { set( similar, c, force ); }
    
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

    // public boolean getSuccessions() { return getBoolean( PROP_SUCCESSIONS ); }
    // public void setSuccessions( boolean b ) { set( successions, new Boolean(b) ); }
    
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
    
   public int getTimeToTP() { return getInt( PROP_TIME_TP ); }
   public String getTimeToTPString() { return formatInt( getTimeToTP() ); }
   public CPSDatumState getTimeToTPState() { return getStateOf( PROP_TIME_TP ); }
    public void setTimeToTP( int i ) { setTimeToTP( i, false ); }
   public void setTimeToTP( String s ) { setTimeToTP( s, false ); }
   public void setTimeToTP( String s, boolean force ) { setTimeToTP( parseInt(s), force ); }
   public void setTimeToTP( int i, boolean force ) { set( timeToTP, i, force ); }
   
   public int getMaturityAdjust() { return getInt( PROP_MAT_ADJUST ); }
   public String getMaturityAdjustString() { return formatInt( getMaturityAdjust() ); }
   public CPSDatumState getMaturityAdjustState() { return getStateOf( PROP_MAT_ADJUST ); }
    public void setMaturityAdjust( int i ) { setMaturityAdjust( i, false ); }
   public void setMaturityAdjust( String s ) { setMaturityAdjust( s, false ); }
   public void setMaturityAdjust( String s, boolean force ) { setMaturityAdjust( parseInt(s), force ); }
   public void setMaturityAdjust( int i, boolean force ) { set( maturityAdjust, i, force ); }
   
   public int getRowsPerBed() { return getInt( PROP_ROWS_BED ); }
   public String getRowsPerBedString() { return formatInt( getRowsPerBed() ); }
   public CPSDatumState getRowsPerBedState() { return getStateOf( PROP_ROWS_BED ); }
    public void setRowsPerBed( int i ) { setRowsPerBed( i, false ); }
   public void setRowsPerBed( String s ) { setRowsPerBed( s, false ); }
   public void setRowsPerBed( String s, boolean force ) { setRowsPerBed( parseInt(s), force ); }
   public void setRowsPerBed( int i, boolean force ) { set( rowsPerBed, i, force ); }
   
   public int getSpaceInRow() { return getInt( PROP_SPACE_INROW ); }
   public String getSpaceInRowString() { return formatInt( getSpaceInRow() ); }
   public CPSDatumState getSpaceInRowState() { return getStateOf( PROP_SPACE_INROW ); }
    public void setSpaceInRow( int i ) { setSpaceInRow( i, false ); }
   public void setSpaceInRow( String s ) { setSpaceInRow( s, false ); }
   public void setSpaceInRow( String s, boolean force ) { setSpaceInRow( parseInt(s), force ); }
   public void setSpaceInRow( int i, boolean force ) { set( spaceInRow, i, force ); }
   
   public int getSpaceBetweenRow() { return getInt( PROP_SPACE_BETROW ); }
   public String getSpaceBetweenRowString() { return formatInt( getSpaceBetweenRow() ); }
   public CPSDatumState getSpaceBetweenRowState() { return getStateOf( PROP_SPACE_BETROW ); }
    public void setSpaceBetweenRow( int i ) { setSpaceBetweenRow( i, false ); }
   public void setSpaceBetweenRow( String s ) { setSpaceBetweenRow( s, false ); }
   public void setSpaceBetweenRow( String s, boolean force ) { setSpaceBetweenRow( parseInt(s), force ); }
   public void setSpaceBetweenRow( int i, boolean force ) { set( spaceBetweenRow, i, force ); }
   
   public String getFlatSize() { return get( PROP_FLAT_SIZE ); }
   public CPSDatumState getFlatSizeState() { return getStateOf( PROP_FLAT_SIZE ); }
    public void setFlatSize( String s ) { setFlatSize( s, false ); }
   public void setFlatSize( String s, boolean force ) { set( flatSize, s, force ); }
   
   public String getPlanter() { return get( PROP_PLANTER ); }
   public CPSDatumState getPlanterState() { return getStateOf( PROP_PLANTER ); }
    public void setPlanter( String s ) { setPlanter( s, false ); }
   public void setPlanter( String s, boolean force ) { set( planter, s, force ); }
   
   public String getPlanterSetting() { return get( PROP_PLANTER_SETTING ); }
   public CPSDatumState getPlanterSettingState() { return getStateOf( PROP_PLANTER_SETTING ); }
    public void setPlanterSetting( String s ) { setPlanterSetting( s, false ); }
   public void setPlanterSetting( String s, boolean force ) { set( planterSetting, s, force ); }
   
   public float getYieldPerFoot() { return getFloat( PROP_YIELD_FOOT ); }
   public String getYieldPerFootString() { return formatFloat( getYieldPerFoot() ); }
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
