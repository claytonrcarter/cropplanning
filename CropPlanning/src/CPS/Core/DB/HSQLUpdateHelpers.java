/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.DB;

import CPS.Data.CPSCrop;
import CPS.Data.CPSPlanting;
//import java.sql.Date;
import java.util.Iterator;
import java.util.Map;

public class HSQLUpdateHelpers {

//  protected static java.util.Date captureDate( Object o ) {
//      if ( o == null )
//         // PENDING this is totally bogus and needs to have a sane "default" date
//         return new Date( 0 );
//      else
//         return (Date) o;
//  }

   protected static Integer captureInt( Object o ) {

     if ( o == null )
       return (Integer) null;
     else if ( o instanceof Integer ) {
       return (Integer) o;
     } else if ( o instanceof Float ) {
//       return ((Float) o).floatValue();
       return new Integer( ((Float) o).intValue() );
     } else if ( o instanceof Double ) {
       return new Integer( ((Double) o).intValue() );
     }
     else
       return -1;

   }

   protected static Float captureFloat( Object o ) {

     if ( o == null )
       return (Float) null;
     else if ( o instanceof Float ) {
       return (Float) o;
     } else if ( o instanceof Double ) {
       return new Float( ((Double) o).floatValue() );
     }
     else
       return -1f;

   }


   protected static CPSPlanting convertPersistMapToPlanting( Map<String, Object> m ) {

      CPSPlanting p = new CPSPlanting();

      Iterator j = m.entrySet().iterator();
      while ( j.hasNext() ) {

        Map.Entry col = (Map.Entry) j.next();

        if ( col.getKey().equals( "id" ) )
            p.setID( ((Integer) col.getValue()).intValue() );
        if ( col.getKey().equals( "crop_name" ) )
          p.setCropName( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "var_name" ) )
          p.setVarietyName( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "location" ) )
          p.setLocation( HSQLDB.captureString( (String) col.getValue() ));

        if ( col.getKey().equals( "date_plant_plan" ) )
          p.setDateToPlantPlanned( (java.util.Date) col.getValue() );
        if ( col.getKey().equals( "date_tp_plan" ) )
          p.setDateToTPPlanned( (java.util.Date) col.getValue() );
        if ( col.getKey().equals( "date_harvest_plan" ) )
          p.setDateToHarvestPlanned( (java.util.Date) col.getValue() );

        if ( col.getKey().equals( "date_plant_actual" ) )
          p.setDateToPlantActual( (java.util.Date) col.getValue() );
        if ( col.getKey().equals( "date_tp_actual" ) )
          p.setDateToTPActual( (java.util.Date) col.getValue() );
        if ( col.getKey().equals( "date_harvest_actual" ) )
          p.setDateToHarvestActual( (java.util.Date) col.getValue() );

        if ( col.getKey().equals( "done_plant" ) )
          p.setDonePlanting( (Boolean) col.getValue());
        if ( col.getKey().equals( "done_TP" ) )
          p.setDoneTP( (Boolean) col.getValue() );
        if ( col.getKey().equals( "done_harvest" ) )
          p.setDoneHarvest( (Boolean) col.getValue() );
        if ( col.getKey().equals( "ignore" ) )
          p.setIgnore( (Boolean) col.getValue() );


        if ( col.getKey().equals( "ds_mat_adjust" ) ) {
          boolean b = p.isDirectSeeded();
          p.setDirectSeeded(true);
          p.setMatAdjust( captureInt( col.getValue() ));
          p.setDirectSeeded(b);
        }
        if ( col.getKey().equals( "ds_rows_p_bed" ) ) {
          boolean b = p.isDirectSeeded();
          p.setDirectSeeded(true);
          p.setRowsPerBed( captureInt( col.getValue() ));
          p.setDirectSeeded(b);
        }
        if ( col.getKey().equals( "ds_row_space" ) ) {
          boolean b = p.isDirectSeeded();
          p.setDirectSeeded(true);
          p.setRowSpacing( captureInt( col.getValue() ));
          p.setDirectSeeded(b);
        }
        if ( col.getKey().equals( "ds_crop_notes" ) ) {
          boolean b = p.isDirectSeeded();
          p.setDirectSeeded(true);
          p.setPlantingNotesInherited( HSQLDB.captureString( (String) col.getValue() ));
          p.setDirectSeeded(b);
        }


