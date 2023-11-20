package ru.simankovd.springredditclone.service;


import ru.simankovd.springredditclone.dto.AuthenticationResponse;
import ru.simankovd.springredditclone.dto.LoginRequest;
import ru.simankovd.springredditclone.dto.RefreshTokenRequest;
import ru.simankovd.springredditclone.dto.RegisterRequest;
import ru.simankovd.springredditclone.model.User;

public interface AuthService {

    void signUp(RegisterRequest request);

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest);

    boolean isLoggedIn();

    User getCurrentUser();

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
