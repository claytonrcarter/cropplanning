/* HSQLDB.java - Created: January 16, 2007
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

package CPS.Core.DB;

import CPS.Data.*;
import CPS.Module.CPSConfigurable;
import CPS.Module.CPSDataModelSQL;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSGlobalSettings;
import CPS.Module.CPSWizardPage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.TableModel;
import net.sf.persist.*;

/**
 *
 * @author Clayton
 */
public class HSQLDB extends CPSDataModelSQL implements CPSConfigurable {
   
    private static final boolean DEBUG = true;

    static final String CROP_VAR_TABLE = "CROPS_VARIETIES";
    static final String CROP_PLAN_TABLE = "CROP_PLANS";
    static final String CPS_META_TABLE = "CPS_METADATA";

    protected static final boolean ESCAPE_WITH_SINGLE_QUOTES = true;
    protected static final boolean ESCAPE_WITH_DOUBLE_QUOTES = false;
    
   private String hsqlDriver = "org.hsqldb.jdbcDriver";
   private String dbDir = System.getProperty("user.dir");
   private String dbFile = "CPSdb";
   
   private ResultSet rsListCache = null;
   private ResultSet rsCropCache = null;
   private ResultSet rsPlantCache = null;
   public String state = null;
   
   private HSQLColumnMap columnMap;
   private HSQLQuerier query;
   private Persist p;
   
   HSQLSettings localSettings;
   
   public HSQLDB() {
      super();

       setModuleName( "HSQLDB" );
       setModuleDescription( "A full featured DataModel based on HSQLDB.");
       setModuleVersion( CPSGlobalSettings.getVersion() );
               
       localSettings = new HSQLSettings();
       
//       init();
   }

   @Override
   public int init () {

      // enable Persist logging
//      org.apache.log4j.BasicConfigurator.configure();

      if ( true || localSettings.getUseGlobalDir() )
         dbDir = CPSGlobalSettings.getDataOutputDir();
      else
         dbDir = localSettings.getCustomOutDir();

      debug( "attempting to connect to the db" );
      Connection con = HSQLConnect.getConnection( dbDir, dbFile, hsqlDriver );
      
      try {
          // if the db DNE
          if ( con == null ) {
              debug( "connection failed, presumably the db DNE" );
              p = HSQLConnect.createDB( dbDir, dbFile, getModuleVersionAsLongInt() );
          }
          else
              p = new Persist( con );

          /* only needed when we're using a server mode db */
          if ( false && HSQLConnect.dbIsEmpty( con ) ) {
              debug( "The db seems to be empty." );
              HSQLDBCreator.createTables( p, getModuleVersionAsLongInt() );
          }
      } catch ( Exception e ) {
          e.printStackTrace();
      }

      // make sure Persist knows about our custom data types
//      p.getClassToTypeNumMap().put( CPSBoolean.class, new Integer( java.sql.Types.BOOLEAN ));

      HSQLUpdate.updateDB( p, getModuleVersionAsLongInt() );

      // TODO Persist port HERE
      query = new HSQLQuerier( p.getConnection() );
      columnMap = new HSQLColumnMap();
      
      return 0;
   }

