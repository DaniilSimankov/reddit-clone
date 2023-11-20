package ru.simankovd.springredditclone.service;

import ru.simankovd.springredditclone.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);
}
