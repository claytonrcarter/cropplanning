/* CPSCalculations.java - created: January 2008
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


package CPS.Data;

import CPS.Module.CPSGlobalSettings;
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
   public static Date calcDatePlantFromDateHarvest( Date dateHarvest, int maturityDays, int matAdjust ) {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime( dateHarvest );
      // -1 ==> subtract
      c.add( GregorianCalendar.DAY_OF_YEAR, -1 * ( maturityDays + matAdjust ) );
      return c.getTime();
   }
   
   public static Date calcDatePlantFromDateTP( Date dateTP, int weeksInGH ) {
      /* tricky tricky or smarty smarty? */
      return calcDatePlantFromDateHarvest( dateTP, weeksInGH * 7, 0 );
   }
   
   public static Date calcDateTPFromDatePlant( Date datePlant, int weeksInGH ) {
      return calcDateHarvestFromDatePlant( datePlant, weeksInGH * 7, 0 );
   }
   
   public static Date calcDateTPFromDateHarvest( Date dateHarvest, int maturityDays, int matAdjust ) {
       return calcDatePlantFromDateHarvest( dateHarvest, maturityDays, matAdjust );
   }
   
   public static Date calcDateHarvestFromDatePlant( Date datePlant, int maturityDays, int matAdjust ) {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime( datePlant );
      c.add( GregorianCalendar.DAY_OF_YEAR, maturityDays + matAdjust );
      return c.getTime();
   }
   
   public static Date calcDateHarvestFromDateTP( Date dateTP, 
                                                 int maturityDays,
                                                 int matAdjust ) {
       return calcDateHarvestFromDatePlant( dateTP, maturityDays, matAdjust );
//      return calcDateHarvestFromDatePlant( calcDatePlantFromDateTP( dateTP, weeksInGH ), maturityDays );
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

   public static float calcBedsToPlantFromTotalYield( float totalYield,
                                                      float yieldPerFt,
                                                      int rowsPerBed,
                                                      int bedLength ) {
       return calcBedsToPlantFromRowFtToPlant( calcRowFtToPlantFromTotalYield( totalYield, 
                                                                               yieldPerFt ),
                                               rowsPerBed, 
                                               bedLength );
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
   
   public static int calcPlantsNeededFromTotalYield( float totalYield,
                                                     float yieldPerFt,
                                                     int inRowSpacing ) {
       return calcPlantsNeededFromRowFtToPlant( calcRowFtToPlantFromTotalYield( totalYield, 
                                                                                yieldPerFt ),
                                                inRowSpacing );
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
   
   public static int calcRowFtToPlantFromTotalYield( float totalYield, float yieldPerFt ) {
       return (int) ( totalYield / yieldPerFt );
   }
   
   public static int calcPlantsToStart( int plantsNeeded, double fudgeFactor ) {
      return (int) ( plantsNeeded * ( 1 + fudgeFactor ) );
   }
   
   public static float calcFlatsNeeded( int plantsToStart, int flatCapacity ) {
      return roundQuarter( plantsToStart / (float) flatCapacity );
//      return plantsToStart / (float) flatCapacity;
   }
   
   /**
    * Given a flat size string, calculates a flat capacity.
    * @param flatSize The flat size string ot parse.  
    *        Formats accepted: <number>, <flat name + (number)>, <flat name>
    * @return The capacity of the flat or 0 on error.
    */
   public static int extractFlatCapacity( String flatSize ) {
      // TODO - Is 0 a reasonable default?  perhaps 1 would be better
       int cap = 0;
      
       // For the case where FlatSize is eg "128" or "72"
      if ( flatSize.matches( "\\d+" )) {
          try {
              cap = Integer.parseInt( flatSize );
          } catch ( NumberFormatException ignore ) {}
      }
      // For the case where FlatSize is eg "1020 tray (500)" or "mini ( 50 )"
      else if ( flatSize.matches( ".*\\(\\s*\\d+\\s*\\).*" )) {
         int openPar = flatSize.lastIndexOf( "(" );
         int closePar = flatSize.lastIndexOf( ")" );
         
         try {
            cap = Integer.parseInt( flatSize.substring( openPar + 1, closePar ).trim() );
         } catch ( NumberFormatException ignore ) {
            System.err.println( "ERROR: couldn't deduce the capacity of given flat size: " + flatSize );
         }
      }
      return cap;
   }
   
   /**
    * Given a field name string, extracts a bed length.
    * @param fieldName The field name string to parse.  
    *        Formats accepted: <blank>, <field name>, <field name + (number)>
    * @return The bed length of that field or the default value on error.  The default value is 100.
    */
   public static int extractBedLength( String fieldName ) {
       
       int len = CPSGlobalSettings.getBedLength();
       
       // only extract a bed length if the fieldName is eg "field blah blah (100)" or "garden blah blah ( 50 )"
       if ( fieldName != null && fieldName.matches( ".*\\(\\s*\\d+\\s*\\).*" ) )
           len = extractFlatCapacity( fieldName );
       
       return len;
   }
   
   public static float calcTotalYieldFromRowFtToPlant( int rowFt, float yieldPerFt ) {
      return rowFt * yieldPerFt;
//      return roundQuarter( rowFt * yieldPerFt );
   }
   
   private static float roundQuarter( float f ) {
       return (float) Math.ceil( f * 4 ) / 4;
   }
   private static float roundHalf( float f ) {
       return (float) Math.ceil( f * 2 ) / 2;
   }
   private static float roundThird( float f ) {
       return (float) Math.ceil( f * 3 ) / 3;
   }
   
}
