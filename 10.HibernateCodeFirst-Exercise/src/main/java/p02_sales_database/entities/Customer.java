package p02_sales_database.entities;

import javax.persistence.*;
import java.util.Set;

//@Entity
//@Table(name = "customers") //default would be "Customer".toLowercase() + "s"
public class Customer {
    private static final int CREDIT_CARD_NUMBER_LENGTH = 10;
    //would be better if I made an exception directory with custom exceptions and exception constants, but it would take too much time
    private static final String INVALID_INFO = "Invalid %s!";

    private long id;
    private String name;
    private String email;
    private String creditCardNumber;
    private Set<Sale> sales;

    public Customer() {
    }

    public Customer(String name, String creditCardNumber) {
        this.setName(name);
        this.setCreditCardNumber(creditCardNumber);
    }

    public Customer(String name, String email, String creditCardNumber) {
        this(name, creditCardNumber);
        this.setEmail(email);
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

    @Column(nullable = false, length = 75) //not sure if the task wants first name + middle name + last name
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email; //validation about email (should contain "@", ".domain" and other stuff)
    }

    @Column(name = "credit_card_number", nullable = false) //columnDefinition = "CHAR[10]" breaks the code for some reason
    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        if (creditCardNumber.length() != CREDIT_CARD_NUMBER_LENGTH)
            throw new IllegalArgumentException(String.format(INVALID_INFO, "credit card number"));


        this.creditCardNumber = creditCardNumber;
    }

    @OneToMany(mappedBy = "customer")
    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
