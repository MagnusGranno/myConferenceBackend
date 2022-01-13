package DTO;

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
}
