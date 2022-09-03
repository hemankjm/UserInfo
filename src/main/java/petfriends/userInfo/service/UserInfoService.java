package petfriends.userInfo.service;

import petfriends.config.SecurityUtil;
import petfriends.userInfo.dto.UserInfoDto;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.model.UserImage;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserImageRepository;
import petfriends.userInfo.repository.UserInfoRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.RequiredArgsConstructor;
// import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

		private final UserInfoRepository userInfoRepository;
		private final UserImageRepository userImageRepository;
		// @Autowired
	  // UserInfoRepository userInfoRepository;

		// @Autowired
		// UserImageRepository userImageRepository;

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

    /**
		 * 사용자 전체 조회
		 * @param userId
		 * @return
		 */
		public UserInfo getMyUserInfo(String userId) {
				Optional<UserInfo> userInfo = userInfoRepository.findByUserId(userId);
				return  userInfo.get();
		}

		/**
		 * 사용자 이미지 저장
		 * @param request
		 * @return
		 * @throws IOException
		 */
		public Long uploadUserImage(HttpServletRequest request) throws IOException{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("file");
				String userId = multipartRequest.getParameter("user_id");

				// System.out.println("file name ======= " + file.getOriginalFilename());
				// System.out.println("userid ======= " + multipartRequest.getParameter("user_id"));

				LocalDateTime current = LocalDateTime.now();

				UserImage userImage = new UserImage();
				userImage.setMimeType(file.getContentType());
				userImage.setOriginalName(file.getOriginalFilename());
				userImage.setUserImage(file.getBytes());
				userImage.setUserId(userId);
				userImage.setCreateDate(java.sql.Timestamp.valueOf(current));

				UserImage saveUuserImg =  userImageRepository.save(userImage);
				Long imageId = saveUuserImg.getId();

				Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(userId);
				UserInfo userInfo = userInfoOptional.get();
				userInfo.setImageId(imageId);
				userInfoRepository.save(userInfo);

				return imageId;
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




