package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.ConferenceFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("conference")
public class ConferenceResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final ConferenceFacade OWNER_FACADE = ConferenceFacade.getOwnerFacade(EMF);
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("conferences")
    @RolesAllowed({"user", "admin"})
    public String getAllOwners() {

        try {
            return gson.toJson(OWNER_FACADE.getConferences());
        } catch (Exception e) {
            return gson.toJson(OWNER_FACADE.createStatusDTO(true, "You are not allowed to view this!"));
        }

    }
}
