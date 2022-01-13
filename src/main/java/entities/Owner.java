package entities;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@NamedQuery(name = "Owner.deleteAllRows", query = "delete from Owner")
@NamedNativeQuery(name = "Owner.resetAutoIncrement", query = "ALTER TABLE Owners AUTO_INCREMENT = 1;")
public class Owner implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private long id;

    @Column(name = "owner_name")
    @Basic(optional = false)
    @NotNull
    private String ownerName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "owner_address")
    private String address;

    @Basic(optional = false)
    @NotNull
    @Column(name = "owner_phone")
    private long phone;

    @JoinTable(name = "owner_boats", joinColumns = {
            @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")}, inverseJoinColumns = {
            @JoinColumn(name = "boat_id", referencedColumnName = "boat_id")})
    @OneToMany()
    private List<Boat> boatList = new ArrayList<>();

    public Owner() {
    }

    public Owner(String ownerName, String address, long phone) {
        this.ownerName = ownerName;
        this.address = address;
        this.phone = phone;
    }


    public void setBoatList(List<Boat> boatList) {
        this.boatList = boatList;
    }

    public void addBoat(Boat boat) {
        this.boatList.add(boat);
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setPhone(long phone) {
        this.phone = phone;
    }
}
