package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final BufferedReader reader;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... strings) throws Exception {
//        this.authorService.seedAuthors();
//        this.categoryService.seedCategories();
//        this.bookService.seedBooks();

//        this.problem01BookTitlesByAgeRestriction();
//        this.problem02GoldenBooks();
//        this.problem03BooksByPrice();
//        this.problem04NotReleasedBooks();
//        this.problem05BooksReleasedBeforeDate();
//        this.problem06AuthorsSearch();
//        this.problem07BooksSearch();
//        this.problem08BookTitlesSearch();
//        this.problem09CountBooks();
//        this.problem10TotalBookCopies();
//        this.problem11ReducedBook();
    }

    /*
    Write a program that prints the titles of all books, for which the age restriction
    matches the given input (minor, teen or adult). Ignore casing of the input.
     */

    private void problem01BookTitlesByAgeRestriction() throws IOException {
        System.out.print("Enter age restriction: ");
        String ageRestriction = this.reader.readLine();

        this.bookService.getBooksByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }
    /*
    Write a program that prints the titles of the golden edition books, which have less than 5000 copies.
     */

    private void problem02GoldenBooks() throws IOException {
        System.out.print("Enter minimum number of copies: ");
        Integer copies = Integer.parseInt(this.reader.readLine());

        this.bookService.getGoldenEditionBooks(copies)
                .forEach(System.out::println);
    }
    /*
    Write a program that prints the titles and prices of books with price lower than 5 and higher than 40.
     */

    private void problem03BooksByPrice() throws IOException {
        System.out.print("Enter price1: ");
        String price1 = this.reader.readLine();

        System.out.print("Enter price2: ");
        String price2 = this.reader.readLine();

        System.out.println(this.bookService.getBooksWithPricesBeforeOrAfter(price1, price2));
    }
    /*
    Write a program that prints the titles of all books that are NOT released in a given year.
     */

    private void problem04NotReleasedBooks() throws IOException {
        System.out.print("Enter year: ");
        String year = this.reader.readLine();

        System.out.println(this.bookService.getBooksWhichAreNotReleasedIn(year));
    }
    /*
    Write a program that prints the title, the edition type and the price of books, which are released before a given date.
    The date will be in the format dd-MM-yyyy.
     */

    private void problem05BooksReleasedBeforeDate() throws IOException {
        System.out.print("Enter date in format 'dd-MM-yyyy': ");
        String releaseDate = this.reader.readLine();

        System.out.println(this.bookService.getBooksReleasedBefore(releaseDate));
    }
    /*
    Write a program that prints the names of those authors, whose first name ends with a given string.
     */

    private void problem06AuthorsSearch() throws IOException {
        System.out.print("Enter first name ending: ");
        String firstNameEnding = this.reader.readLine();

        System.out.println(this.authorService.getAuthorsWhoseFirstNameEndsWith(firstNameEnding));
    }

    /*
    Write a program that prints the titles of books, which contain a given string (regardless of the casing).
     */
    private void problem07BooksSearch() throws IOException {
        System.out.print("Enter part of title: ");
        String partOfTitle = this.reader.readLine();

        System.out.println(this.bookService.getBooksWhichTitlesContain(partOfTitle));
    }

    /*
    Write a program that prints the titles of books, which are written by authors,
    whose last name starts with a given string.
     */

    private void problem08BookTitlesSearch() throws IOException {
        System.out.print("Enter author last name starting with: ");
        String authorLastNameStartingWith = this.reader.readLine();

        System.out.println(this.bookService.getBookTitlesByAuthorLastNameStartingWith(authorLastNameStartingWith));
    }

    /*
    Write a program that prints the number of books, whose title is longer than a given number.
     */
    private void problem09CountBooks() throws IOException {
        System.out.print("Enter title length: ");
        String titleLength = this.reader.readLine();

        System.out.println(this.bookService.getCountOfBooksByTitleLengthMoreThan(titleLength));
    }

    /*
    Write a program that prints the total number of book copies by author.
    Order the results descending by total book copies.
    Note: Program works fine. Word file examples are wrong
     */

    private void problem10TotalBookCopies() {
        System.out.println(this.authorService.getAuthorNamesAndBookCountsOrderedByCountDesc());
    }

    /*
    Write a program that prints information (title, edition type, age restriction and price)
    for a book by given title. When retrieving the book information
    select only those fields and do NOT include any other information in the returned result.
     */
    private void problem11ReducedBook() throws IOException {
        System.out.print("Enter book title: ");
        String bookTitle = this.reader.readLine();

        System.out.println(this.bookService.getCertainBookInfoByBookTitle(bookTitle));
    }
}
