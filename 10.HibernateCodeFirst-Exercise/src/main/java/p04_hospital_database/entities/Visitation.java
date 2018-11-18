package p04_hospital_database.entities;

import p04_hospital_database.root_entities.BaseId;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

//@Entity
@Table(name = "visitations")
public class Visitation extends BaseId {
    private Date date;
    private String comment;
    private Diagnose diagnose;
    private Patient patient;
    private List<Medicament> medicamentList;

    public Visitation() {
    }

    public Visitation(Date date, String comment, Patient patient, Diagnose diagnose, List<Medicament> medicaments) {
        this.setDate(date);
        this.setComment(comment);
        this.setPatient(patient);
        this.setDiagnose(diagnose);
        this.setMedicamentList(medicaments);
    }

    @Column(nullable = false)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(nullable = false)
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    public Diagnose getDiagnose() {
        return this.diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    @ManyToOne
    //one patient can make many visitations, but a single visitation can't be made by many patients. One at a time!
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToMany
    @JoinTable(name = "visitations_medicaments",
            joinColumns = @JoinColumn(name = "visitation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    public List<Medicament> getMedicamentList() {
        return this.medicamentList;
    }

    public void setMedicamentList(List<Medicament> medicamentList) {
        this.medicamentList = medicamentList;
    }


    @Override
    public String toString() {
        return "Visitation{" +
                "date=" + this.date.toString() +
                ", comment='" + this.comment + '\'' +
                ", diagnose=" + this.diagnose.toString() +
                ", patient=" + this.patient.getFirstName()+ " " + this.patient.getLastName() +
                ", medicamentList=" + String.join(", ", this.medicamentList.stream().map(m -> m.getName()).collect(Collectors.toList())) +
                '}';
    }
}
