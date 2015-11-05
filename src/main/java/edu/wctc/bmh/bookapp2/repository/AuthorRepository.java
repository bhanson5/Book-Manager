

package edu.wctc.bmh.bookapp2.repository;

import edu.wctc.bmh.bookapp2.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bhanson
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {

}
