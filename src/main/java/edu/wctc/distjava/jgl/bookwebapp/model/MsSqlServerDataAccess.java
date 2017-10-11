package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Vector;

public class MsSqlServerDataAccess implements DataAccess {
    
    private final int ALL_RECORDS = 0;
    private final boolean DEBUG=true;
    private final String ERROR_INVALID_INPUT="Invalid input";
    
    
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    
    
    public void openConnection(String driverClass, 
            String url, String userName, String password) 
            throws ClassNotFoundException, SQLException {
        
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    public void closeConnection() throws SQLException {
        if(conn !=null) conn.close();
    }
    
    public int createRecord(String tableName, List<String> colNames, 
            List<Object> colValues) throws SQLException{
        String sql= "INSERT INTO " + tableName + " ";
        StringJoiner sj= new StringJoiner(", ", "( ", ")");
        for(String col : colNames){
            sj.add(col);
        }
       
        sql += sj.toString();
        sql += " VALUES ";
        
        sj= new StringJoiner(", ", "( ", ")");
        for(Object value : colValues){
            sj.add("?");
        }
        sql += sj.toString();
        if(DEBUG) System.out.println(sql);
        
         pstmt = conn.prepareStatement(sql);
          for(int i=1; i<= colValues.size(); i++){
           pstmt.setObject(i, colValues.get(i-1));
        }
        return pstmt.executeUpdate();
    }
    
    
    
    @Override
    public int deleteRecordById(String tableName, String pkColName, 
            Object pkValue) throws ClassNotFoundException, 
            SQLException {
        
        String sql = "DELETE FROM " + tableName + " WHERE " 
                + pkColName + " = ?";
        
      
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, pkValue);
        int recsDeleted = pstmt.executeUpdate();
   
        
        return recsDeleted;
    }
    
    /**
     * Returns records from a table. Requires and open connection.
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException 
     */
    public List<Map<String,Object>> getAllRecords(String tableName, int maxRecords) 
            throws SQLException, ClassNotFoundException {
        
        List<Map<String,Object>> rawData = new Vector<>();
        String sql = "";
        
        if(maxRecords > ALL_RECORDS) {
            sql = "select * from " + tableName + " limit " + maxRecords;
        } else {
            sql = "select * from " + tableName;
        }
        
   
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String,Object> record = null;
        
        while( rs.next() ) {
            record = new LinkedHashMap<>();
            for(int colNum=1; colNum <= colCount; colNum++) {
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }
        
   
        
        return rawData;
    }
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
        pstmt = conn.prepareStatement(sql);
        
        for(int i =0;i < colNames.size() ; i++){
           pstmt.setObject(i+1, colValues.get(i));
        }
        
        pstmt.setObject(colNames.size()+1, idColValue);
        recordsUpdated = pstmt.executeUpdate();

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
        
        pstmt = conn.prepareStatement(sql);
        
        for(int i = 0; i < colValues.size(); i++){
            pstmt.setObject(i+1, colValues.get(i));
        }
        
        recordsInserted = pstmt.executeUpdate();
        
        return recordsInserted;
    }
  
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        DataAccess db = new MySqlDataAccess();
        db.openConnection(    "com.mysql.jdbc.Driver",
             "jdbc:mysql://localhost:3306/book",
               "root", "admin");
    
        
       int recsAdded= db.createRecord("author", 
                Arrays.asList("author_name", "date_added"),
                Arrays.asList("Bob Jones", "2002-02-11"));
//            db.openConnection(    "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root", "admin");
//     
//        
         List<String> colNamesUpdate = new ArrayList<>();
        colNamesUpdate.add("author_name");
        colNamesUpdate.add("date_added");
        List<Object> colValuesUpdate = new ArrayList<>();
        colValuesUpdate.add("TEST TEST TEST");
        colValuesUpdate.add("2011-11-11"); 


       db.updateById("author", colNamesUpdate, colValuesUpdate, "author_id", "12");
        







//        int recsDeleted = db.deleteRecordById("author", "author_id", new Integer(52));
//        System.out.println("No. of Recs Deleted: " + recsDeleted);
//        List<Map<String,Object>> list = db.getAllRecords("author", 0);
//        
//        for(Map<String,Object> rec : list) {
//            System.out.println(rec);
//        }
            db.closeConnection();
            System.out.println("Rec created: " + recsAdded);
    }
    
}

    
