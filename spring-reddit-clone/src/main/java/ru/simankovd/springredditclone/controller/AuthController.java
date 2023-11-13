package ru.simankovd.springredditclone.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simankovd.springredditclone.dto.AuthenticationToken;
import ru.simankovd.springredditclone.dto.LoginRequest;
import ru.simankovd.springredditclone.dto.RegisterRequest;
import ru.simankovd.springredditclone.service.AuthService;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {

        authService.signUp(registerRequest);

        return new ResponseEntity<>(
                "User registration Succefull",
                HttpStatus.OK
        );
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);

        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationToken login(@RequestBody LoginRequest loginRequest){

        return authService.login(loginRequest);
    }
}
