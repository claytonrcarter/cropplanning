/*
 * HSQLDBTest.java
 * JUnit based test
 *
 * Created on January 16, 2007, 2:06 PM
 */

package CPS.Core.DB;

import java.io.File;
import junit.framework.*;
import junit.extensions.PrivilegedAccessor;
import CPS.Module.CPSDataModel;
import java.sql.*;

/**
 *
 * @author Clayton
 */
public class HSQLDBTest extends TestCase {
   
   HSQLDB h = null;
   String dbPath;
   boolean printName = true;

   private void printTestName( String s ) {
      if ( printName ) {
         System.out.println( s );
      }
      printName = false;
   }
   
   public void testSetUp() {
      printTestName("testSetUp");
      
      // theoretically, this will only call the constructor once
      // in cases where a test has multiple dependencies
      if ( h == null )
         h = new HSQLDB(true);
   }

   public void testBuildPaths() throws Exception {
      printTestName("testBuildPaths");
      testSetUp();
      
      PrivilegedAccessor.invokeMethod( h, "buildDBPaths()" );
      dbPath = (String) PrivilegedAccessor.getValue( h, "dbPath" );
      
      assertTrue( h.state.equals( "paths built" ));
      assertTrue( dbPath.endsWith( "CPSdb" ) );
   }
     
   public void testLoadDriver() throws Exception {
      printTestName("testLoadDriver");
      testSetUp();
      
      PrivilegedAccessor.invokeMethod( h, "loadDriver()" );
      
      assertTrue( h.state.equals( "driver loaded" ));
   }
   
   
   public void testConnectExisting() throws Exception {
      printTestName("testConnectExisting");
      testBuildPaths();
      testLoadDriver();
      
      String dbCon = (String) PrivilegedAccessor.getValue( h, "dbCon" );
      dbCon += ";ifexists=true";
      
      System.out.println("Connecting to database: " + dbCon );
      
      PrivilegedAccessor.invokeMethod( h,
                                       "connectToDB( java.lang.String )",
                                       dbCon );
      
      System.out.println("state: " + h.state );
   }
   
   
   public void testTearDown() throws Exception {
      printTestName("testTearDown");
      testBuildPaths();
         
      System.out.println( "Deleting database files: " + dbPath );
      
      File f;
      String s[] = { ".lck", ".log", ".script", ".properties" };
      
      for ( int i = 0; i < s.length; i++ ) {
         f = new File( dbPath + s[i] );
         if ( f.exists() )
            if ( f.delete() )
               assertTrue( true );
            else
               assertTrue( false ); // fail
      }
      
   }
   
   public void testConstructor() throws Exception {
      printTestName("testConstructor");
      new HSQLDB();
   }
   
   
   public static Test suite() {
      
      TestSuite suite = new TestSuite();

      suite.addTest( new HSQLDBTest("testSetUp") );
      suite.addTest( new HSQLDBTest("testBuildPaths") );
      suite.addTest( new HSQLDBTest("testLoadDriver") );
      suite.addTest( new HSQLDBTest("testConnectExisting") );
      suite.addTest( new HSQLDBTest("testTearDown") );
      suite.addTest( new HSQLDBTest("testConstructor") );
      suite.addTest( new HSQLDBTest("testTearDown") );
      

      return suite;
      
   }
      
   public HSQLDBTest(String testName) {
      super(testName);
   }

}
