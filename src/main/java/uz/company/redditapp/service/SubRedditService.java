package uz.company.redditapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.company.redditapp.domain.SubReddit;
import uz.company.redditapp.dto.SubredditDTO;
import uz.company.redditapp.dto.SubredditDetailDTO;
import uz.company.redditapp.errors.NotFoundException;
import uz.company.redditapp.repository.SubRedditRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class SubRedditService {

    private final SubRedditRepository subRedditRepository;

    public SubredditDTO save(SubredditDTO subredditDTO) {
        SubReddit subReddit = new SubReddit();
        subRedditRepository.save(subReddit.toEntity(subredditDTO));
        return subredditDTO;
    }


    @Transactional(readOnly = true)
    public List<SubredditDetailDTO> getAll() {
        return subRedditRepository.findAll()
                .stream()
                .map(subReddit -> subReddit.toDetailDTO(subReddit))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDetailDTO get(Long id) {
        SubReddit subReddit = subRedditRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new NotFoundException(SubredditDTO.class.getSimpleName(), "id")
        );
        SubredditDetailDTO subredditDetailDTO = new SubredditDetailDTO();
        subredditDetailDTO.setDescription(subReddit.getDescription());
        subredditDetailDTO.setName(subReddit.getName());
        subredditDetailDTO.setNumberOfPosts(subReddit.getPosts().size());
        subredditDetailDTO.setId(subReddit.getId());
        return subredditDetailDTO;
    }
}
