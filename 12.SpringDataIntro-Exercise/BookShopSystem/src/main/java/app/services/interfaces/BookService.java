package app.services.interfaces;

import app.models.Book;

import java.sql.Date;

public interface BookService extends BaseService<Book> {
    Iterable<Book> getBooksAfterDate(Date date);

    Iterable<Book> getBooksFromAuthorOrderedByReleaseDateDescTitleAsc(String authorFirstName, String authorLastName);
}
