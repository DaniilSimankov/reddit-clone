package ru.simankovd.springredditclone.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.simankovd.springredditclone.exception.SpringRedditException;
import ru.simankovd.springredditclone.service.CommentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommentServiceImplTest {

    @Test
    @DisplayName("Test Should Pass When Comment do not Contains Swear Words")
    void shouldNotContainBadWordsInsideComment() {
        CommentService commentService = new CommentServiceImpl(null, null, null, null, null, null, null);
        Assertions.assertFalse(commentService.containsBadWords("This is a clean comment"));
    }

    @Test
    @DisplayName("Should Throw Exception when Exception Contains Swear Words")
    void shouldFailWhenCommentContainsBadWords() {
        CommentService commentService = new CommentServiceImpl(null, null, null, null, null, null, null);

        assertThatThrownBy(() -> {
            commentService.containsBadWords("This is a shit comment");
        }).isInstanceOf(SpringRedditException.class)
                .hasMessage("Comments contains unacceptable language");
    }
}