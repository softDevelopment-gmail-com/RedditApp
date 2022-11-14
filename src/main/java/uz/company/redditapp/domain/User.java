package uz.company.redditapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE users set deleted='true' where id=?")
@EqualsAndHashCode
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

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    @BatchSize(size = 20)
    Set<Authority> authorities = new HashSet<>();

    @Column(name = "enabled")
    boolean enabled = false;


}
