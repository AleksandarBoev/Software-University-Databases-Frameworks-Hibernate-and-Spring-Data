package app.services.interfaces;

import app.models.Author;

import java.sql.Date;

public interface AuthorService extends BaseService<Author> {
    Iterable<Author> getAuthorsWithBooksReleaseDateAfter(Date date);

    Iterable<Author> getAuthorsSortedDescendingByBookCount();
}
