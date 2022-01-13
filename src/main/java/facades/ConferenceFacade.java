package facades;

import DTO.ConferenceDTO;
import DTO.StatusDTO;
import entities.Conference;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ConferenceFacade {
    private static EntityManagerFactory emf;
    private static ConferenceFacade instance;


    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static ConferenceFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ConferenceFacade();
        }
        return instance;
    }



    public StatusDTO createStatusDTO (boolean error, String message) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setMessage(message);
        statusDTO.setError(error);
        return statusDTO;
    }

    public List<ConferenceDTO> getConferences() {

        List<ConferenceDTO> conferenceDTOList = new ArrayList<>();
        List<Conference> conferenceList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            TypedQuery<Conference> tq = em.createQuery("SELECT c FROM Conference c", Conference.class);
            conferenceList = tq.getResultList();

        } finally {
            em.close();
        }

        for (Conference c : conferenceList) {
            ConferenceDTO conferenceDTO = new ConferenceDTO();
            conferenceDTO.setId(c.getId());
            conferenceDTO.setName(c.getName());
            conferenceDTO.setLocation(c.getLocation());
            conferenceDTO.setDate(c.getDate());
            conferenceDTO.setTime(c.getTime());
            conferenceDTOList.add(conferenceDTO);
        }

        return conferenceDTOList;
    }

}
