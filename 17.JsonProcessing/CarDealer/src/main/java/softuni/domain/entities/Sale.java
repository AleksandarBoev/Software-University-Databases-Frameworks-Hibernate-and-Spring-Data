package softuni.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{
    private Double discount;
    private Car car;
    private Customer customer;

    @Column
    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        String carString = String.format("make - %s, model - %s", this.car.getMake(), this.car.getModel());

        return "Sale{" +
                "discount = " + this.discount +
                ", car: " + carString +
                ", customer = " + this.customer.getName() +
                '}';
    }
}
