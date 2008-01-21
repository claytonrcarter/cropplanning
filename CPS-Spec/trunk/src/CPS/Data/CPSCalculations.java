/* Copyright (C) Jan 20, 2008 Clayton Carter */


package CPS.Data;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * CPSCalculations is a class of static utility methods for use in the calculation of
 * crop and planting data which the user has not specified.
 *
 * @author Clayton Carter
 */
public class CPSCalculations {

   /**
    * Calculates a date to plant based upon a desired harvest date and the crops maturity days.
    * @param dateHarvest Desired date to harvest.
    * @param maturityDays Approx. days from planting to harvest.
    * @return A planting date.
    */
   public static Date calcDatePlantFromDateHarvest( Date dateHarvest, int maturityDays ) {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime( dateHarvest );
      // -1 ==> subtract
      c.add( GregorianCalendar.DAY_OF_YEAR, -1 * maturityDays );
      return c.getTime();
   }
   
   public static Date calcDatePlantFromDateTP( Date dateTP, int weeksInGH ) {
      /* tricky tricky or smarty smarty? */
      return calcDatePlantFromDateHarvest( dateTP, weeksInGH * 7 );
   }
   
   public static Date calcDateTPFromDatePlant( Date datePlant, int weeksInGH ) {
      return calcDateHarvestFromDatePlant( datePlant, weeksInGH * 7 );
   }
   
   public static Date calcDateHarvestFromDatePlant( Date datePlant, int maturityDays ) {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime( datePlant );
      c.add( GregorianCalendar.DAY_OF_YEAR, maturityDays );
      return c.getTime();
   }
   
   public static Date calcDateHarvestFromDateTP( Date dateTP, 
                                                 int maturityDays,
                                                 int weeksInGH ) {
      return calcDateHarvestFromDatePlant( calcDatePlantFromDateTP( dateTP, weeksInGH ), maturityDays );
   }
   
   
   /* this one will be a bit more complicated */
//   public static Date calcDateHarvestFromDateTP
   
   public static float calcBedsToPlantFromRowFtToPlant( int rowFt, int rowsPerBed, int bedLength ) {
      return (float) ( ( 1.0 * rowFt / rowsPerBed ) / bedLength );
   }

   public static float calcBedsToPlantFromPlantsNeeded( int plantsNeeded, 
                                                        int inRowSpacing, 
                                                        int rowsPerBed,
                                                        int bedLength ) {
      float rowFt = plantsNeeded * ( inRowSpacing / 12 ); // LATER 12 is a little too English-unit-centric
      // LATER when we implement calculation of rowFtNeeded, we could just call that here
      return ( rowFt / rowsPerBed ) / bedLength;
   }

   public static int calcPlantsNeededFromBedsToPlant( float bedsToPlant,
                                                      int inRowSpacing,
                                                      int rowsPerBed,
                                                      int bedLength ) {
      /* rowft = beds * rowsPerBed * BED_LENGTH */
      /* plants/ft = 12 / inRowSpacing (inRowSpacing is really inches/plant) */
      /* plants_needed = rowft * plants/ft */
      // TODO Math.ceil()
      return (int) ( ( bedsToPlant * rowsPerBed * bedLength ) * ( 12.0 / inRowSpacing ) );
   }
   
   public static int calcPlantsNeededFromRowFtToPlant( int rowFt, int inRowSpacing ) {
      /* plants_needed = rowFt * plants/ft */
      /* plants/ft = 12 / inRowSpace */
      // TODO Math.ceil()
      return (int) ( rowFt * ( 12.0 / inRowSpacing ) );
   }
 
   public static int calcRowFtToPlantFromBedsToPlant( float bedsToPlant, int rowsPerBed, int bedLength ) {
      /* rowft = beds * rowsPerBed * BED_LENGTH */
      return (int) ( bedsToPlant * rowsPerBed * bedLength );
   }
   
   public static int calcRowFtToPlantFromPlantsNeeded( int plantsNeeded, int inRowSpacing ) {
      /* rowFt = plants_needed * ft/plant */
      /* plants/ft = inRowSpace / 12 */
      return (int) ( plantsNeeded * ( inRowSpacing / 12.0 ) );
   }
   
   public static int calcPlantsToStart( int plantsNeeded, double fudgeFactor ) {
      return (int) ( plantsNeeded * ( 1 + fudgeFactor ) );
   }
   
   public static float calcFlatsNeeded( int plantsToStart, int flatCapacity ) {
      return plantsToStart / (float) flatCapacity;
   }
   
   public static int calcFlatCapacity( String flatSize ) {
      int cap = 0;
      try {
         // For the case where FlatSize is eg "128" or "72"
         cap = Integer.parseInt(flatSize);
      } catch ( NumberFormatException ignore ) {
         // For the case where FlatSize is eg "1020 tray (500)" or "mini (50)"
         int openPar = flatSize.lastIndexOf( "(" );
         int closePar = flatSize.lastIndexOf( ")" );
//         System.out.println( "Parsing string '" + flatSize.substring( openPar + 1, closePar ) + "'" );
         try {
            cap = Integer.parseInt( flatSize.substring( openPar + 1, closePar ) );
         } catch ( NumberFormatException ignoreAgain ) {
            System.err.println( "ERROR: couldn't deduce the capacity of given flat size: " + flatSize );
         }
      }
      return cap;
   }
   
   public static float calcTotalYieldFromRowFtToPlant( int rowFt, float yieldPerFt ) {
      return rowFt * yieldPerFt;
   }
}
