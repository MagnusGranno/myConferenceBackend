package rest;

import DTO.CreateConferenceDTO;
import com.nimbusds.jose.shaded.json.JSONObject;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
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

public class ConferenceResourceTest {


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


            Conference conferenceOne = new Conference("Omega-IT", "Copenhagen", 1000, 2022, 4, 22, "13:00 - 17:30");
            Conference conferenceTwo = new Conference("Meta", "London", 1000, 2023, 6, 15, "09:30 - 12:30");
            Conference conferenceThree = new Conference("Startup Conf", "New York", 1000, 2023, 5, 20, "11:15 - 15:45");


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
    public void getAllConferencesUnAuthorizedTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/conference/conferences")
                .then()
                .statusCode(403);

    }

    @Test
    public void getAllConferencesAuthorizedTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/conference/conferences").then()
                .statusCode(200);
    }

    @Test
    public void createConferenceAuthTest() {
        login("admin", "test");
        RestAssured.baseURI = SERVER_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "ThisisatestConf");
        requestParams.put("capacity", 1000);
        requestParams.put("location", "Test City");
        requestParams.put("year", 2222);
        requestParams.put("month", 5);
        requestParams.put("date", 10);
        requestParams.put("time", "00:00 - 00:00");

        request.header("Content-Type", "application/json");
        request.header("x-access-token", securityToken);
        request.body(requestParams.toJSONString());
        Response response = request.post("/conference/create/conference");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        String statusMessage = response.jsonPath().get("message");
        Assert.assertEquals("Conference: " + "ThisisatestConf" + " created!", statusMessage);
    }

    @Test
    public void createConferenceNoAuthTest() {
        logOut();
        RestAssured.baseURI = SERVER_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "ThisisatestConf");
        requestParams.put("capacity", 1000);
        requestParams.put("location", "Test City");
        requestParams.put("year", 2222);
        requestParams.put("month", 5);
        requestParams.put("date", 10);
        requestParams.put("time", "00:00 - 00:00");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/conference/create/conference");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(403, statusCode);

    }
}
