/* CPSCalculations.java - created: January 2008
 * Copyright (C) 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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
import CPS.Module.CPSModule;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.calculation.AbstractEventListCalculation;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * CPSCalculations is a class of static utility methods for use in the calculation of
 * crop and planting data which the user has not specified.
 *
 * @author Clayton Carter
 */
public final class CPSCalculations {

   /**
    * Calculates a date to plant based upon a desired harvest date and the crops maturity days.
    * @param dateHarvest Desired date to harvest.
    * @param maturityDays Approx. days from planting to harvest.
    * @return A planting date.
    */
   public static Date calcDatePlantFromDateHarvest( Date dateHarvest, int maturityDays, int matAdjust ) {
      return calcDatePlantFromDateHarvest( dateHarvest, maturityDays, matAdjust, 0 );
   }

   public static Date calcDatePlantFromDateHarvest( Date dateHarvest, int maturityDays, int matAdjust, int weeksInGH ) {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime( dateHarvest );
      // -1 ==> subtract
      c.add( GregorianCalendar.DAY_OF_YEAR, -1 * ( maturityDays + matAdjust + weeksInGH * 7 ) );
      return c.getTime();
   }

   public static Date calcDatePlantFromDateTP( Date dateTP, int weeksInGH ) {
      /* tricky tricky or smarty smarty? */
      return calcDatePlantFromDateHarvest( dateTP, weeksInGH * 7, 0 );
   }


//****************************************************************************//
   public static Date calcDateTPFromDatePlant( Date datePlant, int weeksInGH ) {
      return calcDateHarvestFromDatePlant( datePlant, weeksInGH * 7, 0 );
   }
   
   public static Date calcDateTPFromDateHarvest( Date dateHarvest, int maturityDays, int matAdjust ) {
       return calcDatePlantFromDateHarvest( dateHarvest, maturityDays, matAdjust );
   }


//****************************************************************************//
   public static Date calcDateHarvestFromDatePlant( Date datePlant, int maturityDays, int matAdjust ) {
      return calcDateHarvestFromDatePlant( datePlant, maturityDays, matAdjust, 0 );
   }
   public static Date calcDateHarvestFromDatePlant( Date datePlant, int maturityDays, int matAdjust, int weeksInGH ) {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime( datePlant );
      c.add( GregorianCalendar.DAY_OF_YEAR, maturityDays + matAdjust );
      return c.getTime();
   }
   
   public static Date calcDateHarvestFromDateTP( Date dateTP, 
                                                 int maturityDays,
                                                 int matAdjust ) {
       return calcDateHarvestFromDatePlant( dateTP, maturityDays, matAdjust );
   }


//****************************************************************************//
   public static float calcBedsToPlantFromRowFtToPlant( int rowFt, int rowsPerBed, int bedLength ) {
      return (float) ( ( 1.0 * rowFt / rowsPerBed ) / bedLength );
   }

