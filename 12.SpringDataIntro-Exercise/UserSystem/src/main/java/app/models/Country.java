package app.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country implements Identifiable {
    private Long id;
    private String name;
    private Set<Town> towns;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
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

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    public Set<Town> getTowns() {
        return this.towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }
}
