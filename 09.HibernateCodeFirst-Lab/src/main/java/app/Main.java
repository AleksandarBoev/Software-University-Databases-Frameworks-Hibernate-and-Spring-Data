package app;

import app.contracts.Ingredient;
import app.contracts.Label;
import app.contracts.Shampoo;
import app.entities.basic_ingredients.Mint;
import app.entities.basic_ingredients.Nettle;
import app.entities.chemical_ingredients.AmmoniumChloride;
import app.entities.shampoos.FreshNuke;
import app.root_entities.BasicIngredient;
import app.root_entities.BasicLabel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("shampoo_company");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        BasicIngredient ac = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel("Fresh Nuke Shampoo", "Contains mint and nettle");

        Shampoo shampoo = new FreshNuke(label);

//        shampoo.addIngredient(ac); //does not recognize AmmoniumChloride as a subclass of BasicIngredient
        shampoo.addIngredient(mint);
        shampoo.addIngredient(nettle);

        entityManager.persist(shampoo);

        entityManager.getTransaction().commit();
        entityManager.close();
        managerFactory.close();
    }
}
