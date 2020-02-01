package com.preliminary.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: InternalAccount
 *
 */
@Entity
@PersistenceContext(unitName="icPersistenceUnit")
@Table(name="icaccount")
@NamedQueries({
	@NamedQuery(name = "address.findUnique", query = "SELECT a FROM InternalAccount a WHERE a.email = :email"),
	@NamedQuery(name = "address.findByResourceID", query = "SELECT b FROM InternalAccount b WHERE b.resourceID = :resourceID"),
	@NamedQuery(name = "address.findAll", query = "SELECT c FROM InternalAccount c")
})
public class InternalAccount implements Serializable {

	/*
	 * Enter your information Email address * Remember that Eclipse community is
	 * open and transparent. Most of what you submit on our site including your
	 * email address and name will be visible to others who use our services. You
	 * may prefer to use an email account specifically for this purpose. First name
	 * * Last name * Organization Password * Confirm password * Password must be at
	 * least 12 characters long. Weak password will be marked as invalid. Country *
	 * Choose your country of residence.
	 * iAgree box - to the terms of the org
	 */

	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="icaccount_sequencer")
    @SequenceGenerator(name="icaccount_sequencer",
    				   initialValue=1,
                       sequenceName="icaccount_seq",
                       allocationSize=1) 
	@Column(name="id",updatable=true, nullable=false, unique=true) Long id;
	@Column(name="resourceID") String resourceID;
	@Column(name="email") String email;
	@Column(name="firstname") String firstname;
	@Column(name="lastname") String lastname;
	@Column(name="password") String password;
	@Column(name="organization") String organization;
	@Column(name="country") String country;
	@Column(name="iagree") String iagree;
	
	public InternalAccount() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResourceID() {
		return resourceID;
	}
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIagree() {
		return iagree;
	}
	public void setIagree(String iagree) {
		this.iagree = iagree;
	}
	
}