package ru.simankovd.springredditclone.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simankovd.springredditclone.dto.SubredditDto;
import ru.simankovd.springredditclone.exception.SpringRedditException;
import ru.simankovd.springredditclone.mapper.SubredditMapper;
import ru.simankovd.springredditclone.model.Subreddit;
import ru.simankovd.springredditclone.repository.SubredditRepository;
import ru.simankovd.springredditclone.service.SubredditService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class SubredditServiceImpl implements SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Override
    @Transactional
    public SubredditDto save(SubredditDto dto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(dto));
        dto.setId(save.getId());

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubredditDto getSubreddit(Long id) {

        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with id = " + id));

        return subredditMapper.mapSubredditToDto(subreddit);
    }

//    private SubredditDto mapToDto(Subreddit subreddit) {
//        return SubredditDto.builder()
//                .name(subreddit.getName())
//                .id(subreddit.getId())
//                .numberOfPosts(subreddit.getPosts().size())
//                .build();
//    }
//
//    private Subreddit mapSubredditDto(SubredditDto dto) {
//        return Subreddit.builder()
//                .name(dto.getName())
//                .description(dto.getDescription())
//                .build();
//    }
}
