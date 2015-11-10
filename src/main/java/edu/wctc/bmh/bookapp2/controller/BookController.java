package edu.wctc.bmh.bookapp2.controller;

import edu.wctc.bmh.bookapp2.entity.Book;
import edu.wctc.bmh.bookapp2.service.BookService;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for book-related activities
 *
 */
@WebServlet(name = "BookController", urlPatterns = {"/book"})
public class BookController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    /**
     * Message for successfully creating a new book.
     */
    private static final String CREATE_MSG = "Well done! You've successfully created a new book. ";
    private static final String DELETE_MSG = "Just so you know, you've just deleted a book!";
    private static final String READ_PAGE = "/index.jsp";
    private static final String LIST_PAGE = "/Book/List.jsp";
    private static final String EDIT_PAGE = "/Book/Edit.jsp";
    private static final String CREATE_PAGE = "/Book/Create.jsp";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";

    private String destination;
    private String action;
    private BookService bookService;

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

        if (bookService == null) {
            ServletContext sctx = getServletContext();
            WebApplicationContext ctx
                    = WebApplicationContextUtils.getWebApplicationContext(sctx);
            bookService = (BookService) ctx.getBean("bookService");
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
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
        
    }

    private void create(final HttpServletRequest request) throws Exception {

        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);

        bookService.create(book);
        destination = READ_PAGE;
        request.setAttribute("succMsg", CREATE_MSG + book.getTitle());

    }

    private void update(final HttpServletRequest request) throws Exception {

        Integer id = Integer.parseInt(request.getParameter("ID"));
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");

        Book book = new Book();
        book.setBookId(id);
        book.setIsbn(isbn);
        book.setTitle(title);

        bookService.edit(book);
    }

    private void delete(final HttpServletRequest request) throws Exception {

        String deleteId = request.getParameter("ID");
        bookService.remove(bookService.findById(deleteId));
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
}
