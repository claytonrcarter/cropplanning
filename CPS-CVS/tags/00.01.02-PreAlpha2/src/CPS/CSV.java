/* CSV.java - Created on April 3, 2007
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

// TODO, if this class is to be fleshed out, be sure to add the "updateDataListeners" calls

package CPS;

import CPS.Data.*;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSExporter;
import java.util.ArrayList;
import javax.swing.table.TableModel;
import com.csvreader.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class CSV extends CPSDataModel implements CPSExporter {

    
   CSVTableModel ctm;
   boolean exportOnly;
   CPSDateValidator dateValidator;
   
   public CSV() {
       setModuleName("CSV");
       setModuleType( MOD_TYPE_DATAMODEL );
       setModuleDescription("A CSV DataModel (for import/export only)");
       setModuleVersion( GLOBAL_DEVEL_VERSION );
       
       exportOnly = true;
       dateValidator = new CPSDateValidator();
   }
   
   public CSV( String file ) {
       this();
       ctm = new CSVTableModel( file );
       if ( ctm != null )
           exportOnly = false;
   }


   public ArrayList<CPSCrop> getCropsAndVarietiesAsList() {
       if ( exportOnly )
           return new ArrayList<CPSCrop>();
       
      ArrayList<CPSCrop> l = new ArrayList<CPSCrop>();
      CPSCrop temp;
       
      System.out.println( "Exporting data." );
        
      for ( int i = 0; i < ctm.getRowCount(); i++ ) {
         l.add( this.getCropInfoForRow(i) );
      }
      
      return l;
   }

   
   public CPSCrop getCropInfoForRow( int selectedRow ) {
        
      CPSCrop temp = new CPSCrop();
      
      if ( exportOnly )
          return temp;
         
      //"crop_name","var_name","similar_to","bot_name","fam_name",
      //"Groups","Successions","Desc","Keywords","Fudge","other_req","Notes",
      //"Maturity","mat_adjust","misc_adjust",
      //"seeds_sources","seeds_item_codes","seeds_unit_size"
         
      //temp.setID( (int) ( Math.random() * 10000 ) );
      temp.setID( selectedRow );
      int col = 0;
      temp.setCropName( ctm.getStringAt( selectedRow, col++ ) );
      temp.setVarietyName( ctm.getStringAt( selectedRow, col++ ));

      temp.setSimilarCrop( ctm.getStringAt( selectedRow, col++ ) );
      
      temp.setBotanicalName( ctm.getStringAt( selectedRow, col++ ));
      temp.setFamilyName( ctm.getStringAt( selectedRow, col++ ));

      temp.setGroups( ctm.getStringAt( selectedRow, col++ ));
      // temp.setSuccessions( Boolean.parseBoolean( ctm.getStringAt( selectedRow, col++ )));
      col++; // skip the successions column
      temp.setCropDescription( ctm.getStringAt( selectedRow, col++ ));
      temp.setKeywords( ctm.getStringAt( selectedRow, col++ ));
      col++; // fudge
      temp.setOtherRequirements( ctm.getStringAt( selectedRow, col++ ));
      temp.setNotes( ctm.getStringAt( selectedRow, col++ ));
         
      int i = ctm.getIntAt( selectedRow, col++ );
      if ( i <= 0 )
         i = -1;
      temp.setMaturityDays( i );
      
      return temp;

   }
   
   public CPSCrop getCropInfo( String cropName ) {
   
      CPSCrop temp = new CPSCrop();
      
      if ( exportOnly )
          return temp;
      
      if ( ! cropName.equals("") )
         for ( int i = 0; i < ctm.getRowCount(); i ++ ) {
            // match crop_name == cropName and var_name == ""
            if ( ctm.getStringAt( i, 0 ).equalsIgnoreCase( cropName ) &&
                 ctm.getStringAt( i, 1 ).equalsIgnoreCase( "" ) ) {
               temp = getCropInfoForRow( i );
               break;
            }
         }
      
      return temp;
   }

    public void exportCropPlan( String filename, String planName, ArrayList<CPSPlanting> plantings ) {
        
        ArrayList<CPSRecord> records = new ArrayList<CPSRecord>( plantings.size() );
        for ( CPSPlanting p : plantings )
            records.add( (CPSRecord) p );
        
        this.exportRecords( filename, "crop plan: " + planName, records );
        
        
    }

    public void exportCropsAndVarieties( String filename, ArrayList<CPSCrop> crops ) {
        
        ArrayList<CPSRecord> records = new ArrayList<CPSRecord>( crops.size() );
        for ( CPSCrop c : crops )
            records.add( (CPSRecord) c );
        
        this.exportRecords( filename, "Crops and Varieties", records );
        
    }

    private void exportRecords( String filename, String recordType, ArrayList<CPSRecord> records ) {

        final boolean EXPORT_SPARSE_DATA = true;
        
        CsvWriter csvOut = new CsvWriter( filename );
        // mark text with double quotes
        csvOut.setTextQualifier('"');
        // set default comment character to hash
        csvOut.setComment('#');
        
        try {
            // write comment about date, time, etc
            csvOut.writeComment( " Created by CropPlanning Software" );
            csvOut.writeComment( " Available at http://cropplanning.googlecode.com" );
            csvOut.writeComment( " Records exported: " + recordType );
            csvOut.writeComment( " Exported: " + new Date().toString() );
            
            // collect header information
            CPSRecord c = records.get( 0 );
            CPSDatum d;
            Iterator it = c.iterator();
            HashMap<String, Integer> colMap = new HashMap<String, Integer>();
            int columnCount = 0;
            while ( it.hasNext() ) {
                d = (CPSDatum) it.next();
                colMap.put( d.getColumnName(), new Integer( columnCount++ ) );
            }
            
            
            String[] row = new String[columnCount];
            
            // prepare and write header
            it = c.iterator();
            while ( it.hasNext() ) {
                d = (CPSDatum) it.next();
                int colForDatum = colMap.get( d.getColumnName() ).intValue();
                row[colForDatum] = d.getColumnName();
            }
            csvOut.writeRecord( row );
            
            // write rows
            for ( CPSRecord record : records ) {
                row = new String[columnCount];
                it = record.iterator();
                while ( it.hasNext() ) {
                    d = (CPSDatum) it.next();
                    int colForDatum = colMap.get( d.getColumnName() ).intValue();
                    
                    if ( EXPORT_SPARSE_DATA && ! d.isConcrete() ) {
                        row[colForDatum] = "";
                        continue;
                    }
                    
                    Object o = d.getDatum();
                    if ( o instanceof java.util.Date ||
                         o instanceof java.sql.Date )
                        row[colForDatum] = dateValidator.format( (Date) d.getDatum() );
                    else
                        row[colForDatum] = d.getDatum().toString();
                }
                csvOut.writeRecord( row );
            }
            
            // write comment "EOF"
            csvOut.writeComment( " End of file" );
            
            // close
            csvOut.close();
        }
        catch ( Exception ignore ) { ignore.printStackTrace(); }
            
    }
    
    public String getExportFileDefaultExtension() {
        return "csv";
    }
   
   
   
   
   
   
   public TableModel getCropAndVarietyTable() { return null; }
   
   public ArrayList<String> getListOfCropPlans() { return null; }
   public ArrayList<String> getCropNameList() { return null; }
   
   public void createNewCropPlan(String plan_name) {}
   public void retrieveCropPlan(String plan_name) {}
   public void filterCropPlan(String plan_name, String filter) {}
   public TableModel getCropTable() { return null; }
   public TableModel getVarietyTable() { return null; }
   public void shutdown() {}
   
   public void updateCrop(CPSCrop crop) {}
   public CPSCrop createCrop(CPSCrop crop) { return null; }

   public TableModel getCropTable(String sortCol) { return null; }
   public TableModel getVarietyTable(String sortCol) { return null; }
   public TableModel getCropAndVarietyTable(String sortCol) { return null; }
   public TableModel getCropTable(String sortCol, CPSComplexFilter filter) { return null; }
   public TableModel getVarietyTable(String sortCol, CPSComplexFilter filter) { return null; }
   public TableModel getCropAndVarietyTable(String sortCol, CPSComplexFilter filter) { return null; }

   public CPSCrop getVarietyInfo(String cropName, String varName) { return null; }
   public CPSCrop getCropInfo(int CropID) { return null; }

   public void createCropPlan(String plan_name) {}
   public void updateCropPlan(String plan_name) {}
   public TableModel getCropPlan(String plan_name) { return null; }
   public TableModel getCropPlan(String plan_name, String sortCol) { return null; }
   public TableModel getCropPlan(String plan_name, String sortCol, CPSComplexPlantingFilter filterString) { return null; }
   
   public CPSPlanting createPlanting( String plan_name, CPSPlanting planting ) { return null; };
   public CPSPlanting getPlanting(String planName, int PlantingID) { return null; }
   public void updatePlanting(String planName, CPSPlanting planting) {}
   
   public void deletePlanting( String planting, int plantingID ) {}
   public void deleteCrop( int cropID ) {}
   
   public CPSCrop getCommonInfoForCrops( ArrayList<Integer> cropIDs ) { return null; }
   @Override
   public CPSPlanting getCommonInfoForPlantings( String planName,
                                                 ArrayList<Integer> plantingIDs ) {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   @Override
   public ArrayList<String> getFamilyNameList() {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   @Override
   public void updateCrops( CPSCrop changes,
                            ArrayList<Integer> cropIDs ) {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   @Override
   public void updatePlantings( String planName, CPSPlanting changes,
                                ArrayList<Integer> plantingIDs ) {
      throw new UnsupportedOperationException( "Not supported yet." );
   }

    @Override
    public TableModel getCropPlan(String plan_name, String columns, String sortCol, CPSComplexPlantingFilter filterString) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<String> getPlantingDisplayableColumns() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<String> getCropDisplayableColumns() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<String> getPlantingDefaultColumns() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public ArrayList<String[]> getPlantingPrettyNames() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public ArrayList<String> getCropDefaultColumns() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public ArrayList<String[]> getCropPrettyNames() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public TableModel getCropTable( String columns, String sortCol, CPSComplexFilter filterString ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }


    @Override
    public TableModel getVarietyTable( String columns, String sortCol, CPSComplexFilter filterString ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }


    @Override
    public TableModel getCropAndVarietyTable( String columns, String sortCol, CPSComplexFilter filterString ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }


    @Override
    public CPSPlanting getSumsForCropPlan( String plan_name, CPSComplexPlantingFilter filterString ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
   @Override
    public ArrayList<String[]> getPlantingShortNames() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public ArrayList<String> getFieldNameList( String planName ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public ArrayList<CPSPlanting> getCropPlanAsList( String planName ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public ArrayList<String> getVarietyNameList( String crop_name, String cropPlan ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public ArrayList<String> getFlatSizeList( String planName ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
   
    
    
}
