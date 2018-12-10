package alararestaurant.domain.entities;

import alararestaurant.domain.enums.OrderType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    //•	customer – text (required)
    //•	dateTime – date and time of the order (required)
    //•	type – OrderType enumeration with possible values: “ForHere, ToGo (default: ForHere)” (required)
    //•	employee – The employee who will process the order (required)
    //•	orderItems – collection of type OrderItem
    private String customer;
    private LocalDateTime dateTime;
    private OrderType type;
    private Employee employee;
    private List<OrderItem> orderItems;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(nullable = false)
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    public OrderType getType() {
        return this.type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToMany(mappedBy = "order")
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
