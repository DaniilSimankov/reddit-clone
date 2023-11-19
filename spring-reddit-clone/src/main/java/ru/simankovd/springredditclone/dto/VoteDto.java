package ru.simankovd.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simankovd.springredditclone.model.enums.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
