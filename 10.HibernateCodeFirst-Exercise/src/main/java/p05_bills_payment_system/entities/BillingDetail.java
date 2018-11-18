package p05_bills_payment_system.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "billing_details")
@DiscriminatorColumn(name = "type")
public abstract class BillingDetail {
    private int id;
    private String number;
    private User owner;

    protected BillingDetail() {
    }

    protected BillingDetail(String number, User owner) {
        this.number = number;
        this.owner = owner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "id = " + this.id + ", number = '" + this.number + "', Type of billing: ";
    }
}
