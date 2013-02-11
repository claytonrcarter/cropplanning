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


    public int getColumnCount() { return 14; }

    public String getColumnName( int arg0 ) {

        CPSPlanting p = new CPSPlanting();

        switch ( arg0 ) {
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
            case 11: return "Seeds per Ft (DS) or Cell (TP)";
            case 12: return p.getDatum( CPSPlanting.PROP_SEED_NEEDED ).getName() + " (US)";
            case 13: return p.getDatum( CPSPlanting.PROP_SEED_NEEDED ).getName() + " (Metric)";
            
            default: return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int col ) {

      String s, t = "";

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
            case 10:
              s = p.getSeedsPerUnitString();
              t = "/" + p.getSeedUnit();
              if ( t.equals("/") ) t = "";
              break;
            case 11: s = p.getSeedsPerString(); break;

            case 12:
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
              break;

            case 13:
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
              break;
            default: s = "";
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
