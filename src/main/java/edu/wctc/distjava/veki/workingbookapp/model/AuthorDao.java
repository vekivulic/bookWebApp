/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.veki.workingbookapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author vekivulic
 */

    
    public class AuthorDao implements IAuthorDao {
    private DataAccess db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
    private static final String AUTHOR_ID_COL_NAME = "author_id";
    private static final String AUTHOR_NAME_COL_NAME = "author_name";
    private static final String DATE_ADDED_COL_NAME = "date_added";
    /**
     * 
     * @param db
     * @param driverClass
     * @param url
     * @param userName
     * @param password 
     */
    public AuthorDao(DataAccess db, String driverClass, String url, String 
            userName, String password) {
        setDb(db);
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }
    /**
     * 
     * @param tableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final int deleteAuthorById(String tableName, String authorIdColName, 
            Object authorId) throws ClassNotFoundException, SQLException{
        db.openConnection(driverClass, url, userName, password);
        int recsDeleted = db.deleteById(tableName, authorIdColName, authorId);
        db.closeConnection();
        return recsDeleted;
    }
    /**
     * 
     * @param authorTableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final Author retrieveAuthor(String authorTableName, String 
            authorIdColName, String authorId)throws ClassNotFoundException, 
            SQLException {
        db.openConnection(driverClass, url, userName, password);
        
        Map<String,Object> rawRec = db.getSingleRecord(
                authorTableName, authorIdColName, authorId);
        
        Author author = new Author();
        
        Object objId = rawRec.get(AUTHOR_ID_COL_NAME);
        Integer id = (Integer)objId;
        author.setAuthorId(id);

        Object objName = rawRec.get(AUTHOR_NAME_COL_NAME);
        String authorName = (objName != null) ? objName.toString() : "";
        author.setAuthorName(authorName);

        Object objDateAdded = rawRec.get(DATE_ADDED_COL_NAME);
        Date dateAdded = (objDateAdded != null) ? (Date)objDateAdded : null;
        author.setDateAdded(dateAdded);
        
        db.closeConnection();
        
        return author;
    }
    /**
     * 
     * @param tableName
     * @param maxRecords
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final List<Author> getAuthorList(String tableName, int maxRecords) 
            throws ClassNotFoundException, SQLException{
        db.openConnection(driverClass, url, userName, password);
        
        List<Author> records = new ArrayList<>();      
        List<Map<String, Object>> rawData = db.getAllRecords(tableName, 
                maxRecords);
        
        for(Map<String,Object> rawRec : rawData){
            Author author = new Author();
            
            Object objId = rawRec.get(AUTHOR_ID_COL_NAME);
            Integer authorId = (Integer)objId;
            author.setAuthorId(authorId);
            
            Object objName = rawRec.get(AUTHOR_NAME_COL_NAME);
            String authorName = (objName != null) ? objName.toString() : "";
            author.setAuthorName(authorName);
            
            Object objDateAdded = rawRec.get(DATE_ADDED_COL_NAME);
            Date dateAdded = (objDateAdded != null) ? (Date)objDateAdded : null;
            author.setDateAdded(dateAdded);
            
            records.add(author);
        }
        db.closeConnection();
        
        return records;
    }
    /**
     * 
     * @param authorTableName
     * @param colNames
     * @param colValues
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final int updateAuthorById(String authorTableName, List<String> colNames, 
            List<Object> colValues, String authorIdColName, Object authorId) 
            throws SQLException, ClassNotFoundException{
        int authorRecordsUpdated = 0;
        db.openConnection(driverClass, url, userName, password);
        authorRecordsUpdated = db.updateById(authorTableName, colNames, 
                colValues, authorIdColName, authorId);
        db.closeConnection();
        return authorRecordsUpdated;
    }
    /**
     * 
     * @param tableName
     * @param authorTableColNames
     * @param authorTableColValues
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final int addNewAuthor(String tableName, List<String> 
            authorTableColNames, List<Object> authorTableColValues) 
            throws ClassNotFoundException, SQLException{
        int authorsAdded = 0;
        db.openConnection(driverClass, url, userName, password);
        authorsAdded = db.insertInto(tableName, authorTableColNames, authorTableColValues);
        db.closeConnection();
        return authorsAdded;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final DataAccess getDb() {
        return db;
    }
    /**
     * 
     * @param db 
     */
    @Override
    public final void setDb(DataAccess db) {
        //VALIDATE
        this.db = db;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final String getDriverClass() {
        return driverClass;
    }
    /**
     * 
     * @param driverClass 
     */
    @Override
    public final void setDriverClass(String driverClass) {
        //VALIDATE
        this.driverClass = driverClass;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final String getUrl() {
        return url;
    }
    /**
     * 
     * @param url 
     */
    @Override
    public final void setUrl(String url) {
        //VALIDATE
        this.url = url;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final String getUserName() {
        return userName;
    }
    /**
     * 
     * @param userName 
     */
    @Override
    public final void setUserName(String userName) {
        //VALIDATE
        this.userName = userName;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final String getPassword() {
        return password;
    }
    /**
     * 
     * @param password 
     */
    @Override
    public final void setPassword(String password) {
        //VALIDATE
        this.password = password;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.driverClass);
        hash = 97 * hash + Objects.hashCode(this.url);
        hash = 97 * hash + Objects.hashCode(this.userName);
        hash = 97 * hash + Objects.hashCode(this.password);
        return hash;
    }
    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthorDao other = (AuthorDao) obj;
        if (!Objects.equals(this.driverClass, other.driverClass)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }   
    //TESTING PURPOSES
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDao test = new AuthorDao(new MySqlDataAccess(),
                "com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", 
                        "root", "admin");
        
        
//        test.deleteAuthorById("author", "author_id", "5");

//        List<String> colNames = new ArrayList<>();
//        colNames.add("author_name");
//        colNames.add("date_added");
//        List<Object> colValues = new ArrayList<>();
//        colValues.add("Alex Trebek");
//        colValues.add("2011-01-27");
//        test.addNewAuthor("author", colNames, colValues);
        
//        List<String> colNamesUpdate = new ArrayList<>();
//        colNamesUpdate.add("author_name");
//        colNamesUpdate.add("date_added");
//        List<Object> colValuesUpdate = new ArrayList<>();
//        colValuesUpdate.add("THIS IS A TEST");
//        colValuesUpdate.add("1981-12-12"); 
//
//        test.updateAuthorById("author", colNamesUpdate, colValuesUpdate, "author_id", "12");
//
//
//
//
//        List<Author> authors = test.getAuthorList("author", 50);
        
        Author author = test.retrieveAuthor("author", "author_id", "14");
        
        System.out.println(author);
        
    }
}
    
    
    
    
    

