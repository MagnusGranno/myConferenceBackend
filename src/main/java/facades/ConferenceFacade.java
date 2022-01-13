package facades;

import DTO.ConferenceDTO;
import DTO.StatusDTO;
import DTO.TalkDTO;
import entities.Conference;
import entities.Talk;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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
            conferenceDTO.setCapacity(c.getCapacity());
            conferenceDTOList.add(conferenceDTO);
        }

        return conferenceDTOList;
    }


    public List<TalkDTO> getTalkByConference(long conferenceID) {
        List<TalkDTO> talkDTOList = new ArrayList<>();
        List<Talk> talkList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        Conference conference;
        try {
            em.getTransaction().begin();
            conference = em.find(Conference.class, conferenceID);
            TypedQuery<Talk> tq = em.createQuery("Select t from Talk t where t.conference = :conference", Talk.class);
            tq.setParameter("conference", conference);
            talkList = tq.getResultList();
        } catch (Exception e)
        {
            System.out.println(e);
        } finally {
            em.close();
        }
        for (Talk t : talkList) {
            TalkDTO talkDTO = new TalkDTO();
            talkDTO.setId(t.getId());
            talkDTO.setTopic(t.getTopic());
            talkDTO.setDuration(t.getDuration());
            talkDTO.setProps_list(t.getPropsList());
            talkDTOList.add(talkDTO);
        }
        return talkDTOList;

    }

}
