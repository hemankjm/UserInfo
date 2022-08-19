package petfriends.userInfo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;



@Entity
@DynamicUpdate
@Table(name="userInfo")
public class UserInfo {

    @Id
    @Column(nullable = false, unique = true)
    private String userId;
		@Column(nullable = false, length = 50)
    private String userName;
    private String telNo;
		private Double pintAmount;
		private Double useCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Timestamp loginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Timestamp logoutTime;

		@Column(nullable = false, unique = false)
    private String encryptedPwd;
		@Transient
    private String password;

		@Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public Double getPintAmount() {
		return this.pintAmount;
	}

	public void setPintAmount(Double pintAmount) {
		this.pintAmount = pintAmount;
	}

	public Double getUseCount() {
		return this.useCount;
	}

	public void setUseCount(Double useCount) {
		this.useCount = useCount;
	}


	public Timestamp getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public Timestamp getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Timestamp logoutTime) {
		this.logoutTime = logoutTime;
	}


	public String getEncryptedPwd() {
		return this.encryptedPwd;
	}

	public void setEncryptedPwd(String encryptedPwd) {
		this.encryptedPwd = encryptedPwd;
	}


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return this.userRole;
	}

	public void setRole(UserRole userRole) {
		this.userRole = userRole;
	}

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
