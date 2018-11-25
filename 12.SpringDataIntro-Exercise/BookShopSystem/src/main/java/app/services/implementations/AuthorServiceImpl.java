package app.services.implementations;

import app.models.Author;
import app.models.Book;
import app.repositories.AuthorRepository;
import app.services.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(Long id) {
        return this.authorRepository.findById(id).orElse(null);
    }

    /**
     * Searches database for an author with this last name. If there is no such author, returns null.
     * @return null, Author
     */
    @Override
    public Author getByName(String authorLastName) {
        return this.authorRepository.getAuthorByLastName(authorLastName);
    }

    /**
     * Validates and registers author in database. Validation includes:
     * <ul>
     *     <li> Given author perimeter is not null.
     *     <li> Author has an id == null. An id can be generated only via database, meaning the
     *     author is already in the database.
     * </ul>
     * @return true if registration is successful and false if not.
     */
    @Override
    public boolean register(Author author) {
        if (author == null || author.getId() != null) //only already registered authors should have id values!
            return false;

        this.authorRepository.save(author);
        return true;
    }

    @Override
    public List<Author> getAll() {
        List<Author> allAuthors = new ArrayList<>();

        for (Author author : this.authorRepository.findAll())
            allAuthors.add(author);

        return allAuthors;
    }

    @Override
    public boolean update(Author author) {
        if (author == null || !this.authorRepository.existsById(author.getId()))
            return false;

        this.authorRepository.save(author);
        return true;
    }

    @Override
    public Iterable<Author> getAuthorsWithBooksReleaseDateAfter(Date date) {
        List<Author> allAuthors = this.getAll();
        List<Author> result = new ArrayList<>();

        for (Author author : allAuthors) {
            for (Book book : author.getBooks()) {
                if (book.getReleaseDate().compareTo(date) > 0) {
                    result.add(author);
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public Iterable<Author> getAuthorsSortedDescendingByBookCount() {
        return this.getAll()
                .stream()
                .sorted((a1, a2) -> {
                    return Integer.compare(a2.getBooks().size(), a1.getBooks().size());
                }).collect(Collectors.toList());
    }
}
