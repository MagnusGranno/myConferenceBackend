package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.Random;

public class SetupTestUsers {

    public static void main(String[] args) {
        setupTestUsers();
    }

    public static void setupTestUsers() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();


        Conference conferenceOne = new Conference("Omega-IT", "Copenhagen", 1000, 2022, 4, 22, 300);
        Conference conferenceTwo = new Conference("Meta", "London", 1000, 2023, 6, 15, 120);
        Conference conferenceThree = new Conference("Startup Conf", "New York", 1000, 2023, 5, 20, 240);


        Talk talkOne = new Talk("Animal Freedom", 30, "Pictures of animals");
        Talk talkTwo = new Talk("Flying Cars", 45, "Powerpoint");
        Talk talkThree = new Talk("Apples", 20, "Basket, Apples, Chair");
        Talk talkFour = new Talk("Space-X", 60, "Microphone, Table, Chair, Pointer");
        Talk talkFive = new Talk("Metaverse", 60, "Pointer, Microphone");
        Talk talkSix = new Talk("Social Media", 35, "Iphone, Android, Camera");


        Speaker speakerOne = new Speaker("Mark Zuckerberg", "IT", "male", "Meta");
        Speaker speakerTwo = new Speaker("Elon Musk", "IT", "male", "Tesla");
        Speaker speakerThree = new Speaker("Marie Applebuttom", "Fruit & vegetables", "female", "We love apples");
        Speaker speakerFour = new Speaker("Susan Top", "Mechanic", "female", "Toyota");
        Speaker speakerFive = new Speaker("Mike Oxmaul", "TravelAgent", "male", "");



        speakerOne.addTalk(talkOne);
        speakerOne.addTalk(talkSix);
        speakerTwo.addTalk(talkTwo);
        speakerThree.addTalk(talkFour);
        speakerFour.addTalk(talkFive);
        speakerFive.addTalk(talkThree);

        conferenceOne.addTalk(talkOne);
        conferenceOne.addTalk(talkFour);
        conferenceOne.addTalk(talkSix);
        conferenceTwo.addTalk(talkTwo);
        conferenceTwo.addTalk(talkThree);
        conferenceThree.addTalk(talkFive);


        User user = new User("user", "user");
        User admin = new User("admin", "admin");
        User both = new User("user_admin", "useradmin");
        if (admin.getUserPass().equals("test") || user.getUserPass().equals("test") || both.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);

        // PERSIST
        //Roles
        em.persist(userRole);
        em.persist(adminRole);
        //Users
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        //Conferences
        em.persist(conferenceOne);
        em.persist(conferenceTwo);
        em.persist(conferenceThree);
        //Talks
        em.persist(talkOne);
        em.persist(talkTwo);
        em.persist(talkThree);
        em.persist(talkFour);
        em.persist(talkFive);
        em.persist(talkSix);
        //Speakers
        em.persist(speakerOne);
        em.persist(speakerTwo);
        em.persist(speakerThree);
        em.persist(speakerFour);
        em.persist(speakerFive);

        em.getTransaction().commit();

    }

}