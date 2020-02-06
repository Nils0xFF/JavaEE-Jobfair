/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.CompanyCreationController;
import de.hsos.kbse.jobboerse.controllers.UserCreationController;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.primefaces.PrimeFaces;

/**
 *
 * @author nilsgeschwinde
 */
@Named
@ViewScoped
public class LoginRegisterFace implements Serializable {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 2, max = 24)
    private String pw, pw2;

    private boolean seekingUser = false;
    private boolean company = false;
    private boolean successful = false;

    @Inject
    private CompanyCreationController companyRegCntrl;

    @Inject
    private UserCreationController userRegCntrl;

    @Inject
    private GeneralUserRepository generalUserRepository;

    @Transactional
    public void registerLogin() {
        successful = false;
        if (generalUserRepository.checkEmailExists(email)) {
            FacesContext.getCurrentInstance().addMessage("registerForm:accountEmail",
                    new FacesMessage("Email wurde bereits verwendet!"));
            return;
        }

        if (pw.equals(pw2)) {
            if (seekingUser && userRegCntrl.createLogin(email, pw)) {
                successCallback();
                return;
            }
            if (company && companyRegCntrl.createLogin(email, pw)) {
                successCallback();
                return;
            }
        }

        FacesContext.getCurrentInstance().addMessage("registerError",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registrierung fehlgeschlagen, überprüfe deine Eingaben", null));
        return;
    }

    private void successCallback() {
        FacesContext.getCurrentInstance().addMessage("registerGrowl",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Du kannst dich nun anmelden!", null));
        PrimeFaces.current().executeScript("$('#registerModal').modal('hide');");
        successful = true;
    }

    public void userRegistration() {
        this.company = false;
        this.seekingUser = true;
    }

    public void companyRegistration() {
        this.company = true;
        this.seekingUser = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getPw2() {
        return pw2;
    }

    public void setPw2(String pw2) {
        this.pw2 = pw2;
    }

    public boolean isSeekingUser() {
        return seekingUser;
    }

    public boolean isCompany() {
        return company;
    }

    public boolean isSuccessful() {
        return successful;
    }

}
