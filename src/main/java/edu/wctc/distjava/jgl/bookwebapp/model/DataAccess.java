package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jlombardo
 */
public interface DataAccess {

    void closeConnection() throws SQLException;

    /**
     * Returns records from a table. Requires and open connection.
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     */
    
     public abstract void openConnection(String driverClass, 
            String url, String userName, String password) 
            throws ClassNotFoundException, SQLException;
    
    public int deleteRecordById(String tableName, String pkColName, 
            Object pkValue) throws ClassNotFoundException, 
            SQLException;
    
    List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException;

    public abstract int createRecord(String tableName, List<String> colNames, 
            List<Object> colValues)throws SQLException;
    
 public abstract int updateById(String tableName, List<String> colNames, 
            List<Object> colValues, String idColName, Object 
                    idColValue) throws SQLException;
        
        

    public abstract int insertInto(String tableName, List<String> colNames, List<Object> 
            colValues) throws SQLException;
        }
    

