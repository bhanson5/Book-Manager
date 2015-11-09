package edu.wctc.bmh.bookapp2.controller;

import edu.wctc.bmh.bookapp2.entity.Author;
import edu.wctc.bmh.bookapp2.service.AuthorService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for author-related activities
 *
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/author"})
public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    /** Message for successfully creating a new author. */
    private static final String CREATE_MSG = "Well done! You've successfully created a new author. ";
    private static final String DELETE_MSG = "Just so you know, you've just deleted a author!";
    private static final String READ_PAGE = "/index.jsp";
    private static final String LIST_PAGE = "/Author/List.jsp";
    private static final String EDIT_PAGE = "/Author/Edit.jsp";
    private static final String CREATE_PAGE = "/Author/Create.jsp";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";

    private String destination;
    private String action;
    @Autowired
    private AuthorService authService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        destination = READ_PAGE;
        action = request.getParameter(ACTION_PARAM);

        if (authService == null) {
            ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        authService = (AuthorService) ctx.getBean("authorService");
        }
        
        request.setAttribute("listPage", LIST_PAGE);
        request.setAttribute("editPage", EDIT_PAGE);
        request.setAttribute("createPage", CREATE_PAGE);
        
        try {


            if (action != null) {
                switch (action) {
                    case CREATE_ACTION:
                        create(request);
                        break;
                    case UPDATE_ACTION:
                        update(request);
                        break;
                    case DELETE_ACTION:
                        delete(request);
                        break;

                    default:
                        request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                        destination = READ_PAGE;
                }
            }
            
            read(request);

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);

    }

    private void read(final HttpServletRequest request) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }

    private void create(final HttpServletRequest request) throws Exception {

        Object fullname = request.getParameter("fullname");
        Author author = new Author();
        author.setAuthorName((String) fullname);
        author.setDateAdded(new Date());
        authService.create(author);
        destination = READ_PAGE;
        request.setAttribute("succMsg", CREATE_MSG + fullname);

    }

    private void update(final HttpServletRequest request) throws Exception {

        String id = request.getParameter("ID");
        Object editfullname = request.getParameter("editname");
        Date date = new Date();
        Author updateAuthor = new Author();
        updateAuthor.setAuthorId(Integer.valueOf(id));
        updateAuthor.setAuthorName((String) editfullname);
        updateAuthor.setDateAdded(date);
        authService.edit(updateAuthor);
    }

    private void delete(final HttpServletRequest request) throws Exception {

        String deleteId = request.getParameter("ID");
        authService.remove(authService.findById(deleteId));
        destination = READ_PAGE;
        request.setAttribute("delMsg", DELETE_MSG);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
