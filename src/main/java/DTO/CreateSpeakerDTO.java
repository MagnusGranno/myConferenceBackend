package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSpeakerDTO {

    private String name;
    private String company;
    private String profession;
    private String gender;
    private long talk_id;
    private long conf_id;
}
