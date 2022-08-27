package petfriends.userInfo.controller;

import java.util.List;

import petfriends.userInfo.dto.UserInfoDto;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("/userInfo")
 public class UserInfoController {

		private final UserInfoService userInfoService;


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


    // @GetMapping("/{userId}")
    // public ResponseEntity<UserInfoResponseDto> getMemberInfo(@PathVariable String userId) {
    //     return ResponseEntity.ok(userInfoService.getUserInfo(userId));
    // }


	//  @GetMapping("/me")
	//  public List<UserInfo> findUserInfoByUserId(@PathVariable("userId") Long userId) {
	// 	 return userInfoService.findAllByUserId(userId);
	//  }

	//  @GetMapping("/{userId}")
	//  public List<UserInfo> findUserInfoByUserId(@PathVariable("userId") Long userId) {
	// 	 return userInfoService.findAllByUserId(userId);
	//  }

 }

