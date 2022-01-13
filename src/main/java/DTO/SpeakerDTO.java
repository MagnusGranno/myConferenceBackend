package DTO;

import entities.Speaker;
import entities.Talk;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeakerDTO {


    private long id;
    private String name;
    private String profession;
    private String company;
    private String gender;


    public void convertToDTO(Speaker speaker) {
        this.id = speaker.getId();
        this.name = speaker.getName();
        this.profession = speaker.getProfession();
        this.company = speaker.getCompany();
        this.gender = speaker.getGender();
    }

}
