<%-- 
    Document   : index
    Created on : Sep 16, 2015, 4:40:01 PM
    Last edit  : Sep 25, 2015, 6:04:03 PM
    Author     : Benjamin Hanson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Manager</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="col-sm-3 col-md-2 sidebar">
                <ul class="nav nav-sidebar">
                    <li><p class="navbar-brand">Manager</p></li>
                </ul>
                <ul class="nav nav-pills nav-stacked">

                    <li class="active"><a href="author">Authors</a></li>
                    <li class=""><a href="book">Books</a></li>
                </ul>
            </div>

            </div>

            <div class="col-sm-9 col-md-10 col-md-offset-2 main">
                
                <!--Entity List -->            
            <jsp:include page = "${listPage}" />

                <c:if test="${not empty errMsg}">
                    <div class="alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        ${errMsg}
                    </div>                
                </c:if>

                <c:if test="${not empty succMsg}">
                    <div class="alert alert-success" role="alert">
                        <span class="glyphicon glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                        ${succMsg}
                    </div>                
                </c:if>

                <c:if test="${not empty delMsg}">
                    <div class="alert alert-info" role="alert">
                        <span class="glyphicon glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                        ${delMsg}
                    </div>                
                </c:if>

            </div>

            <!-- Create Modal -->
            <jsp:include page = "${createPage}" />

            <!-- Edit Modal -->            
            <jsp:include page = "${editPage}" />

        </div>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="Script/bootstrap.min.js" type="text/javascript"></script>
        <script src="Script/main.js" type="text/javascript"></script>
    </body>
</html>

