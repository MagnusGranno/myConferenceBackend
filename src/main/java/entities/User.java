package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
@Getter
@Setter
@NamedQuery(name = "User.deleteAllRows", query = "delete from User")
@NamedNativeQuery(name = "User.resetAutoIncrement", query = "ALTER TABLE users AUTO_INCREMENT = 1;")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_pass")
    private String userPass;
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})

    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public User() {
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String pw, String hashedPw) {
        return BCrypt.checkpw(pw, hashedPw);
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        String salt = BCrypt.gensalt();
        this.userPass = BCrypt.hashpw(userPass, salt);
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

}
