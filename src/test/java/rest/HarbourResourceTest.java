package rest;

import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;

public class HarbourResourceTest {


    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {

        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }



    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

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

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    private static String securityToken;


    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        ;
    }

    private void logOut() {
        securityToken = null;
    }


    @Test
    public void getAllOwnersUnAuthorizedTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/harbour/owners")
                .then()
                .statusCode(403);

    }

    @Test
    public void getAllOwnersAuthorizedTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/harbour/owners").then()
                .statusCode(200);
    }

}
