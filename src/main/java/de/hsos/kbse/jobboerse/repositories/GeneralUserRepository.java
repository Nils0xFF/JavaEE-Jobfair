/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.facades.AddressFacade;
import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.facades.SeekingUserFacade;
import de.hsos.kbse.jobboerse.entity.facades.User_ProfileFacade;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.User_Profile;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import java.time.LocalDate;
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
public class GeneralUserRepository {

    @Inject
    private LoginFacade logins;

    @Inject
    private SeekingUserFacade users;

    @Inject
    private User_ProfileFacade userprofiles;
    
    @Inject
    private AddressFacade addresses;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public boolean checkEmailExists(String email) {
        return logins.findByEmail(email) != null;
    }
    
    public boolean createUser(Login toInsert){
        if (!checkEmailExists(toInsert.getEmail())) {
            logins.create(toInsert);
            return true;
        }
        return false;
    }
    
    public void createLogin(Login login){
        logins.create(login);
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
            logins.create(toInsert);
            return true;
        }
        return false;
    }
    
    public boolean createUserProfile(String email, Salutation salutation, Title title, String firstname, 
            String lastname, String description, String telefon, LocalDate birthday, 
            Graduation grad, String street, String housenumber, String city, 
            String postalcode, String country, List<Requirement> fullfilledRequirements) {
        Login login = logins.findByEmail(email);
        if (login != null) {
            User_Profile profileToInsert = User_Profile.builder()
                    .salutation(salutation)
                    .title(title)
                    .firstname(firstname)
                    .lastname(lastname)
                    .description(description)
                    .telefon(telefon)
                    .grad(grad)
                    .fullfilledRequirements(fullfilledRequirements)
                    .build();
            Address addressToInsert = Address.builder()
                    .street(street)
                    .housenumber(housenumber)
                    .city(city)
                    .postalcode(postalcode)
                    .country(country)
                    .build();
            login.getSeekingUser().setProfile(profileToInsert);
            login.getSeekingUser().getProfile().setAddress(addressToInsert);
            login.getSeekingUser().setLogin(login);
            logins.edit(login);
            return true;
        }
        return false;
    }

    public boolean editUserProfile(String email, Salutation salutation, Title title, String firstname, String lastname, String description, String telefon) {
        Login login = logins.findByEmail(email);
        if (login != null) {
            User_Profile toEdit = login.getSeekingUser().getProfile();
            toEdit.setSalutation(salutation);
            toEdit.setTitle(title);
            toEdit.setFirstname(firstname);
            toEdit.setLastname(lastname);
            toEdit.setTelefon(telefon);
            userprofiles.edit(toEdit);
            return true;
        }
        return false;
    }
    
    public boolean editUserAddress(String email, String street, String housenumber, String city, String postalcode, String country){
        Login login = logins.findByEmail(email);
        if (login != null) {
            Address toEdit = login.getSeekingUser().getProfile().getAddress();
            toEdit.setStreet(street);
            toEdit.setHousenumber(housenumber);
            toEdit.setCity(city);
            toEdit.setPostalcode(postalcode);
            toEdit.setCountry(country);
            addresses.edit(toEdit);
            return true;
        }
        return false;
    }
    
    public boolean editUserQualifications(String email, Graduation grad, List<Requirement> fullfiledRequirements){
        Login login = logins.findByEmail(email);
        if (login != null) {
            User_Profile toEdit = login.getSeekingUser().getProfile();
            toEdit.setGrad(grad);
            toEdit.setFullfiledRequirements(fullfiledRequirements);
            userprofiles.edit(toEdit);
            return true;
        }
        return false;
    }

    
    public boolean editUserCredentials(String oldEmail, String newEmail, String newPassword) {
        Login login = logins.findByEmail(oldEmail);
        if (login != null) {
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

    public boolean deleteUser(String email) {
        Login login = logins.findByEmail(email);
        if (login != null) {
            logins.remove(login);
            return true;
        }
        return false;
    }

    public SeekingUser getUserByEmail(String email) {
        Login login = logins.findByEmail(email);
        if (login != null) {
            return login.getSeekingUser();
        }
        return null;
    }

    public void edit(SeekingUser value) {
        users.edit(value);
    }

}
