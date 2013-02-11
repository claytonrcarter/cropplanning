/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.gui.TableFormat;

/**
 *
 * @author crcarter
 */
public class SeedStatsTableFormat implements TableFormat<CPSPlanting> {

  private int outputFormat = TODOLists.TL_FORMAT_PDF;
  
  public void setOutputFormat( int f ) {
    outputFormat = f;
  }

    public int getColumnCount() {
      if ( outputFormat == TODOLists.TL_FORMAT_PDF )
        return 14;
      else
        return 15;
    }

    public String getColumnName( int col ) {

        CPSPlanting p = new CPSPlanting();

        switch ( col ) {
            case  0: return p.getDatum( CPSPlanting.PROP_CROP_NAME ).getName();
            case  1: return p.getDatum( CPSPlanting.PROP_VAR_NAME ).getName();
            case  2: return "Plantings";
            case  3: return p.getDatum( CPSPlanting.PROP_DIRECT_SEED ).getName();
            case  4: return p.getDatum( CPSPlanting.PROP_BEDS_PLANT ).getName();
            case  5: return p.getDatum( CPSPlanting.PROP_ROWFT_PLANT ).getName();
            case  6: return p.getDatum( CPSPlanting.PROP_INROW_SPACE ).getName();
            case  7: return p.getDatum( CPSPlanting.PROP_PLANTS_START ).getName();
            case  8: return p.getDatum( CPSPlanting.PROP_FLATS_NEEDED ).getName();
            case  9: return p.getDatum( CPSPlanting.PROP_FLAT_SIZE ).getName();
            case 10: return p.getDatum( CPSPlanting.PROP_SEEDS_PER_UNIT ).getName();
            default:
              int csvOffset = 0;
              if ( outputFormat == TODOLists.TL_FORMAT_CSV )
                csvOffset++;
              if ( csvOffset == 1 && col == 11 )
                 return p.getDatum( CPSPlanting.PROP_SEED_UNIT ).getName();
              else if ( col == 11 + csvOffset )
                return "Seeds per Ft (DS) or Cell (TP)";
              else if ( col == 12 + csvOffset )
                return p.getDatum( CPSPlanting.PROP_SEED_NEEDED ).getName() + " (US)";
              else if ( col == 13 + csvOffset )
                return p.getDatum( CPSPlanting.PROP_SEED_NEEDED ).getName() + " (Metric)";
              else
                return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int col ) {

      String s = "", t = "";

        switch ( col ) {
            case  0: s = p.getCropName(); break;
            case  1: s = p.getVarietyName(); break;
            case  2: s = p.getMaturityDaysString(); break;
            case  3: s = p.isDirectSeeded() ? "X" : ""; break;
            case  4: s = p.getBedsToPlantString(); break;
            case  5: s = p.getRowFtToPlantString(); break;
            case  6: s = p.isDirectSeeded() ? "" : p.getInRowSpacingString(); break;
            case  7: s = p.isDirectSeeded() ? "" : p.getPlantsToStartString(); break;
            case  8: s = p.isDirectSeeded() ? "" : p.getFlatsNeededString(); break;
            case  9: s = p.isDirectSeeded() ? "" : p.getFlatSizeCapacity().toString(); break;
            default:
              int csvOffset = 0;
              if ( outputFormat == TODOLists.TL_FORMAT_CSV )
                csvOffset++;

              if ( csvOffset == 1 && col == 10 )
                s = p.getSeedsPerUnitString();
              else if ( csvOffset == 1 && col == 11 )
                t = p.getSeedUnit();
              else if ( col == 10 + csvOffset ) {
                s = p.getSeedsPerUnitString();
                t = "/" + p.getSeedUnit();
                if ( t.equals("/") )
                  t = "";
              }
              else if ( col == 11 + csvOffset ) {
                s = p.getSeedsPerString();
              }
              else if ( col == 12 + csvOffset ) {
                // seed needed (US)
                t = p.getSeedUnit();
                if ( t.equals( "oz" ) || t.equals( "lb" ) ||
                     t.equals( "ea" ) || t.startsWith( "M" ) )
                  s = p.getSeedNeededString();
                else if ( t.equals( "g" ) ) {
                  s = p.formatFloat( p.getSeedNeeded() / 28.349f, 2 );
                  t = "oz";
                }
                else { // t.equals( "kg" )
                  s = p.formatFloat( p.getSeedNeeded() * 2.204f, 2 );
                  t = "lb";
                }
              }
              else if ( col == 13 + csvOffset ) {
                // seed needed (metric)
                t = p.getSeedUnit();
                if ( t.equals( "g" ) || t.equals( "kg" ) ||
                     t.equals( "ea" ) || t.startsWith( "M" ) )
                  s = p.getSeedNeededString();
                else if ( t.equals( "oz" ) ) {
                  s = p.formatFloat( p.getSeedNeeded() * 28.349f, 2 );
                  t = "g";
                }
                else { // u.equals( "lb" )
                  s = p.formatFloat( p.getSeedNeeded() / 2.204f, 2 );
                  t = "kg";
                }
              }
              else
                s = "";
        }

        // show as an integer if it is one
        if ( s.endsWith(".0") )
          s = s.substring( 0, s.length() - 2 );
        
        // if t has been set, pad it with a space
        if ( ! t.equals("") && ! t.startsWith("/") )
          t = " " + t;

        // and trim off the 1k thingy
        if ( t.endsWith("(1k)") )
          t = t.substring( 0, t.length() - 4 );

        return s.equals("0") ? "" : s + t;
    }


}
