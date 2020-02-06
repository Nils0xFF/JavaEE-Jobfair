/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.UserRegistrationController;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 *
 * @author lennartwoltering, nilsgeschwinde
 */
@Named("UserRegisterFace")
@ViewScoped
public class UserRegisterFace implements Serializable {

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "{de.hsos.kbse.util.validation.invalidPhoneNumber}")
    private String telefon;
    @NotBlank
    private String desc;
    @NotNull
    @Past
    private Date birthday;
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
    private List<Requirement> fullfilledRequirements;
    private List<Benefit> wishedBenefits;
    private List<JobField> wishedJobFields;
    @Enumerated(EnumType.STRING)
    private Salutation salutation;
    @Enumerated(EnumType.STRING)
    private Title titles;
    @Enumerated(EnumType.STRING)
    private Graduation grades;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Inject
    private UserRegistrationController userRegCntrl;

    @Inject
    private RequirementRepository requirementRepo;

    @Inject
    private BenefitRepository benefitRepo;

    @Inject
    private JobFieldRepository jobFieldRepo;

    @Inject
    private SecurityContext context;

    @Transactional
    public void registerUser() {
        userRegCntrl
                .createUserProfile(salutation, titles, firstname, lastname, telefon, birthday)
                .createAddress(street, housenumber, city, postalcode, country)
                .createQualifications(grades, fullfilledRequirements, desc)
                .setupSearchParameters(wishedBenefits, wishedJobFields)
                .finishRegistration(context.getCallerPrincipal().getName());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/dashboard");
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register failed", null));
            Logger.getLogger(CompanyRegisterFace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //GETTER / SETTER
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Graduation getGrades() {
        return grades;
    }

    public void setGrades(Graduation grades) {
        this.grades = grades;
    }

    public List<Requirement> getFullfilledRequirements() {
        return fullfilledRequirements;
    }

    public void setFullfilledRequirements(List<Requirement> fullfilledRequirements) {
        this.fullfilledRequirements = fullfilledRequirements;
    }

    public List<Benefit> getWishedBenefits() {
        return wishedBenefits;
    }

    public void setWishedBenefits(List<Benefit> wishedBenefits) {
        this.wishedBenefits = wishedBenefits;
    }

    public List<JobField> getWishedJobField() {
        return wishedJobFields;
    }

    public void setWishedJobField(List<JobField> wishedJobFields) {
        this.wishedJobFields = wishedJobFields;
    }

    public List<JobField> getJobfields() {
        return jobFieldRepo.findAll();
    }

    public List<Benefit> getAllBenefits() {
        return benefitRepo.findAll();
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public Graduation[] getGraduationValues() {
        return Graduation.values();
    }

    public Title[] getTitleValues() {
        return Title.values();
    }

    public Salutation[] getSalutationValues() {
        return Salutation.values();
    }

}
