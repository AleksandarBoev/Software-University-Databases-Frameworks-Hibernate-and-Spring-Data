package app.models;

import app.enums.AgeRestriction;
import app.enums.EditionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    private Long id;
    private String title;
    private String description;
    private EditionType editionType;
    private BigDecimal price;
    private Integer copies;
    private Date releaseDate;
    private AgeRestriction ageRestriction;
    private Author author;
    private Set<Category> categories;

    public Book() {
        this.categories = new HashSet<>();
    }

    //Is there any point in using setters in constructor since the validation is done by the service layer?
    public Book(String title, String description, EditionType editionType, BigDecimal price, Integer copies, Date releaseDate, AgeRestriction ageRestriction, Author author) {
        this();
        this.title = title;
        this.description = description;
        this.editionType = editionType;
        this.price = price;
        this.copies = copies;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
        this.author = author;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 50, nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "edition_type", nullable = false)
    public EditionType getEditionType() {
        return this.editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public Integer getCopies() {
        return this.copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    @Column(name = "release_date")
    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "age_restriction", nullable = false)
    public AgeRestriction getAgeRestriction() {
        return this.ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        String categoriesString = String.join(", ", this.categories.stream().map(c -> c.getName()).collect(Collectors.toList()));

        return "Book{" +
                "id = " + this.id +
                ", title = '" + this.title + '\'' +
                ", description = '" + this.description + '\'' +
                ", editionType = " + this.editionType.toString() +
                ", price = " + this.price +
                ", copies = " + this.copies +
                ", releaseDate = " + this.releaseDate.toString() +
                ", ageRestriction = " + this.ageRestriction.toString() +
                ", author = " + this.author.getFirstName() + " " + this.author.getLastName() +
                ", categories = " + categoriesString +
                '}';
    }
}
