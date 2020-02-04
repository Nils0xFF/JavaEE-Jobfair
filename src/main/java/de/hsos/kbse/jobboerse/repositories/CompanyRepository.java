/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.company.Contact;
import de.hsos.kbse.jobboerse.entity.facades.AddressFacade;
import de.hsos.kbse.jobboerse.entity.facades.CompanyFacade;
import de.hsos.kbse.jobboerse.entity.facades.CompanyProfileFacade;
import de.hsos.kbse.jobboerse.entity.facades.ContactFacade;
import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.facades.PictureFacade;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.company.CompanyProfile;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Picture;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author soere
 */
@RequestScoped
public class CompanyRepository {
    
    @Inject
    private LoginFacade loginf;
    @Inject
    private Pbkdf2PasswordHash passwordHash;
    @Inject
    private CompanyFacade companyf;    
    @Inject
    private CompanyProfileFacade companyprofilef;
    @Inject
    private JobRepository jobRepo;
    @Inject
    private PictureFacade picturef;    
    @Inject
    private AddressFacade addressf;
    @Inject
    private ContactFacade contactf;
    
    
    public CompanyRepository() { }   

    public boolean checkEmailExists(String email) {
        return loginf.findByEmail(email) != null;
    }
    
    public void addPicture(String email, Picture toInsert){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            login.getCompany().getProfile().setProfilePicture(toInsert);
            System.out.println("ADD PIC");
            loginf.edit(login);
        }
    }
    
    public boolean createCompany(CompanyProfile toInsert, String email){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            login.getCompany().setProfile(toInsert);
            login.getCompany().setCompleted(true);
            login.getCompany().setLogin(login);
            loginf.edit(login);
        return true;
        }
        
        return false;
    }    
    
    public boolean updateCompany(CompanyProfile toInsert, String email){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            login.getCompany().setProfile(toInsert);
            loginf.edit(login);
        return true;
        }
        
        return false;
    }  
    
    public void createLogin(Login login){
        loginf.create(login);
    }
    
    public List<Job> findJobsByCompany(String email){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            return login.getCompany().getJobs();
        }
        return null;
    }
    
    public boolean addJobtoCompany(String email, Job toInsert){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            toInsert.setCompany(login.getCompany());
            login.getCompany().getJobs().add(toInsert);
            return true;
        }
        return false;
        
    }
    
    public void removeJobFromCompany(String email, Long Id){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            login.getCompany().getJobs().remove(jobRepo.find(Id));
            loginf.edit(login);
        }
    }
    
    //Erster Ansatz; Überarbeitet. Registration Controller übernimmt das Erstellen der Klassen.
    public boolean createLogin(String email, String password) {
        if (!checkEmailExists(email)) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
            parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
            parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
            passwordHash.initialize(parameters);
            Login toInsert = Login.builder()
                    .email(email)
                    .password(passwordHash.generate(password.toCharArray()))
                    .build();
            loginf.create(toInsert);
            return true;
        }
        return false;
    }
    
    public boolean createCompanyProfile(/* General Information */ String email, String name, String description, WorkerCount workercount, 
            /* Address */ String street, String housenumber, String city, String postalcode, String country, 
            /* Contact */ Salutation salutation, Title title, String firstname, String lastname, String contactemail, String phone) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            Address addressToInsert = Address.builder()
                    .street(street)
                    .housenumber(housenumber)
                    .city(city)
                    .postalcode(postalcode)
                    .country(country)
                    .build();
            Contact contactToInsert = Contact.builder()
                    .salutation(salutation)
                    .title(title)
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(contactemail)
                    .phone(phone)
                    .build();
            CompanyProfile profileToInsert = CompanyProfile.builder()
                    .name(name)
                    .description(description)
                    .workercount(workercount)
                    .address(addressToInsert)
                    .contact(contactToInsert)
                    .build();            
            login.getCompany().setProfile(profileToInsert);
            login.getCompany().setLogin(login);
            loginf.edit(login);
            return true;
        }
        return false;
    }

    public boolean updateCompanyProfile(String email, String name, String description, WorkerCount workercount, Address address, Contact contact) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            CompanyProfile toEdit = login.getCompany().getProfile();
            toEdit.setName(name);
            toEdit.setDescription(description);
            toEdit.setWorkercount(workercount);
            toEdit.setAddress(address);
            toEdit.setContact(contact);
            companyprofilef.edit(toEdit);
            return true;
        }
        return false;
    }
    
    public boolean updateCompanyAddress(String email, String street, String housenumber, String city, String postalcode, String country){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            Address toEdit = login.getCompany().getProfile().getAddress();
            toEdit.setStreet(street);
            toEdit.setHousenumber(housenumber);
            toEdit.setCity(city);
            toEdit.setPostalcode(postalcode);
            toEdit.setCountry(country);
            addressf.edit(toEdit);
            return true;
        }
        return false;
    }
    
    public boolean updateCompanyQualifications(String email, List<Benefit> benefits){
        Login login = loginf.findByEmail(email);
        if (login != null) {
            CompanyProfile toEdit = login.getCompany().getProfile();
            toEdit.setBenefits(benefits);
            companyprofilef.edit(toEdit);
            return true;
        }
        return false;
    }
    
    public boolean addCompanyBenefit(String email, Benefit benefit) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            CompanyProfile toEdit = login.getCompany().getProfile();
            toEdit.addBenefit(benefit);
            companyprofilef.edit(toEdit);
            return true;
        }
        return false;
    }
    
    public boolean removeCompanyBenefit(String email, Benefit benefit) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            CompanyProfile toEdit = login.getCompany().getProfile();
            toEdit.removeBenefit(benefit);
            companyprofilef.edit(toEdit);
            return true;
        }
        return false;
    }

    
    public boolean updateCompanyCredentials(String oldEmail, String newEmail, String newPassword) {
        Login login = loginf.findByEmail(oldEmail);
        if (login != null) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
            parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
            parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
            passwordHash.initialize(parameters);
            login.setEmail(newEmail);
            login.setPassword(passwordHash.generate(newPassword.toCharArray()));
            loginf.edit(login);
            return true;
        }
        return false;
    }

    public boolean deleteCompany(String email) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            loginf.remove(login);
            return true;
        }
        return false;
    }

    public Company getCompanyByEmail(String email) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            return login.getCompany();
        }
        return null;
    }

    public void update(Company value) {
        companyf.edit(value);
    }
    
    
}
