

package edu.wctc.bmh.bookapp2.model;

import java.util.List;


public class AuthorService {
    
    private AuthorDAOStrategy dao;

    public AuthorService(AuthorDAOStrategy dao) {
        this.dao = dao;
    }

    public AuthorService() {
        
    }   
    
    public List<Author> getAllAuthors() throws DataAccessException {
        return dao.getAllAuthors();
    }
    
    public void addAuthor(Author author) throws DataAccessException {
        dao.addAuthor(author);
    }
    
    public void deleteAuthor(int id) throws DataAccessException {
        dao.deleteAuthor(id);
    }
   
    public void saveAuthor(int id, Author author) throws DataAccessException {
        dao.saveAuthor(id, author);
    }

}
