package petfriends.userInfo.dto;

import petfriends.userInfo.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {
    private String userId;

      public static UserInfoResponseDto of(UserInfo userInfo) {
          return new UserInfoResponseDto(userInfo.getUserId());
      }
}
