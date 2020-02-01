package com.preliminary.view;

import java.io.Serializable;
import javax.inject.Named;

@Named
public class UserWoodChuck implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uid;
	private String pword;
	private String user;
	private String fName;
	private String lName;
	private String email;
	private boolean isValid;

	
	public UserWoodChuck() {}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPword() {
		return pword;
	}

	public void setPword(String pword) {
		this.pword = pword;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void isLoggedIn(Boolean isValid) {
		// TODO Auto-generated method stub
		this.isValid = isValid; 
		
	}
	public boolean isValid() {
		return this.isValid;
	}
}