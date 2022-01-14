package DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateConferenceDTO {

    private String name;
    private String location;
    private int capacity;
    private int year;
    private int date;
    private int month;
    private String time;
    private long talk_id;
    private long speaker_id;
}
