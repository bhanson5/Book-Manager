<%-- 
    Document   : List
    Created on : Nov 4, 2015, 7:16:32 PM
    Author     : bhans_000
--%>

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
