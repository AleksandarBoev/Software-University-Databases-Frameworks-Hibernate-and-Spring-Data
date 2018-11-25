package app.models;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Book> books;

    public Author() {

    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(targetEntity = Book.class, mappedBy = "author", fetch = FetchType.EAGER)
    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        String booksInfo = String.join("\n", this.books.stream()
                .map(b -> "\t" + b.getTitle() + " " + b.getReleaseDate().toString())
                .collect(Collectors.toList()));

        int numberOfBooks = this.books.size();

        return "Author{" +
                "id = " + this.id +
                ", firstName = '" + this.firstName + '\'' +
                ", lastName = '" + this.lastName + '\'' +
                "\nNumber of books: " + numberOfBooks + ", book titles and release dates:\n" + booksInfo +
                '}';
    }
}