        if ( col.getKey().equals( "tp_mat_adjust" ) ) {
          boolean b = p.isTransplanted();
          p.setTransplanted(true);
          p.setMatAdjust( captureInt( col.getValue() ));
          p.setTransplanted(b);
        }
        if ( col.getKey().equals( "tp_rows_p_bed" ) ) {
          boolean b = p.isTransplanted();
          p.setTransplanted(true);
          p.setRowsPerBed( captureInt( col.getValue() ));
          p.setTransplanted(b);
        }
        if ( col.getKey().equals( "inrow_space" ) ) {
          boolean b = p.isTransplanted();
          p.setTransplanted(true);
          p.setInRowSpacing( captureInt( col.getValue() ) );
          p.setTransplanted(b);
        }
        if ( col.getKey().equals( "tp_row_space" ) ) {
          boolean b = p.isTransplanted();
          p.setTransplanted(true);
          p.setRowSpacing( captureInt( col.getValue() ));
          p.setTransplanted(b);
        }
        if ( col.getKey().equals( "flat_size" ) ) {
          boolean b = p.isTransplanted();
          p.setTransplanted(true);
          p.setFlatSize( HSQLDB.captureString( (String) col.getValue() ));
          p.setTransplanted(b);
        }
        if ( col.getKey().equals( "time_to_tp" ) ) {
          boolean b = p.isTransplanted();
          p.setTransplanted(true);
          p.setTimeToTP( captureInt( col.getValue() ));
          p.setTransplanted(b);
        }
        if ( col.getKey().equals( "tp_crop_notes" ) ) {
          boolean b = p.isTransplanted();
          p.setTransplanted(true);
          p.setPlantingNotesInherited( HSQLDB.captureString( (String) col.getValue() ));
          p.setTransplanted(b);
        }

        
        if ( col.getKey().equals( "direct_seed" ) )
          p.setDirectSeeded( (Boolean) col.getValue() );
        
        if ( col.getKey().equals( "maturity" ) )
          p.setMaturityDays( captureInt( col.getValue() ));
        if ( col.getKey().equals( "plant_notes_spec" ) )
          p.setPlantingNotes( HSQLDB.captureString( (String) col.getValue() ));


        if ( col.getKey().equals( "mat_adjust" ) )
          p.setMatAdjust( captureInt( col.getValue() ));
        if ( col.getKey().equals( "rows_p_bed" ) )
          p.setRowsPerBed( captureInt( col.getValue() ));
        if ( col.getKey().equals( "row_space" ) )
          p.setRowSpacing( captureInt( col.getValue() ));
        if ( col.getKey().equals( "planting_notes" ) )
          p.setPlantingNotesInherited( HSQLDB.captureString( (String) col.getValue() ));


        if ( col.getKey().equals( "beds_to_plant" ) )
          p.setBedsToPlant( captureFloat( col.getValue() ) );
        if ( col.getKey().equals( "plants_needed" ) )
          p.setPlantsNeeded( captureInt( col.getValue() ));
        if ( col.getKey().equals( "plants_to_start" ) )
          p.setPlantsToStart( captureInt( col.getValue() ));
        if ( col.getKey().equals( "rowft_to_plant" ) )
          p.setRowFtToPlant( captureInt( col.getValue() ));
        if ( col.getKey().equals( "flats_needed" ) )
          p.setFlatsNeeded( captureFloat( col.getValue() ));
        if ( col.getKey().equals( "total_yield" ) )
          p.setTotalYield( captureFloat( col.getValue() ));

