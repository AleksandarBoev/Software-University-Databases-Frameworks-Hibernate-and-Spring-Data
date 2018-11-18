package p03_university_system;

import p03_university_system.entities.Course;
import p03_university_system.entities.Student;
import p03_university_system.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("university_persistence");
        EntityManager em = emf.createEntityManager();

        fillInInfo(em);

        em.close();
        emf.close();
    }

    private static void fillInInfo(EntityManager em) {
        Teacher teacher = new Teacher(
                "Ivo", "Hristov", "12345",
                "123@gmail.com", BigDecimal.valueOf(20.50));

        Student student1 = new Student("Aleksandar", "Boev", "089123", 6.00, 75.0);
        Student student2 = new Student("Pesho", "Peshov", "088123", 5.00, 45.0);
        Student student3 = new Student("Gosho", "Goshov", "088123", 5.00, 45.0);

        Course cSharpCourse = new Course(
                "C# Basics", "Blabla c#", Date.valueOf("2018-09-09"), Date.valueOf("2018-10-21"),
                3, teacher);

        Course javaCourse = new Course(
                "Java Basics", "Blabla java", Date.valueOf("2017-09-09"), Date.valueOf("2017-10-21"),
                3, teacher);

        student1.getCourseList().add(cSharpCourse);
        student1.getCourseList().add(javaCourse);

        javaCourse.getStudentsList().add(student2);
        javaCourse.getStudentsList().add(student3);

        em.getTransaction().begin();

        em.persist(teacher);
        em.persist(student1);
        em.persist(student2);
        em.persist(student3);
        em.persist(cSharpCourse);
        em.persist(javaCourse);

        em.getTransaction().commit();

        teacher.getCourses().forEach(System.out::println); //TODO does not work
    }
}
