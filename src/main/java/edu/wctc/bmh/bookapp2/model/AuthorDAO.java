

package edu.wctc.bmh.bookapp2.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AuthorDAO implements IAuthorDAO {

   private DataAccessStrategy db;
   private static final String TABLE_NAME = "author";
   

    public final DataAccessStrategy getDb() {
        return db;
    }

    public final void setDb(DataAccessStrategy db) {
        this.db = db;
    }

    public AuthorDAO(DataAccessStrategy db) {
        setDb(db);
    }

    @Override
    public void save(Author author) throws DataAccessException {
       try {
           db.updateRecord(TABLE_NAME , 2, null);
       } catch (IllegalArgumentException ex) {
           throw new DataAccessException(ex);
       } catch (SQLException ex) {
           throw new DataAccessException(ex);
       }
        
    }

    @Override
    public void deleteAuthor(Author author) throws DataAccessException {
       try {
           db.deleteRecord(TABLE_NAME, null);
       } catch (IllegalArgumentException ex) {
           throw new DataAccessException(ex);
       } catch (SQLException ex) {
           throw new DataAccessException(ex);
       }
    }

    @Override
    public Author findAuthorById(String id) throws DataAccessException {
        Author author = new Author();
        
       try {
           db.getRecordsByCriteria(TABLE_NAME, "author_id", id);
       } catch (IllegalArgumentException ex) {
           throw new DataAccessException(ex);
       } catch (SQLException ex) {
           throw new DataAccessException(ex);
       }
            
       return author;
    }

    @Override
    public List<Author> getAllAuthors() throws DataAccessException {
        List<Author> authors = new ArrayList<>();
        
       try {
           for (Object author : db.getAllRecords(TABLE_NAME)) {
               authors.add((Author) author);
           }
       } catch (IllegalArgumentException ex) {
           throw new DataAccessException(ex);
       } catch (SQLException ex) {
           throw new DataAccessException(ex);
       }
       
       return authors;
    }
   
   
}
