package petfriends.userInfo.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import petfriends.userInfo.dto.UserInfoDto;
import petfriends.userInfo.dto.UserInfoRequestDto;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.model.UserImage;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserImageRepository;
import petfriends.userInfo.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("/userInfos")
 public class UserInfoController {

		private final UserInfoService userInfoService;

		@Autowired
		UserImageRepository userImageRepository;

		@GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> getMyMemberInfo() {
        return ResponseEntity.ok(userInfoService.getMyInfo());
    }

		@GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getMyUserInfo(@PathVariable String userId) {

			UserInfo userInfo = userInfoService.getMyUserInfo(userId);

			if(userInfo != null){
				return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
			}
			//내용 없을 때
			return  new ResponseEntity<UserInfo>(userInfo, HttpStatus.NO_CONTENT);

    }


		@PostMapping("/image/upload")
		public Long handleFileUpload(HttpServletRequest request) throws IOException {

			  MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("file");

				// System.out.println("file name ======= " + file.getOriginalFilename());
				// System.out.println("userid ======= " + multipartRequest.getParameter("user_id"));

			  LocalDateTime current = LocalDateTime.now();

				UserImage userImage = new UserImage();
				userImage.setMimeType(file.getContentType());
				userImage.setOriginalName(file.getOriginalFilename());
				userImage.setUserImage(file.getBytes());
				userImage.setCreateDate(java.sql.Timestamp.valueOf(current));

				UserImage saveUuserImg =  userImageRepository.save(userImage);

				return saveUuserImg.getId();
		}


		@GetMapping("/image/{id}")
		public ResponseEntity<byte[]> findOne(@PathVariable Long id) {
					Optional<UserImage> user = userImageRepository.findById(id);

					if(user.isPresent()) {

						UserImage userImage = user.get();
						HttpHeaders headers = new HttpHeaders();
							headers.add("Content-Type", userImage.getMimeType());
							headers.add("Content-Length", String.valueOf(userImage.getUserImage().length));
						return new ResponseEntity<byte[]>(userImage.getUserImage(), headers, HttpStatus.OK);
					}

				return null;

		}


    @GetMapping("/check/{userId}")
    public ResponseEntity<UserInfoResponseDto> getMemberInfo(@PathVariable String userId) {
        return ResponseEntity.ok(userInfoService.getUserInfo(userId));
    }


	//  @GetMapping("/me")
	//  public List<UserInfo> findUserInfoByUserId(@PathVariable("userId") Long userId) {
	// 	 return userInfoService.findAllByUserId(userId);
	//  }

	//  @GetMapping("/{userId}")
	//  public List<UserInfo> findUserInfoByUserId(@PathVariable("userId") Long userId) {
	// 	 return userInfoService.findAllByUserId(userId);
	//  }

 }

