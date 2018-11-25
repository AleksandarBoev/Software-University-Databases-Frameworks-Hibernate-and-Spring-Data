package app.repositories;

import app.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book getByTitle(String title);

    Book getByPriceBefore(BigDecimal maxPrice);

    Book getByPriceAfter(BigDecimal minPrice);

    Book getByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    Iterable<Book> getAllByReleaseDateAfter(Date date);

    Iterable<Book> getBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);
}
