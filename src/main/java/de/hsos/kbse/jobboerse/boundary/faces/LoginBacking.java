/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import static javax.security.enterprise.AuthenticationStatus.NOT_DONE;
import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
//import javax.ws.rs.core.SecurityContext;

@Named
@RequestScoped
public class LoginBacking {
    
    @NotEmpty
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
 
    @NotEmpty
    @Email(message = "Please provide a valid e-mail")
    private String email;
 
    @Inject
    private Pbkdf2PasswordHash passwordHash;
    
    @Inject
    private SecurityContext securityContext;
 
    private final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
 
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
 
    public void submit() throws IOException {
 
        switch (continueAuthentication()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
                break;
            case SUCCESS:
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeed", null));
                externalContext.redirect(externalContext.getRequestContextPath() + "/members/test.xhtml");
                break;
            case NOT_DONE:
        }
    }
 
    private AuthenticationStatus continueAuthentication() {
       Credential credential = new UsernamePasswordCredential(
          email, new Password(password));
       
       AuthenticationStatus status = securityContext
          .authenticate(
            (HttpServletRequest)externalContext.getRequest(),
            (HttpServletResponse)externalContext.getResponse(),
            withParams().newAuthentication(true).credential(credential));
       System.out.println(status);
       return status;
    }
    /*
    public void register(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
        caller callere = new caller();
        callere.setName(email);
        callere.setPassword(passwordHash.generate(password.toCharArray()));
        callere.setGroup_name("USER");
        cf.create(callere);
    }
    */
    
 
   // getters & setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
