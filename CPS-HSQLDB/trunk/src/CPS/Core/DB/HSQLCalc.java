/* Copyright (C) Jan 21, 2008 Clayton Carter */


package CPS.Core.DB;

import java.sql.Date;

/**
 * A simple class containing static methods for use as "stored procedured" in HSQLDB.  Most of
 * these are simply wrappers for methods in CPS.Data.CPSCalculations.
 * 
 * @see CPS.Data.CPSCalculations
 * @author Clayton Carter
 */
public class HSQLCalc {
   
   /*
    * ===[ DEVELOPER NOTES ]===
    * 
    * ==[ METHOD NAMES ]==
    * NOTE all methods herein MUST have unique names.  Regardless of the argument types,
    * HSQLD seems to only consider the argument name.
    * 
    * ==[ PARAMETER TYPES ]==
    * HSQLD will send in NULL if an argument type is an object and that value is NULL in the db.
    * If the type is a primitive, it will just skip the call, replacing it with a NULL "return" 
    * value. So, if a parameter can validly be NULL, then specify it as an Object, otherwise, just
    * use the primitive.
    * 
    */
   
   /**
    * Calculate planting date based upon a desired harvest date and an approx. maturity days.
    */
   public static Date plantFromHarvest( Date harvest, int mat ) {
      if ( harvest == null )
         return null;
      
      return new Date( CPS.Data.CPSCalculations.calcDatePlantFromDateHarvest( harvest, mat ).getTime() );
   }
   
   /**
    * Calculate planting date based upon a desired TP date and the number of weeks seedlings are
    * in the GH.
    */
   public static Date plantFromTP( Date dateTP, int weeksInGH ) {
      if ( dateTP == null )
         return null;
      
      return new Date( CPS.Data.CPSCalculations.calcDatePlantFromDateTP( dateTP, weeksInGH ).getTime() );
   }
   
   /**
    * Calculate an approx harvest date based upon a planting date and maturity days.
    */
   public static Date harvestFromPlant( Date plant, int mat ) {
      if ( plant == null ) 
         return null;
      
      return new Date( CPS.Data.CPSCalculations.calcDateHarvestFromDatePlant( plant, mat ).getTime() );
   }
   
   /**
    * Calculate an approx transplanting date based upon a planting date and the number of weeks 
    * that seedlings are in the greenhouse.
    */
   public static Date TPFromPlant( Date datePlant, int weeksInGH ) {
      if ( datePlant == null )
         return null;
      
      return new Date( CPS.Data.CPSCalculations.calcDateTPFromDatePlant( datePlant, weeksInGH ).getTime() );
   }
   
   /**
    * Calculate number of beds to plant from row feet to plant, rows/bed and bedLength.
    */
   public static double bedsFromRowFt( int rowFt, int rowsPerBed, int bedLength ) {
      return CPS.Data.CPSCalculations.calcBedsToPlantFromRowFtToPlant( rowFt, rowsPerBed, bedLength );
   }

   /**
    * Calculate beds to plant from number of plants needed, in row spacing, rows per bed and bed length
    */
   public static double bedsFromPlants( int plantsNeeded, 
                                       int inRowSpacing,
                                       int rowsPerBed,
                                       int bedLength ) {
      return CPS.Data.CPSCalculations.calcBedsToPlantFromPlantsNeeded( plantsNeeded, 
                                                                       inRowSpacing, 
                                                                       rowsPerBed, 
                                                                       bedLength );
   }
   
   /**
    * Calculate plants needed based on the beds to plant, in row spacing, rows per bed and bed length.
    */
   public static int plantsFromBeds( double bedsToPlant,
                                     int inRowSpacing,
                                     int rowsPerBed,
                                     int bedLength ) {
      return CPS.Data.CPSCalculations.calcPlantsNeededFromBedsToPlant( (float) bedsToPlant,
                                                                       inRowSpacing,
                                                                       rowsPerBed,
                                                                       bedLength );
   }
   
   /**
    * Calculate plants needed from row feet to plant and in row spacing.
    */
   public static int plantsFromRowFt( int rowFt, int inRowSpacing ) {
      return CPS.Data.CPSCalculations.calcPlantsNeededFromRowFtToPlant( rowFt, inRowSpacing );
   }
 
   /**
    * Calculate row feet to plant from beds to plant, rows/bed and bed length.
    */
   public static int rowFtFromBeds( double bedsToPlant, int rowsPerBed, int bedLength ) {
      return CPS.Data.CPSCalculations.calcRowFtToPlantFromBedsToPlant( (float) bedsToPlant, 
                                                                               rowsPerBed,
                                                                               bedLength );
   }
   
   /**
    * Calculate row feet to plant from plants needed and in row spacing.
    */
   public static int rowFtFromPlants( int plantsNeeded, int inRowSpacing ) {
      return CPS.Data.CPSCalculations.calcRowFtToPlantFromPlantsNeeded( plantsNeeded, inRowSpacing );
   }
   
   /**
    * Calculate plants to start from plants needed and a fudge factor.
    */
   public static int plantsToStart( int plantsNeeded, double fudgeFactor ) {
      return CPS.Data.CPSCalculations.calcPlantsToStart( plantsNeeded, fudgeFactor );
   }
   
   /**
    * Calculate number of flats needed based on plants to start and String representing flat size.
    */
   public static Double flatsNeeded( int plantsToStart, String flatSize ) {
      if ( flatSize == null ) 
         return null;
      
      System.out.println("DB CALL: Calculating size of flat: " + flatSize );
      
      int c = CPS.Data.CPSCalculations.calcFlatCapacity( flatSize );
      return new Double( CPS.Data.CPSCalculations.calcFlatsNeeded( plantsToStart, c ));
   }
   
   /**
    * Calculate total yield based on row feet to plant and yieldPerFt.
    */
   public static double totalYieldFromRowFt( int rowFt, double yieldPerFt ) {
      return CPS.Data.CPSCalculations.calcTotalYieldFromRowFtToPlant( rowFt, (float) yieldPerFt );
   }

}
