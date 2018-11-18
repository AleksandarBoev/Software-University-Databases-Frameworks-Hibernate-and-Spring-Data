package p02_sales_database.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

//@Entity
//@Table(name = "products")
public class Product {
    private static final String INVALID_INFO = "Invalid %s!";

    private long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Set<Sale> sales;

    public Product() {
    }

    public Product(String name, int quantity, BigDecimal price) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
    }

    @Id
    @Column //default column name will be the same as the field name
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(length = 40, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException(String.format(INVALID_INFO, "product name"));

        this.name = name;
    }

    @Column(nullable = false)
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0)
            throw new IllegalArgumentException(String.format(INVALID_INFO, "quantity"));

        this.quantity = quantity;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException(String.format(INVALID_INFO, "price"));

        this.price = price;
    }

    @OneToMany(mappedBy = "product")
    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
