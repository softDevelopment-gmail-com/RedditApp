package uz.company.redditapp.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDetailDTO {
    Long id;

    String postName;

    String subredditName;

    String url;

    String description;
}
