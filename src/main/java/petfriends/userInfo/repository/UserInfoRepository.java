package petfriends.userInfo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import petfriends.userInfo.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

    List<UserInfo> findAllByUserId(Long userId);

    Optional<UserInfo> findById(Long id);

}