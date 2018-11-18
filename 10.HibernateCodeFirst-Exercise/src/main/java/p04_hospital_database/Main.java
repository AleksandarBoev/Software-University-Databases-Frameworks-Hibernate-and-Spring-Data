package p04_hospital_database;

import p04_hospital_database.entities.Diagnose;
import p04_hospital_database.entities.Medicament;
import p04_hospital_database.entities.Patient;
import p04_hospital_database.entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main { //TODO task is cool. Spend some extra time on it later
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hospital_persistence");
        EntityManager em = emf.createEntityManager();

//        fillWithData(em);
        Visitation visitation = em.find(Visitation.class, 9);
        System.out.println(visitation);

        em.close();
        emf.close();
    }

    private static void fillWithData(EntityManager em) {
        Patient patient1 = new Patient("Aleksandar", "Boev", "Hristo Botev 30", "hello@abv.bg",
                Date.valueOf("1999-09-09"), null, true);

        Patient patient2 = new Patient("Pesho", "Peshov", "Hristo Botev 99", "goodbye@abv.bg",
                Date.valueOf("1995-10-28"), null, false);



        Diagnose diagnose1 = new Diagnose("[Some complicated sickness name]", "[Some complicated symptoms]");
        Diagnose diagnose2 = new Diagnose("No school day", "New game is out and needs to be played.");

        Medicament medicament1 = new Medicament("Tailol hot");
        Medicament medicament2 = new Medicament("Immune stimulator 2000");
        Medicament medicament3 = new Medicament("Coka-cola");
        Medicament medicament4 = new Medicament("Chips and chocolate");

        List<Medicament> diagnose1List = new ArrayList<>();
        diagnose1List.add(medicament1);
        diagnose1List.add(medicament2);

        List<Medicament> diagnose2List = new ArrayList<>();
        diagnose2List.add(medicament3);
        diagnose2List.add(medicament4);

        Visitation visitation1 = new Visitation(Date.valueOf("2018-09-09"), "Says throat is sore", patient1, diagnose1, diagnose1List);
        Visitation visitation2 = new Visitation(Date.valueOf("2018-12-09"), "Says stomach hurts", patient2, diagnose2, diagnose2List);
        Visitation visitation3 = new Visitation(Date.valueOf("2018-12-14"), "Says he has toxic gasses", patient1, diagnose2, diagnose2List);

        em.getTransaction().begin();

        em.persist(patient1);
        em.persist(patient2);
        em.persist(diagnose1);
        em.persist(diagnose2);
        em.persist(medicament1);
        em.persist(medicament2);
        em.persist(medicament3);
        em.persist(medicament4);
        em.persist(visitation1);
        em.persist(visitation2);

        em.getTransaction().commit();
    }
}
