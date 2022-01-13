package DTO;

import entities.Conference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class ConferenceDTO {

    private long id;
    private String name;
    private String location;
    private int capacity;
    private Date date;
    private String time;

    public void convertToDTO(Conference conference) {
        this.id = conference.getId();
        this.name = conference.getName();
        this.location = conference.getLocation();
        this.capacity = conference.getCapacity();
        this.date = conference.getDate();
        this.time = conference.getTime();
    }
}
