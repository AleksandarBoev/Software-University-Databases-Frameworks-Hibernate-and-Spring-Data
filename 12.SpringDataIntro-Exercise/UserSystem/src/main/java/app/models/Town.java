package app.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town implements Identifiable{
    private Long id;
    private String name;
    private Country country; //maybe the data type should be something else

    public Town() {
    }

    public Town(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne //TODO
    @JoinColumn(referencedColumnName = "id")
    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
