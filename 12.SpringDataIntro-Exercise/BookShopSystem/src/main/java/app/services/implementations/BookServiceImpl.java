package app.services.implementations;

import app.models.Book;
import app.repositories.BookRepository;
import app.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getById(Long id) {
        return this.bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book getByName(String title) {
        return this.bookRepository.getByTitle(title);
    }

    /**
     * Validates and registers book in database. Validation includes:
     * <ul>
     *     <li> Given book perimeter is not null.
     *     <li> Book has an id == null. An id can be generated only via database, meaning the
     *     book is already in the database.
     * </ul>
     * @return true if registration is successful and false if not.
     */
    @Override
    public boolean register(Book book) {
        if (book == null || book.getId() != null)
            return false;

        this.bookRepository.save(book);
        return true;
    }

    @Override
    public List<Book> getAll() {
        List<Book> allBooks = new ArrayList<>();

        for (Book book : this.bookRepository.findAll())
            allBooks.add(book);

        return allBooks;
    }

    @Override
    public boolean update(Book book) {
        if (book == null || !this.bookRepository.existsById(book.getId()))
            return false;

        this.bookRepository.save(book);
        return true;
    }

    @Override
    public Iterable<Book> getBooksAfterDate(Date date) {
        return this.bookRepository.getAllByReleaseDateAfter(date);
    }

    @Override
    public Iterable<Book> getBooksFromAuthorOrderedByReleaseDateDescTitleAsc(String authorFirstName, String authorLastName) {
        //No need to check if there is such an author. Method will not break the program.
        if (authorFirstName == null || "".equals(authorFirstName) ||
                authorLastName == null || "".equals(authorLastName))
            return null;

        return this.bookRepository.
                getBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(authorFirstName, authorLastName);
    }
}
