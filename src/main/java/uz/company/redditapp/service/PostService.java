package uz.company.redditapp.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.company.redditapp.domain.Post;
import uz.company.redditapp.dto.PostDTO;
import uz.company.redditapp.dto.PostDetailDTO;
import uz.company.redditapp.errors.NotFoundException;
import uz.company.redditapp.repository.PostRepository;
import uz.company.redditapp.security.UserAuth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PostService {

    PostRepository postRepository;


    public PostDTO create(PostDTO postDTO) {
        Long userId = getCurrentUserId();
        postRepository.save(Post
                .builder()
                .postName(postDTO.getPostName())
                .description(postDTO.getDescription())
                .url(postDTO.getUrl())
                .voteCount(0)
                .userId(userId)
                .build());
        return postDTO;
    }

    public PostDetailDTO get(Long id) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(
                        () -> new NotFoundException(Post.class.getSimpleName(), "id")
                );
        PostDetailDTO postDetailDTO = new PostDetailDTO();
        postDetailDTO.setPostName(post.getPostName());
        postDetailDTO.setId(id);
        postDetailDTO.setDescription(post.getDescription());
        postDetailDTO.setUrl(post.getUrl());
        return postDetailDTO;
    }

    public List<PostDetailDTO> getAll() {
        return postRepository.findAll().stream()
                .map(this::getPostDetailDTO).collect(Collectors.toList());
    }

    public List<PostDetailDTO> getByUser(Long userId) {
        return postRepository.findAll().stream()
                .map(post -> {
                    if (userId.equals(getCurrentUserId())) {
                        return getPostDetailDTO(post);
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    public List<PostDetailDTO> getBySubreddit(Long subredditId) {
        return postRepository.findAll().stream()
                .map(post -> {
                    if (post.getSubredditId().equals(subredditId)) {
                        return getPostDetailDTO(post);
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    private PostDetailDTO getPostDetailDTO(Post post) {
        PostDetailDTO postDetailDTO = new PostDetailDTO();
        postDetailDTO.setId(post.getId());
        postDetailDTO.setPostName(post.getPostName());
        postDetailDTO.setDescription(post.getDescription());
        postDetailDTO.setUrl(post.getUrl());
        postDetailDTO.setSubredditName(post.getSubReddit().getName());
        return postDetailDTO;
    }

    public Long getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(
                        securityContext.getAuthentication())
                .map(authentication ->
                {
                    if (authentication.getPrincipal() instanceof UserAuth) {
                        UserAuth userAuth = (UserAuth) authentication.getPrincipal();
                        return userAuth.getUserId();
                    } else {
                        return null;
                    }
                })
                .orElse(null);
    }
}
