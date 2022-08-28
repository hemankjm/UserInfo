package petfriends.userInfo.model;

import java.sql.Timestamp;



import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import lombok.*;


// @Getter
@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
@Table(name="userInfo")
public class UserInfo {

    @Id
    @Column(nullable = false, unique = true)
    private String userId;
		private String password;
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

		@Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


	@Builder
	public UserInfo(String userId, String password, UserRole userRole, String userNm, String telNo) {
			this.userId = userId;
			this.password = password;
			this.userRole = userRole;
      this.userNm   = userNm;
      this.telNo    = telNo;
	}


  @PrePersist
  public void prePersist(){
      this.pointAmount = this.pointAmount == null ? (double)0 : this.pointAmount;
      this.useCount = this.useCount == null ? (double)0 : this.useCount;
      this.career = this.career == null ? (double)0 : this.career;
      this.avgScore = this.avgScore == null ? (double)0 : this.avgScore;
      this.walkCount = this.walkCount == null ? (double)0 : this.walkCount;
  }

  // @PrePersist
  //   public void time() {
  //       this.time = LocalDateTime.now();
  //   }

	// @PostPersist
  //   public void onPostPersist(){
  //       Payed payed = new Payed();
  //       BeanUtils.copyProperties(this, payed);
  //       payed.publishAfterCommit();

  //       try {
  //               Thread.currentThread().sleep((long) (400 + Math.random() * 220));
  //       } catch (InterruptedException e) {
  //               e.printStackTrace();
  //       }
  //   }

  //   @PostUpdate
  //   public void onPostUpdate(){
  //       Refunded refunded = new Refunded();
  //       BeanUtils.copyProperties(this, refunded);
  //       refunded.publishAfterCommit();

  //   }





}
