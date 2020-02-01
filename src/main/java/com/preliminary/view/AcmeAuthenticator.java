package com.preliminary.view;

import java.io.IOException;
import java.io.Serializable;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permission;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.resource.spi.security.PasswordCredential;
import javax.resource.spi.work.SecurityContext;
import javax.security.auth.PrivateCredentialPermission;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.preliminary.entity.LoginRecord;
import com.preliminary.view.PageProcessor;
import com.preliminary.view.UserWoodChuck;

@SessionScoped
@Named("acmeAuthenticator")
public class AcmeAuthenticator implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	Logger log;
	@Inject
	PageProcessor page;
	
	@PersistenceUnit(name = "icPersistenceUnit")
	private EntityManagerFactory emf1;
	private EntityManager entityManager;
	
	PasswordCredential passwordCredential;
	PasswordCredential dbID = null;
	UserWoodChuck woodchuck = null;
	FacesMessage facesMsg = null;
	FacesContext fc = null;
	String isUserLoggedIn = new String("log in");
	String username;
	String password;
	String logout = new String("logout");
	String newPage = new String("popupLogin.xhtml");

	public AcmeAuthenticator() {}

	public void authenticate(ActionEvent ev) {
		System.out.println("acmeauthenticate LOGIN called , currentPAGE:" + page.getCurrentPage());
		newPage = page.getCurrentPage();
		String msg = " You ARE not Logged in!";
		String query = new String("gimmeUser");

		log.info("EVENT:" + ev.getComponent().getParent().toString());
		log.info("    USERNAME:" + findValue(ev, "j_username"));

		
		woodchuck.setUser(findValue(ev, "j_username"));
		woodchuck.setPword(findValue(ev, "j_password"));
		passwordCredential = new PasswordCredential(woodchuck.getUser(),
				woodchuck.getPword().toCharArray());

		log.info("AUTH RUNNING TO SEE WHO U ARE");
		

		try {
			entityManager = emf1.createEntityManager();

			// System.out.println("AcmeAuth - em
			// result="+entityManager.getEntityManagerFactory().isOpen());

			TypedQuery q = entityManager.createNamedQuery(query, LoginRecord.class);
			q.setParameter("userName", woodchuck.getUser());
			try {
				LoginRecord lr = (LoginRecord) q.getSingleResult();

				// LoginRecord lr = entityManager.find( LoginRecord.class, ci.getUsername());
				log.info("QUERY IS COMPLETE ---------------");
				if (lr.getPassword().contentEquals(woodchuck.getPword())) {
					
					if (lr != null) {
						woodchuck.isLoggedIn(true);
						msg = new String("Successful login!");
						this.setNewPage("NEWPAGEisTBD.xhtml");
					} else {
						msg = new String("Account NOT verified, Please respond to the verification email.");
					}

				} else {
					log.info("BAD LOGIN ---------------");
					msg = new String("You entered an Incorrect PASSWORD!");
				}

				facesMsg = new FacesMessage(msg);
				fc = FacesContext.getCurrentInstance();
				fc.addMessage("loginIdMessage", facesMsg);

			} catch (Exception notFOUND) {
				log.info("QUERY WAS UNSUCCESSFUL ---------------");
				log.info("NOT FOUND");
				
				msg = new String("Incorrect login: Userid not found, try again.");
				facesMsg = new FacesMessage("UNSUCCESSFULL Login:" + msg);
				fc = FacesContext.getCurrentInstance();
				fc.addMessage("loginIdMessage", facesMsg);

				notFOUND.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("NOT FOUND EXCPTION");
			msg = new String("Incorrect login and/or password, try again.");
			facesMsg = new FacesMessage(msg);
			fc = FacesContext.getCurrentInstance();
			fc.addMessage("loginIdMessage", facesMsg);

		}
		log.info("AcmeAuthenticator completed...........");
	}

	public String isAction() {
		String action="home?faces-redirect=true";
		
		try {
			if ( !( woodchuck.isValid()) ) {
				action=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			action=null;
		}
		
		return action;
	}

	public String getNewPage() {
		log.info("AcmeAuthenticator -----GET a New Page");
		if (newPage == null) {
			log.info("AcmeAuthenticator ----- New Page  is NULL ");
			newPage = new String("popupLogin.xhtml");
		}
		return newPage;
	}

	public void setNewPage(String newPage) {
		this.newPage = newPage;
	}

	public String getLogout() {
		try {
			System.out.println("-----------Say GoodNite Johnny--------------");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().redirect("https://www.google.com");
			context.getExternalContext().invalidateSession();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "https://www.google.com/?faces-redirect=true";
	}

	public void setLogout(String s) {
		System.out.println("setLogOUT-----------Say HELLO Johnny--------------");
		System.out.println("setLogOUT-------VALUR:" + s);
		logout = new String(s);
		this.getLogout();
	}

	public String findValue(ActionEvent e, String id) {
		String value = null;
		UIInput input = null;
		UIComponent c = null;
		try {
			if (e.getComponent().getParent().getChildCount() > 0) {
				System.out.println("-----------Say HELLO KIDS");
				for (UIComponent kid : e.getComponent().getChildren()) {
					if (kid.getId().contains(id)) {
						System.out.println("-----------KID HAVE the ID: FOUNDIT");
					}
				}
			}
			UIComponent parent = e.getComponent().getParent();
			for (UIComponent child : parent.getChildren()) {
				if (child.getId().contains(id)) {
					System.out.println("-----------CHILD HAVE the ID: FOUNDIT");
				}
			}
			c = e.getComponent().findComponent(id);
			if (c != null) {
				input = (UIInput) c;
				value = (String) input.getSubmittedValue();
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return value;
	}
}
