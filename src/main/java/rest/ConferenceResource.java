package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.ConferenceFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("conference")
public class ConferenceResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final ConferenceFacade CONFERENCE_FACADE = ConferenceFacade.getOwnerFacade(EMF);
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("conferences")
    @RolesAllowed({"user", "admin"})
    public String getAllOwners() {

        try {
            return gson.toJson(CONFERENCE_FACADE.getConferences());
        } catch (Exception e) {
            return gson.toJson(CONFERENCE_FACADE.createStatusDTO(true, "You are not allowed to view this!"));
        }

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("talk/{id}")
    @RolesAllowed({"user", "admin"})
    public String getTalkById(@PathParam("id") long id) throws IOException {

        try {
            return gson.toJson(CONFERENCE_FACADE.getTalkByConference(id));
        } catch (Exception e ) {
            return gson.toJson(CONFERENCE_FACADE.createStatusDTO(true, "You are not allowed to view this"));
        }

    }
}
