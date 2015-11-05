

package edu.wctc.bmh.bookapp2.repository;

import edu.wctc.bmh.bookapp2.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bhanson
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {

}
