package facades;

import DTO.StatusDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;


    private UserFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password, user.getUserPass())) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public StatusDTO createUser(String username, String password) {

        EntityManager em = emf.createEntityManager();

        StatusDTO statusDTO = new StatusDTO();
        User user = new User(username, password);
        Role userRole;
        if (em.find(User.class, username) != null) {
            statusDTO.setMessage("Username: " + username + " is already taken.");
            statusDTO.setError(true);
            return statusDTO;

        }
        try {
            if (em.find(Role.class, "user") == null) {
                userRole = new Role("user");
                em.getTransaction().begin();
                em.persist(userRole);
                user.addRole(userRole);
                em.persist(user);
                em.getTransaction().commit();
            }
            else {
                em.getTransaction().begin();
                userRole = em.find(Role.class, "user");
                user.addRole(userRole);
                em.persist(user);
                em.getTransaction().commit();
            }
            statusDTO.setMessage("Signup successful you can now login");
            statusDTO.setError(false);
            return statusDTO;

        } catch (Exception e) {
            statusDTO.setMessage("Username: " + username + " is already taken.");
            statusDTO.setError(true);
            return statusDTO;
        } finally {
            em.close();
        }
    }


}
