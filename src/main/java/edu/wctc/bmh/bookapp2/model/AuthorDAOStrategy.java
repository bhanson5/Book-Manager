

package edu.wctc.bmh.bookapp2.model;

import java.util.List;

/**
 *
 * @author bhans_000
 */
public interface AuthorDAOStrategy {
    
    public abstract void addAuthor(Author author) throws DataAccessException;

    /**
     * Saves an Author as a updated record.
     * @param emp - the entity to be saved or updated
     * @throws DataAccessException - if sql or I/O errors
     */
    public abstract void saveAuthor(Author author) throws DataAccessException;
    
    /**
     * Delete an Author by entity.
     * @param employee - the entity to be deleted.
     * @throws DataAccessException  - if sql or I/O errors
     */
    public abstract void deleteAuthor (int author_id) throws DataAccessException;
    
    /**
     * Finds an Author entity by its id.
     * @param id - the primary key
     * @return the entity object
     * @throws DataAccessException - if sql or I/O errors
     */
    public Author findAuthorById(String id) throws DataAccessException;
    
    /**
     * Retrieve all Author records and package the data up as a
     * collection of entity objects.
     * 
     * @return - collection of entity objects
     * @throws DataAccessException - if sql or I/O errors
     */
    public abstract List<Author> getAllAuthors() throws DataAccessException;
}
