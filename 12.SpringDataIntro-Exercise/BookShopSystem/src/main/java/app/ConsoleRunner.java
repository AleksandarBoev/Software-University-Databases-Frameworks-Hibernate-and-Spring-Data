package app;

import app.enums.AgeRestriction;
import app.enums.EditionType;
import app.models.Author;
import app.models.Book;
import app.models.Category;
import app.repositories.AuthorRepository;
import app.services.interfaces.AuthorService;
import app.services.interfaces.BookService;
import app.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@SpringBootApplication
public class ConsoleRunner implements CommandLineRunner {
    private AuthorService authorService;
    private CategoryService categoryService;
    private BookService bookService;
    private AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(AuthorService authorService, CategoryService categoryService, BookService bookService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        printAuthorBooksSorted();
    }

    private void printAuthorBooksSorted() {
        StringBuilder sb = new StringBuilder();
        for (Book book : this.bookService.getBooksFromAuthorOrderedByReleaseDateDescTitleAsc("George", "Powell")) {
            sb.append(book.toString()).append(System.lineSeparator());
        }

        System.out.println(sb.toString().trim());
    }

    private void printAuthorsSortedByBooksCountDescending() {
        for (Author author : this.authorService.getAuthorsSortedDescendingByBookCount()) {
            System.out.printf("Author name: %s %s | Book count: %d%n",
                    author.getFirstName(), author.getLastName(), author.getBooks().size());
        }
    }

    private void printBooksAfterYear2000() {
        for (Book book: this.bookService.getBooksAfterDate(Date.valueOf("2001-01-01")))
            System.out.println(book.getTitle());
    }

    private void printAuthorsWithAtLeastOneBookAfter1990() {
        for (Author author : this.authorService.getAuthorsWithBooksReleaseDateAfter(Date.valueOf("1990-01-01"))) {
            System.out.println(author);
        }
    }

    private void seedData() throws IOException, ParseException {
        String pathToFiles = "src\\main\\resources\\files\\";

        List<String> authorsInformation = this.getInfoFromTextFile(pathToFiles + "authors.txt");

        for (String currentAuthorInfo : authorsInformation) {
            String[] authorTokens = currentAuthorInfo.split(" ");
            Author author = new Author(authorTokens[0], authorTokens[1]);
            this.authorService.register(author);
        }

        List<String> categories = this.getInfoFromTextFile(pathToFiles + "categories.txt");

        for (String currentCategory : categories) {
            Category category = new Category(currentCategory);
            this.categoryService.register(category);
        }

        List<String> booksInformation = this.getInfoFromTextFile(pathToFiles + "books.txt");

        for (String currentBookInfo : booksInformation) {
            String[] bookTokens = currentBookInfo.split(" ");
        }

        Random random = new Random();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Author> allAuthors = this.authorService.getAll(); //getting them for the database
        Author author1 = allAuthors.get(0);
        List<Category> allCategories = this.categoryService.getAll(); //with generated ids

        for (String bookInfo : booksInformation) {
            String[] bookTokens = bookInfo.split("\\s+", 6);

            int authorIndex = random.nextInt(allAuthors.size());
            Author author = allAuthors.get(authorIndex);

            EditionType editionType = EditionType.values()[Integer.parseInt(bookTokens[0])];

            LocalDate date = LocalDate.parse(bookTokens[1], dateFormat);
            Date releaseDate = Date.valueOf(date);

            int copies = Integer.parseInt(bookTokens[2]);

            BigDecimal price = new BigDecimal(bookTokens[3]);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookTokens[4])];

            String title = bookTokens[5];

            Book book = new Book(title, null, editionType, price, copies, releaseDate, ageRestriction, author);
            this.bookService.register(book);

            Set<Category> bookCategories = new HashSet<>();
            while (bookCategories.size() != 2) {
                int categoryIndex = random.nextInt(allCategories.size());
                bookCategories.add(allCategories.get(categoryIndex)); //if a category is added twice, then its no problem because of the behaviour of sets
            }

            book.setCategories(bookCategories);
            this.bookService.update(book);
            int a = 0;
        }
    }

    /**
     * Returns the contents of a text file in the from of an ArrayList.
     * Each new line is a separate element in the result.
     * @throws IOException
     */
    private List<String> getInfoFromTextFile(String filePath) throws IOException {
        File file = new File(filePath);

        BufferedReader reader = new BufferedReader(new FileReader(file));

        List<String> contents = new ArrayList<>();
        String currentLine = "";
        while ((currentLine = reader.readLine()) != null) {
            contents.add(currentLine);
        }
        reader.close();

        return contents;
    }

    /**
     * Reforms a date per line in a given text file. Example:
     * "An event has happened on 5/9/1995 and it was amazing"
     * Transformed, the text will look like:
     * "An event has happened on 05/09/1995 and it was amazing"
     * @throws IOException
     */
    private void reformDates(String filePath) throws IOException {
        List<String> contents = this.getInfoFromTextFile(filePath);

        String regex = "\\d+/\\d+/\\d+";
        Pattern pattern = Pattern.compile(regex);

        for (int i = 0; i < contents.size(); i++) {
            Matcher matcher = pattern.matcher(contents.get(i));
            if (matcher.find()) {
                String match = matcher.group(0);
                String[] dateInfo = match.split("/");
                for (int j = 0; j < dateInfo.length; j++)
                    dateInfo[j] = this.addStartingZero(dateInfo[j]);

                String answer = String.join("/", Arrays.stream(dateInfo).collect(Collectors.toList()));

                contents.set(i, contents.get(i).replace(match, answer));
            }
        }

        FileWriter fileWriter = new FileWriter(filePath);

        for (String content : contents)
            fileWriter.write(content + "\n");

        fileWriter.close();
    }

    private String addStartingZero(String partOfDate) {
        if (partOfDate.length() < 2)
            return "0" + partOfDate;

        return partOfDate;
    }
}
