package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "talks")
@NamedQuery(name = "Talk.deleteAllRows", query = "delete from Talk")
@NamedNativeQuery(name = "Talk.resetAutoIncrement", query = "ALTER TABLE talks AUTO_INCREMENT = 1;")
public class Talk implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talk_id")
    private long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "talk_topic")
    private String topic;

    @Basic(optional = false)
    @NotNull
    @Column(name = "talk_duration")
    private int duration;

    @Basic(optional = true)
    @Column(name = "talk_props_list")
    private List<String> propsList;


    public Talk(String topic, int duration, List<String> propsList) {
        this.topic = topic;
        this.duration = duration;
        this.propsList = propsList;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPropsList(List<String> propsList) {
        this.propsList = propsList;
    }
}
