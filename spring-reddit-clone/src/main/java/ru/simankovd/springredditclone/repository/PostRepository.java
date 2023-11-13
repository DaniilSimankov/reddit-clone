package ru.simankovd.springredditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simankovd.springredditclone.dto.PostResponse;
import ru.simankovd.springredditclone.model.Post;
import ru.simankovd.springredditclone.model.Subreddit;
import ru.simankovd.springredditclone.model.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
