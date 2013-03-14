/* JTableConverter.java - created: Jan 31, 2008
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

package CPS.Core.TODOLists;

import CPS.Data.CPSCalculations;
import CPS.Data.CPSComplexPlantingFilter;
import CPS.Data.CPSDateValidator;
import CPS.Data.CPSPlanting;
import CPS.Data.CPSRecord;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSGlobalSettings;
import CPS.ModuleManager;
import CPS.UI.Swing.CPSTable;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;

public class PDFExporter {

    private final Font fontHeadFootItal, fontHeadFootReg, fontTableReg, fontTableHead;
    private Font fontTableItal;
    private final Font fontPageHeader;

    Document tempDoc = null;

  public PDFExporter() {
    
    fontHeadFootItal = FontFactory.getFont( FontFactory.HELVETICA_OBLIQUE, 8 );
    fontHeadFootReg = FontFactory.getFont( FontFactory.HELVETICA, 8 );

    fontTableReg = FontFactory.getFont( FontFactory.HELVETICA, 10 );
    fontTableHead = FontFactory.getFont( FontFactory.HELVETICA_BOLD, 10 );
    fontTableItal = FontFactory.getFont( FontFactory.HELVETICA_OBLIQUE, 10 );

    fontPageHeader = FontFactory.getFont( FontFactory.HELVETICA_BOLD, 14 );

  }




    
    public void export( JTable jtable, String filename,
                        String farmName, String docTitle, String tableTitle ) {

        startExport( jtable, filename, farmName, docTitle, tableTitle );
        endExport();
    }

    public void exportPlantingList( AdjacentGroupingList<CPSPlanting> list,
                                  CPSExportTableFormat<CPSPlanting> tableFormat,
                                  String filename, String farmName,
                                  String docTitle, String tableTitle ) {
        
        startExport( filename, farmName, docTitle, tableTitle );
        addTable( convertPlantingList( list, tableFormat ), tableTitle );
        endExport();
    }


    public void exportLandscape( JTable jtable,
                                 String filename,
                                 String farmName,
                                 String docTitle,
                                 String tableTitle ) {

        startExport( filename, farmName, docTitle, tableTitle, PageSize.LETTER.rotate() );
        addTable( convertJTable( jtable ), tableTitle );
        endExport();
    }


    public void startExport( JTable jtable,
                            String filename,
                            String farmName,
                            String docTitle,
                            String tableTitle ) {

        startExport( filename, farmName, docTitle, tableTitle );
        addTable( convertJTable( jtable ), tableTitle );

    }

    public void startExport( String filename,
                            String farmName,
                            String docTitle,
                            String tableTitle ) {
        startExport( filename, farmName, docTitle, tableTitle, PageSize.LETTER );
    }
    
    public void startExport( String filename,
                            String farmName,
                            String docTitle,
                            String tableTitle,
                            Rectangle pageSize ) {
        tempDoc = prepareDocument( filename, docTitle, farmName,
                                   "CropPlanning Software - http://cropplanning.googlecode.com",
                                   pageSize );
        tempDoc.open();
    }
    
    public void addTable( PdfPTable pTable, String tableTitle ) {
        try {
          if ( tableTitle != null ) {
            tempDoc.add( new Paragraph( tableTitle, fontPageHeader ) );
          }
            tempDoc.add( new Paragraph( Chunk.NEWLINE ) ); // TODO halve the height of this
            pTable.setWidthPercentage( 100 ); // 100% page width
            tempDoc.add( pTable );
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }
    
    public void addPage( JTable jtable, String tableTitle ) {
        try {
            tempDoc.newPage();
            addTable( convertJTable( jtable ), tableTitle );
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }
    
    public void endExport() {
        tempDoc.close();
    }
    
    
    
    private Document prepareDocument( String filename, 
                                      final String title,
                                      final String author,
                                      final String creator,
                                      final Rectangle pageSize ) {
        
        System.out.println( "DEBUG(PDFExporter): Creating document: " + filename );
        
        Document d = new Document();

        d.setPageSize( pageSize );
        // TODO alter page orientation?  maybe useful for seed order worksheet

        d.addTitle( title );
        d.addAuthor( author );
//        d.addSubject( );
//        d.addKeywords( );
        d.addCreator( creator );
        
        // left, right, top, bottom - scale in points (~72 points/inch)
        d.setMargins( 35, 35, 35, 44 );
        
        try {
            PdfWriter writer = PdfWriter.getInstance( d, new FileOutputStream( filename ) );
            // add header and footer
            writer.setPageEvent( new PdfPageEventHelper() {
                 public void onEndPage( PdfWriter writer, Document document ) {
                    try {
                        Rectangle page = document.getPageSize();
                        
                        PdfPTable head = new PdfPTable( 3 );
                        head.getDefaultCell().setBorderWidth(0);
                        head.getDefaultCell().setHorizontalAlignment( PdfPCell.ALIGN_LEFT );
                        head.addCell( new Phrase( author, fontHeadFootItal ) );
                        
                        head.getDefaultCell().setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
                        head.addCell( new Phrase( title, fontHeadFootReg ));
                        
                        head.getDefaultCell().setHorizontalAlignment( PdfPCell.ALIGN_RIGHT );
                        head.addCell( "" );
                        
                        head.setTotalWidth( page.getWidth() - document.leftMargin() - document.rightMargin() );
                        head.writeSelectedRows( 0, -1, document.leftMargin(), page.getHeight() - document.topMargin() + head.getTotalHeight(),
                                                writer.getDirectContent() );
                        
                        PdfPTable foot = new PdfPTable( 3 );
                        
                        foot.getDefaultCell().setBorderWidth(0);
                        foot.getDefaultCell().setHorizontalAlignment( PdfPCell.ALIGN_LEFT );
                        foot.addCell( new Phrase( creator, fontHeadFootItal ) );
                        
                        foot.getDefaultCell().setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
                        foot.addCell("");
                        
                        foot.getDefaultCell().setHorizontalAlignment( PdfPCell.ALIGN_RIGHT );
                        foot.addCell( new Phrase( "Page " + document.getPageNumber(), fontHeadFootReg ));
                        
                        
                        foot.setTotalWidth( page.getWidth() - document.leftMargin() - document.rightMargin() );
                        foot.writeSelectedRows( 0, -1, document.leftMargin(), document.bottomMargin(),
                                                writer.getDirectContent() );
                    } catch ( Exception e ) {
                        throw new ExceptionConverter( e );
                    }
                }
            } );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        
        return d;
    }

    
    public PdfPTable convertPlantingList( AdjacentGroupingList<CPSPlanting> plantingList,
                                        CPSExportTableFormat<CPSPlanting> tableFormat ) {

      boolean tableIncludesNotes = false;
       boolean rowHasNotes = false;
       String notesValue = "";
       int notesIndex = -1;

       //*********************************************************************//
       // find Notes column (if there is one)
       //*********************************************************************//
       for ( int col = 0; col < tableFormat.getColumnCount(); col++ ) {
          String headName;
          headName = tableFormat.getColumnName( col );
          if ( headName.equalsIgnoreCase( "Planting Notes" ) ) {
             tableIncludesNotes = true;
             notesIndex = col;
          }
       }

       int colCount = (tableIncludesNotes) ? tableFormat.getColumnCount() - 1 : tableFormat.getColumnCount();
       PdfPTable table = new PdfPTable( colCount );

        //********************************************************************//
        // create header row
        //********************************************************************//
        for ( int col = 0; col < tableFormat.getColumnCount(); col++ ) {
           String headName;
           headName = tableFormat.getColumnName( col );
           if ( ! tableIncludesNotes || col != notesIndex ) {
             HeadCell hc = new HeadCell( headName );

             if ( tableFormat.getColumnClass( col ).equals( Boolean.class ) ||
                  tableFormat.getColumnClass( col ).equals( Integer.class ) ||
                  tableFormat.getColumnClass( col ).equals( Double.class ) ||
                  tableFormat.getColumnClass( col ).equals( Float.class ) ) {
               hc.setRotation(90);
               hc.setFixedHeight( 60f );
             }

             table.addCell( hc );
           }
        }
        table.setHeaderRows( 1 );

        // unclear why, but we have to add data to the calculation list
        // if want it to work.  setting the calculation on a filled list
        // isn't working for us
        BasicEventList<CPSPlanting> calculationList = new BasicEventList<CPSPlanting>();
        CPSCalculations.SumPlantsFlats summaryGH =
                    new CPSCalculations.SumPlantsFlats( calculationList );
        CPSCalculations.SumBedsRowftFlats summaryField =
                    new CPSCalculations.SumBedsRowftFlats( calculationList );

        //********************************************************************//
        // Iterate over the groups
        //********************************************************************//
        for ( Iterator<List<CPSPlanting>> it = plantingList.iterator(); it.hasNext(); ) {

          List<CPSPlanting> group = it.next();

          //****************************************************************//
          // If this is a list of multiple items,
          // Do the summary row
          //****************************************************************//
          if ( group.size() > 1 ) {

            rowHasNotes = false;
            CPSPlanting p = group.get(0);

            calculationList.clear();
            calculationList.addAll( group );

            for ( int col = 0; col < tableFormat.getColumnCount(); col++ ) {

              Object cellValue = tableFormat.getColumnValue( p, col );

              if ( tableIncludesNotes && col == notesIndex )
                continue;

              RegCell c;

              if ( ! tableFormat.isSummaryColumn( col ) ) {

                c = new SummaryCell("");

                if ( tableFormat.getPropNumForColumn( col ) == CPSPlanting.PROP_VAR_NAME )
                  c = new SummaryTitleCell();
                else if ( tableFormat.getPropNumForColumn( col ) == CPSPlanting.PROP_BEDS_PLANT )
                  c.setText( "" + summaryField.beds );
                else if ( tableFormat.getPropNumForColumn( col ) == CPSPlanting.PROP_ROWFT_PLANT )
                  c.setText( "" + summaryField.rowUnitLengthes );
                else if ( tableFormat.getPropNumForColumn( col ) == CPSPlanting.PROP_PLANTS_NEEDED )
                  c.setText( "" + summaryGH.plantsNeeded );
                else if ( tableFormat.getPropNumForColumn( col ) == CPSPlanting.PROP_PLANTS_START )
                  c.setText( "" + summaryGH.plantsToStart );
                else if ( tableFormat.getPropNumForColumn( col ) == CPSPlanting.PROP_FLATS_NEEDED )
                  c.setText( CPSRecord.formatFloat( summaryGH.flats, 2 ));
                
              } else {

                c = new TopBorderCell("");

                setContentsOfCell( c, cellValue );
                
              }

              table.addCell( c );
            }


           //*****************************************************//
           // now go over and spit out the actual list elements
           //*****************************************************//
            ArrayList<RegCell> cellsToAddLater = 
                    new ArrayList<RegCell>( group.size() * ( colCount - 2 ) );
            Map<String, List<String>> notesMap = new HashMap<String, List<String>>();
            int i = 0;
            for ( CPSPlanting e : group ) {

              boolean lastRow = false;
              if ( ++i == group.size() )
                lastRow = true;

              for ( int col = 0; col < tableFormat.getColumnCount(); col++ ) {

                // skip the summary cells
                if ( tableFormat.isSummaryColumn( col ) )
                  continue;

                if ( tableIncludesNotes && col == notesIndex ) {
                  String rowNotes = tableFormat.getColumnValue( e, col).toString();
                  if ( rowNotes.equals( "" ) )
                    continue;

                  if ( notesMap.get( rowNotes ) == null )
                    notesMap.put( rowNotes, new ArrayList<String>() );
                  notesMap.get( rowNotes ).add( e.getVarietyName() );
                  continue;
                }

                Object cellValue = tableFormat.getColumnValue( e, col);

                RegCell c = new NoBorderCell("");
                if ( lastRow )
                  c = new LastInGroupCell( "" );

                setContentsOfCell( c, cellValue );

//                table.addCell( c );
                cellsToAddLater.add( c );

              }

            }


           //*****************************************************//
           // now deal w/ the Notes data
           // add it even it's empty to make sure it's all spaced right
           //*****************************************************//
           SpecialNoteCell groupNotesCell = new SpecialNoteCell();
           if ( tableIncludesNotes && ! notesMap.isEmpty() ) {
             Phrase notesPhrase = new Phrase( "Notes: ", fontHeadFootItal);
             float indextWidth = 3 + ColumnText.getWidth( notesPhrase );

             // all of the notes are the same, or only one entry had a note
             // TODO this is assuming that all the ntoes were the same
             // if only entry had a note, the way to handle it would be to
             // see if the .size() of the only value is == to the .size()
             // of this group ... maybe later
             if ( notesMap.keySet().size() == 1 ) {
               
               // only one entry, but have to convert
               notesPhrase.add( new Phrase( new ArrayList<String>( notesMap.keySet() ).get( 0 ),
                                   fontHeadFootReg ) );
               groupNotesCell.addElement( notesPhrase );

             } else {

               boolean firstRow = true;
               for ( Iterator<String> keyIt = notesMap.keySet().iterator(); keyIt.hasNext(); ) {
                 String key = keyIt.next();

                 String varNames = "";
                 for ( String varName : notesMap.get( key )) {
                   varNames += varName + ", ";
                 }
                 varNames = varNames.substring( 0, varNames.length() - 2 ) + ": ";

                 if ( firstRow ) {
                   
                   notesPhrase.add( new Phrase( varNames + key, fontHeadFootReg) );
                   groupNotesCell.addElement( notesPhrase );
                   firstRow = false;
                   
                 } else {

                   Paragraph para = new Paragraph( varNames + key, fontHeadFootReg );
                   para.setIndentationLeft( indextWidth );
                   groupNotesCell.addElement( para );

                 }


               }
             }

//             groupNotesCell.addElement( notesPhrase );
           }
           groupNotesCell.setColspan( 2 );
           groupNotesCell.setRowspan( group.size() );
           table.addCell( groupNotesCell );

           
           for ( RegCell regCell : cellsToAddLater )
             table.addCell( regCell );

          }
          else {
            // list only has one entry

             rowHasNotes = false;
             CPSPlanting p = group.get(0);

             for ( int col = 0; col < tableFormat.getColumnCount(); col++ ) {

                Object cellValue = tableFormat.getColumnValue( p, col);

                if ( tableIncludesNotes && col == notesIndex ) {
                  if ( cellValue == null || cellValue.equals(""))
                     rowHasNotes = false;
                  else {
                     rowHasNotes = true;
                     notesValue = cellValue.toString();
                  }
                  continue;
                }

                RegCell c = new TopBorderCell("");

                setContentsOfCell( c, cellValue );

                table.addCell( c );

              }

             // now deal w/ the Notes data
             if ( tableIncludesNotes && rowHasNotes ) {
               
               NoteCell c = new NoteCell("");
               Phrase phrase = new Phrase( "Notes: ", fontHeadFootItal);
               phrase.add( new Phrase( notesValue, fontHeadFootReg ) );
               c.setPhrase( phrase );
               c.setColspan( colCount );
               table.addCell( c );

             }

          }

        }


        // set the widths for the columns
        float[] widths = new float[colCount];
        for ( int col = 0; col < colCount; col++ ) {
           if ( tableIncludesNotes && col == notesIndex )
              continue;
           else if ( tableFormat.getColumnClass( col ).equals( Boolean.class ) )
              widths[col] = 2.25f;
           else if ( tableFormat.getColumnClass( col ).equals( Integer.class ) ||
                     tableFormat.getColumnClass( col ).equals( Double.class ) ||
                     tableFormat.getColumnClass( col ).equals( Float.class ) )
              widths[col] = 5f;
           else // String, Date, etc
              widths[col] = 10f;
        }

        try {
            table.setWidths( widths );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        return table;

    }


    private void setContentsOfCell( RegCell cell, Object cellValue ) {

      if ( cellValue instanceof Date )
        cell.setText( CPSDateValidator.format( (Date) cellValue,
                                             CPSDateValidator.DATE_FORMAT_SHORT_DAY_OF_WEEK ));
      else if ( cellValue instanceof Boolean ) {
        cell.setText( "X" );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
      }
      else if ( cellValue instanceof Float )
        cell.setText( CPSRecord.formatFloat( ((Float) cellValue).floatValue(), 3) );
      else if ( cellValue instanceof Double )
        cell.setText( CPSRecord.formatFloat( ((Double) cellValue).floatValue(), 3) );
      else
        cell.setText( cellValue.toString() );
    }





    /**
     * @param args the command line arguments
     */
    public PdfPTable convertJTable( JTable jtable ) {

       boolean tableIncludesNotes = false;
       boolean rowHasNotes = false;
       String notesValue = "";
       int notesIndex = -1;
       
       // find Notes column (if there is one)
       for ( int col = 0; col < jtable.getColumnCount(); col++ ) {
          String headName;
          if ( jtable instanceof CPSTable )
             headName = jtable.getColumnModel().getColumn( col ).getHeaderValue().toString();
          else
             headName = jtable.getColumnName( col );
          if ( headName.equalsIgnoreCase( "Planting Notes" ) ) {
             tableIncludesNotes = true;
             notesIndex = col;
          }
       }

       int colCount = (tableIncludesNotes) ? jtable.getColumnCount() - 1 : jtable.getColumnCount();
       PdfPTable table = new PdfPTable( colCount );

        // create header row
        for ( int col = 0; col < jtable.getColumnCount(); col++ ) {
           String headName;
            if ( jtable instanceof CPSTable )
                headName = jtable.getColumnModel().getColumn(col).getHeaderValue().toString();
            else
                headName = jtable.getColumnName( col );
           if ( ! tableIncludesNotes || col != notesIndex ) {
             HeadCell hc = new HeadCell( headName );

             if ( jtable.getColumnClass( col ).equals( Boolean.TRUE.getClass() ) ||
                  jtable.getColumnClass( col ).equals( new Integer( 0 ).getClass() ) ||
                  jtable.getColumnClass( col ).equals( new Double( 0 ).getClass() ) ||
                  jtable.getColumnClass( col ).equals( new Float( 0 ).getClass() ) ) {
               hc.setRotation(90);
               hc.setFixedHeight( 60f );
             }

             table.addCell( hc );
           }
        }
        table.setHeaderRows( 1 );
        
        // now fill in the rest of the table
        for ( int row = 0; row < jtable.getRowCount(); row++ ) {
           rowHasNotes = false;
           
           for ( int col = 0; col < jtable.getColumnCount(); col++ ) {
                Object o = jtable.getValueAt( row, col );

                if ( o == null ) {
                   if ( ! tableIncludesNotes || col != notesIndex )
                    table.addCell( new RegCell( "" ) );
                }

                else if ( o instanceof Date )
                    table.addCell( new RegCell( CPSDateValidator.format( (Date) o,
                                                                         CPSDateValidator.DATE_FORMAT_SHORT_DAY_OF_WEEK )));

                else if ( o instanceof Boolean )
                    if ( ( (Boolean) o ).booleanValue() )
                        table.addCell( new CenterCell( "X" ) );
                    else
                        table.addCell( new RegCell( "" ) );

                else if ( o instanceof Float )
                    table.addCell( new RegCell( CPSRecord.formatFloat( ((Float) o).floatValue(), 3) ));

                else if ( o instanceof Double )
                    table.addCell( new RegCell( CPSRecord.formatFloat( ((Double) o).floatValue(), 3) ));

                else {
                   
                   if ( tableIncludesNotes && col == notesIndex ) {
                      if ( o == null || o.equals(""))
                         rowHasNotes = false;
                      else {
                         rowHasNotes = true;
                         notesValue = o.toString();
                      }
                   }
                   else
                      table.addCell( new RegCell( o.toString() ) );
                }
            }
           
           // now deal w/ the Notes data
           if ( tableIncludesNotes && rowHasNotes ) {
              table.addCell( new NoteHeadCell() );
              NoteCell c = new NoteCell( notesValue );
              // reset the font to be smaller
              c.setPhrase( new Phrase( notesValue, fontHeadFootReg ));
              c.setColspan( colCount - 1 );
              table.addCell( c );
           }
        }
            
        // set the widths for the columns
        float[] widths = new float[colCount];
        for ( int col = 0; col < colCount; col++ ) {
           if ( tableIncludesNotes && col == notesIndex )
              continue;
           else if ( jtable.getColumnClass( col ).equals( new Boolean( true ).getClass() ) )
              widths[col] = 2.25f;
           else if ( jtable.getColumnClass( col ).equals( new Integer( 0 ).getClass() ) ||
                     jtable.getColumnClass( col ).equals( new Double( 0 ).getClass() ) ||
                     jtable.getColumnClass( col ).equals( new Float( 0 ).getClass() ) )
              widths[col] = 5f;
           else // String, Date, etc
              widths[col] = 10f;
        }
        
        try {
            table.setWidths( widths );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        
        return table;
    }


//****************************************************************************//
//    Table Cell Classes
//****************************************************************************//
    public class RegCell extends PdfPCell {

      protected Font font = fontTableReg;

        public RegCell( String s ) {
            super();
            setPhrase( new Phrase( s, font ));
            setBackgroundColor( BaseColor.WHITE );
            setHorizontalAlignment( PdfPCell.ALIGN_LEFT );
            setBorderWidth( .25f );
        }

        public void setText( String s ) {
          setPhrase( new Phrase( s, font ));
        }

    }

    public class TopBorderCell extends RegCell {

      public TopBorderCell( String s ) {
        super( s );
        disableBorderSide( Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM );
        setPaddingBottom( 4f );
      }

    }

    public class LastInGroupCell extends TopBorderCell {

      public LastInGroupCell( String s ) {
        super(s);
        disableBorderSide( Rectangle.TOP );
      }

    }


    public class NoBorderCell extends RegCell {

      public NoBorderCell( String s ) {
        super( s );
        disableBorderSide( Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM );
      }

    }

    public class SummaryCell extends TopBorderCell {


      public SummaryCell( String s ) {
        super("");
        this.font = fontTableItal;
        setPhrase( new Phrase( s, font ) );
        setBackgroundColor( new BaseColor( 241, 241, 241 ));
        setBorderWidth( .25f );
      }

    }

    public class SummaryTitleCell extends SummaryCell {

      public SummaryTitleCell() {
        super( "Summary:" );
      }

    }

    public class CenterCell extends RegCell {

        public CenterCell( String s ) {
            super( s );
            setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
        }
    }

    public class HeadCell extends PdfPCell {
        
        public HeadCell( String s ) {
            super( new Phrase( s, fontTableHead ));
            setBackgroundColor( BaseColor.LIGHT_GRAY );
            setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
            setVerticalAlignment( PdfPCell.ALIGN_MIDDLE );
        }
    }
    
    public class NoteHeadCell extends PdfPCell {
       public NoteHeadCell() {
         this("Notes:");
       }

       public NoteHeadCell(String s) {
            super( new Phrase( s, fontHeadFootItal ));
            setBackgroundColor( BaseColor.WHITE );
            setHorizontalAlignment( PdfPCell.ALIGN_RIGHT );
            disableBorderSide( Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM );
//            setBorderWidth( .25f );
            setMinimumHeight( 20f );
        }
    }

    public class NoteCell extends RegCell {

      public NoteCell(String s) {
        super(s);
        disableBorderSide( Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM );
        setMinimumHeight( 20f );
      }

    }

    public class SpecialNoteCell extends NoteHeadCell {

      public SpecialNoteCell() {
        super("");
        setVerticalAlignment( Element.ALIGN_TOP );
        setHorizontalAlignment( Element.ALIGN_LEFT );
      }

    }

    public class EmptyCell extends NoBorderCell {

      public EmptyCell() {
        super("");
      }

    }

//****************************************************************************//
//    main() for testing
//****************************************************************************//
  public static void main( String[] args ) {
    CPSGlobalSettings.setDebug( false );
    ModuleManager mm = new ModuleManager();
    CPSDataModel dm = mm.getDM();
    dm.init();

    String planName = dm.getListOfCropPlans().get(0);
    System.out.println( "Getting crop plan: " + planName );

    EventList<CPSPlanting> planList = GlazedLists.eventList( dm.getCropPlan( planName ) );
    FilterList<CPSPlanting> fl = new FilterList<CPSPlanting>( planList );

    // 1 = GH list, 2 = field seeding list, 3 = TP list
    int testOption = 1;

    CPSComplexPlantingFilter planMatcher;
    switch ( testOption ) {
      case 1: planMatcher = CPSComplexPlantingFilter.transplantedFilter(); break;
      case 2: planMatcher = CPSComplexPlantingFilter.directSeededFilter(); break;
      case 3: planMatcher = CPSComplexPlantingFilter.transplantedFilter(); break;
      default:
        throw new AssertionError();
    }
    planMatcher.setFilterOnPlantingDate(true);
    Calendar cal = Calendar.getInstance();
    Date d = CPSDateValidator.parse( "03/01" );
    cal.setTime( d );
    cal.set( Calendar.DAY_OF_MONTH, 1 );
    System.out.print( "Planting range: " + CPSDateValidator.format( cal.getTime() ));
    if ( testOption < 3 )
      planMatcher.setPlantingRangeStart( cal.getTime() );
    else
      planMatcher.setTpRangeStart( cal.getTime() );
    cal.add( Calendar.MONTH, 1 );
    cal.add( Calendar.DAY_OF_YEAR, -1 );
    System.out.println( " - " + CPSDateValidator.format( cal.getTime() ));
    if ( testOption < 3 )
      planMatcher.setPlantingRangeEnd( cal.getTime() );
    else
      planMatcher.setTpRangeEnd( cal.getTime() );

    fl.setMatcher( planMatcher );
    
    Comparator<CPSPlanting> comp = new Comparator<CPSPlanting>() {
          public int compare( CPSPlanting o1, CPSPlanting o2 ) {
            if ( o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() ) != 0 )
              return o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() );
            else
              return o1.getCropName().compareTo( o2.getCropName() );
          }
        };
    SortedList<CPSPlanting> sl = new SortedList<CPSPlanting>( fl, comp );

    System.out.println( "Filtered list contains " + fl.size() + " plantings." );

    AdjacentGroupingList<CPSPlanting> agl = new AdjacentGroupingList<CPSPlanting>( sl, comp );

    CPSExportTableFormat tf;
    switch ( testOption ) {
      case 1: tf = new GHSeedingTableFormat(); break;
      case 2: tf = new DSFieldPlantingTableFormat(); break;
//      case 3: tf = new TPFieldPlantingTableFormat(); break;
      default:
        throw new AssertionError();
    }
    new PDFExporter().exportPlantingList( agl, tf,
                                           "/Users/crcarter/test.pdf", "Test Farm",
                                           "Test Doc Title", "Test Table Title" );

  }

}
