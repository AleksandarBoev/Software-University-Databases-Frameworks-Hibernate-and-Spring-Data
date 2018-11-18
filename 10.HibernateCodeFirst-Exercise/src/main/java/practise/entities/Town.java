package practise.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

//@Entity
@Table(name = "towns")
public class Town extends BaseId {
    private String name;
    private List<Address> addressesList;

    public Town() {
    }

    public Town(String name) {
        this.name = name;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "town")
    public List<Address> getAddressesList() {
        return this.addressesList;
    }

    public void setAddressesList(List<Address> addressesList) {
        this.addressesList = addressesList;
    }

    @Override
    public String toString() {
        String addresses = String.join(", ", this.addressesList.stream().map(a -> a.getName()).collect(Collectors.toList()));

        return "Town{" +
                "name='" + this.name + '\'' +
                ", addressesList=" + addresses +
                '}';
    }
}
