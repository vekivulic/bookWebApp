/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.veki.workingbookapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author vekivulic
 */
public interface IAuthorDao {
    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public abstract boolean equals(Object obj);
    /**
     * 
     * @param tableName
     * @param maxRecords
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public abstract List<Author> getAuthorList(String tableName, int maxRecords) 
            throws ClassNotFoundException, SQLException;
    /**
     * 
     * @param authorTableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public abstract Author retrieveAuthor(String authorTableName, String 
            authorIdColName, String authorId)throws ClassNotFoundException, 
            SQLException;
    /**
     * 
     * @param tableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public abstract int deleteAuthorById(String tableName, String authorIdColName,
            Object authorId) throws ClassNotFoundException, SQLException;
    /**
     * 
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public int addNewAuthor(String tableName, List<String> colNames, List<Object> 
            colValues) throws ClassNotFoundException, SQLException;
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
    public int updateAuthorById(String authorTableName, List<String> colNames, 
            List<Object> colValues, String authorIdColName, Object 
                    authorId) throws SQLException,ClassNotFoundException;
    /**
     * 
     * @return 
     */
    public abstract DataAccess getDb();
    /**
     * 
     * @return 
     */
    public abstract String getDriverClass();
    /**
     * 
     * @return 
     */
    public abstract String getPassword();
    /**
     * 
     * @return 
     */
    public abstract String getUrl();
    /**
     * 
     * @return 
     */
    public abstract String getUsername();
    /**
     * 
     * @return 
     */
    @Override
    public abstract int hashCode();
    /**
     * 
     * @param db 
     */
    public abstract void setDb(DataAccess db);
    /**
     * 
     * @param driverClass 
     */
    public abstract void setDriverClass(String driverClass);
    /**
     * 
     * @param password 
     */
    public abstract void setPassword(String password);
    /**
     * 
     * @param url 
     */
    public abstract void setUrl(String url);
    /**
     * 
     * @param userName 
     */
    public abstract void setUsername(String username);
    
}