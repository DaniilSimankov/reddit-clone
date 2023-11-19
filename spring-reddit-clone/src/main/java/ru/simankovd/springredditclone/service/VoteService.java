package ru.simankovd.springredditclone.service;

import ru.simankovd.springredditclone.dto.VoteDto;

public interface VoteService {
    void vote(VoteDto voteDto);
}
