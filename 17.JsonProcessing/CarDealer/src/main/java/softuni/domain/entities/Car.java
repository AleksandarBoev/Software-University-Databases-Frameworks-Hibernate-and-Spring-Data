package softuni.domain.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/*
A car's make is the brand of the vehicle, while the model refers to the name of a car product
 and sometimes a range of products. For example, Toyota is a car make and Camry is a car model.
 */
@Entity
@Table(name = "cars")
public class Car extends BaseEntity{
    private String make;
    private String model;
    private BigInteger travelledDistance;
    private List<Part> parts;

    public Car() {
        this.parts = new ArrayList<>();
    }

    @Column
    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /*
    Some values in the json seed file are the same as the maximum value of the BigInt in sql.
    Making it unsigned (not negative) increases the maximum value and eliminates the errors.
     */
    @Column(name = "travelled_distance", columnDefinition = "BIGINT(19) UNSIGNED")
    public BigInteger getTravelledDistance() {
        return this.travelledDistance;
    }

    public void setTravelledDistance(BigInteger travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    @ManyToMany //TODO fetch type?
    @JoinTable(
            name = "cars_parts",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id")
    )
    public List<Part> getParts() {
        return this.parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
