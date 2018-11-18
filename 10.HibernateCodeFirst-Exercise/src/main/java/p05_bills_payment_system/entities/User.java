package p05_bills_payment_system.entities;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<BillingDetail> billingDetailList;

    public User() {

    }

    public User(String firstName, String lastName, String email, String password) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.billingDetailList = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 30)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 30)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(length = 30)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email; //an email should contain '@', ".domain" and other stuff..
    }

    @Column(nullable = false)
    public String getPassword() {
        return this.password;
    }

    /*
    A password should have at least 1 upper case, 1 number and other stuff
    Also a password shouldn't be saved in a raw form onto a database.
    It should be hashed or something.
    */
    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "owner")
    public List<BillingDetail> getBillingDetailList() {
        return this.billingDetailList;
    }

    public void setBillingDetailList(List<BillingDetail> billingDetailList) {
        this.billingDetailList = billingDetailList;
    }

    @Override
    public String toString() {
        String billingDetails = String.join(", ", this.billingDetailList.stream().map(b -> b.toString()).collect(Collectors.toList()));

        return "User {" +
                "firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", email = '" + email + '\'' +
                ", password = '" + password + '\'' +
                ", billingDetailList = (" + billingDetails + ')' +
                '}';
    }
}
