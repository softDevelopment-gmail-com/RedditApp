package uz.company.redditapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE users set deleted='true' where id=?")
public class User extends AbstractAuditingEntity {

    @Column(name = "username")
    @NotBlank(message = "Username is required")
    String username;

    @Column(name = "password")
    @NotBlank(message = "Password is required")
    String password;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "Email is required")
    String email;

    @Column(name = "enabled")
    boolean enabled = false;


}
