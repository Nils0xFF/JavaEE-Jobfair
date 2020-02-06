/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.CompanyCreationController;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author lennartwoltering, nilsgeschwinde
 */
@Named("CompanyRegisterFace")
@ViewScoped
public class CompanyRegisterFace implements Serializable {

    @NotBlank
    private String firmname;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "{de.hsos.kbse.util.validation.invalidPhoneNumber}")
    private String telefon;
    @NotBlank
    @Email(message = "Es muss eine gültige Email sein")
    private String contactEmail;
    @NotBlank
    private String desc;
    @Pattern(regexp = "^[^0-9]+$")
    @NotBlank
    private String street;
    @NotBlank
    private String housenumber;
    @NotBlank
    @Pattern(regexp = "^[^0-9]+$")
    private String city;
    @NotBlank
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
    private BenefitRepository benefitRepo;

    @Inject
    private CompanyCreationController companyRegCntrl;

    @Inject
    private SecurityContext context;
    
    public void handleFileUpload(FileUploadEvent event) {
        pictureData = event.getFile().getContents();
        dataType = event.getFile().getContentType();
        this.file = event.getFile();
    }
    
    public String getBase64 (){
        return Base64.getEncoder().encodeToString(file.getContents());
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

    public String getDataType() {
        return dataType;
    }

    public byte[] getPictureData() {
        return pictureData;
    }

}
