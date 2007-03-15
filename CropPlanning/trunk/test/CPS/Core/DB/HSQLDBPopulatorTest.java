/*
 * HSQLDBPopulatorTest.java
 * JUnit based test
 *
 * Created on March 13, 2007, 4:43 PM
 */

package CPS.Core.DB;

import junit.framework.*;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author Clayton
 */
public class HSQLDBPopulatorTest extends TestCase {
   
   public HSQLDBPopulatorTest(String testName) {
      super(testName);
   }

   protected void setUp() throws Exception {
   }

   protected void tearDown() throws Exception {
   }

   /**
    * Test of populateTables method, of class CPS.Core.DB.HSQLDBPopulator.
    */
   public void testPopulateTables() {
      System.out.println("populateTables");
      
      Connection con = null;
      
      HSQLDBPopulator.populateTables(con);
      
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of populateCropsAndVarieties method, of class CPS.Core.DB.HSQLDBPopulator.
    */
   public void testPopulateCropsAndVarieties() {
      System.out.println("populateCropsAndVarieties");
      
      // System.out.println( 10 + " is of type " + 10.getClass().toString() );
      
      
      //Connection con = null;
      
      //HSQLDBPopulator.populateCropsAndVarieties(con);
      
      // TODO review the generated test code and remove the default call to fail.
      //fail("The test case is a prototype.");
   }
   
}
