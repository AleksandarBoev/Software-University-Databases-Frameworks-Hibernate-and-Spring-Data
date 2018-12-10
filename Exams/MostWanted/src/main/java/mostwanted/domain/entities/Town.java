package mostwanted.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    private String name;
    private List<Racer> racers;
    private List<District> districts;

    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "town")
    public List<Racer> getRacers() {
        return this.racers;
    }

    public void setRacers(List<Racer> racers) {
        this.racers = racers;
    }

    @OneToMany(mappedBy = "town")
    public List<District> getDistricts() {
        return this.districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
