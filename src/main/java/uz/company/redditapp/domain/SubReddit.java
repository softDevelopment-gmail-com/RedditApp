package uz.company.redditapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class SubReddit extends AbstractAuditingEntity {

    @NotBlank(message = "Community name is required")
    String name;

    @NotBlank
    String description;

    @OneToMany(fetch = FetchType.LAZY)
    List<Post> posts;

    @Column(name = "user_id")
    Long userId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    User user;

}
