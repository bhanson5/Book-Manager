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
        <title>Author Manager</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="col-sm-3 col-md-2 sidebar">
                <ul class="nav nav-sidebar">
                    <li><p class="navbar-brand">Book Manager</p></li>
                </ul>
                <ul class="nav nav-pills nav-stacked">

                    <li class="active"><a href="author">Authors</a></li>
                    <li class=""><a href="book">Books</a></li>
                </ul>
            </div>

            </div>

            <div class="col-sm-9 col-md-10  main">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <caption><span class="glyphicon glyphicon glyphicon-edit" aria-hidden="true"></span> Author Manager</caption>
                        <div class="btn-group right create" role="group" aria-label=".."> 
                            <button data-toggle="modal" data-target="#createModal" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create</button>
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
                                    <td class="right"><a data-toggle="modal" data-target="#editModal" data-id="${a.authorId}" class="editDialog" href="#Edit">Edit</a> | <a href="author?action=delete&ID=${a.authorId}">Delete</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>

                <c:if test="${errMsg != null}">
                    <div class="alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        ${errMsg}
                    </div>                
                </c:if>

                <c:if test="${succMsg != null}">
                    <div class="alert alert-success" role="alert">
                        <span class="glyphicon glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                        ${succMsg}
                    </div>                
                </c:if>

                <c:if test="${delMsg != null}">
                    <div class="alert alert-info" role="alert">
                        <span class="glyphicon glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                        ${delMsg}
                    </div>                
                </c:if>

            </div>

            <!-- Create author Modal -->
            <div class="modal fade" id="createModal" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header ">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Create new record</h4>
                        </div>

                        <form method="POST" name="create" class="form form-horizontal" action="author?action=create">
                            <div class="modal-body">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="name" placeholder="name" name="fullname">
                                </div>
                            </div>
                            <div class="modal-footer">        
                                <button type="submit" class="btn btn-primary">Create</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- edit author Modal -->
            <div class="modal fade" id="editModal" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header ">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Edit record</h4>
                        </div>

                        <form method="GET" name="edit" class="form form-horizontal" action="author">
                            <div class="modal-body">
                                <div class="form-group">
                                    <input type="hidden" name="action" value="update">
                                    <input type="text" class="form-control" id="name" placeholder="name" name="editname">
                                   
                                    <input type="hidden" id="editID" name="ID" value="">
                                </div>
                            </div>
                            <div class="modal-footer">        
                                <button type="submit" class="btn btn-primary">Save changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="script/main.js" type="text/javascript"></script>
    </body>
</html>

