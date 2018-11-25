package app.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    private static final int MAX_ALLOWED_USER_NAME_LENGTH = 40;
    private static final int ZERO = 0;
    private static final int EIGHTEEN = 18;

    private static final String MAX_ALLOWED_USER_NAME_LENGTH_OVERLAPPED_MESSAGE = "User name can't be over 40 characters long!";
    private static final String USER_NAME_NULL_OR_EMPTY = "User name is null or there are no characters!";
    private static final String USER_AGE_MUST_BE_ATLEAST_18_MESSAGE = "User must be 18 or older!";

    private long id;
    private String userName;
    private int age;
    private List<Account> accounts;

    public User() {
    }

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
//        this.setUserName(userName); //validations in this app will be made in the service layer
//        this.setAge(age);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "user_name", unique = true, length = MAX_ALLOWED_USER_NAME_LENGTH)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        if (userName == null || userName.trim().length() == ZERO)
            throw new IllegalArgumentException(USER_NAME_NULL_OR_EMPTY);

        if (userName.trim().length() > MAX_ALLOWED_USER_NAME_LENGTH)
            throw new IllegalArgumentException(MAX_ALLOWED_USER_NAME_LENGTH_OVERLAPPED_MESSAGE);

        this.userName = userName;
    }

    @Column
    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        if (age < EIGHTEEN)
            throw new IllegalArgumentException(USER_AGE_MUST_BE_ATLEAST_18_MESSAGE);

        this.age = age;
    }

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //TODO if the fetch type is lazy, some problems occur. Find a better fix
    public List<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        String accountsString = String.join("\n", this.accounts.stream()
                        .map(a -> String.format("\tId: %d | Balance: %.2f", a.getId(), a.getBalance()))
                        .collect(Collectors.toList()));

        return "User{" +
                "id = " + this.id +
                ", userName = '" + this.userName + '\'' +
                ", age = " + this.age +
                ", accounts: \n" + accountsString +
                '}';
//        return this.id + " | " + this.userName;
    }
}
