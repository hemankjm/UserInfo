package petfriends.userInfo.model;

import java.sql.Timestamp;



import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import lombok.*;


@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name="userInfo")
public class UserInfo {

    @Id
    @Column(nullable = false, unique = true)
    private String userId;
		private String password;
		// @Column(nullable = false, length = 50)
    // private String userName;
    // private String telNo;
		// private Double pintAmount;
		// private Double useCount;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    // private Timestamp loginTime;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    // private Timestamp logoutTime;

		// @Column(nullable = false, unique = false)
    // private String encryptedPwd;
		// @Transient
    // private String password;

		@Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


	@Builder
	public UserInfo(String userId, String password, UserRole userRole) {
			this.userId = userId;
			this.password = password;
			this.userRole = userRole;
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
