package p04_hospital_database.entities;

import p04_hospital_database.root_entities.BaseName;

import javax.persistence.*;
import java.util.List;

//@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseName {
    private String comment;
    private List<Visitation> sameDiagnoseVisitations;

    public Diagnose() {

    }

    public Diagnose(String name, String comment) {
        super(name);
        this.comment = comment;
    }

    @Column(nullable = false)
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        if ("".equals(comment))
            throw new IllegalArgumentException("Comment can't be empty!");

        this.comment = comment;
    }

    @OneToMany(mappedBy = "diagnose")
    public List<Visitation> getSameDiagnoseVisitations() {
        return this.sameDiagnoseVisitations;
    }

    public void setSameDiagnoseVisitations(List<Visitation> sameDiagnoseVisitations) {
        this.sameDiagnoseVisitations = sameDiagnoseVisitations;
    }

    @Override
    public String toString() {
        return super.toString() + "comment: " + this.comment;
    }

    @Transient
    public String getVisitationsForThisDiagnose() {
        StringBuilder sb = new StringBuilder();
        for (Visitation visitation : this.sameDiagnoseVisitations) {
            sb.append(visitation.toString()).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
