package ru.simankovd.springredditclone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simankovd.springredditclone.dto.AuthenticationResponse;
import ru.simankovd.springredditclone.dto.LoginRequest;
import ru.simankovd.springredditclone.dto.RefreshTokenRequest;
import ru.simankovd.springredditclone.dto.RegisterRequest;
import ru.simankovd.springredditclone.exception.SpringRedditException;
import ru.simankovd.springredditclone.model.User;
import ru.simankovd.springredditclone.model.VerificationToken;
import ru.simankovd.springredditclone.repository.UserRepository;
import ru.simankovd.springredditclone.repository.VerificationTokenRepository;
import ru.simankovd.springredditclone.security.JwtProvider;
import ru.simankovd.springredditclone.service.AuthService;
import ru.simankovd.springredditclone.service.MailService;
import ru.simankovd.springredditclone.service.RefreshTokenService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    @Override
    public void signUp(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .created(Instant.now())
                .enabled(true)
                .build();

        userRepository.save(user);

        String token = generateVerificationToken(user);
//        mailService.sendMail(new NotificationEmail( // todo add mailDev
//                "Please Activate your Account",
//                user.getEmail(),
//                "Thank you for signing up to Spring Reddit, " +
//                        "please click on the below url to activate your account : " +
//                        "http://localhost:8080/api/auth/accountVerification/" + token));

        System.out.println("http://localhost:8080/api/auth/accountVerification/" + token);
    }

    @Override
    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

//        return new AuthenticationToken(token, loginRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .username(loginRequest.getUsername())
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .build();
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("User not found with name " + username));
        user.setEnabled(true);
        userRepository.save(user);

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = VerificationToken
                .builder()
                .token(token)
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        // throw Exception if not valid
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtProvider.generateToken(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}
