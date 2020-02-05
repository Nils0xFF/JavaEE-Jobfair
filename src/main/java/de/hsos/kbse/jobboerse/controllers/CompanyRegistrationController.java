/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.controllers;

import de.hsos.kbse.jobboerse.entity.company.CompanyProfile;
import de.hsos.kbse.jobboerse.entity.company.Contact;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.company.Company;
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
public class CompanyRegistrationController {

    @Inject
    private CompanyRepository companyRepo;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    private Login login;
    private CompanyProfile profile;
    private Address address;
    private Contact contact;
    private Picture picture;

    public boolean checkEmailAvailability(String email) {
        return !companyRepo.checkEmailExists(email);
    }

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

    public CompanyRegistrationController createAddress(String street, String housenumber, String city,
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

    public CompanyRegistrationController createContact(Salutation salutation, Title title, String firstname, String lastname, String phone, String contactEmail) {
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

    public CompanyRegistrationController createProfile(String name, String description, WorkerCount workercount, byte[] pictureData, String dataType) {
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

    public boolean finishRegistration(List<Benefit> benefits, String email) {
        profile.setBenefits(benefits);
        return companyRepo.createCompany(profile, email);

    }

    public boolean finishUpdating(List<Benefit> benefits, String email) {
        profile.setBenefits(benefits);
        return companyRepo.updateCompany(profile, email);
    }

}
