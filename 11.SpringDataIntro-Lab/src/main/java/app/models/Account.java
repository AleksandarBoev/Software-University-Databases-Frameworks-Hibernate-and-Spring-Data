package app.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
    private static final String NEGATIVE_BALANCE_MESSAGE = "Balance can't be a negative!";

    private long id;
    private BigDecimal balance;
    private User owner;

    public Account() {
    }

    public Account(BigDecimal balance, User owner) {
        this.balance = balance;
        this.owner = owner;
//        this.setBalance(balance);
//        this.setOwner(owner);
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

    @Column
    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        if (balance.abs().compareTo(balance) != 0)
            throw new IllegalArgumentException(NEGATIVE_BALANCE_MESSAGE);

        this.balance = balance;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("Account id: %d | Balance: %.2f | Owner username: %s",
                this.id, this.balance, this.owner.getUserName());
    }
}
