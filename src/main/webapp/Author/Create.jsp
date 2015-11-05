<%-- 
    Document   : Create
    Created on : Nov 3, 2015, 4:48:44 PM
    Author     : bhans_000
--%>

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
