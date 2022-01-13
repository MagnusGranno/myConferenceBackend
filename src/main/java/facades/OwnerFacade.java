package facades;

import DTO.OwnerDTO;
import DTO.StatusDTO;
import entities.Owner;
import lombok.NoArgsConstructor;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class OwnerFacade {
    private static EntityManagerFactory emf;
    private static OwnerFacade instance;


    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static OwnerFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }



    public StatusDTO createStatusDTO (boolean error, String message) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setMessage(message);
        statusDTO.setError(error);
        return statusDTO;
    }

    public List<OwnerDTO> getAllOwners() {

        List<OwnerDTO> ownerDTOList = new ArrayList<>();
        List<Owner> ownerList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            TypedQuery<Owner> tq = em.createQuery("SELECT o FROM Owner o", Owner.class);
            ownerList = tq.getResultList();

        } finally {
            em.close();
        }

        for (Owner o : ownerList) {
            OwnerDTO ownerDTO = new OwnerDTO();
            ownerDTO.setId(o.getId());
            ownerDTO.setName(o.getOwnerName());
            ownerDTO.setAddress(o.getAddress());
            ownerDTO.setPhone(o.getPhone());
            ownerDTOList.add(ownerDTO);
        }

        return ownerDTOList;
    }

}
