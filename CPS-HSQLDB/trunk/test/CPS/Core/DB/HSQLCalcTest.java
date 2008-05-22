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

package CPS.Core.DB;

import CPS.Core.DB.HSQLCalc;
import java.sql.Date;
import java.util.GregorianCalendar;
import junit.framework.TestCase;

/**
 *
 * @author kendrajm
 */
public class HSQLCalcTest extends TestCase {
   
   private GregorianCalendar cal;
   
    public HSQLCalcTest(String testName) {
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
    * Test of plantFromHarvest method, of class HSQLCalc.
    */
   public void testPlantFromHarvest() {
      System.out.println( "plantFromHarvest" );
      Date d = null;
      int mat = 0;
      Integer adj = null;
      Integer weeksInGH = null;
      Boolean ds = null;
      Date expResult = null;
      Date result = HSQLCalc.plantFromHarvest( d, mat, adj, weeksInGH, ds );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of plantFromTP method, of class HSQLCalc.
    */
   public void testPlantFromTP() {
      System.out.println( "plantFromTP" );
      Date d = null;
      int weeksInGH = 0;
      Boolean ds = null;
      Date expResult = null;
      Date result = HSQLCalc.plantFromTP( d, weeksInGH, ds );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of TPFromPlant method, of class HSQLCalc.
    */
   public void testTPFromPlant() {
      System.out.println( "TPFromPlant" );
      Date d = null;
      int weeksInGH = 0;
      Boolean ds = null;
      Date expResult = null;
      Date result = HSQLCalc.TPFromPlant( d, weeksInGH, ds );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of TPFromHarvest method, of class HSQLCalc.
    */
   public void testTPFromHarvest() {
      System.out.println( "TPFromHarvest" );
      Date d = null;
      int mat = 0;
      Integer adj = null;
      Boolean ds = null;
      Date expResult = null;
      Date result = HSQLCalc.TPFromHarvest( d, mat, adj, ds );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of harvestFromPlant method, of class HSQLCalc.
    */
   public void testHarvestFromPlant() {
      System.out.println( "Testing harvestFromPlant" );
      
      cal.setTime( new java.util.Date() );
      
      Date d = new Date( cal.getTimeInMillis() );
      int mat = 0;
      Integer adj = new Integer(0);
      Integer weeksInGH = new Integer(0);
      
      Boolean ds = new Boolean(true);
      Date result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      Date expResult = d;
      assertEquals( expResult, result );
      
      // make sure weeks in GH are ignored
      weeksInGH = new Integer(1);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      assertEquals( expResult, result );
      
      // make sure result is same for type == TP
      ds = new Boolean(false);
      weeksInGH = new Integer(0);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      expResult = d;
      assertEquals( expResult, result );
      
      // DS w/ maturity days
      ds = new Boolean( true );
      mat = 10;
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      cal.add( GregorianCalendar.DAY_OF_YEAR, mat );
      expResult = new Date( cal.getTimeInMillis() );
      assertEquals( expResult, result );
      
      // make sure weeks in GH are ignored
      weeksInGH = new Integer(1);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      assertEquals( expResult, result );
      
      // TP w/ maturity days
      ds = new Boolean(false);
      weeksInGH = new Integer(0);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
//      expResult should be the same
      assertEquals( expResult, result );
      
      // DS w/ mat & mat adjust
      ds = new Boolean( true );
      adj = new Integer(-5);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      cal.add( GregorianCalendar.DAY_OF_YEAR, adj.intValue() );
      expResult = new Date( cal.getTimeInMillis() );
      assertEquals( expResult, result );
      
      // make sure weeks in GH are ignored
      weeksInGH = new Integer(1);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      assertEquals( expResult, result );
      
      // TP w/ maturity adjust
      ds = new Boolean(false);
      weeksInGH = new Integer(0);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
//      expResult should be the same
      assertEquals( expResult, result );
      
      // TP w/ mat, mat adjust and weeks in GH
      mat = 10;
      adj = new Integer(-5);
      weeksInGH = new Integer(1);
      result = HSQLCalc.harvestFromPlant( d, mat, adj, weeksInGH, ds );
      cal.setTimeInMillis( d.getTime() );
      cal.add( GregorianCalendar.DAY_OF_YEAR, (mat + adj.intValue() + ( weeksInGH.intValue() * 7 )) );
      expResult = new Date( cal.getTimeInMillis() );
      assertEquals( expResult, result );
      
      System.out.println( "Done testing harvestFromPlant" );
   }

   /**
    * Test of harvestFromTP method, of class HSQLCalc.
    */
   public void testHarvestFromTP() {
      System.out.println( "Testing harvestFromTP" );
      
      cal.setTime( new java.util.Date() );
      
      Date d = new Date( cal.getTimeInMillis() );
      int mat = 0;
      Integer adj = new Integer(0);
      
      Boolean ds = new Boolean(true);
      Date result = HSQLCalc.harvestFromTP( d, mat, adj, ds );
      Date expResult = null;
      assertEquals( expResult, result );
      
      // make sure result is same for type == TP
      ds = new Boolean(false);
      result = HSQLCalc.harvestFromTP( d, mat, adj, ds );
      expResult = d;
      assertEquals( expResult, result );
      
      // TP w/ maturity days
      ds = new Boolean(false);
      mat = 10;
      result = HSQLCalc.harvestFromTP( d, mat, adj, ds );
      cal.add( GregorianCalendar.DAY_OF_YEAR, mat );
      expResult = new Date( cal.getTimeInMillis() );
      assertEquals( expResult, result );

      // DS w/ maturity days
      ds = new Boolean( true );
      result = HSQLCalc.harvestFromTP( d, mat, adj, ds );
      expResult = null;
      assertEquals( expResult, result );
      
      // TP w/ maturity adjust
      ds = new Boolean(false);
      adj = new Integer(-5);
      result = HSQLCalc.harvestFromTP( d, mat, adj, ds );
      cal.add( GregorianCalendar.DAY_OF_YEAR, adj.intValue() );
      expResult = new Date( cal.getTimeInMillis() );
      assertEquals( expResult, result );
      
      // DS w/ mat & mat adjust
      ds = new Boolean( true );
      result = HSQLCalc.harvestFromTP( d, mat, adj, ds );
      expResult = null;
      assertEquals( expResult, result );
      
      System.out.println( "Done testing harvestFromTP" );
   }

   /**
    * Test of bedsFromRowFt method, of class HSQLCalc.
    */
   public void testBedsFromRowFt() {
      System.out.println( "bedsFromRowFt" );
      int rowFt = 0;
      int rowsPerBed = 0;
      Integer bedLength = null;
      double expResult = 0.0;
      double result = HSQLCalc.bedsFromRowFt( rowFt, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of bedsFromPlants method, of class HSQLCalc.
    */
   public void testBedsFromPlants() {
      System.out.println( "bedsFromPlants" );
      int plantsNeeded = 0;
      int inRowSpacing = 0;
      int rowsPerBed = 0;
      int bedLength = 0;
      double expResult = 0.0;
      double result = HSQLCalc.bedsFromPlants( plantsNeeded, inRowSpacing, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of plantsFromBeds method, of class HSQLCalc.
    */
   public void testPlantsFromBeds() {
      System.out.println( "plantsFromBeds" );
      double bedsToPlant = 0.0;
      int inRowSpacing = 0;
      int rowsPerBed = 0;
      int bedLength = 0;
      int expResult = 0;
      int result = HSQLCalc.plantsFromBeds( bedsToPlant, inRowSpacing, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of plantsFromRowFt method, of class HSQLCalc.
    */
   public void testPlantsFromRowFt() {
      System.out.println( "plantsFromRowFt" );
      int rowFt = 0;
      int inRowSpacing = 0;
      int expResult = 0;
      int result = HSQLCalc.plantsFromRowFt( rowFt, inRowSpacing );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of rowFtFromBeds method, of class HSQLCalc.
    */
   public void testRowFtFromBeds() {
      System.out.println( "rowFtFromBeds" );
      double bedsToPlant = 0.0;
      int rowsPerBed = 0;
      int bedLength = 0;
      int expResult = 0;
      int result = HSQLCalc.rowFtFromBeds( bedsToPlant, rowsPerBed, bedLength );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of rowFtFromPlants method, of class HSQLCalc.
    */
   public void testRowFtFromPlants() {
      System.out.println( "rowFtFromPlants" );
      int plantsNeeded = 0;
      int inRowSpacing = 0;
      int expResult = 0;
      int result = HSQLCalc.rowFtFromPlants( plantsNeeded, inRowSpacing );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of plantsToStart method, of class HSQLCalc.
    */
   public void testPlantsToStart() {
      System.out.println( "plantsToStart" );
      int plantsNeeded = 0;
      int expResult = 0;
      int result = HSQLCalc.plantsToStart( plantsNeeded );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of bedLength method, of class HSQLCalc.
    */
   public void testBedLength() {
      System.out.println( "bedLength" );
      String fieldName = "";
      int expResult = 0;
      int result = HSQLCalc.bedLength( fieldName );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of flatsNeeded method, of class HSQLCalc.
    */
   public void testFlatsNeeded() {
      System.out.println( "flatsNeeded" );
      int plantsToStart = 0;
      String flatSize = "";
      Double expResult = null;
      Double result = HSQLCalc.flatsNeeded( plantsToStart, flatSize );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   /**
    * Test of totalYieldFromRowFt method, of class HSQLCalc.
    */
   public void testTotalYieldFromRowFt() {
      System.out.println( "totalYieldFromRowFt" );
      int rowFt = 0;
      double yieldPerFt = 0.0;
      double expResult = 0.0;
      double result = HSQLCalc.totalYieldFromRowFt( rowFt, yieldPerFt );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

}
