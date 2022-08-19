package petfriends.userInfo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserInfoRepository;

@Service
public class UserInfoService {

	 @Autowired
	 UserInfoRepository userInfoRepository;


	 public List<UserInfo> findAllByUserId(Long userId) {
		 return userInfoRepository.findAllByUserId(userId);
	 }

	 public UserInfo findById(Long id) {

		 if(userInfoRepository.findById(id).isPresent()) {
			 Optional<UserInfo> user = userInfoRepository.findById(id);
			 return user.get();
		 }
		 return null;
	 }


}

