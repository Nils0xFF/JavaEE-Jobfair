/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.UserCreationController;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
 * @author nilsgeschwinde
 */
@Named
@ViewScoped
public class UserProfileFace implements Serializable{

    @Inject
    private SecurityContext context;

    @Inject
    private GeneralUserRepository userRepository;
    
    @Inject
    private UserCreationController userCntrl;

    
    private boolean editMode = false;
    
    private SeekingUser userDetails;

    private String email;

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

    @Enumerated(EnumType.STRING)
    private Salutation salutation;
    @Enumerated(EnumType.STRING)
    private Title titles;
    @Enumerated(EnumType.STRING)
    private Graduation grades;

    @PostConstruct
    private void init() {
        userDetails = userRepository.getUserByEmail(context.getCallerPrincipal().getName());
        email = userDetails.getLogin().getEmail();

        salutation = userDetails.getProfile().getSalutation();
        titles = userDetails.getProfile().getTitle();
        firstname = userDetails.getProfile().getFirstname();
        lastname = userDetails.getProfile().getLastname();
        telefon = userDetails.getProfile().getTelefon();
        desc = userDetails.getProfile().getDescription();
        birthday = userDetails.getProfile().getBirthday();
        street = userDetails.getProfile().getAddress().getStreet();
        housenumber = userDetails.getProfile().getAddress().getHousenumber();
        postalcode = userDetails.getProfile().getAddress().getPostalcode();
        city = userDetails.getProfile().getAddress().getCity();
        country = userDetails.getProfile().getAddress().getCountry();

        grades = userDetails.getProfile().getGrad();
        fullfilledRequirements = userDetails.getProfile().getFullfiledRequirements();
    }
    
    @Transactional
    public void updateProfile(){
        userCntrl.createUserProfile(salutation, titles, firstname, lastname, telefon, birthday)
                .createAddress(street, housenumber, city, postalcode, country)
                .createQualifications(grades, fullfilledRequirements, desc)
                .finishUpdating(context.getCallerPrincipal().getName());
        
        editMode = false;
    }
    
    
    public void startEdit(){
        editMode = true;
    }
    
    public void cancleEdit(){
        editMode = false;
        init();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Requirement> getFullfilledRequirements() {
        return fullfilledRequirements;
    }

    public void setFullfilledRequirements(List<Requirement> fullfilledRequirements) {
        this.fullfilledRequirements = fullfilledRequirements;
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

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
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
