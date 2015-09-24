

package edu.wctc.bmh.bookapp2.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Benjamin Hanson
 * This interface represents the base for a simple C.R.U.D (Create, Read, Update, Delete)
 */
public interface DataAccessStrategy {

    /**
     *  Closes the connection to the database
     * 
     * @throws SQLException
     */
    public abstract void closeConnection() throws SQLException;
    
    /**
     *  Opens the connection to the database
     * 
     * @param driverName
     * @param url
     * @param userName
     * @param password
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public abstract void openConnection(final String driverName, final String url, String userName, String password) throws IllegalArgumentException, ClassNotFoundException, SQLException;
    
    /**
     *  Reads all the records for a table
     *
     * @param tableName
     * @return records
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public abstract List<Map<String, Object>> getAllRecords(final String tableName) throws IllegalArgumentException, SQLException;

    /**
     *  Reads all the record by a certain criteria
     * 
     * @param tableName
     * @param columnName
     * @param searchTerm
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public abstract List<Map<String, Object>> getRecordsByCriteria(final String tableName, final String columnName, final String searchTerm) throws IllegalArgumentException, SQLException;

    /**
     *  Creates a new record to a database table
     * 
     * @param tableName
     * @param record
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public abstract void insertRecord (final String tableName, final Map<String, Object> record ) throws IllegalArgumentException, SQLException;
    
    /**
     *  Creates multiple records to the database table
     * 
     * @param tableName
     * @param records
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public abstract void insertRecords (final String tableName, final List<Map<String, Object>> records ) throws IllegalArgumentException, SQLException;
    
    /**
     *  Deletes a record from the database Table with a record
     * 
     * @param tableName
     * @param record
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public abstract void deleteRecord (final String tableName, final Map<String, Object> record ) throws IllegalArgumentException, SQLException;
    
    /**
     *  Deletes a record from the database table with a primary key value
     * 
     * @param tableName
     * @param primaryKeyFieldName
     * @param primaryKeyValue
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public abstract void deleteRecord(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws IllegalArgumentException, SQLException;
    
    /**
     *  Updates a record from the database table with a primary key value
     * 
     * @param tableName
     * @param primaryKey
     * @param record
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public abstract void updateRecord (final String tableName, final int primaryKey, final Map<String, Object> record) throws IllegalArgumentException, SQLException;
    
    
}
