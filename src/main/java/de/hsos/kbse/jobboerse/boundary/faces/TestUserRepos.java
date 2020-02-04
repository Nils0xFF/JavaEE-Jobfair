/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.UserRegistrationController;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.transaction.Transactional;
import org.primefaces.model.DualListModel;

/**
 *
 * @author lennartwoltering
 */
@Named("test")
@RequestScoped
public class TestUserRepos {
    private String email, pw;
    private String firstname, lastname,telefon,desc;
    private Date birthday;
    private String street, housenumber, city, postalcode, country;
    
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    public Salutation[] getSalutationValues() {
        return Salutation.values();
    }

    public Salutation getSalutations() {
        return salutation;
    }
    
    @Enumerated(EnumType.STRING)
    private Title titles;

    public Title[] getTitleValues() {
        return Title.values();
    } 

    public Title getTitles() {
        return titles;
    }
    
    private List<Requirement> reqTarget = new ArrayList<Requirement>();
    private DualListModel<Requirement> selectedRequirements;
    
    @Inject
    private JobRepository jobRepo;
    
    @Inject
    private RequirementRepository requirementRepo;
    
    @Inject
    private UserRegistrationController regCntrl;
    
    @Inject
    private GeneralUserRepository ur;
    
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
    
    public void printJobs(){
        for(Job j : jobRepo.findJobsByJobField("Softwareentwicklung")){
            System.out.println(j.getName());
        }
    }
    
    public void createPorfile(){
        /*
        ur.createLogin(email, pw);
        regCntrl.createUserProfile(salutation, titles, firstname, lastname, desc, telefon, birthday);
        regCntrl.createAddress(street, housenumber, city, postalcode, country);
        regCntrl.createQualifications("test@test.de", grades, selectedRequirements.getTarget());
*/
        //ur.createUserProfile(email, Salutation.mister , Title.Emtpy ,firstname, lastname, desc, telefon, grades);

    }
    
    public void createQualis(){
        //regCntrl.createQualifications("test@test.de", grades, selectedRequirements.getTarget());
    }
    
    @Transactional
    public void removeUser(){
        ur.deleteUser(email);
    }

    public void getRequirements(){
        try {
            selectedRequirements = new DualListModel<Requirement>(
                    requirementRepo.findAll(), reqTarget);
        } catch (Exception ex) {
            Logger.getLogger(TestUserRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DualListModel<Requirement> getSelectedRequirements() {
        getRequirements();
        return selectedRequirements;
    }

    public void setSelectedRequirements(DualListModel<Requirement> selectedRequirements) {
        this.selectedRequirements = selectedRequirements;
    }

    public Date getBirthday() {
        return birthday;
    }
 
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Salutation getSalutation() {
        return salutation;
    }
    
    public Title getTitle(){
        return titles;
    }
    
    public void setTitle(Title title){
        this.titles = title;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
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

    public GeneralUserRepository getUr() {
        return ur;
    }

    public void setUr(GeneralUserRepository ur) {
        this.ur = ur;
    }
    
    
}
