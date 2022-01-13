package entities;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "harbours")
@Getter
@NamedQuery(name = "Harbour.deleteAllRows", query = "delete from Harbour")
@NamedNativeQuery(name = "Harbour.resetAutoIncrement", query = "ALTER TABLE harbours AUTO_INCREMENT = 1;")
public class Harbour implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "harbour_id")
    private long id;

    @Basic(optional = false)
    @Column(name = "harbour_name")
    private String name;

    @Basic(optional = false)
    @Column(name = "harbour_address")
    private String address;

    @Basic(optional = false)
    @Column(name = "harbour_capacity")
    private int capacity;

    @JoinTable(name = "harbour_boats", joinColumns = {
            @JoinColumn(name = "harbour_id", referencedColumnName = "harbour_id")}, inverseJoinColumns = {
            @JoinColumn(name = "boat_id", referencedColumnName = "boat_id")})
    @OneToMany
    private List<Boat> boatList = new ArrayList<>();


    public Harbour() {
    }

    public Harbour(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }


    public void addBoat(Boat boat) {
        this.boatList.add(boat);
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
