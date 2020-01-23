package de.hsos.kbse.jobboerse.control.usermanagement;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author nilsgeschwinde
 */
@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private boolean isLoggedIn = false;

    private boolean isAdmin = false;

    private boolean isCompany = true;

    public String login() {
        System.out.print("Login!");
        isLoggedIn = true;
        return "/pages/members/index";
    }

    public String logout() {
        isLoggedIn = false;
        System.out.print("Logout!" + this.userIsLoggedIn());
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/pages/public/index";
    }

    public boolean userIsLoggedIn() {
        return isLoggedIn;
    }

    public boolean userIsAdmin() {
        return isAdmin;
    }

    public boolean userIsCompany() {
        return isCompany;
    }

}
