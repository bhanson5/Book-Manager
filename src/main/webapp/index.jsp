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
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Manager</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <jsp:include page = "sidebar.jsp" />
        </div>

        <div class="col-sm-9 col-md-10 col-md-offset-2 main">

            <c:if test="${not empty authors}">

                <div class="panel panel-success">
                    <div class="panel-heading">
                        <caption><span class="glyphicon glyphicon glyphicon-edit" aria-hidden="true"></span> Author Manager</caption>
                        <div class="btn-group right create" role="group" aria-label=".."> 
                            <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                <button data-toggle="modal" data-target="#createModal" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create</button>
                            </sec:authorize>
                            <button onclick="window.location.href = 'author'" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> Refresh</button>
                        </div>
                    </div>


                    <table class="table table-condensed  ">
                        <thead>
                            <tr class="primary">
                                <th>#</th><th>Name</th><th>Dated added</th><th></th>
                            </tr>
                        </thead>    
                        <tbody>
                            <c:forEach var="a" items="${authors}" varStatus="rowCount">
                                <tr>
                                    <th class="row">${a.authorId}</td>
                                    <td>${a.authorName}</td>
                                    <td>${a.dateAdded}</td>
                                    <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                        <td class="right"><a data-toggle="modal" data-target="#editModal" data-id="${a.authorId}" class="editDialog" href="#Edit">Edit</a> | <a href="author?action=delete&ID=${a.authorId}">Delete</a></td>
                                    </sec:authorize>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
            </c:if>

            <c:if test="${not empty books}">

                <div class="panel panel-success">
                    <div class="panel-heading">
                        <caption><span class="glyphicon glyphicon glyphicon-edit" aria-hidden="true"></span> Book Manager</caption>
                        <div class="btn-group right create" role="group" aria-label=".."> 
                            <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                <button data-toggle="modal" data-target="#createModal" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create</button>
                            </sec:authorize>
                            <button onclick="window.location.href = 'BookController'" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> Refresh</button>
                        </div>
                    </div>


                    <table class="table table-condensed  ">
                        <thead>
                            <tr class="primary">
                                <th>ID</th><th>Title</th><th>ISBN</th><th>Author</th><th></th>
                            </tr>
                        </thead>    
                        <tbody>
                            <c:forEach var="a" items="${books}" varStatus="rowCount">
                                <tr>
                                    <th class="row">${a.bookId}</td>
                                    <td>${a.title}</td>
                                    <td>${a.isbn}</td>
                                    <td>${a.authorId.authorName}</td>
                                    <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                        <td class="right"><a data-toggle="modal" data-target="#editModal" data-id="${a.bookId}" class="editDialog" href="#Edit">Edit</a> | <a href="BookController?action=delete&ID=${a.authorId}">Delete</a></td>
                                    </sec:authorize>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
            </c:if>



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
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <jsp:include page = "${createPage}" />

            <!-- Edit Modal -->            
            <jsp:include page = "${editPage}" />
        </sec:authorize>

    </div>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="resources/script/bootstrap.min.js" type="text/javascript"></script>
    <script src="resources/script/main.js" type="text/javascript"></script>
</body>
</html>

