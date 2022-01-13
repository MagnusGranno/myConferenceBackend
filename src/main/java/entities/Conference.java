package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.DateCreator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @Temporal(TemporalType.DATE)
    private Date date;

    @Basic(optional = false)
    @NotNull
    @Column(name = "conference_time")
    private int time;


    @OneToMany(mappedBy = "conference")
    private List<Talk> c_talkList = new ArrayList<>();




    public Conference(String name, String location, int capacity, int year, int month, int date, int time) {
        DateCreator dateCreator = new DateCreator();
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.date = dateCreator.calcDate(year, month, date);
        System.out.println(this.date);
        this.time = time;
    }



//    public Date formatDate (date) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        return dateFormat.parse(dateFormat.format(date));
//    }


    public void addTalk (Talk talk) {
        talk.setConference(this);
        c_talkList.add(talk);
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
