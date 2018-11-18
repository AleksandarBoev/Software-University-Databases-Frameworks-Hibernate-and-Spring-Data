package practise;

import practise.entities.Address;
import practise.entities.Department;
import practise.entities.Employee;
import practise.entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("practise_persistence");

        EntityManager em = emf.createEntityManager();

        fillWithInfo(em);

        em.close();
        emf.close();
    }

    private static void fillWithInfo(EntityManager em) {
        Town town = new Town("Sofia");
        Address address1 = new Address("Cherni vruh 12", town);
        Address address2 = new Address("Cherni vruh 11", town);

        Department department = new Department("Software Engineering");

        Employee employee1 = new Employee("Aleksandar", department, address1);
        Employee employee2 = new Employee("Pavel", department, address2);

        List<Object> objects = new ArrayList<>(6);
        objects.add(town);
        objects.add(address1);
        objects.add(address2);
        objects.add(department);
        objects.add(employee1);
        objects.add(employee2);

        em.getTransaction().begin();

        for (Object object : objects)
            em.persist(object);

        em.getTransaction().commit();

    }

    private static void printTownAndAddresses(EntityManager em) {
        Town sofiaTown = em.find(Town.class, 1L);
        System.out.println(sofiaTown);
        System.out.println("----------");
        Address address1 = em.find(Address.class, 2L);
        Address address2 = em.find(Address.class, 3L);
        System.out.println(address1);
        System.out.println(address2);
    }
}
