package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTalkDTO {

    private String Topic;
    private int duration;
    private String props_list;
    private long conf_id;
    private long speaker_id;
}
