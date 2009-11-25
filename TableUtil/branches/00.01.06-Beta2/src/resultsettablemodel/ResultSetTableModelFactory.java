/* ResultSetTableModelFactory.java
 * Copyright © 2000-2008 O'Reilly Media, Inc. All Rights Reserved.
 * 
 * This file is distributed as part of the project "Crop Planning Software".
 * For more information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 *
 * The source for this class was originally found at:
 * 
 *     http://www.oreillynet.com/oreilly/java/news/javaex_code.zip
 *
 * With an accompanying article here:
 * 
 *     http://www.oreillynet.com/pub/a/oreilly/java/news/javaex_1000.html
 * 
 * No statement is made at that site regarding the licensing of this source
 * code.  As such we are using it AS IS and WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE.
 * 
 */


package resultsettablemodel;

import java.sql.*;
import javax.swing.table.*;

/**
 * This class encapsulates a JDBC database connection and, given a SQL query
 * as a string, returns a ResultSetTableModel object suitable for display
 * in a JTable Swing component
 **/
public class ResultSetTableModelFactory {
    Connection connection;  // Holds the connection to the database

    /** package level constructor
     *  required for subclass constructors that will not use the heavier duty
     *  constructor listed below */
    ResultSetTableModelFactory() {}
    
    /** The constructor method uses the arguments to create db Connection */
    public ResultSetTableModelFactory(String driverClassName, String dbname,
				      String username, String password)
        throws ClassNotFoundException, SQLException
    {
	// Look up the JDBC driver by class name.  When the class loads, it
	// automatically registers itself with the DriverManager used in
	// the next step.
	Class driver = Class.forName(driverClassName);

	// Now use that driver to connect to the database
	connection = DriverManager.getConnection(dbname, username, password);
    }
    
    /**
     * This method takes a SQL query, passes it to the database, obtains the
     * results as a ResultSet, and returns a ResultSetTableModel object that
     * holds the results in a form that the Swing JTable component can use.
     **/
    public ResultSetTableModel getResultSetTableModel(String query)
        throws SQLException
    {
	// If we've called close(), then we can't call this method
	if (connection == null)
	    throw new IllegalStateException("Connection already closed.");

	// Create a Statement object that will be used to excecute the query.
	// The arguments specify that the returned ResultSet will be 
	// scrollable, read-only, and insensitive to changes in the db.
	Statement statement =
	    connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				       ResultSet.CONCUR_READ_ONLY);
	// Run the query, creating a ResultSet
	ResultSet r = statement.executeQuery(query);
	// Create and return a TableModel for the ResultSet
	return new ResultSetTableModel(r);
    }

    /** 
     * Call this method when done with the factory to close the DB connection 
     **/
    public void close() {
	try { connection.close(); } // Try to close the connection
	catch (Exception e) {}      // Do nothing on error. At least we tried.
	connection = null; 
    }

    /** Automatically close the connection when we're garbage collected */
    protected void finalize() { close(); }
}
