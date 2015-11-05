

package edu.wctc.bmh.bookapp2.model.service;

import edu.wctc.bmh.bookapp2.entity.Book;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class BookFacade extends AbstractFacade<Book> {
    @PersistenceContext(unitName = "edu.wctc.bmh_bookApp2_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

}
