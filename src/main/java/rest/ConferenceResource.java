package rest;

import DTO.CreateConferenceDTO;
import DTO.CreateSpeakerDTO;
import DTO.CreateTalkDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import errorhandling.API_Exception;
import facades.ConferenceFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
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
   // @RolesAllowed({"user", "admin"})
    public String getAllConferences() {

        try {
            return gson.toJson(CONFERENCE_FACADE.getConferences());
        } catch (Exception e) {
            return gson.toJson(CONFERENCE_FACADE.createStatusDTO(true, "You are not allowed to view this!"));
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("talks")
   // @RolesAllowed({"user", "admin"})
    public String getAllTalks() {

        try {
            return gson.toJson(CONFERENCE_FACADE.getAllTalks());
        } catch (Exception e) {
            return gson.toJson(CONFERENCE_FACADE.createStatusDTO(true, "You are not allowed to view this!"));
        }

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("talk/{id}")
    // @RolesAllowed({"user", "admin"})
    public String getTalkById(@PathParam("id") long id) throws IOException {
        try {
            return gson.toJson(CONFERENCE_FACADE.getTalkByConference(id));
        } catch (Exception e) {
            return gson.toJson(CONFERENCE_FACADE.createStatusDTO(true, "You are not allowed to view this"));
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("talk/speaker/{id}")
    // @RolesAllowed({"user", "admin"})
    public String getTalkBySpeaker(@PathParam("id") long id) throws IOException {

        try {
            return gson.toJson(CONFERENCE_FACADE.getTalkBySpeaker(id));
        } catch (Exception e) {
            return gson.toJson(CONFERENCE_FACADE.createStatusDTO(true, "You are not allowed to view this"));
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("speakers")
    // @RolesAllowed({"user", "admin"})
    public String getallSpeakers() throws IOException {

        try {
            return gson.toJson(CONFERENCE_FACADE.getAllSpeakers());
        } catch (Exception e) {
            return gson.toJson(CONFERENCE_FACADE.createStatusDTO(true, "You are not allowed to view this"));
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create/conference")
    // @RolesAllowed({"user", "admin"})
    public String createConference(String jsonString) throws API_Exception {
        CreateConferenceDTO createConferenceDTO = new CreateConferenceDTO();
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            createConferenceDTO.setName(json.get("name").getAsString());
            createConferenceDTO.setCapacity(json.get("capacity").getAsInt());
            createConferenceDTO.setLocation(json.get("location").getAsString());
            createConferenceDTO.setYear(json.get("year").getAsInt());
            createConferenceDTO.setMonth(json.get("month").getAsInt());
            createConferenceDTO.setDate(json.get("date").getAsInt());
            createConferenceDTO.setTime(json.get("time").getAsString());

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        return gson.toJson(CONFERENCE_FACADE.createConference(createConferenceDTO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create/talk")
    // @RolesAllowed({"user", "admin"})
    public String createTalk(String jsonString) throws API_Exception {
        CreateTalkDTO createTalkDTO = new CreateTalkDTO();
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            createTalkDTO.setTopic(json.get("topic").getAsString());
            createTalkDTO.setDuration(json.get("duration").getAsInt());
            createTalkDTO.setProps_list(json.get("props_list").getAsString());
            createTalkDTO.setConf_id(json.get("conf_id").getAsLong());
            createTalkDTO.setSpeaker_id(json.get("speaker_id").getAsLong());
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        return gson.toJson(CONFERENCE_FACADE.createTalk(createTalkDTO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create/speaker")
    // @RolesAllowed({"user", "admin"})
    public String createSpeaker(String jsonString) throws API_Exception {
        CreateSpeakerDTO createSpeakerDTO = new CreateSpeakerDTO();
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            createSpeakerDTO.setName(json.get("name").getAsString());
            createSpeakerDTO.setProfession(json.get("profession").getAsString());
            createSpeakerDTO.setCompany(json.get("company").getAsString());
            createSpeakerDTO.setGender(json.get("gender").getAsString());
            createSpeakerDTO.setTalk_id(json.get("talk_id").getAsLong());
            createSpeakerDTO.setConf_id(json.get("conf_id").getAsLong());
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        return gson.toJson(CONFERENCE_FACADE.createSpeaker(createSpeakerDTO));
    }

}
