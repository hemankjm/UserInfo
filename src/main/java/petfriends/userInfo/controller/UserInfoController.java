package petfriends.userInfo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.service.UserInfoService;


 @RestController
 @RequestMapping("/")
 public class UserInfoController {

	 @Autowired
	 UserInfoService userInfoService;


	 @GetMapping("/userInfos/{userId}")
	 public List<UserInfo> findUserInfoByUserId(@PathVariable("userId") Long userId) {
		 return userInfoService.findAllByUserId(userId);
	 }


 }

