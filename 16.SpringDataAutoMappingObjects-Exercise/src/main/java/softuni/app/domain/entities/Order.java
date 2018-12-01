package softuni.app.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private User customer;
    private LocalDate orderDate;
    private List<Game> products;

    public Order() {
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public User getCustomer() {
        return this.customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Column(name = "order_date")
    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders_games",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id")
    )
    public List<Game> getProducts() {
        return this.products;
    }

    public void setProducts(List<Game> products) {
        this.products = products;
    }
}
