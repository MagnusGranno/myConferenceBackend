package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Random;

public class SetupTestUsers {

  public static void main(String[] args) {
    setupTestUsers();
  }

  public static void setupTestUsers(){
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();



    User user = new User("user", "user");
    User admin = new User("admin", "admin");
    User both = new User("user_admin", "useradmin");
    Owner ownerOne = new Owner("Jacob", "Douglas Extension", 19128734);
    Owner ownerTwo = new Owner("Martin", "Some Boulevard", 56473839);
    Owner ownerThree = new Owner("Erik", "Very nice street", 12345678);
    Owner ownerFour = new Owner("Elliot", "Bleeker street", 98771233);
    Boat boatOne = new Boat("Nike", "Alicia", "thisisanimageurl");
    Boat boatTwo = new Boat("Adidas", "Melanie", "thisisapicture");
    Boat boatThree = new Boat("Apple", "Lulu", "thisismypicture");
    Boat boatFour = new Boat("Steelseries", "Karen", "kareninapicture");
    Boat boatFive = new Boat("Lunar", "Melissa", "thispictureisyikes");
    Boat boatSix = new Boat("Booster", "Joanna", "image");
    Harbour harbourOne = new Harbour("Le Harbour", "Near water street", 1000);
    Harbour harbourTwo = new Harbour("Se Harbour", "Close to the ocean street", 10000);

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);


    boatOne.setOwner(ownerOne);
    boatTwo.setOwner(ownerTwo);
    boatThree.setOwner(ownerOne);
    boatFour.setOwner(ownerThree);
    boatFive.setOwner(ownerFour);
    boatSix.setOwner(ownerThree);
    boatOne.setHarbour(harbourOne);
    boatTwo.setHarbour(harbourOne);
    boatThree.setHarbour(harbourOne);
    boatFour.setHarbour(harbourOne);
    boatFive.setHarbour(harbourTwo);
    boatSix.setHarbour(harbourTwo);

    em.persist(ownerOne);
    em.persist(ownerTwo);
    em.persist(ownerThree);
    em.persist(ownerFour);
    em.persist(boatOne);
    em.persist(boatTwo);
    em.persist(boatThree);
    em.persist(boatFour);
    em.persist(boatFive);
    em.persist(boatSix);
    em.persist(harbourOne);
    em.persist(harbourTwo);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();

  }

}