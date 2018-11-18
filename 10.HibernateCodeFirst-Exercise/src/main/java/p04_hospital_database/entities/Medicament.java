package p04_hospital_database.entities;

import p04_hospital_database.root_entities.BaseName;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
@Table(name = "medicaments")
public class Medicament extends BaseName {
    public Medicament() {
    }

    public Medicament(String name) {
        super(name);
    }
}
