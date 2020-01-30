/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.UserRegistrationController;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class UserRegisterFace {
    private String email, pw;
    private String firstname, lastname,telefon,desc;
    private Date birthday;
    private String street, housenumber, city, postalcode, country;
    private List<Requirement> fullfilledRequirements;
    
    @Inject
    UserRegistrationController userRegCntrl;
    
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    public Salutation[] getSalutationValues() {
        return Salutation.values();
    }
    
    @Enumerated(EnumType.STRING)
    private Title titles;

    public Title[] getTitleValues() {
        return Title.values();
    } 
    
    @Enumerated(EnumType.STRING)
    private Graduation grades;

    public Graduation[] getGraduationValues() {
        return Graduation.values();
    }

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
    
    

}
