package alararestaurant.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {
    //•	name – text with min length 3 and max length 30 (required)
    //•	age – integer in the range [15, 80] (required)
    //•	position – the employee’s position (required)
    //•	orders – the orders the employee has processed
    private String name;
    private Integer age;
    private Position position;
    private List<Order> orders;

    public Employee() {
    }

    @Column(nullable = false)//, length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)//, columnDefinition = "INT(2)")
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false, referencedColumnName = "id")
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @OneToMany(mappedBy = "employee")
    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
