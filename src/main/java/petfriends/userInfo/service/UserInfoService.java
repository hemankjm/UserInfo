package petfriends.userInfo.service;

import petfriends.config.SecurityUtil;
import petfriends.userInfo.dto.UserInfoDto;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserInfoRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
// import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

		private final UserInfoRepository userInfoRepository;
		// @Autowired
	  // UserInfoRepository userInfoRepository;


		// 현재 SecurityContext 에 있는 유저 정보 가져오기
		@Transactional /* (readOnly = true) */
		public UserInfoResponseDto getMyInfo() {
				return userInfoRepository.findByUserId(SecurityUtil.getCurrentMemberId())
								.map(UserInfoResponseDto::of)
								.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
		}


		@Transactional /* (readOnly = true) */
		public UserInfoResponseDto getUserInfo(String userId) {
				return userInfoRepository.findByUserId(userId)
								.map(UserInfoResponseDto::of)
								.orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
		}

    //사용자 전체 정보 반환
		public UserInfo getMyUserInfo(String userId) {
				Optional<UserInfo> userInfo = userInfoRepository.findByUserId(userId);
				return  userInfo.get();
		}

}





	//  @Autowired
	//  UserInfoRepository userInfoRepository;

	//  public List<UserInfo> findAllByUserId(Long userId) {
	// 	 return userInfoRepository.findAllByUserId(userId);
	//  }

	//  public UserInfo findById(Long id) {

	// 	 if(userInfoRepository.findById(id).isPresent()) {
	// 		 Optional<UserInfo> user = userInfoRepository.findById(id);
	// 		 return user.get();
	// 	 }
	// 	 return null;
	//  }




