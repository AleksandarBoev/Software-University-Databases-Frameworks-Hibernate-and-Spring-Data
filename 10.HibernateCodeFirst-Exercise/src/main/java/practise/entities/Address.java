package practise.entities;

import javax.persistence.*;

//@Entity
@Table(name = "addresses")
public class Address extends BaseId {
    private String name;
    private Town town;

    public Address() {
    }

    public Address(String name, Town town) {
        this.name = name;
        this.town = town;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String description) {
        this.name = description;
    }

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    public Town getTown() {
        return this.town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + this.name + '\'' +
                ", town=" + this.town.getName() +
                '}';
    }
}
