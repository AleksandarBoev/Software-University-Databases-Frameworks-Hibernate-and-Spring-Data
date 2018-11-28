package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findAuthorByFirstNameEndsWith(String firstNameEnding);

    @Query(value = "" +
            "SELECT " +
            "   CONCAT(a.firstName, ' ', a.lastName) AS full_name," +
            "   SUM(b.copies) AS book_copies " +
            "FROM bookshopsystemapp.domain.entities.Book b " +
            "INNER JOIN b.author a " +
            "GROUP BY a " +
            "ORDER BY book_copies DESC"
    )
    List<Object[]> getAuthorNamesAndBooksCountOrderedByCountDesc();
}
