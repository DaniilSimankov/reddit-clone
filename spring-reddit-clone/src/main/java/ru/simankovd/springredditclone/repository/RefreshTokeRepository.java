package ru.simankovd.springredditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simankovd.springredditclone.model.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokeRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
