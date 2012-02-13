/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.DB;

import CPS.Data.CPSCrop;
import java.util.Iterator;
import java.util.Map;

public class HSQLUpdateHelpers {

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


   protected static CPSCrop convertPersistMapToCrop( Map<String, Object> m ) {

    CPSCrop crop = new CPSCrop();

    Iterator j = m.entrySet().iterator();
    while ( j.hasNext() ) {

      Map.Entry col = (Map.Entry) j.next();

      if ( col.getKey().equals( "id" ) )
          crop.setID( ((Integer) col.getValue()).intValue() );
//          crop.setID( m.getInt( "id" ));


          if ( col.getKey().equals( "crop_name" ) )
            crop.setCropName( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setCropName( HSQLDB.captureString( m.getString( "crop_name" ) ));

          if ( col.getKey().equals( "var_name" ) )
            crop.setVarietyName( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setVarietyName( HSQLDB.captureString( m.getString( "var_name" ) ));

          if ( col.getKey().equals( "fam_name" ) )
            crop.setFamilyName( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setFamilyName( HSQLDB.captureString( m.getString( "fam_name" ) ));


          if ( col.getKey().equals( "maturity" ) )
            crop.setMaturityDays( captureInt( col.getValue() ));
//          crop.setMaturityDays( HSQLDB.getInt( m, "maturity" ) );


          if ( col.getKey().equals( "direct_seed" ) )
            crop.setDirectSeeded( (Boolean) col.getValue() );


          if ( col.getKey().equals( "ds_mat_adjust" ) )
            crop.setDSMaturityAdjust( captureInt( col.getValue() ));
//          crop.setDSMaturityAdjust( HSQLDB.getInt( m, "ds_mat_adjust" ));

          if ( col.getKey().equals( "ds_rows_p_bed" ) )
            crop.setDSRowsPerBed( captureInt( col.getValue() ));
//          crop.setDSRowsPerBed( HSQLDB.getInt( m, "ds_rows_p_bed" ) );

          if ( col.getKey().equals( "ds_row_space" ) )
            crop.setDSSpaceBetweenRow( captureInt( col.getValue() ) );
//          crop.setDSSpaceBetweenRow( HSQLDB.getInt( m, "ds_row_space" ) );

          if ( col.getKey().equals( "ds_plant_notes" ) )
            crop.setDSPlantNotes( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setDSPlantNotes( HSQLDB.captureString( m.getString( "ds_plant_notes" )));


          if ( col.getKey().equals( "transplant" ) )
            crop.setTransplanted( (Boolean) col.getValue() );


          if ( col.getKey().equals( "tp_mat_adjust" ) )
            crop.setTPMaturityAdjust( captureInt( col.getValue() ));
//          crop.setTPMaturityAdjust( HSQLDB.getInt( m, "tp_mat_adjust" ));

          if ( col.getKey().equals( "tp_rows_p_bed" ) )
            crop.setTPRowsPerBed( captureInt( col.getValue() ));
//          crop.setTPRowsPerBed( HSQLDB.getInt( m, "tp_rows_p_bed" ));

          if ( col.getKey().equals( "tp_inrow_space" ) )
            crop.setTPSpaceInRow( captureInt( col.getValue() ) );

//          crop.setTPSpaceInRow( HSQLDB.getInt( m, "tp_inrow_space" ));

          if ( col.getKey().equals( "tp_row_space" ) )
            crop.setTPSpaceBetweenRow( captureInt( col.getValue() ) );
//          crop.setTPSpaceBetweenRow( HSQLDB.getInt( m, "tp_row_space" ));

          if ( col.getKey().equals( "tp_flat_size" ) )
            crop.setTPFlatSize( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setTPFlatSize( HSQLDB.captureString( m.getString( "tp_flat_size" )));

          if ( col.getKey().equals( "tp_time_in_gh" ) )
            crop.setTPTimeInGH( captureInt( col.getValue() ));
//          crop.setTPTimeInGH( HSQLDB.getInt( m, "tp_time_in_gh" ));

          if ( col.getKey().equals( "tp_plant_notes" ) )
            crop.setTPPlantNotes( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setTPPlantNotes( HSQLDB.captureString( m.getString( "tp_plant_notes" )));

          if ( col.getKey().equals( "yield_p_foot" ) )
            crop.setYieldPerFoot( captureFloat( col.getValue() ));
//          crop.setYieldPerFoot( HSQLDB.getFloat( m, "yield_p_foot" ));

          if ( col.getKey().equals( "yield_num_weeks" ) )
            crop.setYieldNumWeeks( captureInt( col.getValue() ));
//          crop.setYieldNumWeeks( HSQLDB.getInt( m, "yield_num_weeks" ));

          if ( col.getKey().equals( "yield_p_week" ) )
            crop.setYieldPerWeek( captureInt( col.getValue() ));
//          crop.setYieldPerWeek( HSQLDB.getInt( m, "yield_p_week" ));

          if ( col.getKey().equals( "crop_unit" ) )
            crop.setCropYieldUnit( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setCropYieldUnit( HSQLDB.captureString( m.getString( "crop_unit" )));

          if ( col.getKey().equals( "crop_unit_value" ) )
            crop.setCropUnitValue( captureFloat( col.getValue() ));
//          crop.setCropUnitValue( HSQLDB.getFloat( m, "crop_unit_value" ));

          
          if ( col.getKey().equals( "frost_hardy" ) )
            crop.setFrostHardy( (Boolean) col.getValue() );


          if ( col.getKey().equals( "bot_name" ) )
            crop.setBotanicalName( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setBotanicalName( HSQLDB.captureString( m.getString( "bot_name" ) ) );

          if ( col.getKey().equals( "description" ) )
            crop.setCropDescription( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setCropDescription( HSQLDB.captureString( m.getString("description") ));


          if ( col.getKey().equals( "groups" ) )
            crop.setGroups( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setGroups( HSQLDB.captureString( m.getString( "groups" ) ));

          if ( col.getKey().equals( "other_req" ) )
            crop.setOtherRequirements( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setOtherRequirements( HSQLDB.captureString( m.getString( "other_req" ) ));

          if ( col.getKey().equals( "keywords" ) )
            crop.setKeywords( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setKeywords( HSQLDB.captureString( m.getString( "keywords" ) ));

          if ( col.getKey().equals( "notes" ) )
            crop.setNotes( HSQLDB.captureString( (String) col.getValue() ));
//          crop.setNotes( HSQLDB.captureString( m.getString( "notes" ) ));


//        /* for varieties, inherit info from their crop, too */
//        if ( ! crop.getVarietyName().equals( "" ) ) {
//            CPSCrop superCrop = getCropInfo( crop.getCropName() );
//            crop.inheritFrom( superCrop );
//        }


  }
    return crop;
   }
}
