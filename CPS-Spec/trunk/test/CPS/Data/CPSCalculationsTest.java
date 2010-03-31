/* 
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


import CPS.Data.CPSCalculations;
import java.util.Date;
import java.util.GregorianCalendar;
import junit.framework.TestCase;

/**
 *
 * @author kendrajm
 */
public class CPSCalculationsTest extends TestCase {
    
   private GregorianCalendar cal;
   
    public CPSCalculationsTest(String testName) {
        super(testName);
        cal = new GregorianCalendar();
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

   /**
    * Test of calcDatePlantFromDateHarvest method, of class CPSCalculations.
    */
   public void testCalcDatePlantFromDateHarvest() {
      System.out.println( "Testing calcDatePlantFromDateHarvest" );
      
      cal.setTime( new Date() );
      
      Date dateHarvest = cal.getTime();
      int maturityDays = 0;
      int matAdjust = 0;
      
      Date result = CPSCalculations.calcDatePlantFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      Date expResult = dateHarvest;
      assertEquals( expResult, result );
      
      maturityDays = 10;
      result = CPSCalculations.calcDatePlantFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, maturityDays * -1 );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = 5;
      result = CPSCalculations.calcDatePlantFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, matAdjust * -1 );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = -5;
      result = CPSCalculations.calcDatePlantFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      cal.setTime( dateHarvest );
      cal.add( GregorianCalendar.DAY_OF_YEAR, ( maturityDays + matAdjust ) * -1 );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      System.out.println( "Done testing calcDatePlantFromDateHarvest" );
   }

   /**
    * Test of calcDatePlantFromDateTP method, of class CPSCalculations.
    */
   public void testCalcDatePlantFromDateTP() {
      System.out.println( "Testing calcDatePlantFromDateTP" );
      
      cal.setTime( new Date() );
      
      Date dateTP = cal.getTime();
      int weeksInGH = 0;
      
      Date result = CPSCalculations.calcDatePlantFromDateTP( dateTP, weeksInGH );
      Date expResult = dateTP;
      assertEquals( expResult, result );
      
      weeksInGH = 1;
      result = CPSCalculations.calcDatePlantFromDateTP( dateTP, weeksInGH );
      cal.add( GregorianCalendar.WEEK_OF_YEAR, -1 * weeksInGH );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      System.out.println( "Done testing calcDatePlantFromDateTP" );
      
   }

   /**
    * Test of calcDateTPFromDatePlant method, of class CPSCalculations.
    */
   public void testCalcDateTPFromDatePlant() {
      System.out.println( "Testing calcDateTPFromDatePlant" );
      
      cal.setTime( new Date() );
      
      Date datePlant = cal.getTime();
      int weeksInGH = 0;
      
      Date result = CPSCalculations.calcDateTPFromDatePlant( datePlant, weeksInGH );
      Date expResult = datePlant;
      assertEquals( expResult, result );
      
      weeksInGH = 1;
      result = CPSCalculations.calcDateTPFromDatePlant( datePlant, weeksInGH );
      cal.add( GregorianCalendar.WEEK_OF_YEAR, weeksInGH );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      System.out.println( "Done testing calcDateTPFromDatePlant" );
   }

   /**
    * Test of calcDateTPFromDateHarvest method, of class CPSCalculations.
    */
   public void testCalcDateTPFromDateHarvest() {
      System.out.println( "Testing calcDateTPFromDateHarvest" );
      
      cal.setTime( new Date() );
      
      Date dateHarvest = cal.getTime();
      int maturityDays = 0;
      int matAdjust = 0;
      
      Date result = CPSCalculations.calcDateTPFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      Date expResult = dateHarvest;
      assertEquals( expResult, result );
      
      maturityDays = 10;
      result = CPSCalculations.calcDateTPFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, maturityDays * -1 );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = -5;
      result = CPSCalculations.calcDateTPFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, -1 * matAdjust );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = 5;
      result = CPSCalculations.calcDateTPFromDateHarvest( dateHarvest, maturityDays, matAdjust );
      cal.setTime( dateHarvest );
      cal.add( GregorianCalendar.DAY_OF_YEAR, -1 * ( maturityDays + matAdjust ));
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      System.out.println( "Done testing calcDateTPFromDateHarvest" );
   }

   /**
    * Test of calcDateHarvestFromDatePlant method, of class CPSCalculations.
    */
   public void testCalcDateHarvestFromDatePlant() {
      
      System.out.println( "Testing calcDateHarvestFromDatePlant" );
      
      cal.setTime( new Date() );
      
      Date datePlant = cal.getTime();
      int maturityDays = 0;
      int matAdjust = 0;
      
      Date result = CPSCalculations.calcDateHarvestFromDatePlant( datePlant, maturityDays, matAdjust );
      Date expResult = datePlant;
      assertEquals( expResult, result );
      
      maturityDays = 10;
      result = CPSCalculations.calcDateHarvestFromDatePlant( datePlant, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, maturityDays );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = -5;
      result = CPSCalculations.calcDateHarvestFromDatePlant( datePlant, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, matAdjust );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = 5;
      result = CPSCalculations.calcDateHarvestFromDatePlant( datePlant, maturityDays, matAdjust );
      cal.setTime( datePlant );
      cal.add( GregorianCalendar.DAY_OF_YEAR, maturityDays + matAdjust );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      System.out.println( "Done testing calcDateHarvestFromDatePlant" );
   }

   /**
    * Test of calcDateHarvestFromDateTP method, of class CPSCalculations.
    */
   public void testCalcDateHarvestFromDateTP() {
      System.out.println( "Testing calcDateHarvestFromDateTP" );
      
      cal.setTime( new Date() );
      
      Date dateTP = cal.getTime();
      int maturityDays = 0;
      int matAdjust = 0;
      
      Date result = CPSCalculations.calcDateHarvestFromDateTP( dateTP, maturityDays, matAdjust );
      Date expResult = dateTP;
      assertEquals( expResult, result );
      
      maturityDays = 10;
      result = CPSCalculations.calcDateHarvestFromDateTP( dateTP, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, maturityDays );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = -5;
      result = CPSCalculations.calcDateHarvestFromDateTP( dateTP, maturityDays, matAdjust );
      cal.add( GregorianCalendar.DAY_OF_YEAR, matAdjust );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      matAdjust = 5;
      result = CPSCalculations.calcDateHarvestFromDateTP( dateTP, maturityDays, matAdjust );
      cal.setTime( dateTP );
      cal.add( GregorianCalendar.DAY_OF_YEAR, maturityDays + matAdjust );
      expResult = cal.getTime();
      assertEquals( expResult, result );
      
      System.out.println( "Done testing calcDateHarvestFromDateTP" );
   }

   /**
    * Test of calcBedsToPlantFromRowFtToPlant method, of class CPSCalculations.
    */
   public void testCalcBedsToPlantFromRowFtToPlant() {
      System.out.println( "calcBedsToPlantFromRowFtToPlant" );

      int rowFt = 100;
      int rowsPerBed = 1;
      int bedLength = 100;

      float expResult = 1.0F;
      float result = CPSCalculations.calcBedsToPlantFromRowFtToPlant( rowFt, rowsPerBed, bedLength );
      assertEquals( expResult, result );

      // TODO more tests here

      System.out.println( "Done testing calcBedsToPlantFromRowFtToPlant" );
   }

   /**
    * Test of calcBedsToPlantFromPlantsNeeded method, of class CPSCalculations.
    */
   public void testCalcBedsToPlantFromPlantsNeeded() {
      System.out.println( "calcBedsToPlantFromPlantsNeeded" );
      int plantsNeeded = 0;
      int inRowSpacing = 0;
      int rowsPerBed = 0;
      int bedLength = 0;
      float expResult = 0.0F;
      float result = CPSCalculations.calcBedsToPlantFromPlantsNeeded( plantsNeeded, inRowSpacing, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcBedsToPlantFromTotalYield method, of class CPSCalculations.
    */
   public void testCalcBedsToPlantFromTotalYield() {
      System.out.println( "calcBedsToPlantFromTotalYield" );
      float totalYield = 0.0F;
      float yieldPerFt = 0.0F;
      int rowsPerBed = 0;
      int bedLength = 0;
      float expResult = 0.0F;
      float result = CPSCalculations.calcBedsToPlantFromTotalYield( totalYield, yieldPerFt, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcPlantsNeededFromBedsToPlant method, of class CPSCalculations.
    */
   public void testCalcPlantsNeededFromBedsToPlant() {
      System.out.println( "calcPlantsNeededFromBedsToPlant" );
      float bedsToPlant = 0.0F;
      int inRowSpacing = 0;
      int rowsPerBed = 0;
      int bedLength = 0;
      int expResult = 0;
      int result = CPSCalculations.calcPlantsNeededFromBedsToPlant( bedsToPlant, inRowSpacing, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcPlantsNeededFromRowFtToPlant method, of class CPSCalculations.
    */
   public void testCalcPlantsNeededFromRowFtToPlant() {
      System.out.println( "calcPlantsNeededFromRowFtToPlant" );
      int rowFt = 0;
      int inRowSpacing = 0;
      int expResult = 0;
      int result = CPSCalculations.calcPlantsNeededFromRowFtToPlant( rowFt, inRowSpacing );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcPlantsNeededFromTotalYield method, of class CPSCalculations.
    */
   public void testCalcPlantsNeededFromTotalYield() {
      System.out.println( "calcPlantsNeededFromTotalYield" );
      float totalYield = 0.0F;
      float yieldPerFt = 0.0F;
      int inRowSpacing = 0;
      int expResult = 0;
      int result = CPSCalculations.calcPlantsNeededFromTotalYield( totalYield, yieldPerFt, inRowSpacing );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcRowFtToPlantFromBedsToPlant method, of class CPSCalculations.
    */
   public void testCalcRowFtToPlantFromBedsToPlant() {
      System.out.println( "calcRowFtToPlantFromBedsToPlant" );
      float bedsToPlant = 0.0F;
      int rowsPerBed = 0;
      int bedLength = 0;
      int expResult = 0;
      int result = CPSCalculations.calcRowFtToPlantFromBedsToPlant( bedsToPlant, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcRowFtToPlantFromPlantsNeeded method, of class CPSCalculations.
    */
   public void testCalcRowFtToPlantFromPlantsNeeded() {
      System.out.println( "calcRowFtToPlantFromPlantsNeeded" );
      int plantsNeeded = 0;
      int inRowSpacing = 0;
      int expResult = 0;
      int result = CPSCalculations.calcRowFtToPlantFromPlantsNeeded( plantsNeeded, inRowSpacing );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcRowFtToPlantFromTotalYield method, of class CPSCalculations.
    */
   public void testCalcRowFtToPlantFromTotalYield() {
      System.out.println( "calcRowFtToPlantFromTotalYield" );
      float totalYield = 0.0F;
      float yieldPerFt = 0.0F;
      int expResult = 0;
      int result = CPSCalculations.calcRowFtToPlantFromTotalYield( totalYield, yieldPerFt );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcPlantsToStart method, of class CPSCalculations.
    */
   public void testCalcPlantsToStart() {
      System.out.println( "calcPlantsToStart" );
      int plantsNeeded = 0;
      int expResult = 0;
      int result = CPSCalculations.calcPlantsToStart( plantsNeeded );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcFlatsNeeded method, of class CPSCalculations.
    */
   public void testCalcFlatsNeeded() {
      System.out.println( "calcFlatsNeeded" );
      int plantsToStart = 0;
      int flatCapacity = 0;
      float expResult = 0.0F;
      float result = CPSCalculations.calcFlatsNeeded( plantsToStart, flatCapacity );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of extractFlatCapacity method, of class CPSCalculations.
    */
   public void testExtractFlatCapacity() {
      System.out.println( "extractFlatCapacity" );
      String flatSize = "";
      int expResult = 0;
      int result = CPSCalculations.extractFlatCapacity( flatSize );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of extractBedLength method, of class CPSCalculations.
    */
   public void testExtractBedLength() {
      System.out.println( "extractBedLength" );
      String fieldName = "";
      int expResult = 0;
      int result = CPSCalculations.extractBedLength( fieldName );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of calcTotalYieldFromRowFtToPlant method, of class CPSCalculations.
    */
   public void testCalcTotalYieldFromRowFtToPlant() {
      System.out.println( "calcTotalYieldFromRowFtToPlant" );
      int rowFt = 0;
      float yieldPerFt = 0.0F;
      float expResult = 0.0F;
      float result = CPSCalculations.calcTotalYieldFromRowFtToPlant( rowFt, yieldPerFt );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

}