        if ( col.getKey().equals( "yield_p_foot" ) )
          p.setYieldPerFoot( captureFloat( col.getValue() ) ) ;
        if ( col.getKey().equals( "yield_num_weeks" ) )
          p.setYieldNumWeeks( captureInt( col.getValue() ));
        if ( col.getKey().equals( "yield_p_week" ) )
          p.setYieldPerWeek( captureFloat( col.getValue() ));
        if ( col.getKey().equals( "crop_unit" ) )
          p.setCropYieldUnit( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "crop_unit_value" ) )
          p.setCropYieldUnitValue( captureFloat( col.getValue() ));

        if ( col.getKey().equals( "frost_hardy" ) )
          p.setFrostHardy( (Boolean) col.getValue() );

        if ( col.getKey().equals( "groups" ) )
          p.setGroups( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "other_req" ) )
          p.setOtherRequirements( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "keywords" ) )
          p.setKeywords( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "notes" ) )
          p.setNotes( HSQLDB.captureString( (String) col.getValue() ));

        if ( col.getKey().equals( "custom1" ) )
          p.setCustomField1( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "custom2" ) )
          p.setCustomField2( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "custom3" ) )
          p.setCustomField3( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "custom4" ) )
          p.setCustomField4( HSQLDB.captureString( (String) col.getValue() ));
        if ( col.getKey().equals( "custom5" ) )
          p.setCustomField5( HSQLDB.captureString( (String) col.getValue() ));

      }

      return p;
   }

   protected static CPSCrop convertPersistMapToCrop( Map<String, Object> m ) {

    CPSCrop crop = new CPSCrop();

    Iterator j = m.entrySet().iterator();
    while ( j.hasNext() ) {

      Map.Entry col = (Map.Entry) j.next();

      if ( col.getKey().equals( "id" ) )
        crop.setID( ((Integer) col.getValue()).intValue() );

      if ( col.getKey().equals( "crop_name" ) )
        crop.setCropName( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "var_name" ) )
        crop.setVarietyName( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "fam_name" ) )
        crop.setFamilyName( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "maturity" ) )
        crop.setMaturityDays( captureInt( col.getValue() ));


      if ( col.getKey().equals( "direct_seed" ) )
        crop.setDirectSeeded( (Boolean) col.getValue() );

      if ( col.getKey().equals( "ds_mat_adjust" ) )
        crop.setDSMaturityAdjust( captureInt( col.getValue() ));

      if ( col.getKey().equals( "ds_rows_p_bed" ) )
        crop.setDSRowsPerBed( captureInt( col.getValue() ));

      if ( col.getKey().equals( "ds_row_space" ) )
        crop.setDSSpaceBetweenRow( captureInt( col.getValue() ) );

      if ( col.getKey().equals( "ds_plant_notes" ) )
        crop.setDSPlantNotes( HSQLDB.captureString( (String) col.getValue() ));


      if ( col.getKey().equals( "transplant" ) )
        crop.setTransplanted( (Boolean) col.getValue() );

      if ( col.getKey().equals( "tp_mat_adjust" ) )
        crop.setTPMaturityAdjust( captureInt( col.getValue() ));

      if ( col.getKey().equals( "tp_rows_p_bed" ) )
        crop.setTPRowsPerBed( captureInt( col.getValue() ));

      if ( col.getKey().equals( "tp_inrow_space" ) )
        crop.setTPSpaceInRow( captureInt( col.getValue() ) );


      if ( col.getKey().equals( "tp_row_space" ) )
        crop.setTPSpaceBetweenRow( captureInt( col.getValue() ) );

      if ( col.getKey().equals( "tp_flat_size" ) )
        crop.setTPFlatSize( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "tp_time_in_gh" ) )
        crop.setTPTimeInGH( captureInt( col.getValue() ));

      if ( col.getKey().equals( "tp_plant_notes" ) )
        crop.setTPPlantNotes( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "yield_p_foot" ) )
        crop.setYieldPerFoot( captureFloat( col.getValue() ));

      if ( col.getKey().equals( "yield_num_weeks" ) )
        crop.setYieldNumWeeks( captureInt( col.getValue() ));

      if ( col.getKey().equals( "yield_p_week" ) )
        crop.setYieldPerWeek( captureInt( col.getValue() ));

      if ( col.getKey().equals( "crop_unit" ) )
        crop.setCropYieldUnit( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "crop_unit_value" ) )
        crop.setCropUnitValue( captureFloat( col.getValue() ));


      if ( col.getKey().equals( "frost_hardy" ) )
        crop.setFrostHardy( (Boolean) col.getValue() );

      if ( col.getKey().equals( "bot_name" ) )
        crop.setBotanicalName( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "description" ) )
        crop.setCropDescription( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "groups" ) )
        crop.setGroups( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "other_req" ) )
        crop.setOtherRequirements( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "keywords" ) )
        crop.setKeywords( HSQLDB.captureString( (String) col.getValue() ));

      if ( col.getKey().equals( "notes" ) )
        crop.setNotes( HSQLDB.captureString( (String) col.getValue() ));


    }
    return crop;
  }
}
