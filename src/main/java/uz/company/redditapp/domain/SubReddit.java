package uz.company.redditapp.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.company.redditapp.dto.SubredditDTO;
import uz.company.redditapp.dto.SubredditDetailDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public SubredditDetailDTO toDetailDTO(SubReddit subReddit) {
        SubredditDetailDTO subredditListDTO = new SubredditDetailDTO();
        subredditListDTO.setId(subReddit.getId());
        subredditListDTO.setName(subReddit.getName());
        subredditListDTO.setDescription(subReddit.getDescription());
        subredditListDTO.setNumberOfPosts(subReddit.getPosts().size());
        return subredditListDTO;
    }


    public SubReddit toEntity(SubredditDTO subredditDTO) {
        return SubReddit.builder()
                .name(subredditDTO.getName())
                .description(subredditDTO.getDescription())
                .build();
    }

}