   public synchronized List<String> getFlatSizeList( String planName ) {

       // if the plan doesn't exist, then don't get the mapping, just return an empty list
       if ( ! HSQLConnect.tableExists( p.getConnection(), planName ))
           return new ArrayList<String>();
       
       TableMapping tm = (TableMapping) p.getMapping( CPSPlanting.class, planName );
       String plantColName = tm.getColumnNameForMethod( "getFlatSize" );
       tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE );
       String cropColName = tm.getColumnNameForMethod( "getFlatSize" );
       return getDistinctValsFromCVAndPlan( planName, plantColName, cropColName );
   }

   public synchronized List<String> getFieldNameList( String planName ) {
       // if the plan doesn't exist, then don't get the mapping, just return an empty list
       if ( ! HSQLConnect.tableExists( p.getConnection(), planName ))
           return new ArrayList<String>();

       TableMapping tm = (TableMapping) p.getMapping( CPSPlanting.class, planName );
       // TODO should this query all crop plans, or just one.  Just one for now.
       return HSQLQuerier.getDistinctValuesForColumn( p.getConnection(),
                                                      planName,
                                                      tm.getColumnNameForMethod( "getLocation" ) );
   }

   public synchronized List<String> getCropNameList() {
       TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE );
       return HSQLQuerier.getDistinctValuesForColumn( p.getConnection(),
                                                      CROP_VAR_TABLE,
                                                      tm.getColumnNameForMethod( "getCropName" ) );
   }

   public synchronized List<String> getVarietyNameList( String crop_name, String planName ) {

       // if the plan doesn't exist, then don't get the mapping, just return an empty list
       if ( ! HSQLConnect.tableExists( p.getConnection(), planName ))
           return new ArrayList<String>();
       
       TableMapping tm = (TableMapping) p.getMapping( CPSPlanting.class, planName );
       String plantColName = tm.getColumnNameForMethod( "getVarietyName" );
       tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE );
       String cropColName = tm.getColumnNameForMethod( "getVarietyName" );
       return getDistinctValsFromCVAndPlan( planName, plantColName, cropColName );
   }

   // TODO port to Persist
   private synchronized List<String> getDistinctValsFromCVAndPlan( String planName, String planColName, String cropColName ) {
       ArrayList<String> l = new ArrayList<String>();
       Set set = new HashSet();
       
       if ( planName != null )
           l.addAll( HSQLQuerier.getDistinctValuesForColumn( p.getConnection(),
                                                             planName,
                                                             planColName ) );

       l.addAll( HSQLQuerier.getDistinctValuesForColumn( p.getConnection(),
                                                         CROP_VAR_TABLE,
                                                         cropColName ) );

       // list now contains all values, possibly some duplicates
       // this ensures that the values are unique
       set.addAll ( l  );
       l.clear();
       l.addAll( set );
      
       return l;
       
   }


   public synchronized List<String> getFamilyNameList() {
       TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE );
       return HSQLQuerier.getDistinctValuesForColumn( p.getConnection(),
                                                      CROP_VAR_TABLE,
                                                      tm.getColumnNameForMethod( "getFamilyName" ));
   }

   // TODO port to Persist
   public synchronized List<String> getListOfCropPlans() {
      
       return HSQLQuerier.getDistinctValuesForColumn( p.getConnection(), "CROP_PLANS", "plan_name" );

   }
   
   /* COLUMN NAMES */
   private List<String> getCropVarFilterColumnNames() {
      return columnMap.getCropFilterColumnNames();
//       return getAbbreviatedCropVarColumnNames( true ) + ", keywords, groups";
   }
   
   private String getCropsColumnNames() {
      return  listToCSVString( columnMap.getCropColumnList() );
//       return "*";
   }
   
   private String getVarietiesColumnNames() {
      return getCropsColumnNames();
   }
   
   
   private List<String> getCropColumnList() {
       return columnMap.getCropColumns();
   }

   public List<Integer> getCropDefaultProperties() {
      return columnMap.getDefaultCropPropertyList();
   }

   public List<Integer> getCropDisplayableProperties() {
      return columnMap.getCropPropertyList();
   }
   
   public List<String> getCropDefaultPropertyNames() {
      return columnMap.getDefaultCropColumnList();
   }
   
   public List<String> getCropDisplayablePropertyNames() {
      return columnMap.getCropDisplayableColumns();
   }
   public List<String[]> getCropPrettyNames() {
      return columnMap.getCropPrettyNameMapping(); 
   }
   
   private List<String[]> getCropInheritanceColumnMapping() {
       return columnMap.getCropInheritanceColumnMapping();
   }
   
   private List<String[]> getPlantingCropColumnMapping() {
      return columnMap.getPlantingToCropColumnMapping();
//       return plantingCropColumnMapping;
   }

   public List<Integer> getPlantingDefaultProperties() {
      return columnMap.getDefaultPlantingPropertyList();
   }

   public List<Integer> getPlantingDisplayableProperties() {
      return columnMap.getPlantingPropertyList();
   }
   
   public List<String> getPlantingDefaultPropertyNames() {
      return columnMap.getDefaultPlantingColumnList();
   }
   
   public List<String> getPlantingDisplayablePropertyNames() {
      return columnMap.getPlantingDisplayableColumns();
   }
   
   public List<String[]> getPlantingPrettyNames() {
      return columnMap.getPlantingPrettyNameMapping(); 
   }
   
   public List<String[]> getPlantingShortNames() {
      return columnMap.getPlantingShortNameMapping(); 
   }
   
   private String getAbbreviatedCropPlanColumnNames() {
       return listToCSVString( columnMap.getDefaultPlantingColumnList() );
   }
   private List<String> getCropPlanFilterColumnNames() {
       return columnMap.getPlantingFilterColumnNames();
   }
   
   private String includeMandatoryColumns( String columns ) {
       if ( columns.indexOf( "crop_name" ) == -1 )
           columns = "crop_name, " + columns;
       if ( columns.indexOf( "id" ) == -1 )
           columns = "id, " + columns;
       return columns;
   }
   
   /* END COLUMN NAMES */

   /** Method to cache results of a query and then return those results as a table
    *  @param t Table name
    *  @param col list of columns to select
    *  @param cond conditional statement
    *  @param sort sort statement
    *  @param filter filter statement
    */
   private TableModel cachedListTableQuery( String t, String col, 
                                            String cond, String sort, String filter ) {
      rsListCache = query.storeQuery( t, col, cond, sort, filter );
//      rsListCache.get
      // return query.getCachedResultsAsTable();
      return HSQLQuerier.tableResults( this, rsListCache );
   }
   
   /*
    * CROP LIST METHODS
    */

   public List<CPSCrop> getCropList() {

       TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE);
       String selectSQL = tm.getSelectWhereSql() + tm.getColumnNameForMethod( "getVarietyName" ) + " is null";

       return p.readList( CPSCrop.class, selectSQL );
       
   }
   
   public List<CPSCrop> getVarietyList() {
       
       TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE);
       String selectSQL = tm.getSelectWhereSql() + tm.getColumnNameForMethod( "getVarietyName" ) + " is not null";

       List<CPSCrop> vars = p.readList( CPSCrop.class, selectSQL );

       for ( CPSCrop v : vars)
          performInheritanceForCropVar( v );

       return vars;
   }

   public List<CPSCrop> getCropAndVarietyList() {

       List<CPSCrop> crops = p.readList( CROP_VAR_TABLE, CPSCrop.class );

       for ( CPSCrop c : crops)
          performInheritanceForCropVar( c );

       return crops;
   }

   /*
    * CROP PLAN METHODS
    */
   public void createCropPlan( String planName, int year, String desc ) {
      HSQLDBCreator.createCropPlan( p, planName, year, desc );
      updateDataListeners();
   }
   
   public void updateCropPlan( String planName, int year, String desc ) {
      HSQLDBCreator.updateCropPlan( p, planName, year, desc );
      // TODO commented out because I can't imagine a time (yet) when 
      // we'll need to update when a plan changes metadata
      // updateDataListeners();
   }
   
   public void deleteCropPlan( String planName ) {
      HSQLDBCreator.deleteCropPlan( p, planName );
      updateDataListeners();
   }
   
   public int getCropPlanYear( String planName ) {
      return HSQLQuerier.getCropPlanYear( p.getConnection(), planName );
   }
   
   public String getCropPlanDescription( String planName ) {
      return HSQLQuerier.getCropPlanDescription( p.getConnection(), planName );
   }
   
   public List<CPSPlanting> getCropPlan(String plan_name) {

      debug( "Retrieving crop plan: " + plan_name );
      List<CPSPlanting> cropPlan = p.readList( plan_name, CPSPlanting.class );

      debug( "Crop plan contains " + cropPlan.size() + " plantings." );

      for ( CPSPlanting planting : cropPlan ) {
         performInheritanceForPlanting( planting );
      }

      return cropPlan;
   }

   /**
    * Returns the summation of certain data for a particular crop plan.  DEPRECATED after version .1.5 Beta1.
    * @param plan_name Name of plan to sum on.
    * @param filter Tilter to apply to the plan before summing happens.
    * @return a CPSPlanting object whose columns are filled with the sums from the crop plan.
    * @deprecated 
    */
   public CPSPlanting getSumsForCropPlan( String plan_name, CPSComplexPlantingFilter filter ) {

       return new CPSPlanting();

       // began porting this section of code to Persist but gave up because -- in theory --
       // the sums and such will happen in the "Master" view modules, not in the db
//       TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE );
//
//       final ArrayList<String> colsToSum = new ArrayList<String>( 6 );
//       colsToSum.add( tm.getColumnNameForMethod( "getBedsToPlant" ));
//       colsToSum.add( tm.getColumnNameForMethod( "getRowFtToPlant()" ));
//       colsToSum.add( tm.getColumnNameForMethod( "getPlantsNeeded()" ));
//       colsToSum.add( tm.getColumnNameForMethod( "getPlantsToStart()" ));
//       colsToSum.add( tm.getColumnNameForMethod( "getFlatsNeeded()" ));
//       colsToSum.add( tm.getColumnNameForMethod( "getTotalYield()" ));



       // this is original code and has not been altered
//       try {
//           return resultSetAsPlanting( query.submitSummedCropPlanQuery( plan_name,
//                                                                        getPlantingCropColumnMapping(),
//                                                                        getCropInheritanceColumnMapping(),
//                                                                        colsToSum,
//                                                                        buildComplexFilterExpression( this.getCropPlanFilterColumnNames(),
//                                                                                                      filter )));
//       }
//       catch ( SQLException e ) {
//           e.printStackTrace();
//           return new CPSPlanting();
//       }
   }
   
   
   /* ***************************************************************** */
   /* CROP AND VARIETY METHODS */
   /* ***************************************************************** */

   public CPSPlanting getCommonInfoForPlantings( String plan_name, List<Integer> plantingIDs ) {
      
       TableMapping tm = (TableMapping) p.getMapping( CPSPlanting.class, plan_name);
         
       CPSPlanting planting = p.read( CPSPlanting.class, query.buildCommonInfoQuery( plan_name, tm.getColumns(), plantingIDs ) );

       planting.setCommonIDs( plantingIDs );
       return planting;
      
   }
   
   public CPSCrop getCommonInfoForCrops( List<Integer> cropIDs ) {

       TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE);

       CPSCrop crop = p.read( CPSCrop.class, query.buildCommonInfoQuery( CROP_VAR_TABLE, tm.getColumns(), cropIDs ) );

       crop.setCommonIDs( cropIDs );
       return crop;
       
   }
   
   public CPSCrop getCropInfo( int id ) {
       CPSCrop c;
       if ( id != -1 )
           c = p.readByPrimaryKey( CROP_VAR_TABLE, CPSCrop.class, id );
       else
           c = new CPSCrop();

       performInheritanceForCropVar( c );

       return c;
   }
   
   
   public CPSCrop getVarietyInfo( String cropName, String varName ) {
      
      if ( cropName == null || cropName.equalsIgnoreCase("null") || cropName.equals("") )
         return new CPSCrop();

      TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE);

      String condExp = tm.getColumnNameForMethod( "getCropName" ) + " = " + escapeValue( cropName );
      
      varName = escapeValue( varName );
      
      if ( varName == null || varName.equalsIgnoreCase( "null" ) || varName.equals("") )
         condExp += " AND " + tm.getColumnNameForMethod( "getVarietyName" ) + " IS NULL";
      else
         condExp += " AND " + tm.getColumnNameForMethod( "getVarietyName" ) + " = " + varName;

      String selectSQL = tm.getSelectWhereSql() + condExp;

