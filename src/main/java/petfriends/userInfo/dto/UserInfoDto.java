package petfriends.userInfo.dto;

import petfriends.AbstractEvent;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDto extends AbstractEvent{

  private String userId;
  // @Column(nullable = false, length = 50)
  private String userNm;
  private String telNo;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
  private Timestamp loginTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
  private Timestamp logoutTime;
  private Double pointAmount;
  private Double useCount;
  private Double career;
  private Double avgScore;
  private Double walkCount;

}
