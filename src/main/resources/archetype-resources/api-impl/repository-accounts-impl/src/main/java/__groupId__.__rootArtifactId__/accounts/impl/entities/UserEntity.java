#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.impl.entities;

import ${groupId}.${rootArtifactId}.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author vedad
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "username")
    private String username; // Treat username as id

    @Column(name = "email")
    private String email;

    @Column(name = "hashed_pw")
    private String hashedPw;

    @Column(name = "salt")
    private String salt;

    public User toDomainModel() {
        return new User(this.id.toString(), this.email, this.username);
    }
}
