/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.boundary.faces.mockfiles.AutoCompleteData;
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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 *
 * @author lennartwoltering
 */
@Named("UserRegisterFace")
@ViewScoped
public class UserRegisterFace implements Serializable {

    @NotEmpty
    @Email(message = "Es muss eine gültige Email sein")
    private String email;
    @Min(value = 5, message = "Passwort muss länger als 5 Zeichen sein.")
    private String pw, pw2;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @Pattern(regexp= "^[^a-zA-Z]+$")
    private String telefon;
    @NotEmpty
    private String desc;
    @NotNull
    @Past
    private Date birthday;
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
    private List<Requirement> fullfilledRequirements;
    private List<Benefit> wishedBenefits;
    private List<JobField> wishedJobFields;
    @Enumerated(EnumType.STRING)
    private Salutation salutation;
    @Enumerated(EnumType.STRING)
    private Title titles;
    @Enumerated(EnumType.STRING)
    private Graduation grades;
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");

    @Inject
    UserRegistrationController userRegCntrl;

    @Inject
    RequirementRepository requirementRepo;

    @Inject
    BenefitRepository benefitRepo;

    @Inject
    JobFieldRepository jobFieldRepo;

    @Inject
    SecurityContext context;

   

    @Transactional
    public boolean registerLogin() {
        if (pw.equals(pw2)) {
            return userRegCntrl.createLogin(email, pw);
        }
        return false;
    }

    @Transactional
    public boolean registerUser() {
        return userRegCntrl
                .createUserProfile(salutation, titles, firstname, lastname, telefon, birthday)
                .createAddress(street, housenumber, city, postalcode, country)
                .createQualifications(grades, fullfilledRequirements, desc)
                .setupSearchParameters(wishedBenefits, wishedJobFields)
                .finishRegistration(context.getCallerPrincipal().getName());
    }

    //GETTER / SETTER

    //GETTER / SETTER
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

    public String getPw2() {
        return pw2;
    }

    public void setPw2(String pw2) {
        this.pw2 = pw2;
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
