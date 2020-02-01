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
import java.util.ArrayList;
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
    
    private boolean checkEmailAvailability(String email){
        return !userRepo.checkEmailExists(email);
    }
    
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
    
    public UserRegistrationController createQualifications(Graduation grad, List<Requirement> fullfilledRequirements, String description){
        userProfile.setGrad(grad);
        userProfile.setDescription(description);
        userProfile.setFullfiledRequirements(fullfilledRequirements);
        return this;
    }
    
    public UserRegistrationController setupSearchParameters(List<Benefit> wishedBenefits, List<JobField> jobfields){
        searchrequest = SearchRequest.builder()
                .benefits(wishedBenefits)
                .jobField(jobfields)
                .build();
        return this;
    }
    
    public boolean finishRegistration(String email){
        System.out.println(userProfile.getFirstname());
        return userRepo.createUser(userProfile,searchrequest, email);
    }
    
    public boolean finishUpdating(String email){
        return userRepo.updateUser(userProfile, email);
    }
    
}
