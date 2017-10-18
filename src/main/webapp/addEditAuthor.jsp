<%-- 
    Document   : addEditAuthor
    Created on : Oct 12, 2017, 5:34:26 PM
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
            Add/Edit Author
        </title>
    </head>
    <div class="container"> 
        <body>
            <h1>
                Add/Edit Author
            </h1>
            <form id="addEditAuthorForm" name="addEditAuthorForm" method="POST" action="ac?rType=saveAuthor">
                <table class="table">  
                    <c:if test="${not empty authorId}">
                        <tr class="info">
                            <td>
                                Author ID     
                            </td>
                            <td>
                                <input type="text" id="authorId" name="authorId" readonly="readonly" value="${authorId}">
                            </td>
                        </tr>
                    </c:if>                     
                    <tr class="success">
                        <td>
                            Author Name   
                        </td>
                        <td>
                            <input type="text" id="authorName" name="authorName" value="${authorName}">
                        </td>
                    </tr>
                    <c:if test="${not empty dateAdded}">
                        <tr class="info">
                            <td>
                                Date Added
                            </td>
                            <td>
                                <input type="text" id="dateAdded" name="dateAdded" readonly="readonly" value="${dateAdded}">
                            </td>
                        </tr>  
                    </c:if>
            </table>
                <br>
                <input type="submit"   name="submit" value="Submit Changes">
                <button type="submit" class="btn btn-success"   formaction="ac?rType=authorList" name="cancel">Cancel</button>
            </form>
        </body>
    </div>    
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
</html>