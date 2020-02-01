package com.preliminary.view;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@SessionScoped
@Named("pageProcessor")
public class PageProcessor implements Serializable {
	@Inject
	Logger log;
	// @Resource
	// private SessionContext ctx;
	@Inject
	UserWoodChuck woodchuck;
	
//	@Inject CacheManager cacheManager;
	
	
	private String currentPage;
	private String user = "Guest";
	private boolean poll = true;
	private boolean auth = true;
	private int maxInactiveInterval=(900);
	private String sessionId=null;

	final String PRE_LOGIN_URL = AcmeAuthenticator.class.getName()
			+ "_PRE_LOGIN_URL";
	// final ExternalContext externalContext =
	// FacesContext.getCurrentInstance().getExternalContext();
	// final Map<String, Object> sessionMap = externalContext.getSessionMap();
	// String redirectURL = (String) sessionMap.get(PRE_LOGIN_URL);
	String redirectURL = new String("FOO");

	public PageProcessor() { // No OP WOrks in this Constructor..
		// log.info("CONSTRUCTOR"); Uanble to initialize at startup???
	}
	public void currentPage() {
		this.setCurrentPage("who.xhtml");
		System.out.println("PageProcessor - CurrentPage no param");
	}
	public void currentPage(String s) {
		this.setCurrentPage(s+".xhtml");
		System.out.println("PageProcessor - CurrentPage with  param PARAM:"+s);
	}
	public void currentPage( ActionEvent event ) {
		String s=null;
		try {
			
			s = (String) event.getComponent().getAttributes().get("currentPage");
			System.out.println("PageProcessor -  actionlistener CurrentPage chekc using currentPage:"+s);
			this.setCurrentPage( (String) s+".xhtml" );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getCurrentPage() {
		
		if (currentPage == null) {
			this.setCurrentPage("MainPage.xhtml");
//			System.out.println("PageProcessor - initialized currentPage");
		}

		return this.currentPage;
	}

	public void setCurrentPage(String s) {

		try {
			this.currentPage = new String(s);
			PrimeFaces.current().ajax().update("MainForm");
			System.out.println("PageProcessor - setCurrentPage DONE  page:"+this.currentPage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("PageProcessor - setCurrentPage incoming value=" + s);
		// log.info("currentPage   AFTER SETTIN THE   param="+this.currentPage);
	}

	public boolean identityCheck() {
		boolean status = true;

		try {
			// this.getUserPrincipalName();
			// Principal prince = ctx.getCallerPrincipal();
			// log.info("THIS PRINCE="+ prince.getName());
			if (!(woodchuck.isValid())) {
//				System.out.println("PageProcessor USER is NOT loggerd in.");
				status = false;
				if ((this.currentPage.contains("Products"))
						|| (this.currentPage.contains("employee"))
						|| (this.currentPage.contains("employer"))
						|| (this.currentPage.contains("blogger"))
						|| (this.currentPage.contains("profile"))
						|| (this.currentPage.contains("Logout"))
						|| (this.currentPage.contains("----------"))
						|| (this.currentPage.contains("Support"))) {

					this.setCurrentPage("popupLogin.xhtml");
				}
			} else {
				System.out.println("PageProcessor USER is  loggerd in.");
				// System.out.println("PageProcessor USER NAME="+identity.getUser()
				// );
				this.setUser(woodchuck.getfName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;
	}
	
	public String getSessionId() {
		SecureRandom random = new SecureRandom();
		
		System.out.println("PageProcessor - get session");
		if ( this.sessionId == null) {
			
				System.out.println("PageProcessor - session is null");
				this.sessionId = new BigInteger(130, random).toString(32);
		}
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		System.out.println("PageProcessor - set session");
		this.sessionId = sessionId;
	}

	public String getUserPrincipalName() {
		FacesContext context = FacesContext.getCurrentInstance();
		Principal principal = context.getExternalContext().getUserPrincipal();
		if (principal == null) {
			System.out.println("PageProcessor - cant get Prince");
		}

		return principal.getName();
	}

	public void poll() {
		// log.info("Hello POLL" );
	}

	public boolean getPoll() {
		return poll;
	}

	public void setPoll(boolean pollEnabled) {
		if (poll) {
			// setPollStartTime(null);
			// log.info(" SET POLL message" );
		}
		this.poll = pollEnabled;
	}
	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
}
