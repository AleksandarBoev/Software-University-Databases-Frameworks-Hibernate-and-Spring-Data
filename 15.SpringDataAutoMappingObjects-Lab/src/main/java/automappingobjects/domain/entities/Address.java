package automappingobjects.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
    private String city;

    public Address() {
    }

    public Address(String city) {
        this.city = city;
    }

    @Column
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city = '" + this.city + '\'' +
                '}';
    }
}
