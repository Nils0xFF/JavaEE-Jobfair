/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.CompanyRegistrationController;
import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Picture;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author nilsgeschwinde
 */
@Named
@ViewScoped
public class CompanyProfileFace implements Serializable {

    @Inject
    private SecurityContext context;

    @Inject
    private CompanyRepository compRepository;
    
    @Inject
    private CompanyRegistrationController companyCntrl;

    private boolean editMode = false;

    private Company companyDetails;

    @NotEmpty
    @Email(message = "Es muss eine gültige Email sein")
    private String email;

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
    private List<Benefit> fullfilledBenefits;

    @Enumerated(EnumType.STRING)
    private Salutation salutation;
    @Enumerated(EnumType.STRING)
    private Title titles;
    @Enumerated(EnumType.STRING)
    private WorkerCount workercount;

    @PostConstruct
    private void init() {
        companyDetails = compRepository.getCompanyByEmail(context.getCallerPrincipal().getName());
        email = context.getCallerPrincipal().getName();

        firmname = companyDetails.getProfile().getName();
        desc = companyDetails.getProfile().getDescription();
        workercount = companyDetails.getProfile().getWorkercount();

        street = companyDetails.getProfile().getAddress().getStreet();
        housenumber = companyDetails.getProfile().getAddress().getHousenumber();
        city = companyDetails.getProfile().getAddress().getCity();
        postalcode = companyDetails.getProfile().getAddress().getPostalcode();
        country = companyDetails.getProfile().getAddress().getCountry();
        
        fullfilledBenefits = companyDetails.getProfile().getBenefits();

        salutation = companyDetails.getProfile().getContact().getSalutation();
        titles = companyDetails.getProfile().getContact().getTitle();
        firstname = companyDetails.getProfile().getContact().getFirstname();
        lastname = companyDetails.getProfile().getContact().getLastname();
        telefon = companyDetails.getProfile().getContact().getPhone();
        contactEmail = companyDetails.getProfile().getContact().getEmail();

    }
    
    @Transactional
    public void handleFileUpload(FileUploadEvent event){
        System.out.println("ADD");
        Picture toInsert = Picture.builder().data(event.getFile().getContents()).dataType(event.getFile().getContentType()).build();
        compRepository.addPicture(context.getCallerPrincipal().getName(), toInsert);
    }
    
    @Transactional
    public void updateProfile(){
        companyCntrl.createProfile(firmname, desc, workercount, null, null)
                .createAddress(street, housenumber, city, postalcode, country)
                .createContact(salutation, titles, firstname, lastname, email, contactEmail)
                .finishUpdating(fullfilledBenefits, context.getCallerPrincipal().getName());
                
        editMode = false;
    }

    public void startEdit() {
        editMode = true;
    }

    public void cancleEdit() {
        editMode = false;
        init();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirmname() {
        return firmname;
    }

    public void setFirmname(String firmname) {
        this.firmname = firmname;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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

    public WorkerCount getWorkercount() {
        return workercount;
    }

    public void setWorkercount(WorkerCount workercount) {
        this.workercount = workercount;
    }

    public WorkerCount[] getWorkerValues() {
        return WorkerCount.values();
    }

    public Title[] getTitleValues() {
        return Title.values();
    }

    public Salutation[] getSalutationValues() {
        return Salutation.values();
    }

}
