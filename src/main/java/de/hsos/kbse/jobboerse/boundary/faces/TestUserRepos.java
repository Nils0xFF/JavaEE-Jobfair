/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.repositories.UserRepository;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.transaction.Transactional;

/**
 *
 * @author lennartwoltering
 */
@Named("test")
@RequestScoped
public class TestUserRepos {
    private String email;
    private String pw;
    private String firstname;
    private String lastname;
    private String telefon;
    private String desc;
    
    @Inject
    private UserRepository ur;
    
    @Enumerated(EnumType.ORDINAL)
    private Graduation grades;

    public Graduation[] getStatuses() {
        return Graduation.values();
    }

    public Graduation getGrades() {
        return grades;
    }

    public void setGrades(Graduation grades) {
        this.grades = grades;
    }
    
    
    
    @Transactional
    public void createLogin(){
        ur.createLogin(email, pw);
    }
    
    @Transactional
    public void createPorfile(){
        ur.createUserProfile(email, Salutation.mister , Title.Emtpy ,firstname, lastname, desc, telefon, grades);
    }
    
    @Transactional
    public void removeUser(){
        ur.deleteUser(email);
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

    public UserRepository getUr() {
        return ur;
    }

    public void setUr(UserRepository ur) {
        this.ur = ur;
    }
    
    
}
