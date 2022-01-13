package entities;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boats")
@Getter
@NamedQuery(name = "Boat.deleteAllRows", query = "delete from Boat")
@NamedNativeQuery(name = "Boat.resetAutoIncrement", query = "ALTER TABLE boats AUTO_INCREMENT = 1;")
public class Boat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_id")
    private long id;


    @Basic(optional = false)
    @NotNull
    @Column(name = "boat_make")
    private String make;

    @Basic(optional = false)
    @NotNull
    @Column(name = "boat_name")
    private String name;

    @Basic(optional = true)
    @Column(name = "boat_image")
    private String image;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "harbour_id")
    private Harbour harbour;

    public Boat() {
    }

    public Boat(String make, String name, String image) {

        this.make = make;
        this.name = name;
        this.image = image;
    }


    public void setHarbour(Harbour harbour) {
        harbour.addBoat(this);
        this.harbour = harbour;
    }


    public void setMake(String make) {
        this.make = make;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setOwner(Owner owner) {
        owner.addBoat(this);
        this.owner = owner;
    }
}
