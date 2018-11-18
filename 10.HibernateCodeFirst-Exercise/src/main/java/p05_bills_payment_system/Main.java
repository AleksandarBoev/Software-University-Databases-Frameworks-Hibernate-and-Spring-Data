package p05_bills_payment_system;

import p05_bills_payment_system.entities.BankAccount;
import p05_bills_payment_system.entities.BillingDetail;
import p05_bills_payment_system.entities.CreditCard;
import p05_bills_payment_system.entities.User;
import p05_bills_payment_system.enums.CardType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("bill_payment_persistence");

        EntityManager em = emf.createEntityManager();

//        fillWithInfo(em);
        User user1 = em.find(User.class, 1);
        User user2 = em.find(User.class, 2);

        System.out.println(user1);
        System.out.println(user2);

        em.close();
        emf.close();
    }

    private static void fillWithInfo(EntityManager em) {
        User user1 = new User("Aleksandar", "Boev", "123@abv.bg", "****");
        User user2 = new User("Pesho", "Peshev", "356@yahoo.com", "***");

        BillingDetail bankAccount = new BankAccount("1234", user1, "TrustBank", "AABBCC123");
        BillingDetail creditCard1 = new CreditCard("1234567890123", user1, CardType.GOLD, 12, 2018);
        BillingDetail creditCard2 = new CreditCard("1234567890123", user2, CardType.BRONZE, 11, 2018);

        em.getTransaction().begin();

        em.persist(user1);
        em.persist(user2);

        em.persist(bankAccount);
        em.persist(creditCard1);
        em.persist(creditCard2);

        em.getTransaction().commit();
    }
}
