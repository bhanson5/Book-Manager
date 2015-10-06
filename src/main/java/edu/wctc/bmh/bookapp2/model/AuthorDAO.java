package edu.wctc.bmh.bookapp2.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AuthorDAO implements AuthorDAOStrategy {

    private DataAccessStrategy db;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private static final String TABLE_NAME = "author";
    private static final String ID_COLUMN_NAME = "author_id";
    private static final String NAME_COLUMN_NAME = "author_name";
    private static final String DATE_COLUMN_NAME = "date_added";

    public AuthorDAO(DataAccessStrategy db, String driverClassName, String url, String userName, String password) {
        this.db = db;
        this.driverClassName = driverClassName;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

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
    public void saveAuthor(int author_id, Author author) throws DataAccessException {
        Map<String, Object> record = new LinkedHashMap();
        
        try {
            db.openConnection(driverClassName, url, userName, password);
            
            record.put(ID_COLUMN_NAME, author.getAuthorId());
            record.put(NAME_COLUMN_NAME, author.getAuthorName());
            record.put(DATE_COLUMN_NAME, author.getDateAdded());
            
            db.updateRecord(TABLE_NAME, ID_COLUMN_NAME, author_id, record);
            
            db.closeConnection();
        } catch (SQLException | IllegalArgumentException | ClassNotFoundException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void deleteAuthor(int author_id) throws DataAccessException {
        try {
            db.openConnection(driverClassName, url, userName, password);
            
            db.deleteRecord(TABLE_NAME, ID_COLUMN_NAME, author_id);
            
            db.closeConnection();
        } catch (IllegalArgumentException | SQLException | ClassNotFoundException ex) {
            throw new DataAccessException(ex);
        } 
    }

    @Override
    public Author findAuthorById(String id) throws DataAccessException {
        Author author = new Author();

        return author;
    }

    @Override
    public List<Author> getAllAuthors() throws DataAccessException {
        List<Author> authors = new ArrayList<>();

        try {
            db.openConnection(driverClassName, url, userName, password);            
            
            for (Map<String, Object> record : db.getAllRecords(TABLE_NAME)) {
                Author author = new Author();
                author.setAuthorId((Integer) record.get(ID_COLUMN_NAME));
                author.setAuthorName((String) record.get(NAME_COLUMN_NAME));
                author.setDateAdded((Date) record.get(DATE_COLUMN_NAME));
                authors.add(author);
            }
            
            db.closeConnection();
        } catch (IllegalArgumentException | SQLException | ClassNotFoundException ex) {
            throw new DataAccessException(ex);
        }

        return authors;
    }

    @Override
    public void addAuthor(Author author) throws DataAccessException {
        try {
            db.openConnection(driverClassName, url, userName, password);
            
            Map<String, Object> record = new LinkedHashMap();
//            record.put(ID_COLUMN_NAME, author.getAuthorId());
            record.put(NAME_COLUMN_NAME, author.getAuthorName());
            record.put(DATE_COLUMN_NAME, author.getDateAdded());
            
            db.insertRecord(TABLE_NAME, record);
            
            db.closeConnection();
        } catch (IllegalArgumentException | SQLException | ClassNotFoundException ex) {
            throw new DataAccessException(ex);
        } 
    }

}
