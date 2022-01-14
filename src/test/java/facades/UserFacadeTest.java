package facades;

import DTO.StatusDTO;
import entities.Role;
import entities.User;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.*;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    private List<User> userList = new ArrayList<>();
    private List<User> adminList = new ArrayList<>();

    private User Peter = new User("Peter", "parker");
    private User Steven = new User("Steven", "strange");
    private User Scott = new User("Scott", "lang");
    private User Tony = new User("Tony", "stark");
    private User Nick = new User("Nick", "fury");



    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);


        Role user_role = new Role("user");
        Role admin_role = new Role("admin");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from Role").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.persist(user_role);
            em.persist(admin_role);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.resetAutoIncrement").executeUpdate();

            userList.add(Peter);
            userList.add(Scott);
            userList.add(Steven);

            adminList.add(Tony);
            adminList.add(Nick);

            for (User u : userList) {
                em.persist(u);
            }
            for (User a : adminList) {
                em.persist(a);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test
    public void getVeryfiedUserTest() {
        try {
            Assert.assertEquals(facade.getVeryfiedUser("Peter", "parker").getUserName(), Peter.getUserName());
        } catch (AuthenticationException AE) {
            System.out.println(AE);
        }
    }

    @Test
    public void createNewUserTest() {
        StatusDTO expectedDTO = new StatusDTO();
        StatusDTO actualDTO = facade.createUser("Clint", "barton");
        expectedDTO.setError(false);
        expectedDTO.setMessage("Signup successful you can now login");
        Assert.assertEquals(actualDTO.isError(), expectedDTO.isError());
        Assert.assertEquals(actualDTO.getMessage(), expectedDTO.getMessage());

    }

    @Test
    public void createExistingUserTest() {
        StatusDTO expectedDTO = new StatusDTO();
        StatusDTO actualDTO = facade.createUser("Peter", "parker");
        expectedDTO.setError(true);
        expectedDTO.setMessage("Username: " + Peter.getUserName() + " is already taken.");
        Assert.assertEquals(actualDTO.isError(), expectedDTO.isError());
        Assert.assertEquals(actualDTO.getMessage(), expectedDTO.getMessage());
    }
}
