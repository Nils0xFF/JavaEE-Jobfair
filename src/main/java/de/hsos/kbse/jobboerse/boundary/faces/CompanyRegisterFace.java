/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.CompanyRegistrationController;
import de.hsos.kbse.jobboerse.controllers.UserRegistrationController;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author lennartwoltering
 */
@Named("CompanyRegisterFace")
@ViewScoped
public class CompanyRegisterFace implements Serializable{
    @NotEmpty
    @Email(message = "Es muss eine gültige Email sein")
    private String email;
    @Size(min=2, max=24, message = "Passwort muss länger als 5 Zeichen sein.")
    private String pw, pw2;
    @NotEmpty
    private String firmname;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @Pattern(regexp= "^[^a-zA-Z]+$")
    private String telefon;
    @NotEmpty
    private String desc;
    @Pattern(regexp= "^[^0-9]+$")
    @NotEmpty
    private String street;
    @NotEmpty
    private String housenumber;
    @NotEmpty
    @Pattern(regexp= "^[^0-9]+$")
    private String city;
    @NotEmpty
    private String postalcode, country;
    private List<Benefit> fullfilledBenefits;
    
    @Enumerated(EnumType.STRING)
    private Salutation salutation;
    @Enumerated(EnumType.STRING)
    private Title titles;
    @Enumerated(EnumType.STRING)
    private WorkerCount workercount;
    
    
    @Inject
    BenefitRepository benefitRepo;
    
    @Inject
    CompanyRegistrationController companyRegCntrl;
    
    @Inject
    SecurityContext context;
   

            
    @Transactional
    public void registerLogin() {
        if (pw.equals(pw2)) {
            if(companyRegCntrl.createLogin(email, pw)){
                 FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Du kannst dich nun anmelden!", null));
                 return;
            }
        }
         FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registrierung fehlgeschlagen, überprüfe deine Eingaben", null));
    }
    
    @Transactional
    public void registerUser() {
        if(companyRegCntrl.createProfile(firmname, desc, workercount)
                .createAddress(street, housenumber, city, postalcode, country)
                .createContact(salutation, titles,firstname, lastname, telefon)
                .finishRegistration(fullfilledBenefits, context.getCallerPrincipal().getName())){
            try{
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/dashboard");
            } catch (IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register failed", null));
                Logger.getLogger(CompanyRegisterFace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register failed", null));
        }
    }
    
    
    
    
    
    
    //-------------------

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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Benefit> getFullfilledBenefits() {
        return fullfilledBenefits;
    }

    public void setFullfilledBenefits(List<Benefit> fullfilledBenefits) {
        this.fullfilledBenefits = fullfilledBenefits;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public Title getTitles() {
        return titles;
    }

    public void setTitles(Title titles) {
        this.titles = titles;
    }

    public String getFirmname() {
        return firmname;
    }

    public void setFirmname(String firmname) {
        this.firmname = firmname;
    }

    public WorkerCount getWorkercount() {
        return workercount;
    }

    public void setWorkercount(WorkerCount workercount) {
        this.workercount = workercount;
    }
    
    public Title[] getTitleValues() {
        return Title.values();
    }

    public Salutation[] getSalutationValues() {
        return Salutation.values();
    }
    
    public WorkerCount[] getWorkerValues(){
        return WorkerCount.values();
    }
    
    
    
    
    
    
    
}
