package DTO;

import entities.Talk;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TalkDTO {

    private long id;
    private String topic;
    private int duration;
    private String props_list;
    private List<SpeakerDTO> speaker_list;



    public void convertToDTO(Talk talk) {
        this.id = talk.getId();
        this.topic = talk.getTopic();
        this.duration = talk.getDuration();
        this.props_list = talk.getPropsList();
    }
}
