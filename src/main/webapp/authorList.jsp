<%-- 
    Document   : authorList
    Created on : Oct 12, 2017, 5:35:19 PM
    Author     : vekivulic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>





<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            Author List
        </title>
    </head>
    <div class="container"> 
        <body>


            <h1>
                Author List
            </h1>
            
            
            <br>
            <form id="authorFormDelete" name="authorFormDelete" method="POST" action="ac?rType=deleteAuthor">  
                <button type="submit" class="btn btn-success" formaction="ac?rType=addAuthor" name="add">Add</button>
                <input type="submit" class="btn btn-info" name="delete" value="Delete">
            <br>            
            <br>
            <table class="table">
                <tr class="warning">
                    <th class="warning">
                       
                    </th>
                    <th>
                        ID
                    </th>
                    <th >
                        Name
                    </th>
                    <th>
                        Date Added
                    </th>
                    <th>
                       
                    </th>
                </tr>    
                <c:forEach var="a" items="${authors}" varStatus="varStatus">
                   <c:choose>
                        <c:when test="${varStatus.count%2 == 0}">
                            <tr class="info">                                 
                        </c:when>
                        <c:otherwise>
                            <tr class="success">    
                        </c:otherwise>     
                                
                    </c:choose>
                                <td >
                                    <input type="checkbox" name="authorId" value="${a.authorId}">
                                </td>
                                <td >
                                    ${a.authorId}
                                </td>
                                <td >
                                    ${a.authorName}
                                </td>
                                <td>
                                    <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"/>                                 
                                </td>
                                <td>
                                    <button  class="btn btn-warning" type="submit" formaction="ac?rType=editAuthor&id=${a.authorId}" value="${a.authorId}" name="edit">Edit</button>
                                </td>
                            </tr>  
                </c:forEach>             
            </table>
            </form>  
            </form>       
            <br>
            <br>
            <a href="ac?rType=home">Go to Home Page</a>
        </body>
        <br>
        <br>
        
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
</html>