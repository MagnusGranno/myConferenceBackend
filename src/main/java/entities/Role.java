package entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Plaul
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NamedQuery(name = "Role.deleteAllRows", query = "delete from Role")
@NamedNativeQuery(name = "Role.resetAutoIncrement", query = "ALTER TABLE roles AUTO_INCREMENT = 1;")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "role_name", length = 20)
    private String roleName;

    @ManyToMany(mappedBy = "roleList")
    private List<User> userList;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

}