   public static float calcBedsToPlantFromPlantsNeeded( int plantsNeeded, 
                                                        int inRowSpacing, 
                                                        int rowsPerBed,
                                                        int bedLength ) {
      float rowFt = plantsNeeded * ( inRowSpacing / 12f ); // LATER 12 is a little too English-unit-centric
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


//****************************************************************************//
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

   public static int calcPlantsNeededFromPlantsToStart( int plantsToStart ) {
       float fudgeFactor = CPSGlobalSettings.getFudgeFactor();
      return (int) ( plantsToStart / ( 1 + fudgeFactor ) );
   }


//****************************************************************************//
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


//****************************************************************************//
   public static int calcPlantsToStart( int plantsNeeded ) {
       float fudgeFactor = CPSGlobalSettings.getFudgeFactor();
       int i = (int) ( plantsNeeded * ( 1 + fudgeFactor ) );
       // make sure we always fudge by at least 1
       if ( fudgeFactor > 0 && i == plantsNeeded )
         i++;
       return i;
   }

   public static int calcPlantsToStart( float flatsToStart, int flatCapacity ) {
     return (int) ( flatsToStart * flatCapacity );
   }


//****************************************************************************//
   public static float calcFlatsNeeded( int plantsToStart, int flatCapacity ) {
      return roundQuarter( plantsToStart / (float) flatCapacity );
   }


//****************************************************************************//
   public static float calcTotalYieldFromRowFtToPlant( int rowFt, float yieldPerFt ) {
      return precision3( rowFt * yieldPerFt );
   }


//****************************************************************************//
//  Public Utility Methods
//****************************************************************************//
   /**
    * Given a flat size string, calculates a flat capacity.
    * @param flatSize The flat size string ot parse.  
    *        Formats accepted: <number>, <flat name + (number)>, <flat name>
    * @return The capacity of the flat or 0 on error.
    */
   public static int extractFlatCapacity( String flatSize ) {
      // TODO - Is 0 a reasonable default?  perhaps 1 would be better
       int cap = 0;

       if ( flatSize != null ) {
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


//****************************************************************************//
// Private Utility Methods
//****************************************************************************//
   public static float precision3( float f ) {
       return (float) ((int) ( f * 1000 )) / 1000f;
   }
   
   public static float precision2( float f ) {
       return (float) ((int) ( f * 100 )) / 100f;
   }
   
   public static float roundQuarter( float f ) {
       return (float) Math.ceil( f * 4 ) / 4;
   }
   public static float roundHalf( float f ) {
       return (float) Math.ceil( f * 2 ) / 2;
   }
   public static float roundThird( float f ) {
       return (float) Math.ceil( f * 3 ) / 3;
   }


//****************************************************************************//
//   Utility Classes
//****************************************************************************//
//****************************************************************************//
// Advanced Calculation
//****************************************************************************//
  public static final class SumBedsRowftFlats extends AbstractEventListCalculation<Float, CPSPlanting> {

    public float beds, rowUnitLengthes, flats;

    public SumBedsRowftFlats(EventList<CPSPlanting> source) {
        super(new Float(0), source);
        beds = rowUnitLengthes = flats = 0;
    }

    protected void inserted( CPSPlanting p ) {
      beds   += p.getBedsToPlant();
      rowUnitLengthes += p.getRowFtToPlant();
      flats  += p.getFlatsNeeded();
    }

    protected void deleted(CPSPlanting p) {
      beds   -= p.getBedsToPlant();
      rowUnitLengthes -= p.getRowFtToPlant();
      flats  -= p.getFlatsNeeded();
    }

    protected void updated( CPSPlanting oldP, CPSPlanting newP ) {
      beds   = beds   - oldP.getBedsToPlant()  + newP.getBedsToPlant();
      rowUnitLengthes = rowUnitLengthes - oldP.getRowFtToPlant() + newP.getRowFtToPlant();
      flats  = flats  - oldP.getFlatsNeeded()  + newP.getFlatsNeeded();
    }

  }

  public static final class SumPlantsFlats extends AbstractEventListCalculation<Float, CPSPlanting> {

    public int plantsNeeded, plantsToStart;
    public float flats;

    public SumPlantsFlats(EventList<CPSPlanting> source) {
        super(new Float(0), source);
        plantsNeeded = plantsToStart = 0;
        flats = 0;
    }

    protected void inserted( CPSPlanting p ) {
      plantsNeeded   += p.getPlantsNeeded();
      plantsToStart += p.getPlantsToStart();
      flats  += p.getFlatsNeeded();
    }

    protected void deleted(CPSPlanting p) {
      plantsNeeded   -= p.getPlantsNeeded();
      plantsToStart -= p.getPlantsToStart();
      flats  -= p.getFlatsNeeded();
    }

    protected void updated( CPSPlanting oldP, CPSPlanting newP ) {
      plantsNeeded   = plantsNeeded   - oldP.getPlantsNeeded()  + newP.getPlantsNeeded();
      plantsToStart = plantsToStart - oldP.getPlantsToStart() + newP.getPlantsToStart();
      flats  = flats  - oldP.getFlatsNeeded()  + newP.getFlatsNeeded();
    }


  }


  
  
  public static void main( String[] args ) {
    
    BasicEventList<CPSPlanting> el = new BasicEventList<CPSPlanting>();
    
    SumPlantsFlats sum1 = new SumPlantsFlats( el );
    SumBedsRowftFlats sum2 = new SumBedsRowftFlats( el );
    
    for ( int i = 0; i < 10; i++ ) {
      CPSPlanting p = new CPSPlanting();
      p.setVarietyName( "[" + i + "]" );
      p.setFlatsNeeded( i );
      el.add( p );
    }

    System.out.println( "Flats: " + sum1.flats );
    System.out.println( "Flats: " + sum2.flats );


    
  }




}
