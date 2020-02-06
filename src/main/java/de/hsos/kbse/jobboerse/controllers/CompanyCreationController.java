/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.controllers;

import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.company.CompanyProfile;
import de.hsos.kbse.jobboerse.entity.company.Contact;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.Picture;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
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
public class CompanyCreationController {

    @Inject
    private CompanyRepository companyRepo;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    private Login login;
    private CompanyProfile profile;
    private Address address;
    private Contact contact;
    private Picture picture;

    /**
     * Checks the Availability of a Email in the Database
     * 
     * @param email the email address that should be checked
     * @return returns true if the email is available
     */
    public boolean checkEmailAvailability(String email) {
        return !companyRepo.checkEmailExists(email);
    }

    /**
     * Tries to create a new Company Login in the Database.
     * It hashes the password with pbkdf2 and uses COMPANY as
     * group_name.
     * 
     * @param email the email that should be persisted
     * @param password the password that should be persisted
     * @return returns true if the Login was successfull created
     */
    public boolean createLogin(String email, String password) {
        if (checkEmailAvailability(email)) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
            parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
            parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
            passwordHash.initialize(parameters);
            login = Login.builder()
                    .email(email)
                    .password(passwordHash.generate(password.toCharArray()))
                    .company(new Company())
                    .group_name("COMPANY")
                    .build();
            companyRepo.createLogin(login);
            return true;
        }
        return false;
    }

    /**
     * Creates a address and saves it in address field.
     * !!!Must create a Profile first!!!
     * 
     * @param street street where the company is located
     * @param housenumber housenumber of the company
     * @param city city where the company is located
     * @param postalcode postalcode of the city
     * @param country country where the company is located
     * @return returns a reference to itself
     */
    public CompanyCreationController createAddress(String street, String housenumber, String city,
            String postalcode, String country) {
        address = Address.builder()
                .street(street)
                .housenumber(housenumber)
                .city(city)
                .postalcode(postalcode)
                .country(country)
                .build();
        profile.setAddress(address);
        return this;
    }

    /**
     *Creates a contact and saves it in contact field.
     * !!!Must create a Profile first!!!
     * 
     * @param salutation An enum for the salutation of the contact
     * @param title An enum for the title of the contact
     * @param firstname Represents the firstname of the contact
     * @param lastname Represents the lastname of the contact
     * @param phone Represents the phonenumber of the contact
     * @param contactEmail Represents the email address of the contact
     * @return returns a reference to itself
     */
    public CompanyCreationController createContact(Salutation salutation, Title title, String firstname, String lastname, String phone, String contactEmail) {
        contact = Contact.builder().salutation(salutation)
                .title(title)
                .firstname(firstname)
                .lastname(lastname)
                .email(contactEmail)
                .phone(phone)
                .email(contactEmail)
                .build();
        profile.setContact(contact);
        return this;
    }

    /**
     * Creates a Company Profile and saves it in profile field.
     * @param name Represents the name of the company
     * @param description Represents the description of the company
     * @param workercount An enum for the workercount of the company
     * @param pictureData Bytes for the companypicture
     * @param dataType Datatype of the companypicture
     * @return returns a reference to itself
     */
    public CompanyCreationController createProfile(String name, String description, WorkerCount workercount, byte[] pictureData, String dataType) {
        if (pictureData != null) {
            picture = Picture.builder()
                    .data(pictureData)
                    .dataType(dataType)
                    .build();
        }
        profile = CompanyProfile.builder()
                .name(name)
                .description(description)
                .workercount(workercount)
                .profilePicture(picture)
                .build();
        return this;
    }

    /**
     * Completes the Registration process
     * 
     * @param benefits Wished Company benefits
     * @param email email that represents the company
     * @return returns true if the email is found and the profile can be created
     */
    public boolean finishRegistration(List<Benefit> benefits, String email) {
        profile.setBenefits(benefits);
        return companyRepo.createCompany(profile, email);

    }

    /**
     * Updates the Updating Process
     * 
     * @param benefits Wished Company benefits
     * @param email email that represents the company
     * @return returns true if the email is found and the profile can be created
     */
    public boolean finishUpdating(List<Benefit> benefits, String email) {
        profile.setBenefits(benefits);
        return companyRepo.updateCompany(profile, email);
    }

}
