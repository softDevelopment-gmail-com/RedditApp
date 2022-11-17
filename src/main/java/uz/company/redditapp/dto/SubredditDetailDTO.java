package uz.company.redditapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDetailDTO {
    Long id;
    String name;
    String description;
    Integer numberOfPosts;
}
