package de.hsos.kbse.jobboerse.control.usermanagement;

import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
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

    @Inject
    private CompanyRepository companyRepo;

    @Inject
    private GeneralUserRepository userRepo;

    private boolean isLoggedIn = false;

    private boolean isAdmin = false;

    private boolean isCompany = true;

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public boolean userHasSetup() {

        if (userIsCompany()) {
            return companyRepo.getCompanyByEmail(getUserName()).isCompleted();
        }
        if (userIsAdmin()) {
            return true;
        } else {
            return userRepo.getUserByEmail(getUserName()).hasCompleted();
        }
    }

    public boolean userIsLoggedIn() {
        return (context.isCallerInRole("USER") || context.isCallerInRole("COMPANY"));
    }

    public boolean userIsAdmin() {
        return isAdmin;
    }

    public boolean userIsCompany() {
        return context.isCallerInRole("COMPANY");
    }

    public String getUserName() {
        return context.getCallerPrincipal().getName();
    }

}
