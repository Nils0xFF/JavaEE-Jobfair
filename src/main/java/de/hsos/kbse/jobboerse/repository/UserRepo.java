/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repository;

import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.facades.SeekingUserFacade;
import de.hsos.kbse.jobboerse.entity.facades.User_ProfileFacade;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.User_Profile;
import de.hsos.kbse.jobboerse.enums.Graduation;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class UserRepo {
    @Inject 
    private LoginFacade logins;
    
    @Inject
    private SeekingUserFacade users;
    
    @Inject
    private User_ProfileFacade userprofiles;
    
    @Inject
    private Pbkdf2PasswordHash passwordHash;
    
    public boolean checkEmailExists(String email){
        return logins.findByEmail(email) != null; 
    }
    
    public boolean createLogin(String email, String password){
        if(!checkEmailExists(email)){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
            parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
            parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
            passwordHash.initialize(parameters);
            Login toInsert = Login.builder()
                    .email(email)
                    .password(passwordHash.generate(password.toCharArray()))
                    .build();
            logins.create(toInsert);
            return true;
        }
        return false;
    }
    
    public boolean createUserProfile(String email, String firstname, String lastname, String description, String telefon, Graduation graduation){
        Login login = logins.findByEmail(email);
        if(login != null){
        User_Profile profileToInsert =  User_Profile.builder()
                .firstname(firstname)
                .lastname(lastname)
                .description(description)
                .telefon(telefon)
                .grad(graduation)
                .build();
        login.getSeekingUser().setProfile(profileToInsert);
        login.getSeekingUser().setLogin(login);
        logins.edit(login);
        return true;
        }
        return false; 
    }
    
    public boolean editUserProfile(String email, String firstname, String lastname, String description, String telefon, Graduation graduation){
        Login login = logins.findByEmail(email);
        if(login != null){
            User_Profile toEdit = login.getSeekingUser().getProfile();
            toEdit.setFirstname(firstname);
            toEdit.setLastname(lastname);
            toEdit.setTelefon(telefon);
            toEdit.setGrad(graduation);
            userprofiles.edit(toEdit);
        }
        return false;
    }
    
    public boolean editUserCredentials(String oldEmail, String newEmail, String newPassword){
        Login login = logins.findByEmail(oldEmail);
        if(login != null){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
            parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
            parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
            passwordHash.initialize(parameters);
            login.setEmail(newEmail);
            login.setPassword(passwordHash.generate(newPassword.toCharArray()));
            logins.edit(login);
            return true;
        }
        return false;
    }
    
    public boolean deleteUser(String email){
        Login login = logins.findByEmail(email);
        if(login != null){
            logins.remove(login);
            return true;
        }
        return false;
    }
    
    public SeekingUser getUserByEmail(String email){
        Login login = logins.findByEmail(email);
        if(login != null){
            return login.getSeekingUser();
        }
        return null;
    }
    
}
