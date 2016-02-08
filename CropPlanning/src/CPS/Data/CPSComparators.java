/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Data;

import java.util.Comparator;

/**
 *
 * @author crcarter
 */
public class CPSComparators {

    /**
     * A Comparator to sort CPSPlantings by effective planting date.
     */
    public static class DatePlantComparator implements Comparator<CPSPlanting> {
      public int compare( CPSPlanting o1, CPSPlanting o2 ) {
        return o1.getDateToPlant().compareTo( o2.getDateToPlant() );
      }
    }

    /**
     * A Comparator to sort CPSPlantings by effective TP date.
     */
    public static class DateTPComparator implements Comparator<CPSPlanting> {
      public int compare( CPSPlanting o1, CPSPlanting o2 ) {
        return o1.getDateToTP().compareTo( o2.getDateToTP() );
      }
    }

    /**
     * A Comparator to sort CPSPlantings by crop name.
     */
    public static class CropNameComparator implements Comparator<CPSPlanting> {
      public int compare( CPSPlanting o1, CPSPlanting o2 ) {
        return o1.getCropName().compareTo( o2.getCropName() );
      }
    }

    /**
     * A Comparator to sort CPSPlantings first by planned planting date, then crop name.
     */
    public static class DatePlantCropNameComparator implements Comparator<CPSPlanting> {
      public int compare( CPSPlanting o1, CPSPlanting o2 ) {
        if ( o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() ) != 0 )
          return o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() );
        else
          return o1.getCropName().compareTo( o2.getCropName() );
      }
    }

    /**
     * A Comparator to sort CPSPlantings first by planned TP date, then crop name.
     */
    public static class DateTPCropNameComparator implements Comparator<CPSPlanting> {
      public int compare( CPSPlanting o1, CPSPlanting o2 ) {
        if ( o1.getDateToTP().compareTo( o2.getDateToTP() ) != 0 )
          return o1.getDateToTP().compareTo( o2.getDateToTP() );
        else
          return o1.getCropName().compareTo( o2.getCropName() );
      }
    }



}
