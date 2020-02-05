/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.controllers;

import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.User_Profile;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    
    public boolean checkEmailAvailability(String email){
        return !userRepo.checkEmailExists(email);
    }
    
    public UserRegistrationController createLogin(String email, String password){
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
            return this;
    }
    
    public UserRegistrationController createUserProfile(Salutation salutation, Title title, String firstname, 
            String lastname, String description, String telefon, Date birthday){
            userProfile = User_Profile.builder()
                    .salutation(salutation)
                    .title(title)
                    .firstname(firstname)
                    .lastname(lastname)
                    .description(description)
                    .telefon(telefon)
                    .birthday(birthday)
                    .build();
            return this;
    }
    
    public UserRegistrationController createAddress(String street, String housenumber, String city, 
            String postalcode, String country){
        System.out.println("ADDRESS: " + userProfile.getFirstname());
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
    
    public boolean createQualifications(Graduation grad, List<Requirement> fullfilledRequirements){
        login.getSeekingUser().setProfile(userProfile);
        userProfile.setGrad(grad);
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
        return userRepo.createUser(userProfile,searchrequest, email);
    }
    
}
