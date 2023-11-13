package ru.simankovd.springredditclone.service;

import ru.simankovd.springredditclone.dto.SubredditDto;

import java.util.List;

public interface SubredditService {

    public SubredditDto save(SubredditDto dto);

    List<SubredditDto> getAll();

    SubredditDto getSubreddit(Long id);
}
