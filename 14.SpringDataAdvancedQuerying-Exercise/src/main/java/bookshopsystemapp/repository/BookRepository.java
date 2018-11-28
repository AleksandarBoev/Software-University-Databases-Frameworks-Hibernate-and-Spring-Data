package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findBooksByAgeRestriction(AgeRestriction ageRestriction);

    @Query(value = "" +
            "SELECT b FROM  bookshopsystemapp.domain.entities.Book b " +
            "WHERE b.editionType = bookshopsystemapp.domain.entities.EditionType.GOLD AND b.copies < :numberOfCopies"
    )
    List<Book> findBooksByEditionType_GoldAndCopiesLessThan(@Param(value = "numberOfCopies") Integer copies);

    List<Book> findBooksByPriceBeforeOrPriceAfter(BigDecimal minimumPrice, BigDecimal maximumPrice);

    @Query(value = "" +
            "SELECT b FROM bookshopsystemapp.domain.entities.Book b " +
            "WHERE FUNCTION('YEAR', b.releaseDate) <> :year"
    )
    List<Book> findBooksByReleaseDateNot(@Param(value = "year") Integer year);

    @Query(value = "" +
            "SELECT b FROM bookshopsystemapp.domain.entities.Book b " +
            "WHERE b.title LIKE CONCAT('%', :partOfTitle, '%') "
    )
    List<Book> findBooksByTitleContains(@Param(value = "partOfTitle") String partOfTitle);

    @Query(value = "" +
            "SELECT b FROM bookshopsystemapp.domain.entities.Book b " +
            "INNER JOIN b.author a " +
            "WHERE a.lastName LIKE CONCAT(:authorFirstNameStarting, '%') "
    )
    List<Book> findBookTitlesByAuthorLastNameStartingWith(@Param(value = "authorFirstNameStarting") String authorNameStarting);

    @Query(value = "" +
            "SELECT COUNT(b) FROM bookshopsystemapp.domain.entities.Book b " +
            "WHERE FUNCTION('CHAR_LENGTH', b.title) > :titleLength"
    )
    Integer getCountOfBooksByTitleLengthMoreThan(@Param(value = "titleLength") Long titleLength);

    //TODO jpql does not support 'limit'. Not sure how to take just one result from a query
    @Query(value = "" +
            "SELECT " +
            "   b.title, " +
            "   b.editionType, " +
            "   b.ageRestriction, " +
            "   b.price " +
            "FROM bookshopsystemapp.domain.entities.Book b " +
            "WHERE b.title = :title "
    )
    List<Object[]> getCertainBookInfoByBookTitle(@Param(value = "title") String bookTitle);
}
