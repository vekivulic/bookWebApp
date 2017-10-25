/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.veki.workingbookapp.model;

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
import java.util.StringJoiner;

/**
 *
 * @author vekivulic
 */

    public class MySqlDataAccess implements DataAccess {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private static final String ERROR_INVALID_INPUT = "Error: Invalid input. "
            + "Input cannot be null or empty. Number values cannot be less than"
            + "1.";
    /**
     * Open database connection
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    //null first then empty 
    public final void openConnection(String driverClass, String url, String userName, 
            String password) throws ClassNotFoundException, SQLException{
        if(driverClass==null || driverClass.isEmpty() || userName==null ||
                userName.isEmpty() || password==null || password.isEmpty()||
                url==null|| url.isEmpty()){        
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        
        Class.forName(driverClass);
        connection = DriverManager.getConnection(url,userName,password);
    }
    /**
     * 
     * @throws SQLException 
     */
    @Override
    public final void closeConnection() throws SQLException{
        if(connection!= null){
            connection.close();
        }
    }
    /**
     * 
     * @param table
     * @param idColName
     * @param recordId
     * @return
     * @throws SQLException 
     */
    @Override
    public final Map<String,Object> getSingleRecord(String table, String 
            idColName, String recordId)throws SQLException{
        String sql = "SELECT * FROM " + table + " WHERE " + idColName + " = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, recordId);
        resultSet = preparedStatement.executeQuery();
        
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        Map<String,Object> map = new LinkedHashMap<>();
        
        while(resultSet.next()){
            for(int col = 1 ; col < colCount+1 ;col++){
                map.put(rsmd.getColumnName(col), resultSet.getObject(col));
            }
        }

        return map;
    }
    /**
     * Retrieve all records
     * @param table
     * @param maxRecords
     * @return
     * @throws SQLException 
     */
    @Override
    public final List<Map<String,Object>> getAllRecords(String table, int maxRecords) 
            throws SQLException{
        if(table == null || table.isEmpty()){
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        String sql = "";
        
        if(maxRecords > 0){
            sql = "SELECT * FROM " + table + " LIMIT " + maxRecords;
        } else {
            sql = "SELECT * FROM " + table;
        }
        
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        List<Map<String,Object>> results = new ArrayList<>();
        
        while(resultSet.next()){
            Map<String,Object> map = new LinkedHashMap<>();
            
            for(int col = 1 ; col < colCount+1 ;col++){
                map.put(rsmd.getColumnName(col), resultSet.getObject(col));
            }
            results.add(map);
        }
        return results;
    }
    /**
     * Delete record(s) by id
     * @param table
     * @param pkName
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public final int deleteById(String table, String pkName, Object id) throws 
            SQLException{
        if(table == null ||table.isEmpty() || pkName == null || pkName.isEmpty() 
                || id == null){
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        int recordsDeleted = 0;
        String sql = "DELETE FROM  " + table + " WHERE " + pkName + 
                " = ?";
            
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, id);
        recordsDeleted = preparedStatement.executeUpdate();
            
        return recordsDeleted;
    }
    /**
     * 
     * @param tableName
     * @param colNames
     * @param colValues
     * @param idColName
     * @param idColValue
     * @return
     * @throws SQLException 
     */
    @Override
    public final int updateById(String tableName, List<String> colNames, 
            List<Object> colValues, String idColName, Object 
                    idColValue) throws SQLException{
        if(tableName == null || tableName.isEmpty() || colNames == null ||
                colValues == null || idColName == null || 
                idColName.isEmpty() || idColValue ==null){
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        int recordsUpdated = 0;
        
        String sql = "UPDATE " + tableName + " SET ";
        
        StringJoiner sj = new StringJoiner(",");
        
        for(String colName : colNames){
            sj.add(colName + " = ?");
        }
        sql += sj.toString();
        
        sql += " WHERE " + idColName + " = " + " ? ";
        preparedStatement = connection.prepareStatement(sql);
        
        for(int i =0;i < colNames.size() ; i++){
            preparedStatement.setObject(i+1, colValues.get(i));
        }
        
        preparedStatement.setObject(colNames.size()+1, idColValue);
        recordsUpdated = preparedStatement.executeUpdate();

        return recordsUpdated;
    }
    /**
     * 
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     * @throws SQLException 
     */
    @Override
    public final int insertInto(String tableName, List<String> colNames, List<Object> 
            colValues) throws SQLException{
        if(tableName == null || tableName.isEmpty() || colNames == null ||
                colValues == null){
             throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        int recordsInserted = 0;
        
        String sql = "INSERT INTO " + tableName + " ";
        StringJoiner sj = new StringJoiner(",","(",")");
        
        for(String colName : colNames){
            sj.add(colName);
        }
        sql += sj.toString();
        sql += " VALUES ";
        
        sj = new StringJoiner(",","(",")");
        
        for(Object colValue : colValues){
            sj.add("?");
        }
        
        sql += sj.toString();
        
        preparedStatement = connection.prepareStatement(sql);
        
        for(int i = 0; i < colValues.size(); i++){
            preparedStatement.setObject(i+1, colValues.get(i));
        }
        
        recordsInserted = preparedStatement.executeUpdate();
        
        return recordsInserted;
    }
    //TESTING PURPOSES
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        MySqlDataAccess test = new MySqlDataAccess();
        
        test.openConnection("com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", 
                        "root", "admin");
//        test.deleteById("author", "author_id", "2");
        
        
//        List<String> colNames = new ArrayList<>();
//        colNames.add("author_name");
//        colNames.add("date_added");
//        List<Object> colValues = new ArrayList<>();
//        colValues.add("Chris Gonzalez ");
//        colValues.add("2017-02-17");
//        test.insertInto("author", colNames, colValues);


//        List<String> colNamesUpdate = new ArrayList<>();
//        colNamesUpdate.add("author_name");
//        colNamesUpdate.add("date_added");
//        List<Object> colValuesUpdate = new ArrayList<>();
//        colValuesUpdate.add("TEST TEST TEST");
//        colValuesUpdate.add("2011-11-11"); 
//
//
//        test.updateById("author", colNamesUpdate, colValuesUpdate, "author_id", "12");
//        
//
//
//
//        List<Map<String,Object>> records = test.getAllRecords("author", 50);
        
        Map<String,Object> record = test.getSingleRecord("author", "author_id", "14");
        System.out.println(record);
        test.closeConnection();
    }
}
    
    
    

