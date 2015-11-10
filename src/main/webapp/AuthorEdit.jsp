<%-- 
    Document   : Edit
    Created on : Sep 16, 2015, 4:48:19 PM
    Author     : bhans_000
--%>
<sec:authorize access="hasAnyRole('ROLE_MGR')">
<div class="modal fade" id="editModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header ">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Edit record</h4>
            </div>

            <form method="GET" name="edit" class="form form-horizontal" action="author">
                <sec:csrfInput />
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
</sec:authorize>
    
