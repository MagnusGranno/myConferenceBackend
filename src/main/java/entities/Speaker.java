package entities;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "speakers")
@NamedQuery(name = "Speaker.deleteAllRows", query = "delete from Speaker")
@NamedNativeQuery(name = "Speaker.resetAutoIncrement", query = "ALTER TABLE speakers AUTO_INCREMENT = 1;")
public class Speaker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speaker_id")
    private long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "speaker_name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "speaker_profession")
    private String profession;

    @Basic(optional = false)
    @NotNull
    @Column(name = "speaker_gender")
    private String gender;


    public Speaker(String name, String profession, String gender) {
        this.name = name;
        this.profession = profession;
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
