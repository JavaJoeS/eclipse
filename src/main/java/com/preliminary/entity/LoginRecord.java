package com.preliminary.entity;


import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity 
@NamedQuery (
	name="gimmeUser",
	query="SELECT l FROM LoginRecord l WHERE l.userId = :userName"
)
@Table(name = "LoginRecord")
public class LoginRecord implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="login_id") 
	long loginId; 
	
	@Column (name="userId") String userId;	
	@Column (name="password")  String password;
	
	public LoginRecord() {}

	//@UiHidden
	public long getLoginId() {
		return loginId;
	}

	public void setLoginId(long loginId) {
		this.loginId = loginId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//@UiComesAfter("userId")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
