package ru.simankovd.springredditclone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simankovd.springredditclone.exception.SpringRedditException;
import ru.simankovd.springredditclone.model.RefreshToken;
import ru.simankovd.springredditclone.repository.RefreshTokeRepository;
import ru.simankovd.springredditclone.service.RefreshTokenService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokeRepository refreshTokeRepository;

    @Override
    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokeRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String token){
        refreshTokeRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
    }

    @Override
    public void deleteRefreshToken(String token){
        //if token is not in DB, Spring throw Exception
        refreshTokeRepository.deleteByToken(token);
    }
}
