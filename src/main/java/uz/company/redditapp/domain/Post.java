package uz.company.redditapp.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends AbstractAuditingEntity {

    @Column(name = "post_name")
    @NotBlank(message = "Post name cannot be empty")
    String postName;

    @Nullable
    String url;

    @Nullable
    @Lob
    String description;

    Integer voteCount;

    @Column(name = "user_id")
    Long userId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;

    @Column(name = "subreddit_id")
    Long subredditId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_id", insertable = false, updatable = false)
    SubReddit subReddit;

}
