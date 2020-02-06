/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.controllers;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.User_Profile;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class UserRegistrationController implements Serializable{
    
    
    @Inject
    private GeneralUserRepository userRepo;
    
    @Inject
    private Pbkdf2PasswordHash passwordHash;
    
    private Login login;
    private User_Profile userProfile;
    private Address userAddress;
    private SearchRequest searchrequest;
    
     /**
     * Checks the Availability of a Email in the Database
     * 
     * @param email the email address that should be checked
     * @return returns true if the email is available
     */
    public boolean checkEmailAvailability(String email){
        return !userRepo.checkEmailExists(email);
    }
    
    /**
     * Tries to create a new User Login in the Database.
     * It hashes the password with pbkdf2 and uses USER as
     * group_name.
     * 
     * @param email the email that should be persisted
     * @param password the password that should be persisted
     * @return returns true if the Login was successfull created
     */
    public boolean createLogin(String email, String password){
        if(checkEmailAvailability(email)){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
            parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
            parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
            passwordHash.initialize(parameters);
            login = Login.builder()
                    .email(email)
                    .password(passwordHash.generate(password.toCharArray()))
                    .seekingUser(new SeekingUser())
                    .group_name("USER")
                    .build();
            userRepo.createLogin(login);
            return true;
            }
            return false;
    }
    
    /**
     * Creates a User Profile and saves it to the userProfile field
     *
     * @param salutation An enum that represents the salutation of the user
     * @param title An enum that represents the title of the user
     * @param firstname Represents the firstname of the user
     * @param lastname Represents the lastname of the user
     * @param telefon Represents the phonenumber of the user
     * @param birthday Represents the date of birth of the user
     * @return returns a reference to itself
     */
    public UserRegistrationController createUserProfile(Salutation salutation, Title title, String firstname, 
            String lastname, String telefon, Date birthday){
            userProfile = User_Profile.builder()
                    .salutation(salutation)
                    .title(title)
                    .firstname(firstname)
                    .lastname(lastname)
                    .telefon(telefon)
                    .birthday(birthday)
                    .build();
            return this;
    }
    
    /**
     * Creates a User address and saves it to the userProfile field
     * 
     * @param street Represents the street where the user lives
     * @param housenumber Represents the housenumber where the user lives
     * @param city Represents the city where the user lives
     * @param postalcode Represents the postalcode of the city
     * @param country Reprensents the country of the city
     * @return returns a reference to itself
     */
    public UserRegistrationController createAddress(String street, String housenumber, String city, 
            String postalcode, String country){
            userAddress = Address.builder()
                    .street(street)
                    .housenumber(housenumber)
                    .city(city)
                    .postalcode(postalcode)
                    .country(country)
                    .build();
            userProfile.setAddress(userAddress);
            return this;
    }
    
    /**
     * Adds a Qualification to the userprofile
     * 
     * @param grad An enum that represents the graduation of the user
     * @param fullfilledRequirements A list with fullfilled Requirements
     * @param description Represents a description of the user
     * @return returns a reference to itself
     */
    public UserRegistrationController createQualifications(Graduation grad, List<Requirement> fullfilledRequirements, String description){
        userProfile.setGrad(grad);
        userProfile.setDescription(description);
        userProfile.setFullfiledRequirements(fullfilledRequirements);
        return this;
    }
    
    /**
     * Creates setupParameters and saves it to the searchrequest field
     * 
     * @param wishedBenefits Represents a list with wishedBenefits
     * @param jobfields Represents the jobfields where the User wants to work
     * @return returns a reference to itself
     */
    public UserRegistrationController setupSearchParameters(List<Benefit> wishedBenefits, List<JobField> jobfields){
        searchrequest = SearchRequest.builder()
                .benefits(wishedBenefits)
                .jobField(jobfields)
                .build();
        return this;
    }
    
    /**
     * Completes the Registration and saves it to the database
     * 
     * @param email Email address of the user
     * @return returns true if the email is assigned and the user is created
     */
    public boolean finishRegistration(String email){
        return userRepo.createUser(userProfile,searchrequest, email);
    }
    
    /**
     * Completes the Updating and edits the data in the database
     * @param email Email address of the user
     * @return returns true if the email is assigned and the user is edited
     */
    public boolean finishUpdating(String email){
        return userRepo.updateUser(userProfile, email);
    }
    
}
