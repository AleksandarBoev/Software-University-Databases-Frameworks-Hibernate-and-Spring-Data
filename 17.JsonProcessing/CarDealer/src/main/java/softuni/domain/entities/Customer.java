package softuni.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{
    private String name;
    private LocalDateTime birthDate;
    private Boolean isYoungDriver;
    private List<Sale> carsBought;

    public Customer() {
        this.carsBought = new ArrayList<>();
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "date_of_birth")
    public LocalDateTime getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "is_young_driver")
    public Boolean getYoungDriver() {
        return this.isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    public List<Sale> getCarsBought() {
        return this.carsBought;
    }

    public void setCarsBought(List<Sale> carsBought) {
        this.carsBought = carsBought;
    }
}
