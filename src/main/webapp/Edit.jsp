<%-- 
    Document   : Edit
    Created on : Sep 16, 2015, 4:48:19 PM
    Author     : bhans_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Page</title>
    </head>
    <body>
        <h1>Edit Page</h1>
    <caption>Edit Page | not as pretty but works</caption>
    <form method="GET" name="create" class="form form-horizontal" action="author?action=edit">
                            
                                <div class="form-group">
                                    <input type="text" class="form-control" id="name" placeholder="name" name="editname">
                                </div>
                                  
                                <button type="submit" class="btn btn-primary">Save changes</button>
                        </form>
    </body>
</html>
