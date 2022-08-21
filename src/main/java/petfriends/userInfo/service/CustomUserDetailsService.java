package petfriends.userInfo.service;

import lombok.RequiredArgsConstructor;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserInfoRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInfoRepository.findByUserId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(UserInfo userInfo) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userInfo.getUserRole().toString());

        return new User(
                String.valueOf(userInfo.getUserId()),
                userInfo.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}