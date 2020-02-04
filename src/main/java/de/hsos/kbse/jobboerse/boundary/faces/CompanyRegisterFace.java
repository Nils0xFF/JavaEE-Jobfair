/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.CompanyRegistrationController;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author lennartwoltering, nilsgeschwinde
 */
@Named("CompanyRegisterFace")
@ViewScoped
public class CompanyRegisterFace implements Serializable {

    @NotEmpty
    private String firmname;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @Pattern(regexp = "^[^a-zA-Z]+$")
    private String telefon;
    @NotEmpty
    @Email(message = "Es muss eine gültige Email sein")
    private String contactEmail;
    @NotEmpty
    private String desc;
    @Pattern(regexp = "^[^0-9]+$")
    @NotEmpty
    private String street;
    @NotEmpty
    private String housenumber;
    @NotEmpty
    @Pattern(regexp = "^[^0-9]+$")
    private String city;
    @NotEmpty
    private String postalcode, country;

    private byte[] pictureData;

    private String dataType;

    private List<Benefit> fullfilledBenefits;

    private UploadedFile file;

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

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("UPLOAD");
        pictureData = event.getFile().getContents();
        dataType = event.getFile().getContentType();
    }

    public StreamedContent getProfileImage() {
        if(pictureData != null){
        return new DefaultStreamedContent(new ByteArrayInputStream(pictureData), dataType);
        }
        return null;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    @Transactional
    public void registerUser() {
        if (companyRegCntrl.createProfile(firmname, desc, workercount, pictureData, dataType)
                .createAddress(street, housenumber, city, postalcode, country)
                .createContact(salutation, titles, firstname, lastname, telefon, contactEmail)
                .finishRegistration(fullfilledBenefits, context.getCallerPrincipal().getName())) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/dashboard");
            } catch (IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register failed", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register failed", null));
        }
    }

    public void validatePasswordRepeat(FacesContext context, UIComponent component, Object value) {
        
        // Get Password Conformation
        String confirmPassword = (String) value;
        
        // Get Value of Input Field
        UIInput passwordInput = (UIInput) component.findComponent("password");
        String password = (String) passwordInput.getLocalValue();
        
        if (password == null ||confirmPassword == null || !password.equals(confirmPassword)) {
            String message = context.getApplication().evaluateExpressionGet(context, "Passwort muss übereinstimmen", String.class);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            throw new ValidatorException(msg);
        }
    }

    //-------------------
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Title[] getTitleValues() {
        return Title.values();
    }

    public Salutation[] getSalutationValues() {
        return Salutation.values();
    }

    public WorkerCount[] getWorkerValues() {
        return WorkerCount.values();
    }

    public byte[] getPictureData() {
        return pictureData;
    }

    public void setPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
    }

}
