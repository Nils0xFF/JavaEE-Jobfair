package de.hsos.kbse.jobboerse.control.usermanagement;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author nilsgeschwinde
 */

@SessionScoped
public class SessionController implements Serializable {

    private boolean isLoggedIn;

    private boolean isAdmin;

    public void login(){
        isLoggedIn = true;
    }
    
    public void logout(){
        isLoggedIn = false;
    }
    
    public boolean userIsLoggedIn() {
        return isLoggedIn;
    }

    public boolean userIsAdmin() {
        return isAdmin;
    }

}
