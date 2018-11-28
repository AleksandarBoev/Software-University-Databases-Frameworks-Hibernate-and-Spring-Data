package bookshopsystemapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<String> getBooksByAgeRestriction(String ageRestriction);

    List<String> getGoldenEditionBooks(Integer maxNumberOfCopies);

    String getBooksWithPricesBeforeOrAfter(String price1, String price2);

    String getBooksWhichAreNotReleasedIn(String year);

    String getBooksReleasedBefore(String releaseDate);

    String getBooksWhichTitlesContain(String partOfTitle);

    String getBookTitlesByAuthorLastNameStartingWith(String authorLastNameStarting);

    String getCountOfBooksByTitleLengthMoreThan(String titleLength);

    String getCertainBookInfoByBookTitle(String bookTitle);
}
