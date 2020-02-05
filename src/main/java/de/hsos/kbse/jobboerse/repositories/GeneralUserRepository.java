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
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.User_Profile;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
@Transactional(rollbackOn = {SQLException.class})
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
        try {
            Login login = logins.findByEmail(email);
            if (login != null) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }        
    }

    public boolean createUser(User_Profile toInsert, SearchRequest searchInsert, String email) throws EntityExistsException {
        Login login = logins.findByEmail(email);
        if (login != null) {
            login.getSeekingUser().setProfile(toInsert);
            login.getSeekingUser().setSearchrequest(searchInsert);
            login.getSeekingUser().setLogin(login);
            login.getSeekingUser().setCompleted(true);
            try {
                logins.edit(login);
            } catch (IllegalArgumentException ex) {
                throw new EntityExistsException("User already exists!");
            }
            return true;
        }
        return false;
    }

    public void createUser(Login toInsert) throws EntityExistsException {
        if (!checkEmailExists(toInsert.getEmail())) {
            logins.create(toInsert);
        } else {
            throw new EntityExistsException("User already exists!");
        }
    }

    public boolean updateUser(User_Profile toInsert, String email) throws IllegalArgumentException {
        Login login = logins.findByEmail(email);
        if (login != null) {
            login.getSeekingUser().setProfile(toInsert);
            logins.edit(login);
            return true;
        }
        return false;
    }

    public void createLogin(Login login) throws EntityExistsException {
        logins.create(login);
    }

    //Erster Ansatz; Überarbeitet. Registration Controller übernimmt das Erstellen der Klassen.
    public boolean createLogin(String email, String password) throws EntityExistsException {
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
            String postalcode, String country, List<Requirement> fullfilledRequirements) throws EntityExistsException {
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

    public void createUserProfile(String email, User_Profile profile) throws IllegalArgumentException {
        Login login = logins.findByEmail(email);
        if (login != null) {
            login.getSeekingUser().setProfile(profile);
            login.getSeekingUser().setLogin(login);
            logins.edit(login);
        } else {
            throw new IllegalArgumentException("User not found!");
        }
    }

    public void editUserProfile(String email, Salutation salutation, Title title, String firstname, String lastname, String description, String telefon) throws IllegalArgumentException {
        Login login = logins.findByEmail(email);
        if (login != null) {
            User_Profile toEdit = login.getSeekingUser().getProfile();
            toEdit.setSalutation(salutation);
            toEdit.setTitle(title);
            toEdit.setFirstname(firstname);
            toEdit.setLastname(lastname);
            toEdit.setTelefon(telefon);
            userprofiles.edit(toEdit);
        } else {
            throw new IllegalArgumentException("User not found!");
        }
    }

    public boolean editUserProfile(String email, User_Profile profile) throws IllegalArgumentException {
        Login login = logins.findByEmail(email);
        if (login != null) {
            User_Profile toEdit = login.getSeekingUser().getProfile();
            profile.setId(toEdit.getId());
            userprofiles.edit(profile);
            return true;
        }
        return false;
    }

    public void editUserAddress(String email, String street, String housenumber, String city, String postalcode, String country) throws IllegalArgumentException {
        Login login = logins.findByEmail(email);
        Address toEdit = login.getSeekingUser().getProfile().getAddress();
        toEdit.setStreet(street);
        toEdit.setHousenumber(housenumber);
        toEdit.setCity(city);
        toEdit.setPostalcode(postalcode);
        toEdit.setCountry(country);
        addresses.edit(toEdit);
    }

    public void editUserQualifications(String email, Graduation grad, List<Requirement> fullfiledRequirements) throws IllegalArgumentException {
        Login login = logins.findByEmail(email);
        User_Profile toEdit = login.getSeekingUser().getProfile();
        toEdit.setGrad(grad);
        toEdit.setFullfiledRequirements(fullfiledRequirements);
        userprofiles.edit(toEdit);
    }

    public void editUserCredentials(String oldEmail, String newEmail, String newPassword) throws IllegalArgumentException {
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
        } else {
            throw new IllegalArgumentException("User not found!");
        }
    }

    public void deleteUser(String email) throws IllegalArgumentException {
        Login login = logins.findByEmail(email);
        logins.remove(login);
    }

    public Collection<SeekingUser> getAllUsers() {
        return users.findAll();
    }

    public SeekingUser getUser(Long id) throws IllegalArgumentException {
        Login login = logins.find(id);
        return login.getSeekingUser();
    }

    public SeekingUser getUserByEmail(String email) throws IllegalArgumentException {
        return logins.findByEmail(email).getSeekingUser();
    }

    public void edit(SeekingUser value) throws IllegalArgumentException {
        users.edit(value);
    }

}
