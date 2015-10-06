package edu.wctc.bmh.bookapp2.controller;

import edu.wctc.bmh.bookapp2.model.Author;
import edu.wctc.bmh.bookapp2.model.AuthorDAO;
import edu.wctc.bmh.bookapp2.model.AuthorService;
import edu.wctc.bmh.bookapp2.model.DataAccessStrategy;
import edu.wctc.bmh.bookapp2.model.AuthorDAOStrategy;
import edu.wctc.bmh.bookapp2.model.DataAccessException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The main controller for author-related activities
 *
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/author"})
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!
    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String CREATE_MSG = "Well done! You've successfully created a new author. ";
    private static final String DELETE_MSG = "Just so you know, you've just deleted a author!";
    private static final String READ_PAGE = "/index.jsp";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private String dbClassName;
    private String destination;
    private String action;
    private AuthorService authService;
    private AuthorDAOStrategy authDao;

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

        try {

            Class dbClass = Class.forName(dbClassName);
            DataAccessStrategy db = (DataAccessStrategy) dbClass.newInstance();

            authDao = new AuthorDAO(db, driverClass, url, userName, password);

            authService = new AuthorService(authDao);

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

        } catch (DataAccessException e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);

    }

    private void read(final HttpServletRequest request) throws DataAccessException {
        List<Author> authors = null;
        authors = authService.getAllAuthors();
        request.setAttribute("authors", authors);
    }

    private void create(final HttpServletRequest request) throws DataAccessException {

        Object fullname = request.getParameter("fullname");
        Author author = new Author();
        author.setAuthorName((String) fullname);
        author.setDateAdded(new Date());
        authService.addAuthor(author);
        destination = READ_PAGE;
        request.setAttribute("succMsg", CREATE_MSG + fullname);

    }

    private void update(final HttpServletRequest request) throws DataAccessException {

        String id = request.getParameter("ID");
        Object editfullname = request.getParameter("editname");
        Date date = new Date();
        Author updateAuthor = new Author();
        updateAuthor.setAuthorId(Integer.valueOf(id));
        updateAuthor.setAuthorName((String) editfullname);
        updateAuthor.setDateAdded(date);
        authService.saveAuthor(Integer.valueOf(id), updateAuthor);
    }

    private void delete(final HttpServletRequest request) throws DataAccessException {

        String deleteId = request.getParameter("ID");
        authService.deleteAuthor(Integer.valueOf(deleteId));
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

    /**
     *  Initialize the servlet
     * 
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        driverClass = this.getServletContext().getInitParameter("driverClass");
        url = getServletConfig().getInitParameter("url");
        userName = getServletConfig().getInitParameter("userName");
        password = getServletConfig().getInitParameter("password");
        dbClassName = this.getServletContext().getInitParameter("dbStrategy");
        daoClassName = this.getServletConfig().getInitParameter("authorDao");
    }

}
