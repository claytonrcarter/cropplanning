/* HSQLCalc.java - Created: January 2008
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
 */


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
//   public static Date plantFromHarvest( Date harvest, int mat, Integer adj, Integer weeksInGH ) {
   public static Date plantFromHarvest( Date d, int mat, Integer adj, Integer weeksInGH, Boolean ds ) {
       
       if ( d == null )
           return null;
      
       if ( adj == null )
           adj = new Integer( 0 );
       
       if ( ds != null && weeksInGH != null && ! ds.booleanValue() )
         // type IS tp
         mat += weeksInGH.intValue() * 7;
      
       return new Date( CPS.Data.CPSCalculations.calcDatePlantFromDateHarvest( d, mat, adj.intValue() ).getTime() );
   }
   
   /**
    * Calculate planting date based upon a desired TP date and the number of weeks seedlings are
    * in the GH.
    */
   public static Date plantFromTP( Date d, int weeksInGH, Boolean ds ) {
      if ( d == null || ds != null && ds.booleanValue() )
         return null;
      
      return new Date( CPS.Data.CPSCalculations.calcDatePlantFromDateTP( d, weeksInGH ).getTime() );
   }
   
   /**
    * Calculate an approx transplanting date based upon a planting date and the number of weeks 
    * that seedlings are in the greenhouse.
    */
   public static Date TPFromPlant( Date d, int weeksInGH, Boolean ds ) {
      if ( d == null || ds != null && ds.booleanValue() )
         return null;
      
      return new Date( CPS.Data.CPSCalculations.calcDateTPFromDatePlant( d, weeksInGH ).getTime() );
   }
   
   // we have to include weeksInGH here; currently, the db is set so that planting is TP type
   // iff weekInGH is NOT null.  HSQL will not call this method if the parameter we're
   // trying to pass in is null; so if we're trying to passing time_to_tp, this is sort of our way
   // (stupid as it is) of saying "if type IS tp"
   public static Date TPFromHarvest( Date d, int mat, Integer adj, Boolean ds ) {
       if ( d == null || ds != null && ds.booleanValue() )
         return null;
       
      if ( adj == null )
         adj = new Integer( 0 );
       
      return new Date( CPS.Data.CPSCalculations.calcDateTPFromDateHarvest( d, mat, adj.intValue() ).getTime() );
   }
   
   /**
    * Calculate an approx harvest date based upon a planting date and maturity days.
    */
   public static Date harvestFromPlant( Date d, int mat, Integer adj, Integer weeksInGH, Boolean ds ) {
      if ( d == null ) 
         return null;
      
      if ( adj == null )
         adj = new Integer( 0 );
      
      if ( ds != null && weeksInGH != null && ! ds.booleanValue() )
         // type IS tp
         mat += weeksInGH.intValue() * 7;
       
      return new Date( CPS.Data.CPSCalculations.calcDateHarvestFromDatePlant( d, mat, adj.intValue() ).getTime() );
   }
   
   public static Date harvestFromTP( Date d, int mat, Integer adj, Boolean ds ) {
      if ( d == null || ds != null && ds.booleanValue() )
         return null;
      
      if ( adj == null )
         adj = new Integer( 0 );
      
      return new Date( CPS.Data.CPSCalculations.calcDateHarvestFromDateTP( d, mat, adj.intValue() ).getTime() );
   }
   
   
   
   
   
   /**
    * Calculate number of beds to plant from row feet to plant, rows/bed and bedLength.
    */
   public static double bedsFromRowFt( int rowFt, int rowsPerBed, Integer bedLength ) {
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
   public static int plantsToStart( int plantsNeeded ) {
      return CPS.Data.CPSCalculations.calcPlantsToStart( plantsNeeded );
   }
   
   public static int bedLength( String fieldName ) {
//       if ( fieldName == null )
//           return null;
       
       return CPS.Data.CPSCalculations.extractBedLength( fieldName );
   }
   
   /**
    * Calculate number of flats needed based on plants to start and String representing flat size.
    */
   public static Double flatsNeeded( int plantsToStart, String flatSize ) {
      if ( flatSize == null ) 
         return null;
      
      int c = CPS.Data.CPSCalculations.extractFlatCapacity( flatSize );
      return new Double( CPS.Data.CPSCalculations.calcFlatsNeeded( plantsToStart, c ));
   }
   
   /**
    * Calculate total yield based on row feet to plant and yieldPerFt.
    */
   public static double totalYieldFromRowFt( int rowFt, double yieldPerFt ) {
      return CPS.Data.CPSCalculations.calcTotalYieldFromRowFtToPlant( rowFt, (float) yieldPerFt );
   }

}
