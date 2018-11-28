package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Software-University-Databases-Frameworks-Hibernate-and-Spring-Data\\14.SpringDataAdvancedQuerying-Exercise\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(b -> b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName())).collect(Collectors.toSet());
    }

    @Override
    public List<String> getBooksByAgeRestriction(String ageRestriction) {
        AgeRestriction ageRestrictionEnum = AgeRestriction.valueOf(ageRestriction.toUpperCase());
        return this.bookRepository.findBooksByAgeRestriction(ageRestrictionEnum)
                .stream()
                .map(b -> b.getTitle())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getGoldenEditionBooks(Integer maxNumberOfCopies) {
        return this.bookRepository.findBooksByEditionType_GoldAndCopiesLessThan(maxNumberOfCopies)
                .stream()
                .map(b -> b.getTitle())
                .collect(Collectors.toList());
    }

    @Override
    public String getBooksWithPricesBeforeOrAfter(String price1, String price2) {
        List<Book> books = this.bookRepository.findBooksByPriceBeforeOrPriceAfter(new BigDecimal(price1), new BigDecimal(price2));

        StringBuilder result = new StringBuilder();
        books.forEach(b -> result.append(b.getTitle()).append(" - $").append(b.getPrice()).append(System.lineSeparator()));

        return result.toString().trim();
    }

    @Override
    public String getBooksWhichAreNotReleasedIn(String year) {
        Integer yearValue = Integer.parseInt(year);
        StringBuilder result = new StringBuilder();

        this.bookRepository.findBooksByReleaseDateNot(yearValue)
                .forEach(b -> result.append(b.getTitle()).append(System.lineSeparator()));

        return result.toString().trim();
    }

    @Override
    public String getBooksReleasedBefore(String releaseDate) {
        String format = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate releaseDateValue = LocalDate.parse(releaseDate, formatter);

        StringBuilder result = new StringBuilder();

        this.bookRepository.findAllByReleaseDateBefore(releaseDateValue)
                .forEach(b -> result.append(
                        String.format("%s %s %s %s%n",
                                b.getTitle(), b.getEditionType().name(), b.getPrice(), b.getReleaseDate())));

        return result.toString().trim();
    }

    @Override
    public String getBooksWhichTitlesContain(String partOfTitle) {
        StringBuilder result = new StringBuilder();

        this.bookRepository.findBooksByTitleContains(partOfTitle)
                .forEach(b -> result.append(b.getTitle()).append(System.lineSeparator()));

        return result.toString().trim();
    }

    @Override
    public String getBookTitlesByAuthorLastNameStartingWith(String authorLastNameStarting) {
        StringBuilder result = new StringBuilder();

        this.bookRepository.findBookTitlesByAuthorLastNameStartingWith(authorLastNameStarting)
                .forEach(b -> result.append(String.format("%s (by %s %s)%n", b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName())));

        return result.toString().trim();
    }

    @Override
    public String getCountOfBooksByTitleLengthMoreThan(String titleLength) {
        Long titleLengthValue = Long.parseLong(titleLength);

        return "" + this.bookRepository.getCountOfBooksByTitleLengthMoreThan(titleLengthValue);
    }

    @Override
    public String getCertainBookInfoByBookTitle(String bookTitle) {
        StringBuilder result = new StringBuilder();

        for (Object object : this.bookRepository.getCertainBookInfoByBookTitle(bookTitle).get(0)) {
            result.append("" + object).append(" ");
        }

        return result.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}
