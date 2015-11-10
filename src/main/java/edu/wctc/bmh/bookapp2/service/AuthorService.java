package edu.wctc.bmh.bookapp2.service;

import edu.wctc.bmh.bookapp2.entity.Author;
import edu.wctc.bmh.bookapp2.repository.AuthorRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 */
@Repository("authorService")
@Transactional(readOnly = true)
public class AuthorService {

    private transient final Logger LOG = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepo;

    public AuthorService() {
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    public Author findById(String id) {
        return authorRepo.findOne(Integer.valueOf(id));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Author author) {
        LOG.debug("Deleting author: " + author.getAuthorName());
        authorRepo.delete(author);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Author edit(Author author) {
        LOG.debug("Editting author: " + author.getAuthorName());
        return authorRepo.save(author);
    }
    
    public Author create(Author author) {
        LOG.debug("Creating author: " + author.getAuthorName());
        return authorRepo.saveAndFlush(author);
    }
}
