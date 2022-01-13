package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TalkDTO {

    private long id;
    private String topic;
    private int duration;
    private String props_list;
}
