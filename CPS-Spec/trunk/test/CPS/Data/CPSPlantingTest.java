/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Data;

import CPS.Data.CPSDatum.CPSDatumState;
import CPS.Data.CPSPlanting.PlantingIterator;
import java.util.ArrayList;
import java.util.Date;
import junit.framework.TestCase;

/**
 *
 * @author crcarter
 */
public class CPSPlantingTest extends TestCase {
    
    public CPSPlantingTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

   public void testLastValidProperty () {
      System.out.println( "lastValidProperty" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.lastValidProperty();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDatum () {
      System.out.println( "getDatum" );
      int prop = 0;
      CPSPlanting instance = new CPSPlanting();
      CPSDatum expResult = null;
      CPSDatum result = instance.getDatum( prop );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetListOfInheritableProperties () {
      System.out.println( "getListOfInheritableProperties" );
      CPSPlanting instance = new CPSPlanting();
      ArrayList<Integer> expResult = null;
      ArrayList<Integer> result = instance.getListOfInheritableProperties();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCropName () {
      System.out.println( "getCropName" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCropName();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCropNameState () {
      System.out.println( "getCropNameState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCropNameState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropName_String () {
      System.out.println( "setCropName" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCropName( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropName_String_boolean () {
      System.out.println( "setCropName" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCropName( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetVarietyName () {
      System.out.println( "getVarietyName" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getVarietyName();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetVarietyNameState () {
      System.out.println( "getVarietyNameState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getVarietyNameState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetVarietyName_String () {
      System.out.println( "setVarietyName" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setVarietyName( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetVarietyName_String_boolean () {
      System.out.println( "setVarietyName" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setVarietyName( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetMaturityDays () {
      System.out.println( "getMaturityDays" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getMaturityDays();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetMaturityDaysString () {
      System.out.println( "getMaturityDaysString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getMaturityDaysString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetMaturityDaysState () {
      System.out.println( "getMaturityDaysState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getMaturityDaysState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMaturityDays_int () {
      System.out.println( "setMaturityDays" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setMaturityDays( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMaturityDays_int_boolean () {
      System.out.println( "setMaturityDays" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setMaturityDays( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMaturityDays_String () {
      System.out.println( "setMaturityDays" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setMaturityDays( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMaturityDays_String_boolean () {
      System.out.println( "setMaturityDays" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setMaturityDays( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetLocation () {
      System.out.println( "getLocation" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getLocation();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetLocationState () {
      System.out.println( "getLocationState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getLocationState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetLocation_String () {
      System.out.println( "setLocation" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setLocation( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetLocation_String_boolean () {
      System.out.println( "setLocation" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setLocation( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetEffectiveDate () {
      System.out.println( "getEffectiveDate" );
      int prop_effective = 0;
      int prop_actual = 0;
      int prop_plan = 0;
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getEffectiveDate( prop_effective, prop_actual, prop_plan );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantAbstract () {
      System.out.println( "getDateToPlantAbstract" );
      int date_type = 0;
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToPlantAbstract( date_type );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlant () {
      System.out.println( "getDateToPlant" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToPlant();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantString () {
      System.out.println( "getDateToPlantString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToPlantString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantState () {
      System.out.println( "getDateToPlantState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToPlantState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   
   public void testGetDateToPlantPlanned () {
      System.out.println( "getDateToPlantPlanned" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToPlantPlanned();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantPlannedString () {
      System.out.println( "getDateToPlantPlannedString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToPlantPlannedString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantPlannedState () {
      System.out.println( "getDateToPlantPlannedState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToPlantPlannedState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantPlanned_Date () {
      System.out.println( "setDateToPlantPlanned" );
      Date d = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantPlanned( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantPlanned_String () {
      System.out.println( "setDateToPlantPlanned" );
      String d = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantPlanned( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantPlanned_Date_boolean () {
      System.out.println( "setDateToPlantPlanned" );
      Date d = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantPlanned( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantPlanned_String_boolean () {
      System.out.println( "setDateToPlantPlanned" );
      String d = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantPlanned( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantActual () {
      System.out.println( "getDateToPlantActual" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToPlantActual();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantActualString () {
      System.out.println( "getDateToPlantActualString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToPlantActualString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToPlantActualState () {
      System.out.println( "getDateToPlantActualState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToPlantActualState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantActual_Date () {
      System.out.println( "setDateToPlantActual" );
      Date d = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantActual( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantActual_String () {
      System.out.println( "setDateToPlantActual" );
      String d = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantActual( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantActual_Date_boolean () {
      System.out.println( "setDateToPlantActual" );
      Date d = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantActual( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToPlantActual_String_boolean () {
      System.out.println( "setDateToPlantActual" );
      String d = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToPlantActual( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPAbstract () {
      System.out.println( "getDateToTPAbstract" );
      int date_type = 0;
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToTPAbstract( date_type );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTP () {
      System.out.println( "getDateToTP" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToTP();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPString () {
      System.out.println( "getDateToTPString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToTPString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPState () {
      System.out.println( "getDateToTPState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToTPState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPPlanned () {
      System.out.println( "getDateToTPPlanned" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToTPPlanned();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPPlannedString () {
      System.out.println( "getDateToTPPlannedString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToTPPlannedString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPPlannedState () {
      System.out.println( "getDateToTPPlannedState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToTPPlannedState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPPlanned_Date () {
      System.out.println( "setDateToTPPlanned" );
      Date d = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPPlanned( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPPlanned_String () {
      System.out.println( "setDateToTPPlanned" );
      String d = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPPlanned( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPPlanned_Date_boolean () {
      System.out.println( "setDateToTPPlanned" );
      Date d = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPPlanned( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPPlanned_String_boolean () {
      System.out.println( "setDateToTPPlanned" );
      String d = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPPlanned( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPActual () {
      System.out.println( "getDateToTPActual" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToTPActual();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPActualString () {
      System.out.println( "getDateToTPActualString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToTPActualString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToTPActualState () {
      System.out.println( "getDateToTPActualState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToTPActualState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPActual_Date () {
      System.out.println( "setDateToTPActual" );
      Date d = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPActual( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPActual_String () {
      System.out.println( "setDateToTPActual" );
      String d = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPActual( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPActual_Date_boolean () {
      System.out.println( "setDateToTPActual" );
      Date d = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPActual( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToTPActual_String_boolean () {
      System.out.println( "setDateToTPActual" );
      String d = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToTPActual( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestAbstract () {
      System.out.println( "getDateToHarvestAbstract" );
      int date_type = 0;
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToHarvestAbstract( date_type );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvest () {
      System.out.println( "getDateToHarvest" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToHarvest();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestString () {
      System.out.println( "getDateToHarvestString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToHarvestString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestState () {
      System.out.println( "getDateToHarvestState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToHarvestState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestPlanned () {
      System.out.println( "getDateToHarvestPlanned" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToHarvestPlanned();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestPlannedString () {
      System.out.println( "getDateToHarvestPlannedString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToHarvestPlannedString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestPlannedState () {
      System.out.println( "getDateToHarvestPlannedState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToHarvestPlannedState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestPlanned_Date () {
      System.out.println( "setDateToHarvestPlanned" );
      Date d = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestPlanned( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestPlanned_Date_boolean () {
      System.out.println( "setDateToHarvestPlanned" );
      Date d = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestPlanned( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestPlanned_String () {
      System.out.println( "setDateToHarvestPlanned" );
      String d = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestPlanned( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestPlanned_String_boolean () {
      System.out.println( "setDateToHarvestPlanned" );
      String d = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestPlanned( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestActual () {
      System.out.println( "getDateToHarvestActual" );
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.getDateToHarvestActual();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestActualString () {
      System.out.println( "getDateToHarvestActualString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getDateToHarvestActualString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDateToHarvestActualState () {
      System.out.println( "getDateToHarvestActualState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDateToHarvestActualState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestActual_Date () {
      System.out.println( "setDateToHarvestActual" );
      Date d = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestActual( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestActual_Date_boolean () {
      System.out.println( "setDateToHarvestActual" );
      Date d = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestActual( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestActual_String () {
      System.out.println( "setDateToHarvestActual" );
      String d = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestActual( d );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDateToHarvestActual_String_boolean () {
      System.out.println( "setDateToHarvestActual" );
      String d = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDateToHarvestActual( d, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDonePlanting () {
      System.out.println( "getDonePlanting" );
      CPSPlanting instance = new CPSPlanting();
      Boolean expResult = null;
      Boolean result = instance.getDonePlanting();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDonePlantingState () {
      System.out.println( "getDonePlantingState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDonePlantingState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDonePlanting_String () {
      System.out.println( "setDonePlanting" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDonePlanting( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDonePlanting_Boolean () {
      System.out.println( "setDonePlanting" );
      Boolean b = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDonePlanting( b );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDonePlanting_Boolean_boolean () {
      System.out.println( "setDonePlanting" );
      Boolean b = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDonePlanting( b, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDoneTP () {
      System.out.println( "getDoneTP" );
      CPSPlanting instance = new CPSPlanting();
      Boolean expResult = null;
      Boolean result = instance.getDoneTP();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDoneTPState () {
      System.out.println( "getDoneTPState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDoneTPState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDoneTP_String () {
      System.out.println( "setDoneTP" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDoneTP( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDoneTP_Boolean () {
      System.out.println( "setDoneTP" );
      Boolean b = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDoneTP( b );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDoneTP_Boolean_boolean () {
      System.out.println( "setDoneTP" );
      Boolean b = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDoneTP( b, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDoneHarvest () {
      System.out.println( "getDoneHarvest" );
      CPSPlanting instance = new CPSPlanting();
      Boolean expResult = null;
      Boolean result = instance.getDoneHarvest();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDoneHarvestState () {
      System.out.println( "getDoneHarvestState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDoneHarvestState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDoneHarvest_String () {
      System.out.println( "setDoneHarvest" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDoneHarvest( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDoneHarvest_Boolean () {
      System.out.println( "setDoneHarvest" );
      Boolean b = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDoneHarvest( b );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDoneHarvest_Boolean_boolean () {
      System.out.println( "setDoneHarvest" );
      Boolean b = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDoneHarvest( b, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetIgnore () {
      System.out.println( "getIgnore" );
      CPSPlanting instance = new CPSPlanting();
      Boolean expResult = null;
      Boolean result = instance.getIgnore();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetIgnoreState () {
      System.out.println( "getIgnoreState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getIgnoreState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetIgnore_String () {
      System.out.println( "setIgnore" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setIgnore( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetIgnore_Boolean () {
      System.out.println( "setIgnore" );
      Boolean b = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setIgnore( b );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetIgnore_Boolean_boolean () {
      System.out.println( "setIgnore" );
      Boolean b = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setIgnore( b, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetMatAdjust () {
      System.out.println( "getMatAdjust" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getMatAdjust();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetMatAdjustString () {
      System.out.println( "getMatAdjustString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getMatAdjustString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetMatAdjustState () {
      System.out.println( "getMatAdjustState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getMatAdjustState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMatAdjust_int () {
      System.out.println( "setMatAdjust" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setMatAdjust( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMatAdjust_int_boolean () {
      System.out.println( "setMatAdjust" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setMatAdjust( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMatAdjust_String () {
      System.out.println( "setMatAdjust" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setMatAdjust( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetMatAdjust_String_boolean () {
      System.out.println( "setMatAdjust" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setMatAdjust( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetTimeToTP () {
      System.out.println( "getTimeToTP" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getTimeToTP();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetTimeToTPString () {
      System.out.println( "getTimeToTPString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getTimeToTPString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetTimeToTPState () {
      System.out.println( "getTimeToTPState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getTimeToTPState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTimeToTP_int () {
      System.out.println( "setTimeToTP" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setTimeToTP( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTimeToTP_int_boolean () {
      System.out.println( "setTimeToTP" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setTimeToTP( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTimeToTP_String () {
      System.out.println( "setTimeToTP" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setTimeToTP( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTimeToTP_String_boolean () {
      System.out.println( "setTimeToTP" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setTimeToTP( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowsPerBedState () {
      System.out.println( "getRowsPerBedState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getRowsPerBedState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowsPerBed () {
      System.out.println( "getRowsPerBed" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getRowsPerBed();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowsPerBedString () {
      System.out.println( "getRowsPerBedString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getRowsPerBedString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowsPerBed_int () {
      System.out.println( "setRowsPerBed" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowsPerBed( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowsPerBed_int_boolean () {
      System.out.println( "setRowsPerBed" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowsPerBed( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowsPerBed_String () {
      System.out.println( "setRowsPerBed" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setRowsPerBed( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowsPerBed_String_boolean () {
      System.out.println( "setRowsPerBed" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowsPerBed( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetInRowSpacing () {
      System.out.println( "getInRowSpacing" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getInRowSpacing();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetInRowSpacingString () {
      System.out.println( "getInRowSpacingString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getInRowSpacingString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetInRowSpacingState () {
      System.out.println( "getInRowSpacingState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getInRowSpacingState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetInRowSpacing_int () {
      System.out.println( "setInRowSpacing" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setInRowSpacing( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetInRowSpacing_int_boolean () {
      System.out.println( "setInRowSpacing" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setInRowSpacing( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetInRowSpacing_String () {
      System.out.println( "setInRowSpacing" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setInRowSpacing( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetInRowSpacing_String_boolean () {
      System.out.println( "setInRowSpacing" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setInRowSpacing( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowSpacing () {
      System.out.println( "getRowSpacing" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getRowSpacing();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowSpacingString () {
      System.out.println( "getRowSpacingString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getRowSpacingString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowSpacingState () {
      System.out.println( "getRowSpacingState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getRowSpacingState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowSpacing_int () {
      System.out.println( "setRowSpacing" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowSpacing( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowSpacing_int_boolean () {
      System.out.println( "setRowSpacing" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowSpacing( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowSpacing_String () {
      System.out.println( "setRowSpacing" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setRowSpacing( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowSpacing_String_boolean () {
      System.out.println( "setRowSpacing" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowSpacing( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetFlatSize () {
      System.out.println( "getFlatSize" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getFlatSize();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetFlatSizeCapacity () {
      System.out.println( "getFlatSizeCapacity" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getFlatSizeCapacity();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetFlatSizeState () {
      System.out.println( "getFlatSizeState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getFlatSizeState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFlatSize_String () {
      System.out.println( "setFlatSize" );
      String i = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setFlatSize( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFlatSize_String_boolean () {
      System.out.println( "setFlatSize" );
      String i = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setFlatSize( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantingNotesInherited () {
      System.out.println( "getPlantingNotesInherited" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getPlantingNotesInherited();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantingNotesInheritedState () {
      System.out.println( "getPlantingNotesInheritedState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getPlantingNotesInheritedState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantingNotesInherited_String () {
      System.out.println( "setPlantingNotesInherited" );
      String i = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantingNotesInherited( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantingNotesInherited_String_boolean () {
      System.out.println( "setPlantingNotesInherited" );
      String i = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantingNotesInherited( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantingNotes () {
      System.out.println( "getPlantingNotes" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getPlantingNotes();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantingNotesState () {
      System.out.println( "getPlantingNotesState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getPlantingNotesState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantingNotes_String () {
      System.out.println( "setPlantingNotes" );
      String i = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantingNotes( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantingNotes_String_boolean () {
      System.out.println( "setPlantingNotes" );
      String i = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantingNotes( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetBedsToPlant () {
      System.out.println( "getBedsToPlant" );
      CPSPlanting instance = new CPSPlanting();
      float expResult = 0.0F;
      float result = instance.getBedsToPlant();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetBedsToPlantString () {
      System.out.println( "getBedsToPlantString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getBedsToPlantString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetBedsToPlantState () {
      System.out.println( "getBedsToPlantState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getBedsToPlantState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetBedsToPlant_float () {
      System.out.println( "setBedsToPlant" );
      float i = 0.0F;
      CPSPlanting instance = new CPSPlanting();
      instance.setBedsToPlant( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetBedsToPlant_float_boolean () {
      System.out.println( "setBedsToPlant" );
      float i = 0.0F;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setBedsToPlant( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetBedsToPlant_String () {
      System.out.println( "setBedsToPlant" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setBedsToPlant( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetBedsToPlant_String_boolean () {
      System.out.println( "setBedsToPlant" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setBedsToPlant( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantsNeeded () {
      System.out.println( "getPlantsNeeded" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getPlantsNeeded();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantsNeededString () {
      System.out.println( "getPlantsNeededString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getPlantsNeededString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantsNeededState () {
      System.out.println( "getPlantsNeededState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getPlantsNeededState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsNeeded_int () {
      System.out.println( "setPlantsNeeded" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsNeeded( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsNeeded_int_boolean () {
      System.out.println( "setPlantsNeeded" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsNeeded( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsNeeded_String () {
      System.out.println( "setPlantsNeeded" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsNeeded( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsNeeded_String_boolean () {
      System.out.println( "setPlantsNeeded" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsNeeded( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowFtToPlant () {
      System.out.println( "getRowFtToPlant" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getRowFtToPlant();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowFtToPlantString () {
      System.out.println( "getRowFtToPlantString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getRowFtToPlantString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetRowFtToPlantState () {
      System.out.println( "getRowFtToPlantState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getRowFtToPlantState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowFtToPlant_int () {
      System.out.println( "setRowFtToPlant" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowFtToPlant( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowFtToPlant_int_boolean () {
      System.out.println( "setRowFtToPlant" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowFtToPlant( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowFtToPlant_String () {
      System.out.println( "setRowFtToPlant" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setRowFtToPlant( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetRowFtToPlant_String_boolean () {
      System.out.println( "setRowFtToPlant" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setRowFtToPlant( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantsToStart () {
      System.out.println( "getPlantsToStart" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getPlantsToStart();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantsToStartString () {
      System.out.println( "getPlantsToStartString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getPlantsToStartString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetPlantsToStartState () {
      System.out.println( "getPlantsToStartState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getPlantsToStartState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsToStart_int () {
      System.out.println( "setPlantsToStart" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsToStart( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsToStart_int_boolean () {
      System.out.println( "setPlantsToStart" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsToStart( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsToStart_String () {
      System.out.println( "setPlantsToStart" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsToStart( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetPlantsToStart_String_boolean () {
      System.out.println( "setPlantsToStart" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setPlantsToStart( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetFlatsNeeded () {
      System.out.println( "getFlatsNeeded" );
      CPSPlanting instance = new CPSPlanting();
      float expResult = 0.0F;
      float result = instance.getFlatsNeeded();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetFlatsNeededString () {
      System.out.println( "getFlatsNeededString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getFlatsNeededString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetFlatsNeededState () {
      System.out.println( "getFlatsNeededState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getFlatsNeededState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFlatsNeeded_float () {
      System.out.println( "setFlatsNeeded" );
      float i = 0.0F;
      CPSPlanting instance = new CPSPlanting();
      instance.setFlatsNeeded( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFlatsNeeded_float_boolean () {
      System.out.println( "setFlatsNeeded" );
      float i = 0.0F;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setFlatsNeeded( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFlatsNeeded_String () {
      System.out.println( "setFlatsNeeded" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setFlatsNeeded( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFlatsNeeded_String_boolean () {
      System.out.println( "setFlatsNeeded" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setFlatsNeeded( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldPerFoot () {
      System.out.println( "getYieldPerFoot" );
      CPSPlanting instance = new CPSPlanting();
      float expResult = 0.0F;
      float result = instance.getYieldPerFoot();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldPerFootString () {
      System.out.println( "getYieldPerFootString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getYieldPerFootString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldPerFootState () {
      System.out.println( "getYieldPerFootState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getYieldPerFootState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerFoot_float () {
      System.out.println( "setYieldPerFoot" );
      float i = 0.0F;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerFoot( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerFoot_float_boolean () {
      System.out.println( "setYieldPerFoot" );
      float i = 0.0F;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerFoot( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerFoot_String () {
      System.out.println( "setYieldPerFoot" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerFoot( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerFoot_String_boolean () {
      System.out.println( "setYieldPerFoot" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerFoot( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldNumWeeks () {
      System.out.println( "getYieldNumWeeks" );
      CPSPlanting instance = new CPSPlanting();
      int expResult = 0;
      int result = instance.getYieldNumWeeks();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldNumWeeksString () {
      System.out.println( "getYieldNumWeeksString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getYieldNumWeeksString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldNumWeeksState () {
      System.out.println( "getYieldNumWeeksState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getYieldNumWeeksState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldNumWeeks_int () {
      System.out.println( "setYieldNumWeeks" );
      int i = 0;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldNumWeeks( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldNumWeeks_int_boolean () {
      System.out.println( "setYieldNumWeeks" );
      int i = 0;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldNumWeeks( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldNumWeeks_String () {
      System.out.println( "setYieldNumWeeks" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldNumWeeks( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldNumWeeks_String_boolean () {
      System.out.println( "setYieldNumWeeks" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldNumWeeks( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldPerWeek () {
      System.out.println( "getYieldPerWeek" );
      CPSPlanting instance = new CPSPlanting();
      float expResult = 0.0F;
      float result = instance.getYieldPerWeek();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldPerWeekString () {
      System.out.println( "getYieldPerWeekString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getYieldPerWeekString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetYieldPerWeekState () {
      System.out.println( "getYieldPerWeekState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getYieldPerWeekState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerWeek_float () {
      System.out.println( "setYieldPerWeek" );
      float i = 0.0F;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerWeek( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerWeek_float_boolean () {
      System.out.println( "setYieldPerWeek" );
      float i = 0.0F;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerWeek( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerWeek_String () {
      System.out.println( "setYieldPerWeek" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerWeek( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetYieldPerWeek_String_boolean () {
      System.out.println( "setYieldPerWeek" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setYieldPerWeek( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCropYieldUnit () {
      System.out.println( "getCropYieldUnit" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCropYieldUnit();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCropYieldUnitState () {
      System.out.println( "getCropYieldUnitState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCropYieldUnitState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropYieldUnit_String () {
      System.out.println( "setCropYieldUnit" );
      String i = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCropYieldUnit( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropYieldUnit_String_boolean () {
      System.out.println( "setCropYieldUnit" );
      String i = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCropYieldUnit( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCropYieldUnitValue () {
      System.out.println( "getCropYieldUnitValue" );
      CPSPlanting instance = new CPSPlanting();
      float expResult = 0.0F;
      float result = instance.getCropYieldUnitValue();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCropYieldUnitValueString () {
      System.out.println( "getCropYieldUnitValueString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCropYieldUnitValueString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCropYieldUnitValueState () {
      System.out.println( "getCropYieldUnitValueState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCropYieldUnitValueState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropYieldUnitValue_float () {
      System.out.println( "setCropYieldUnitValue" );
      float i = 0.0F;
      CPSPlanting instance = new CPSPlanting();
      instance.setCropYieldUnitValue( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropYieldUnitValue_float_boolean () {
      System.out.println( "setCropYieldUnitValue" );
      float i = 0.0F;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCropYieldUnitValue( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropYieldUnitValue_String () {
      System.out.println( "setCropYieldUnitValue" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCropYieldUnitValue( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCropYieldUnitValue_String_boolean () {
      System.out.println( "setCropYieldUnitValue" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCropYieldUnitValue( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetTotalYield () {
      System.out.println( "getTotalYield" );
      CPSPlanting instance = new CPSPlanting();
      float expResult = 0.0F;
      float result = instance.getTotalYield();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetTotalYieldString () {
      System.out.println( "getTotalYieldString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getTotalYieldString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetTotalYieldState () {
      System.out.println( "getTotalYieldState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getTotalYieldState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTotalYield_float () {
      System.out.println( "setTotalYield" );
      float i = 0.0F;
      CPSPlanting instance = new CPSPlanting();
      instance.setTotalYield( i );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTotalYield_float_boolean () {
      System.out.println( "setTotalYield" );
      float i = 0.0F;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setTotalYield( i, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTotalYield_String () {
      System.out.println( "setTotalYield" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setTotalYield( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetTotalYield_String_boolean () {
      System.out.println( "setTotalYield" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setTotalYield( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testIsDirectSeeded () {
      System.out.println( "isDirectSeeded" );
      CPSPlanting instance = new CPSPlanting();
      boolean expResult = false;
      boolean result = instance.isDirectSeeded();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetDirectSeededState () {
      System.out.println( "getDirectSeededState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getDirectSeededState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDirectSeeded_String () {
      System.out.println( "setDirectSeeded" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setDirectSeeded( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDirectSeeded_Boolean () {
      System.out.println( "setDirectSeeded" );
      Boolean b = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setDirectSeeded( b );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetDirectSeeded_Boolean_boolean () {
      System.out.println( "setDirectSeeded" );
      Boolean b = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setDirectSeeded( b, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testIsFrostHardy () {
      System.out.println( "isFrostHardy" );
      CPSPlanting instance = new CPSPlanting();
      boolean expResult = false;
      boolean result = instance.isFrostHardy();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testIsFrostTender () {
      System.out.println( "isFrostTender" );
      CPSPlanting instance = new CPSPlanting();
      boolean expResult = false;
      boolean result = instance.isFrostTender();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetFrostHardyState () {
      System.out.println( "getFrostHardyState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getFrostHardyState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFrostHardy_String () {
      System.out.println( "setFrostHardy" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setFrostHardy( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFrostHardy_Boolean () {
      System.out.println( "setFrostHardy" );
      Boolean b = null;
      CPSPlanting instance = new CPSPlanting();
      instance.setFrostHardy( b );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetFrostHardy_Boolean_boolean () {
      System.out.println( "setFrostHardy" );
      Boolean b = null;
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setFrostHardy( b, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetGroups () {
      System.out.println( "getGroups" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getGroups();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetGroupsState () {
      System.out.println( "getGroupsState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getGroupsState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetGroups_String () {
      System.out.println( "setGroups" );
      String e = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setGroups( e );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetGroups_String_boolean () {
      System.out.println( "setGroups" );
      String e = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setGroups( e, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetKeywords () {
      System.out.println( "getKeywords" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getKeywords();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetKeywordsState () {
      System.out.println( "getKeywordsState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getKeywordsState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetKeywords_String () {
      System.out.println( "setKeywords" );
      String e = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setKeywords( e );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetKeywords_String_boolean () {
      System.out.println( "setKeywords" );
      String e = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setKeywords( e, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetOtherRequirments () {
      System.out.println( "getOtherRequirments" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getOtherRequirments();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetOtherRequirmentsState () {
      System.out.println( "getOtherRequirmentsState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getOtherRequirmentsState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetOtherRequirements_String () {
      System.out.println( "setOtherRequirements" );
      String e = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setOtherRequirements( e );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetOtherRequirements_String_boolean () {
      System.out.println( "setOtherRequirements" );
      String e = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setOtherRequirements( e, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetNotes () {
      System.out.println( "getNotes" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getNotes();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetNotesState () {
      System.out.println( "getNotesState" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getNotesState();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetNotes_String () {
      System.out.println( "setNotes" );
      String e = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setNotes( e );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetNotes_String_boolean () {
      System.out.println( "setNotes" );
      String e = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setNotes( e, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField1 () {
      System.out.println( "getCustomField1" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCustomField1();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField2 () {
      System.out.println( "getCustomField2" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCustomField2();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField3 () {
      System.out.println( "getCustomField3" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCustomField3();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField4 () {
      System.out.println( "getCustomField4" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCustomField4();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField5 () {
      System.out.println( "getCustomField5" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.getCustomField5();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField1State () {
      System.out.println( "getCustomField1State" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCustomField1State();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField2State () {
      System.out.println( "getCustomField2State" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCustomField2State();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField3State () {
      System.out.println( "getCustomField3State" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCustomField3State();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField4State () {
      System.out.println( "getCustomField4State" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCustomField4State();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testGetCustomField5State () {
      System.out.println( "getCustomField5State" );
      CPSPlanting instance = new CPSPlanting();
      CPSDatumState expResult = null;
      CPSDatumState result = instance.getCustomField5State();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField1_String () {
      System.out.println( "setCustomField1" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField1( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField2_String () {
      System.out.println( "setCustomField2" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField2( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField3_String () {
      System.out.println( "setCustomField3" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField3( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField4_String () {
      System.out.println( "setCustomField4" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField4( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField5_String () {
      System.out.println( "setCustomField5" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField5( s );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField1_String_boolean () {
      System.out.println( "setCustomField1" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField1( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField2_String_boolean () {
      System.out.println( "setCustomField2" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField2( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField3_String_boolean () {
      System.out.println( "setCustomField3" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField3( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField4_String_boolean () {
      System.out.println( "setCustomField4" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField4( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testSetCustomField5_String_boolean () {
      System.out.println( "setCustomField5" );
      String s = "";
      boolean force = false;
      CPSPlanting instance = new CPSPlanting();
      instance.setCustomField5( s, force );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testDiff () {
      System.out.println( "diff" );
      CPSRecord thatPlanting = null;
      CPSPlanting instance = new CPSPlanting();
      CPSRecord expResult = null;
      CPSRecord result = instance.diff( thatPlanting );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testInheritFrom () {
      System.out.println( "inheritFrom" );
      CPSRecord thatRecord = null;
      CPSPlanting instance = new CPSPlanting();
      CPSRecord expResult = null;
      CPSRecord result = instance.inheritFrom( thatRecord );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testIterator () {
      System.out.println( "iterator" );
      CPSPlanting instance = new CPSPlanting();
      PlantingIterator expResult = null;
      PlantingIterator result = instance.iterator();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testToString () {
      System.out.println( "toString" );
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.toString();
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testParseDate () {
      System.out.println( "parseDate" );
      String s = "";
      CPSPlanting instance = new CPSPlanting();
      Date expResult = null;
      Date result = instance.parseDate( s );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

   public void testFormatDate () {
      System.out.println( "formatDate" );
      Date d = null;
      CPSPlanting instance = new CPSPlanting();
      String expResult = "";
      String result = instance.formatDate( d );
      assertEquals( expResult, result );
      // TODO review the generated test code and remove the default call to fail.
      fail( "The test case is a prototype." );
   }

}
