package petfriends.userInfo.repository;

import petfriends.userInfo.model.UserInfo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

    Optional<UserInfo> findByUserId(String userId);
    boolean existsByUserId(String userId);
    // List<UserInfo> findAllByUserId(String userId);

}