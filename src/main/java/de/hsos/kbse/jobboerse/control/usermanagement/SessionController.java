package de.hsos.kbse.jobboerse.control.usermanagement;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;

/**
 *
 * @author nilsgeschwinde
 */
@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

    @Inject
    private SecurityContext context;

    private boolean isLoggedIn = false;

    private boolean isAdmin = false;

    private boolean isCompany = false;

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public boolean userHasSetup() {
        return false;
    }

    public boolean userIsLoggedIn() {
        return context.isCallerInRole("USER");
    }

    public boolean userIsAdmin() {
        return isAdmin;
    }

    public boolean userIsCompany() {
        return isCompany;
    }

    public String getUserName() {
        return context.getCallerPrincipal().getName();
    }

}
