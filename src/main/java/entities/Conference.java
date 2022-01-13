package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "conferences")
@NamedQuery(name = "Conference.deleteAllRows", query = "delete from Conference")
@NamedNativeQuery(name = "Conference.resetAutoIncrement", query = "ALTER TABLE conferences AUTO_INCREMENT = 1;")
public class Conference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id")
    private long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "conference_name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "conference_location")
    private String location;

    @Basic(optional = false)
    @NotNull
    @Column(name = "conference_capacity")
    private int capacity;

    @Basic(optional = false)
    @NotNull
    @Column(name = "conference_date")
    private Date date;

    @Basic(optional = false)
    @NotNull
    @Column(name = "conference_time")
    private int time;

    public Conference(String name, String location, int capacity, Date date, int time) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.date = date;
        this.time = time;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
