/* JDBCTableModel.java
 * Copyright © 2008 SYS-CON Media. All Rights Reserved.
 * 
 * This file is distributed as part of the project "Crop Planning Software".
 * For more information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 *
 * The source for this class was originally found at:
 * 
 *     http://photos.sys-con.com/story/res/36848/source.html
 *
 * With an accompanying article here:
 * 
 *     http://jdj.sys-con.com/read/36848.htm
 * 
 * No statement is made at that site regarding the licensing of this source
 * code.  As such we are using it AS IS and WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE.
 * 
 */

package jdbctablemodel;

// new class. This is the table model
import javax.swing.table.*;
import java.sql.*;
import java.util.Vector;

public class JDBCTableModel extends AbstractTableModel {
   Connection con;
   Statement stat;
   ResultSet rs;
   int li_cols = 0;
   Vector allRows;
   Vector row;
   Vector newRow;
   Vector colNames;
   String dbColNames[];
   String pkValues[];
   String tableName;
   ResultSetMetaData myM;
   String pKeyCol;
   Vector deletedKeys;
   Vector newRows;
   boolean ibRowNew = false;
   boolean ibRowInserted = false;
   
   JDBCTableModel(){
      try{
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      } catch (ClassNotFoundException e){
         System.out.println("Cannot Load Driver!");
      }
      try{
         String url = "jdbc:odbc:northwind";
         con = DriverManager.getConnection(url);
         stat = con.createStatement();
         rs = stat.executeQuery("SELECT productid, productname," +
          "quantityperunit, unitsinstock," +
          "discontinued from products;");
         deletedKeys = new Vector();
         newRows = new Vector();
         myM = rs.getMetaData();
         tableName = myM.getTableName(1);
         li_cols = myM.getColumnCount();
         dbColNames = new String[li_cols];
         for(int col = 0; col < li_cols; col ++){
            dbColNames[col] = myM.getColumnName(col + 1);
         }
         allRows = new Vector();
         while(rs.next()){
            newRow = new Vector();
            for(int i = 1; i <= li_cols; i++){
               newRow.addElement(rs.getObject(i));
            } // for
            allRows.addElement(newRow);
         } // while
      } catch(SQLException e){
         System.out.println(e.getMessage());
      }
   }
   public Class getColumnClass(int col){
      return getValueAt(0,col).getClass();
   }
   public boolean isCellEditable(int row, int col){
      if (ibRowNew){
         return true;
      }
      if (col == 0){
         return false;
      } else {
         return true;
      }
   }
   public String getColumnName(int col){
      return dbColNames[col];
   }
   public int getRowCount(){
      return allRows.size();
   }
   public int getColumnCount(){
      return li_cols;
   }
   public Object getValueAt(int arow, int col){
      row = (Vector) allRows.elementAt(arow);
      return row.elementAt(col);
   }
   public void setValueAt(Object aValue, int aRow, int aCol) {
      Vector dataRow = (Vector) allRows.elementAt(aRow);
      dataRow.setElementAt(aValue, aCol);
      fireTableCellUpdated(aRow, aCol);
   }
   public void updateDB(){
      String updateLine[] = new String[dbColNames.length];
      try{
         DatabaseMetaData dbData = con.getMetaData();
         String catalog;
// Get the name of all of the columns for this table
         String curCol;
         colNames = new Vector();
         ResultSet rset1 = dbData.getColumns(null,null,tableName,null);
         while (rset1.next()) {
            curCol = rset1.getString(4);
            colNames.addElement(curCol);
         }
         rset1.close();
         pKeyCol = colNames.firstElement().toString();
         
// Go through the rows and perform INSERTS/UPDATES/DELETES
         int totalrows;
         totalrows = allRows.size();
         String dbValues[];
         Vector currentRow = new Vector();
         pkValues = new String[allRows.size()];
         
// Get column names and values
         for(int i=0;i < totalrows;i++){
            currentRow = (Vector) allRows.elementAt(i);
            int numElements = currentRow.size();
            dbValues = new String[numElements];
            for(int x = 0; x < numElements; x++){
               String classType = currentRow.elementAt(x).getClass().toString();
               int pos = classType.indexOf("String");
               if(pos > 0){ // we have a String
                  
                  dbValues[x] = "'" + currentRow.elementAt(x) + "'";
                  updateLine[x] = dbColNames[x] + " = " + "'" + currentRow.elementAt(x) + "',";
                  if (dbColNames[x].toUpperCase().equals(pKeyCol.toUpperCase())){
                     pkValues[i] = currentRow.elementAt(x).toString() ;
                  }
               }
               pos = classType.indexOf("Integer");
               if(pos > 0){ // we have an Integer
                  dbValues[x] = currentRow.elementAt(x).toString();
                  if (dbColNames[x].toUpperCase().equals(pKeyCol.toUpperCase())){
                     pkValues[i] = currentRow.elementAt(x).toString();
                  } else{
                     updateLine[x] = dbColNames[x] + " = " + currentRow.elementAt(x).toString() + ",";
                  }
               }
               pos = classType.indexOf("Boolean");
               if(pos > 0){ // we have a Boolean
                  dbValues[x] = currentRow.elementAt(x).toString();
                  updateLine[x] = dbColNames[x] + " = " + currentRow.elementAt(x).toString() + ",";
                  if (dbColNames[x].toUpperCase().equals(pKeyCol.toUpperCase())){
                     pkValues[i] = currentRow.elementAt(x).toString() ;
                  }
               }
            } // For Loop
            
// If we are here, we have read one entire row of data. Do an UPDATE or an INSERT
            int numNewRows = newRows.size();
            int insertRow = 0;
            boolean newRowFound;
            
            for (int z = 0;z < numNewRows;z++){
               insertRow = ((Integer) newRows.get(z)).intValue();
               if(insertRow == i+1){
                  StringBuffer InsertSQL = new StringBuffer();
                  InsertSQL.append("INSERT INTO " + tableName + " (");
                  for(int zz=0;zz<=dbColNames.length-1;zz++){
                     if (dbColNames[zz] != null){
                        InsertSQL.append(dbColNames[zz] + ",");
                     }
                  }
// Strip out last comma
                  InsertSQL.replace(InsertSQL.length()-1,InsertSQL.length(),")");
                  InsertSQL.append(" VALUES(" + pkValues[i] + ",");
                  for(int c=1;c < dbValues.length;c++){
                     InsertSQL.append(dbValues[c] + ",");
                  }
                  InsertSQL.replace(InsertSQL.length()-1,InsertSQL.length(),")");
                  stat.executeUpdate(InsertSQL.toString());
                  ibRowInserted=true;
               }
            } // End of INSERT Logic
            
// If row has not been INSERTED perform an UPDATE
            if(ibRowInserted == false){
               StringBuffer updateSQL = new StringBuffer();
               updateSQL.append("UPDATE " + tableName + " SET ");
               for(int z=0;z<=updateLine.length-1;z++){
                  if (updateLine[z] != null){
                     updateSQL.append(updateLine[z]);
                  }
               }
// Replace the last ',' in the SQL statement with a blank. Then add WHERE clause
               updateSQL.replace(updateSQL.length()-1,updateSQL.length()," ");
               updateSQL.append(" WHERE " + pKeyCol + " = " + pkValues[i] );
               stat.executeUpdate(updateSQL.toString());
            } //for
         }
      } catch(Exception ex){
         System.out.println("SQL Error! Cannot perform SQL UPDATE " + ex.getMessage());
      }
// Delete records from the DB
      try{
         int numDeletes = deletedKeys.size();
         String deleteSQL;
         for(int i = 0; i < numDeletes;i++){
            deleteSQL = "DELETE FROM " + tableName + " WHERE " + pKeyCol + " = " +
             ((Integer) deletedKeys.get(i)).toString();
            System.out.println(deleteSQL);
            stat.executeUpdate(deleteSQL);
         }
// Assume deletes where successful. Recreate Vector holding PK Keys
         deletedKeys = new Vector();
      } catch(Exception ex){
         System.out.println(ex.getMessage());
      }
   }
   public void deleteRow(int rowToDelete){
// Mark row for a SQL DELETE from the Database
      Vector deletedRow = (Vector) allRows.get(rowToDelete);
      Integer pkKey = (Integer) deletedRow.get(0);
      deletedKeys.add(pkKey);
      allRows.remove(rowToDelete);
      fireTableRowsDeleted(rowToDelete,rowToDelete);
   }
   public void addRow(){
// Mark the row for a SQL INSERT in the Database
      newRows.add(new Integer(allRows.size() +1));
// Get the total number of rows in the Vector
      int rowNumber = allRows.size();
      int pos;
      
// Get what a row looks like
      int numElements = newRow.size();
      Vector newRowVect = new Vector();
      for(int i = 0; i < numElements; i++){
         String classType = newRow.elementAt(i).getClass().toString();
         pos = classType.indexOf("String");
         if(pos > 0){ // we have a String
            String blankString = new String();
            newRowVect.addElement(blankString);
         }
         pos = classType.indexOf("Integer");
         if(pos > 0){ // we have an Integer
            Integer blankInt = new Integer("0");
            newRowVect.addElement(blankInt);
         }
         pos = classType.indexOf("Boolean");
         if(pos > 0){ // we have a Boolean
            Boolean blankBool = new Boolean(false);
            newRowVect.addElement(blankBool);
         }
      }
      allRows.addElement(newRowVect);
      ibRowNew = true;
      this.isCellEditable(allRows.size(),0);
      System.out.println(allRows.size());
      fireTableRowsInserted(rowNumber,rowNumber);
   }
}

