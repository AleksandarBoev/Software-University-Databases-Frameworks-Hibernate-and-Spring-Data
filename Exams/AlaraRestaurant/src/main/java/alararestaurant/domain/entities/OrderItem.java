package alararestaurant.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    //•	order – the item’s order (required)
    //•	item – the order’s item (required)
    //•	quantity – the quantity of the item in the order (required, non-negative and non-zero)
    private Order order;
    private Item item;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(nullable = false, name = "order_id", referencedColumnName = "id")
    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "item_id", referencedColumnName = "id")
    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(nullable = false)//, columnDefinition = "INT(11) UNSIGNED")
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
