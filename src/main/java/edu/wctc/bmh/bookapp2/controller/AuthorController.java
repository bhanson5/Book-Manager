package edu.wctc.bmh.bookapp2.controller;

import edu.wctc.bmh.bookapp2.model.Author;
import edu.wctc.bmh.bookapp2.model.AuthorDAO;
import edu.wctc.bmh.bookapp2.model.AuthorService;
import edu.wctc.bmh.bookapp2.model.DataAccessStrategy;
import edu.wctc.bmh.bookapp2.model.AuthorDAOStrategy;
import edu.wctc.bmh.bookapp2.model.MySQLDatabase;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The main controller for author-related activities
 *
 * @author jlombardo
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/author"})
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!
    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String CREATE_MSG = "Well done! You've successfully created a new author. ";
    private static final String DELETE_MSG = "Just so you know, you've just deleted a author!";
    private static final String READ_PAGE = "/index.jsp";
    private static final String UPDATE_PAGE = "/Edit.jsp";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = READ_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        

        /*
         For now we are hard-coding the strategy objects into this
         controller. In the future we'll auto inject them from a config
         file. Also, the DAO opens/closes a connection on each method call,
         which is not very efficient. In the future we'll learn how to use
         a connection pool to improve this.
         */
        DataAccessStrategy db = new MySQLDatabase();
        AuthorDAOStrategy authDao = new AuthorDAO(db, "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        AuthorService authService = new AuthorService(authDao);

        try {
            /*
             Here's what the connection pool version looks like.
             */
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource)ctx.lookup("jdbc/book");
//            AuthorDaoStrategy authDao = new ConnPoolAuthorDao(ds, new MySqlDbStrategy());
//            AuthorService authService = new AuthorService(authDao);
            /*
             Determine what action to take based on a passed in QueryString
             Parameter
             */
        if (action != null) {            
            switch (action) {

                case UPDATE_ACTION:
                    
                    break;
                case DELETE_ACTION:
                    String id = request.getParameter("ID");
                    authService.deleteAuthor(Integer.valueOf(id));
                    destination = READ_PAGE;
                    request.setAttribute("delMsg", DELETE_MSG);
                    
                    break;
                case CREATE_ACTION:
                    Object fullname = request.getParameter("fullname");

                    Author author = new Author();
                    author.setAuthorName((String) fullname);
                    author.setDateAdded(new Date());
                    authService.addAuthor(author);
                    destination = READ_PAGE;
                    request.setAttribute("succMsg", CREATE_MSG + fullname);

                    break;
                default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = READ_PAGE;
            }
        }

            List<Author> authors = null;
            authors = authService.getAllAuthors();
            request.setAttribute("authors", authors);

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
