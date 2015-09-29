package edu.wctc.bmh.bookapp2.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MySQLDatabase implements DataAccessStrategy {

    private Connection connection;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private static final String IAE_TABLE_EMPTY = "null or empty table name!";
    private static final String IAE_COLUMN_EMPTY = "null or empty column!";
    private static final String IAE_SEARCH_EMPTY = "null or empty search!";
    private static final String IAE_RECORD_EMPTY = "null or empty record";
    private static final String IAE_INVALID_KEY = "Key doesn't exist or is invalid";

    public MySQLDatabase() {
    }

    /**
     *
     * Opens the connection to the MySQL database
     *
     * @param driverName
     * @param url
     * @param userName
     * @param password
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public final void openConnection(final String driverName, final String url, String userName, String password) throws IllegalArgumentException, ClassNotFoundException, SQLException {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (userName == null) {
            userName = "";
        }
        if (password == null) {
            password = "";
        }

        Class.forName(driverName);
        connection = DriverManager.getConnection(url, userName, password);
    }

    /**
     *
     * Closes the connection to the MySQL database
     *
     * @throws SQLException
     */
    @Override
    public final void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     *
     * Adds multiple records to the a table
     *
     * @param tableName
     * @param records
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    @Override
    public final void insertRecords(final String tableName, final List<Map<String, Object>> records) throws IllegalArgumentException, SQLException {
        for (Map<String, Object> record : records) {
            insertRecord(tableName, record);
        }
    }

    /**
     *
     * Adds one new record with a table name and record data
     *
     * @param tableName
     * @param record
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    @Override
    public final void insertRecord(final String tableName, final Map<String, Object> record) throws IllegalArgumentException, SQLException {

        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException(IAE_TABLE_EMPTY);
        } else if (record == null || record.isEmpty()) {
            throw new IllegalArgumentException(IAE_RECORD_EMPTY);
        }

//        String mySQLQuery = "INSERT INTO " + tableName + " (";
//
//        for (String column : record.keySet()) {
//
//            
//            if (column.equals(record.keySet().toArray()[0])) {
//                mySQLQuery += "`" + column + "`";
//            } else {
//                mySQLQuery += ", `" + column + "`";
//            }
//        }
//        mySQLQuery += ") VALUES (";
//
//        for (Object value : record.values()) {
//
//            if (value.equals(record.values().toArray()[0])) {
//                mySQLQuery += "'" + value + "'";
//            } else {
//                mySQLQuery += ", '" + value + "'";
//            }
//        }
//        mySQLQuery += ");";
//
//        executeQuery(mySQLQuery);
        
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        (sql.append(tableName)).append(" (");
        for (String column : record.keySet()) {
            sql.append("`").append(column).append("`").append(", ");
        }
        sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) + ") VALUES (" );
        for (int i =0; i<record.size(); i++) {
            sql.append("?, ");
        }
        final String finalSQL=(sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ")) + ");";
        
        PreparedStatement statement = connection.prepareStatement(finalSQL);
        
        for (int i =1; i<= record.size(); i++) {
            statement.setObject(i, record.values().toArray()[i - 1]);
        }
        
        statement.executeUpdate();
        statement.close();
        
    }

    /**
     *
     * Grabs all records with a table name
     *
     * SELECT * FROM tableName
     *
     * @param tableName
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    @Override
    public final List<Map<String, Object>> getAllRecords(final String tableName) throws IllegalArgumentException, SQLException {

        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException(IAE_TABLE_EMPTY);
        }

        final String mySQLQuery = "SELECT * FROM " + tableName + ";";
        return getRecordsFromQuery(mySQLQuery);
    }

    /**
     *
     * Grabs certain records with a table name based on search terms and column
     * names
     *
     * @param tableName
     * @param columnName
     * @param searchTerm
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    @Override
    public final List<Map<String, Object>> getRecordsByCriteria(final String tableName, final String columnName, final String searchTerm) throws IllegalArgumentException, SQLException {

        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException(IAE_TABLE_EMPTY);
        } else if (columnName == null || columnName.isEmpty()) {
            throw new IllegalArgumentException(IAE_COLUMN_EMPTY);
        } else if (searchTerm == null || searchTerm.isEmpty()) {
            throw new IllegalArgumentException(IAE_SEARCH_EMPTY);
        }

        final String mySQLQuery = "SELECT * FROM " + tableName + " WHERE " + columnName + " = " + searchTerm + ";";
        return getRecordsFromQuery(mySQLQuery);
    }


    private List<Map<String, Object>> getRecordsFromQuery(final String mySQLQuery) throws SQLException {

        final List<Map<String, Object>> records = new ArrayList<>();
        Map<String, Object> record = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metadata = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(mySQLQuery);
            metadata = resultSet.getMetaData();
            final int fields = metadata.getColumnCount();

            while (resultSet.next()) {
                record = new LinkedHashMap();
                for (int i = 1; i <= fields; i++) {
                    try {
                        record.put(metadata.getColumnName(i), resultSet.getObject(i));
                    } catch (NullPointerException ne) {

                    }
                }
                records.add(record);
            }

        } finally {
            closeConnection();
        }

        return records;
    }

    /**
     *
     * Delete a record with a similar record
     *
     * @param tableName
     * @param record
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    @Override
    public void deleteRecord(String tableName, Map<String, Object> record) throws IllegalArgumentException, SQLException {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException(IAE_TABLE_EMPTY);
        } else if (record == null || record.isEmpty()) {
            throw new IllegalArgumentException(IAE_RECORD_EMPTY);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * Deletes a record with a primary Key
     *
     * DELETE [LOW_PRIORITY] [QUICK] [IGNORE] FROM tbl_name [WHERE
     * where_condition] [ORDER BY ...] [LIMIT row_count]
     *
     * @param tableName
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    @Override
    public void deleteRecord(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws IllegalArgumentException, SQLException {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException(IAE_TABLE_EMPTY);
        } else if (primaryKeyFieldName == null || primaryKeyFieldName.isEmpty() || primaryKeyValue == null) {
            throw new IllegalArgumentException(IAE_INVALID_KEY);
        }

        final StringBuffer sql = new StringBuffer("DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        (sql.append(primaryKeyFieldName)).append(" = ?");

        PreparedStatement stmt = connection.prepareStatement(sql.toString());
        stmt.setObject(1, primaryKeyValue);
        stmt.executeUpdate();

    }

    /**
     *
     * Updates a record with a primary key
     *
     * UPDATE [LOW_PRIORITY] [IGNORE] table_reference SET
     * col_name1={expr1|DEFAULT} [, col_name2={expr2|DEFAULT}] ... [WHERE
     * where_condition] [ORDER BY ...] [LIMIT row_count]
     *
     * @param tableName - String
     * @param primaryKeyFieldName - String
     * @param primaryKeyValue - Object
     * @param record - Map<String, Object>
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    @Override
    public void updateRecord(String tableName, String primaryKeyFieldName, Object primaryKeyValue, Map<String, Object> record) throws IllegalArgumentException, SQLException {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException(IAE_TABLE_EMPTY);
        } else if (primaryKeyValue == null) {
            throw new IllegalArgumentException(IAE_INVALID_KEY);
        } else if (primaryKeyValue == null) {
            throw new IllegalArgumentException(IAE_RECORD_EMPTY);
        }
        
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");

        for (String column : record.keySet()) {
            (sql.append( column )).append(" = ?, ");
        }
        
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(primaryKeyFieldName)).append(" = ?");
        final String finalSQL = sql.toString();

        PreparedStatement statement = connection.prepareStatement(sql.toString());
        
        for (int i = 1; i <= record.size(); i++) {
            statement.setObject(i, record.values().toArray()[i-1]);
        }
        
        statement.setObject(record.size() + 1, primaryKeyValue);

        statement.executeUpdate();
    }

}
