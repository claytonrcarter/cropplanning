/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Data;

import CPS.UI.Modules.CPSComparator;

/**
 * This currently doesn't do anything.
 * @deprecated 
 */
public class CPSPlantingComarator extends CPSComparator {

  @Override
  public int compare( Object a, Object b ) {

    CPSPlanting c = (CPSPlanting) a;
    CPSPlanting d = (CPSPlanting) b;

    switch ( propNum ) {

      case CPSPlanting.PROP_CROP_NAME: return super.compare( c.getCropName(), d.getCropName() );
      case CPSPlanting.PROP_VAR_NAME: return super.compare( c.getVarietyName(), d.getVarietyName() );
      case CPSPlanting.PROP_MATURITY: return super.compare( c.getMaturityDays(), d.getMaturityDays() );
      case CPSPlanting.PROP_LOCATION: return super.compare( c.getLocation(), d.getLocation() );

      // Dates
      // "effective" dates
      case CPSPlanting.PROP_DATE_PLANT: return super.compare( c.getDateToPlant(), d.getDateToPlant() );
      case CPSPlanting.PROP_DATE_TP: return super.compare( c.getDateToTP(), d.getDateToTP() );
      case CPSPlanting.PROP_DATE_HARVEST: return super.compare( c.getDateToHarvest(), d.getDateToHarvest() );
      // planned dates
      case CPSPlanting.PROP_DATE_PLANT_PLAN: return super.compare( c.getDateToPlantPlanned(), d.getDateToPlantPlanned() );
      case CPSPlanting.PROP_DATE_TP_PLAN: return super.compare( c.getDateToTPPlanned(), d.getDateToTPPlanned() );
      case CPSPlanting.PROP_DATE_HARVEST_PLAN: return super.compare( c.getDateToHarvestPlanned(), d.getDateToHarvestPlanned() );
      // actual dates
      case CPSPlanting.PROP_DATE_PLANT_ACTUAL:  return super.compare( c.getDateToPlantActual(), d.getDateToPlantActual() );
      case CPSPlanting.PROP_DATE_TP_ACTUAL:  return super.compare( c.getDateToTPActual(), d.getDateToTPActual() );
      case CPSPlanting.PROP_DATE_HARVEST_ACTUAL:  return super.compare( c.getDateToHarvestActual(), d.getDateToHarvestActual() );

      // Status Booleans
      case CPSPlanting.PROP_DONE_PLANTING:  return super.compare( c.getDonePlanting(), d.getDonePlanting() );
      case CPSPlanting.PROP_DONE_TP:  return super.compare( c.getDoneTP(), d.getDoneTP() );
      case CPSPlanting.PROP_DONE_HARVEST:  return super.compare( c.getDoneHarvest(), d.getDoneHarvest() );
      case CPSPlanting.PROP_IGNORE:  return super.compare( c.getIgnore(), d.getIgnore() );

      // Static Data
      // inheritable
      case CPSPlanting.PROP_MAT_ADJUST:  return super.compare( c.getMatAdjust(), d.getMatAdjust() );
      case CPSPlanting.PROP_ROWS_P_BED:  return super.compare( c.getRowsPerBed(), d.getRowsPerBed() );
      case CPSPlanting.PROP_ROW_SPACE:  return super.compare( c.getRowSpacing(), d.getRowSpacing() );
//      case CPSPlanting.PROP_CROP_NOTES:  return super.compare( c.get, d.get );

      case CPSPlanting.PROP_TIME_TO_TP:  return super.compare( c.getTimeToTP(), d.getTimeToTP() );
      case CPSPlanting.PROP_INROW_SPACE:  return super.compare( c.getInRowSpacing(), d.getInRowSpacing() );
      case CPSPlanting.PROP_FLAT_SIZE:  return super.compare( c.getFlatSize(), d.getFlatSize() );
      case CPSPlanting.PROP_PLANTING_NOTES:  return super.compare( c.getPlantingNotes(), d.getPlantingNotes() );

      // Calculated Data
      case CPSPlanting.PROP_BEDS_PLANT:  return super.compare( c.getBedsToPlant(), d.getBedsToPlant() );
      case CPSPlanting.PROP_PLANTS_NEEDED:  return super.compare( c.getPlantsNeeded(), d.getPlantsNeeded() );
//      case CPSPlanting.PROP_ROWFT_PLANT:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_PLANTS_START:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_FLATS_NEEDED:  return super.compare( c.get, d.get );
//
//      // Yield
//      // static
//      case CPSPlanting.PROP_YIELD_P_FOOT:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_YIELD_NUM_WEEKS:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_YIELD_P_WEEK:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_CROP_UNIT:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_CROP_UNIT_VALUE:  return super.compare( c.get, d.get );
//      // calculated
//      case CPSPlanting.PROP_TOTAL_YIELD:  return super.compare( c.get, d.get );
//
//      // Misc Metadata
//      // bools
//      case CPSPlanting.PROP_DIRECT_SEED:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_FROST_HARDY:  return super.compare( c.get, d.get );
//      // Strings
//      case CPSPlanting.PROP_GROUPS:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_KEYWORDS:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_OTHER_REQ:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_NOTES:  return super.compare( c.get, d.get );
//
//      case CPSPlanting.PROP_CUSTOM1:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_CUSTOM2:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_CUSTOM3:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_CUSTOM4:  return super.compare( c.get, d.get );
//      case CPSPlanting.PROP_CUSTOM5:  return super.compare( c.get, d.get );

      default: return CPSPlanting.PROP_ID;

    }

  }



}
