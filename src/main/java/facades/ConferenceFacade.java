package facades;

import DTO.*;
import entities.Conference;
import entities.Speaker;
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


    public StatusDTO createStatusDTO(boolean error, String message) {
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
            conferenceDTO.convertToDTO(c);
            conferenceDTOList.add(conferenceDTO);
        }

        return conferenceDTOList;
    }

public List<TalkDTO> getAllTalks() {
    List<Talk> talkList = new ArrayList<>();
    List<TalkDTO> talkDTOList = new ArrayList<>();

    EntityManager em = emf.createEntityManager();

    try {
        em.getTransaction().begin();
        TypedQuery<Talk> tq = em.createQuery("Select t from Talk t", Talk.class);
        talkList = tq.getResultList();
    } catch (Exception e) {
        System.out.println(e);
    } finally {
        em.close();
    }
    for (Talk t : talkList) {
        TalkDTO talkDTO = new TalkDTO();
        talkDTO.convertToDTO(t);
        talkDTO.setSpeaker_list(getSpeakersOnTalks(t));
        talkDTOList.add(talkDTO);
    }
    return talkDTOList;
}
    public List<TalkDTO> getTalkByConference(long conferenceId) {

        List<Talk> talkList = new ArrayList<>();
        List<TalkDTO> talkDTOList = new ArrayList<>();

        EntityManager em = emf.createEntityManager();
        Conference conference;
        try {
            em.getTransaction().begin();
            conference = em.find(Conference.class, conferenceId);
            TypedQuery<Talk> tq = em.createQuery("Select t from Talk t where t.conference = :conference", Talk.class);
            tq.setParameter("conference", conference);
            talkList = tq.getResultList();

            for (Talk t : talkList) {
                TalkDTO talkDTO = new TalkDTO();
                talkDTO.convertToDTO(t);
                talkDTO.setSpeaker_list(getSpeakersOnTalks(t));
                talkDTOList.add(talkDTO);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }

        return talkDTOList;

    }


    public List<TalkDTO> getTalkBySpeaker(long speakerId) {
        List<TalkDTO> talkDTOList = new ArrayList<>();
        List<Talk> talkList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        Speaker speaker;
        try {
            em.getTransaction().begin();
            speaker = em.find(Speaker.class, speakerId);
            TypedQuery<Talk> tq = em.createQuery("SELECT s.talkList from Speaker s where s.id = :speaker_id", Talk.class);
            tq.setParameter("speaker_id", speakerId);
            talkList = tq.getResultList();
        } catch (Exception e) {
            System.out.println(e);

        } finally {
            em.close();
        }
        for (Talk t : talkList) {
            TalkDTO talkDTO = new TalkDTO();
            talkDTO.convertToDTO(t);
            talkDTO.setSpeaker_list(getSpeakersOnTalks(t));
            talkDTOList.add(talkDTO);
        }
        return talkDTOList;
    }


    public List<SpeakerDTO> getAllSpeakers() {
        List<Speaker> speakerList = new ArrayList<>();
        List<SpeakerDTO> speakerDTOList = new ArrayList<>();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            TypedQuery<Speaker> tq = em.createQuery("Select s from Speaker s", Speaker.class);
            speakerList = tq.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
        for (Speaker s : speakerList) {
            SpeakerDTO speakerDTO = new SpeakerDTO();
            speakerDTO.convertToDTO(s);
            speakerDTOList.add(speakerDTO);
        }
        return speakerDTOList;
    }


    public StatusDTO createConference(CreateConferenceDTO con) {
        StatusDTO statusDTO = new StatusDTO();
        EntityManager em = emf.createEntityManager();
        Conference conference = new Conference(con.getName(), con.getLocation(), con.getCapacity(), con.getYear(), con.getMonth(), con.getDate(), con.getTime());

        try {
            em.getTransaction().begin();
            em.persist(conference);
            em.getTransaction().commit();

            statusDTO.setError(false);
            statusDTO.setMessage("Conference: " + conference.getName() + " created!");
            return statusDTO;
        } catch (Exception e) {

            statusDTO.setError(true);
            statusDTO.setMessage("unable to create conference! Try again");
            return statusDTO;
        } finally {
            em.close();
        }

    }
    public StatusDTO createTalk(CreateTalkDTO ctd) {
        StatusDTO statusDTO = new StatusDTO();
        EntityManager em = emf.createEntityManager();
        Talk talk = new Talk(ctd.getTopic(), ctd.getDuration(), ctd.getProps_list());
        Conference conference;
        Speaker speaker;

        try {
            em.getTransaction().begin();
            if (ctd.getConf_id() != 0 && ctd.getSpeaker_id() != 0) {
                conference = em.find(Conference.class, ctd.getConf_id());
                speaker = em.find(Speaker.class, ctd.getSpeaker_id());
                conference.addTalk(talk);
                speaker.addTalk(talk);
            }
            em.persist(talk);
            em.getTransaction().commit();

            statusDTO.setError(false);
            statusDTO.setMessage("Talk: " + talk.getTopic() + " created!");
            return statusDTO;
        } catch (Exception e) {
            statusDTO.setError(true);
            statusDTO.setMessage("Unable to create talk! Try again");
            return statusDTO;
        } finally {
            em.close();
        }
    }

    public StatusDTO createSpeaker(CreateSpeakerDTO csd) {
        StatusDTO statusDTO = new StatusDTO();
        EntityManager em = emf.createEntityManager();
        Speaker speaker = new Speaker(csd.getName(), csd.getProfession(), csd.getGender(), csd.getCompany());
        Talk talk;
        Conference conference;
        try {
            em.getTransaction().begin();
            if (csd.getTalk_id() != 0 && csd.getConf_id() != 0) {
                talk = em.find(Talk.class, csd.getTalk_id());
                conference = em.find(Conference.class, csd.getConf_id());
                speaker.addTalk(talk);
                conference.addTalk(talk);
            }
            em.persist(speaker);
            em.getTransaction().commit();


            statusDTO.setError(false);
            statusDTO.setMessage("Speaker: " + speaker.getName() + " created!");
            return statusDTO;
        }catch (Exception e) {
            statusDTO.setError(true);
            statusDTO.setMessage("Unable to create speaker! Try again");
            return statusDTO;
        }
    }
    //TODO
//    public StatusDTO updateTalk (long talk_id) {
//
//        EntityManager em = emf.createEntityManager();
//        Talk talk;
//        try {
//            talk = em.find(Talk.class, talk_id);
//
//        }
//    }

    public List<SpeakerDTO> getSpeakersOnTalks(Talk talk) {
        List<SpeakerDTO> speakerDTOList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Speaker> tqs = em.createQuery("Select t.speakerList from Talk t where t.id = :talk_id", Speaker.class);
            tqs.setParameter("talk_id", talk.getId());
            List<Speaker> speakerList = new ArrayList<>();

            speakerList = tqs.getResultList();

            for (Speaker s : speakerList) {
                SpeakerDTO speakerDTO = new SpeakerDTO();
                speakerDTO.convertToDTO(s);
                speakerDTOList.add(speakerDTO);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
        return speakerDTOList;
    }


}
