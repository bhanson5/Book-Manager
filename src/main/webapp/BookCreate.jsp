<%-- 
    Document   : Create
    Created on : Nov 3, 2015, 4:48:44 PM
    Author     : bhans_000
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="createModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header ">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Create new record</h4>
            </div>

            <form method="POST" name="create" class="form form-horizontal" action="book?action=create">
                <div class="modal-body">


                    <div class="form-group">
                        <input type="text" name="title" id="title" placeholder="Enter a book title">
                    </div>
                    <div class="form-group">
                        <input type="text" name="isbn" id="isbn" placeholder="Enter a book isbn">
                    </div>
                    <div class="form-group">
                        <select id="authorDropDown" name="authorId">
                    <c:choose>
                        <c:when test="${not empty book.authorId}">
                            <option value="">None</option>
                            <c:forEach var="author" items="${authors}">                                       
                                <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="author" items="${authors}" varStatus="rowCount">                                       
                                <option value="${author.authorId}" <c:if test="${rowCount.count == 1}">selected</c:if>>${author.authorName}</option>
                            </c:forEach>
                         </c:otherwise>
                    </c:choose>
                    </select>
                    </div>


                </div>
                <div class="modal-footer">        
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form>
        </div>
    </div>
</div>
