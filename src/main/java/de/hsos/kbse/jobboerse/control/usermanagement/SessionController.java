package de.hsos.kbse.jobboerse.control.usermanagement;

import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;

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

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
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
        return (context.isCallerInRole("USER") || context.isCallerInRole("COMPANY") || context.isCallerInRole("ADMIN"));
    }

    public boolean userIsAdmin() {
        return context.isCallerInRole("ADMIN");
    }

    public boolean userIsCompany() {
        return context.isCallerInRole("COMPANY");
    }

    public String getUserName() {
        return context.getCallerPrincipal().getName();
    }

}
