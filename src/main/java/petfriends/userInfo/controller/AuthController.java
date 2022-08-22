package petfriends.userInfo.controller;




import petfriends.userInfo.dto.TokenDto;
import petfriends.userInfo.dto.TokenRequestDto;
import petfriends.userInfo.dto.UserInfoRequestDto;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponseDto> signup(@RequestBody UserInfoRequestDto userInfoRequestDto) {
        return ResponseEntity.ok(authService.signup(userInfoRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserInfoRequestDto userInfoRequestDto) {
        return ResponseEntity.ok(authService.login(userInfoRequestDto));
    }

    // @PostMapping("/reissue")
    // public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
    //     return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    // }
}