//      debug( "Looking up variety info with query: " + selectSQL );
      
      CPSCrop c = p.read( CPSCrop.class, selectSQL );
      if ( c == null ) {
//          debug( "Couldn't ready variety info, returning empty crop" );
          c = new CPSCrop();
      }
      else
         performInheritanceForCropVar( c );

      return c;
   }

   public void updateCrop( CPSCrop crop ) {
      HSQLDBCreator.updateCrop( p, crop );
      updateDataListeners();
   }
   
   public void updateCrops( CPSCrop changes, List<Integer> ids ) {
      HSQLDBCreator.updateCrops( p, columnMap, changes, ids );
      updateDataListeners();
   }

   public CPSCrop createCrop(CPSCrop crop) {
      int newID = HSQLDBCreator.insertCrop( p, crop );
      updateDataListeners();
      if ( newID == -1 )
         return new CPSCrop();
      else
         return getCropInfo( newID );
   }
   
   public void deleteCrop( int cropID ) {
       CPSCrop c = p.readByPrimaryKey( CROP_VAR_TABLE, CPSCrop.class, cropID );
       p.delete( CROP_VAR_TABLE, c );
       updateDataListeners();
   }


   protected void performInheritanceForCropVar( CPSCrop crop ) {

      if ( crop == null ) return;

      if ( crop.isVariety() ) {
         
         CPSCrop parent = getCropInfo( crop.getCropName() );
         if ( parent != null && parent.getID() != -1 ) {
            crop.inheritFrom( parent );
         }

      }
   }


   /* ********************************************************************************************************* */
   /* Planting methods */
   /* ********************************************************************************************************* */


   public void deletePlanting( String planName, int plantingID ) {
       CPSPlanting planting = p.readByPrimaryKey( planName, CPSPlanting.class, plantingID );
       p.delete( planName, planting );
       updateDataListeners();
   }
   
   // also called with "empty" or new CPSPlantings, so should handle case where
   // the "cropID" is not valid
   public CPSPlanting createPlanting( String planName, CPSPlanting planting ) {
      int newID = HSQLDBCreator.insertPlanting( p, planName, planting );
      updateDataListeners();
      if ( newID == -1 )
         return new CPSPlanting();
      else
         return getPlanting( planName, newID );
   }

   
   public CPSPlanting getPlanting( String planName, int id ) {

      CPSPlanting planting;
      TableMapping tm = (TableMapping) p.getMapping( CPSPlanting.class, planName );

      if ( id != -1 )
         planting = p.read( CPSPlanting.class, tm.getSelectSql(), new Object[]{ new Integer( id ) } );
      else
         return new CPSPlanting(); // inheriting not necessary

      performInheritanceForPlanting( planting );

      return planting;

   }

   public void updatePlanting( String planName, CPSPlanting planting ) {
      HSQLDBCreator.updatePlanting( p, planName, planting );
//      updateDataListeners();
   }
   
   public void updatePlantings( String planName, CPSPlanting changes, List<Integer> changedIDs ) {

      debug( "saving changes to plan " + planName + ": " + changes.toString() );

      ArrayList<Integer> cropIDs = new ArrayList();
      for ( Integer i : changedIDs ) {
         CPSPlanting planting = getPlanting( planName, i.intValue() );

         // I'm not really sure what these are doing or if they're necessary.  Can anyone clarify?
         if ( changes.getDatum( CPSPlanting.PROP_CROP_NAME ).isNotNull() )
            planting.setCropName( changes.getCropName() );
         if ( changes.getDatum( CPSPlanting.PROP_VAR_NAME).isNotNull() )
            planting.setVarietyName( changes.getVarietyName() );
         
         int cropID = getVarietyInfo( planting.getCropName(), planting.getVarietyName() ).getID();
         
         if ( cropID == -1 )
            cropID = getCropInfo( planting.getCropName() ).getID();
         
         // TODO error if cropID == -1 again
         
         cropIDs.add( new Integer( cropID  ));
      }
      
      HSQLDBCreator.updatePlantings( p, columnMap, planName, changes, changedIDs, cropIDs );
      updateDataListeners();
   }

   protected void performInheritanceForPlanting( CPSPlanting planting ) {

      if ( planting == null ) return;

      TableMapping tm = (TableMapping) p.getMapping( CPSCrop.class, CROP_VAR_TABLE );
      String selectSQL = tm.getSelectWhereSql();
      selectSQL += tm.getColumnNameForMethod( "getCropName" ) + " = " + escapeValue( planting.getCropName() );
      selectSQL += " AND ( " + tm.getColumnNameForMethod( "getVarietyName" ) + " = " + escapeValue( planting.getVarietyName() );
      selectSQL += " OR " + tm.getColumnNameForMethod( "getVarietyName" ) + " IS NULL ) ";

      List<CPSCrop> vars = p.readList( CPSCrop.class, selectSQL );

      CPSCrop parent = null;
      if ( vars.isEmpty() ) {
        // no inheritance to do
        return;
      } else if ( vars.size() > 1 ) {
        // multiple matches == there is a crop && a variety
        // so we need to remove the crop
        CPSCrop c = null;

        for ( CPSCrop v : vars)
          if ( v.isCrop() )
            c = v;

        if ( c != null )
          vars.remove(c);

        if ( vars.size() == 1 )
          parent = vars.get(0);
        else
           debug( "Uh Oh!! Still have more than on variety in list!!" );

        if ( parent != null && c != null )
          parent.inheritFrom( c );

      } else {

        // only one match, so there is nothing to inherit from
        parent = vars.get(0);

      }

      if ( parent != null && parent.getID() != -1 ) {
//         debug( "Inheriting info for planting of [ " + planting.getCropName() + " : " + planting.getVarietyName() + " ] from crop [ " +
//                 parent.getCropName() + " : " + parent.getVarietyName() + " ]" );
         planting.inheritFrom( parent );
      }

   }


   /* ********************************************************************************************************* */
   /* Utility methods */
   /* ********************************************************************************************************* */

   /** 
    * SQL distinguishes between values that are NULL and those that are just
    * blank.  This method is meant to capture our default values
    * and format them properly so that we might detect proper null values
    * upon read.  We use this to distinguish between null values and just
    * data with no entry.
    * 
    * OK, so what constitutes a null value, blank value, and one that is
    * just a default value?  Let's try to answer that for different data types:
    *   Object: only 'null' objects are SQL NULL
    *   String: a string "null" is read as SQL NULL
    *           anything else is considered a valid entry
    *           Therefore we must decide when to pass back "null" vs "" when
    *             strings w/o entries are encountered.
    *  Integer: a value of -1 is an SQL NULL
    *           anything else is valid
    *  Float:   a value of -1.0 is an SQL NULL, all else valid
    *  Date:    a millisecond date of 0 is an SQL NULL
    *           anything else is valid
    * 
    * @param o The object to test and format.
    * @return A string representing the SQL value of Object o.
    */
   /* Currently handles: null, String, Integer, CPSCrop, CPSPlanting */
   public static String escapeValue( Object o ) {
      // if the datum doesn't exist, use NULL
      if      ( o == null )
         return "NULL";
      // if the datum is a string and is only "", use NULL, else escape it
      else if ( o instanceof String )
         if ( ((String) o).equalsIgnoreCase( "null" ) || ((String) o).equals( "" ) )
            return "NULL";
         else {
            String val = o.toString();
            val = val.replaceAll( "'", "''" );
//            val = val.replaceAll( "\"", "\"\"" );
            return "'" + val + "'";
         }
      // if the datum is an int whose value is -1, use NULL
      else if ( o instanceof Integer && ((Integer) o).intValue() == -1 )
         return "NULL";
      else if ( o instanceof Float && ((Float) o).floatValue() == -1.0 )
         return "NULL";
      else if ( o instanceof java.util.Date || o instanceof java.sql.Date ) {
          // cast to util.Date to cover all of our bases, sql.Date is in scope
          // here, so we must use fully qualified name
          if ( ((java.util.Date) o).getTime() == 0 )
              return "NULL";
          else {
              // TODO figure how to make the date format more flexible
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              return "'" + sdf.format((java.util.Date) o) + "'";
          }
      }
      /* Not entirely sure what these are here for.  Actually, the CPSCrop
       * case is probably for data cascading.  Planting case might not be 
       * appropriate */
      else if ( o instanceof CPSCrop )
         return escapeValue( ((CPSCrop) o).getCropName() );
      else if ( o instanceof CPSPlanting )
         return escapeValue( ((CPSPlanting) o).getCropName() );
      else
         return o.toString();
   }

   public static String escapeTableName( String t ) {
        return escapeTableName( t, ESCAPE_WITH_DOUBLE_QUOTES );
   }

   public static String escapeTableName( String t, boolean useSingleQuotes ) {
       // if the "table" is an embedded select statement (enclosed in "()")
       // then don't quote it
       if ( t.trim().matches( "\\(.*\\)" ))
           return t;
       else {
           // if the string contains a quotation mark, escape all quotation marks
           if ( t.matches( ".*\".*" ) )
               t = t.replaceAll( "\\\"", "\"\"" );
           if ( useSingleQuotes )
               return "'" + t + "'";
           else
               return "\"" + t + "\"";
       }
   }
   
   /** captureValue methods are opposite of escapeValue method.
    *  Takes an SQL value and converts it to the correct "default" or "null"
    *  value for our program.
    */
   public static Date captureDate( Date d ) {
      if ( d == null )
         // PENDING this is totally bogus and needs to have a sane "default" date
         return new Date( 0 );
      else
         return d;
   }
   
   public static float getFloat( ResultSet rs, String columnName ) throws SQLException {
       
       float f = rs.getFloat( columnName );
       
       if ( rs.wasNull() )
           f = -1f;
       return captureFloat( f );
   }
   
   public static float captureFloat( float f ) {
//      if ( f <= 0 )
      if ( f == -1 )
         return (float) -1.0;
      else
         return f;
   }
   
   
   public static int getInt( ResultSet rs, String columnName ) throws SQLException {
//       captureInt( rs.getInt( "time_to_tp" ) );
       int i = rs.getInt( columnName );
       
       if ( rs.wasNull() )
           i = -1;
       return captureInt( i );
   }
   
   public static int captureInt( int i ) {
//      if ( i <= 0 )
      if ( i == -1 )
         return -1;
      else
         return i;
   }
   
   public static String captureString( String s ) {
      if ( s == null || s.equalsIgnoreCase("null") || s.equals("") )
         return null;
      else
         return s;
   }
   
   /**
    * Create a comma delimited string of integers from an List of Integers.
    * @param ids List<Integer> to convert
    * @return comma delimited string of integers
    */
   public static String intListToIDString( List<Integer> ids ) {
      String idString = "";
      for ( Integer i : ids )
         idString += i.toString() + ", ";
      idString = idString.substring( 0, idString.lastIndexOf( ", " ));
      return idString;
   }
   
   public static String listToCSVString( List l ) {
       String s = "";
       for ( Object o : l )
           s += o.toString() + ", ";
       s = s.substring( 0, s.lastIndexOf( ", " ));
       return s;
   }

   
   private String sortColumnFromPropertyNum( int recordType, int prop ) {
      String sortCol = " asc";
      
      if ( prop < 0 ) {
         sortCol = " desc";
         prop = -1 * prop;
      }
              
      if      ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
         sortCol = columnMap.getCropColumnNameForProperty(prop) + sortCol;
      else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
         sortCol = columnMap.getPlantingColumnNameForProperty(prop) + sortCol;
      else
         sortCol = "";
      
      return sortCol;
   }
   
   public String propNameFromPropNum( int recordType, int propertyNum ) {
      if      ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
         return columnMap.getCropColumnNameForProperty( propertyNum );
      else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
         return columnMap.getPlantingColumnNameForProperty( propertyNum );
      else
         return "UnknownProperty";
   }

   public int propNumFromPropName( int recordType, String propertyName ) {
      if      ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
         return columnMap.getCropPropertyNumFromName( propertyName );
      else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
         return columnMap.getPlantingPropertyNumFromName( propertyName );
      else
         return 0;
   }

   
   
   public String propertyNumListToColumnString( int recordType, List<Integer> props ) {
      String cols = "";
      for ( Integer prop : props )
         if ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
            cols += columnMap.getCropColumnNameForProperty( prop ) + ", ";
         else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
            cols += columnMap.getPlantingColumnNameForProperty( prop ) + ", ";
      cols = cols.substring( 0, cols.lastIndexOf( ", " ));
      return cols;
   }

   
   public int shutdown() {
      try {
          // TODO check to see which which mode we're in: if server, don't do the following
          // TODO port to Persist in a better manner, perhaps add a shutdown() method to Persist?
         Statement st = p.getConnection().createStatement();
         st.execute("SHUTDOWN");
         p.getConnection().close();
      }
      catch ( SQLException ex ) {
         ex.printStackTrace();
      }
      return 0;
   }

    @Override
    protected void updateDataListeners() {
        super.updateDataListeners();
    }
    
    @Override
    protected int saveState() { throw new UnsupportedOperationException( "Not supported yet." ); }

   public JPanel getConfigurationDisplay () { return null; }

   public CPSWizardPage[] getConfigurationWizardPages () {
      return new CPSWizardPage[] { new NewPlanWizardPage( this ) };
   }

   public void resetConfiguration () {}
   public void resetConfigurationToDefaults () {}
   public void saveConfiguration () {}

   
   private static String toXML(ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        StringBuffer xml = new StringBuffer();
        xml.append("<Results>\n");

        while (rs.next())
        {
            xml.append("  <Row>\n");

            for (int i = 1; i <= colCount; i++)
            {
               String columnName = rsmd.getColumnName( i );
               Object value = rs.getObject( i );
               if ( rs.wasNull() )
                  continue;
               xml.append( "    <" + columnName + ">" );
               xml.append( value.toString().trim() );
               xml.append( "</" + columnName + ">\n" );
            }
            xml.append(" </Row>\n");
        }

        xml.append("</Results>\n");

        return xml.toString();
    }


}
