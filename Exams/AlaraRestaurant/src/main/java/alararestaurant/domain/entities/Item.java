package alararestaurant.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {
    //•	name – text with min length 3 and max length 30 (required, unique)
    //•	category – the item’s category (required)
    //•	price – decimal (non-negative, minimum value: 0.01, required)
    //•	orderItems – collection of type OrderItem

    private String name;
    private Category category;
    private BigDecimal price;
    private List<OrderItem> orderItems; //how many times this class has been used in OrderItem objects(excluding quantity)

    @Column(nullable = false, unique = true)//, length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(nullable = false)//, columnDefinition = "DECIMAL(19,2) UNSIGNED")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToMany(mappedBy = "item")
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
