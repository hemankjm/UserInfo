package petfriends.userInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import petfriends.userInfo.model.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long>{

}