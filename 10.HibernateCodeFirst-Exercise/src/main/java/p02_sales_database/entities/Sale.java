package p02_sales_database.entities;

import javax.persistence.*;
import java.sql.Date;

//@Entity
//@Table(name = "sales")
public class Sale {
    private long id;
    private Product product;
    private Customer customer;
    private StoreLocation storeLocation;
    private Date date;

    public Sale() {
    }

    public Sale(Product product, Customer customer, StoreLocation storeLocation, Date date) {
        this.setProduct(product);
        this.setCustomer(customer);
        this.setStoreLocation(storeLocation);
        this.setDate(date);
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

    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne //one type of product can be sold many times.
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne //one customer can buy stuff many times
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @ManyToOne //one store can have many sales
    public StoreLocation getStoreLocation() {
        return this.storeLocation;
    }

    public void setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
    }

    @Column()
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", product=" + product +
                ", customer=" + customer +
                ", storeLocation=" + storeLocation +
                ", date=" + date +
                '}';
    }
}
