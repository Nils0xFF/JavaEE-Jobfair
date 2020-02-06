/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.company.CompanyProfile;
import de.hsos.kbse.jobboerse.entity.company.Contact;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.facades.AddressFacade;
import de.hsos.kbse.jobboerse.entity.facades.BenefitFacade;
import de.hsos.kbse.jobboerse.entity.facades.CompanyFacade;
import de.hsos.kbse.jobboerse.entity.facades.CompanyProfileFacade;
import de.hsos.kbse.jobboerse.entity.facades.ContactFacade;
import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.facades.PictureFacade;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.Picture;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import java.sql.SQLException;
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
 * @author soere
 */
@RequestScoped
@Transactional(rollbackOn = SQLException.class)
public class CompanyRepository {

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private LoginFacade loginf;
    @Inject
    private CompanyFacade companyf;
    @Inject
    private CompanyProfileFacade companyprofilef;
    @Inject
    private PictureFacade picturef;
    @Inject
    private AddressFacade addressf;
    @Inject
    private ContactFacade contactf;
    @Inject
    private BenefitFacade benefitf;

    @Inject
    private JobRepository jobRepo;
    @Inject
    private JobFieldRepository jobfieldRepo;
    @Inject
    private BenefitRepository benefitRepo;
    
    public CompanyRepository() {
    }

    public boolean checkEmailExists(String email) {
        try {
            Login login = loginf.findByEmail(email);
            if (login != null) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public void addPicture(String email, Picture toInsert) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            login.getCompany().getProfile().setProfilePicture(toInsert);
            loginf.edit(login);
        }
    }

    public boolean createCompany(CompanyProfile toInsert, String email) {
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
    
    public void createCompany(String email) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        login.getCompany().setLogin(login);
    }

    public boolean updateCompany(CompanyProfile toInsert, String email) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            login.getCompany().setProfile(toInsert);
            loginf.edit(login);
            return true;
        }

        return false;
    }

    public void createLogin(Login login) {
        loginf.create(login);
    }

    public List<Job> findJobsByCompany(String email) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            return login.getCompany().getJobs();
        }
        return null;
    }

    public boolean addJobtoCompany(String email, Job toInsert) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            toInsert.setCompany(login.getCompany());
            login.getCompany().getJobs().add(toInsert);
            loginf.edit(login);
            return true;
        }
        return false;
    }

    public void removeJobFromCompany(String email, Long Id) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            login.getCompany().getJobs().remove(jobRepo.find(Id));
            loginf.edit(login);
        }
    }

    public void updateJobOfCompany(String email, Long id, Job job) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        Job toEdit = jobRepo.find(id);
        if (toEdit.getDescription() != null) {
            toEdit.setDescription(toEdit.getDescription());
        }
        if (toEdit.getName() != null) {
            toEdit.setName(toEdit.getName());
        }
        if (toEdit.getRelation() != null) {
            toEdit.setRelation(toEdit.getRelation());
        }
        if (toEdit.getSalary() != null) {
            toEdit.setSalary(toEdit.getSalary());
        }
        jobRepo.update(id, job);
    }

    public void updateJobAddressOfCompany(String email, Long id, Address address) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        Job job = jobRepo.find(id);
        Address toEdit = job.getAddress();
        if (toEdit != null) {
            if (address.getCity() != null) {
                toEdit.setCity(address.getCity());
            }
            if (address.getCountry() != null) {
                toEdit.setCountry(address.getCountry());
            }
            if (address.getHousenumber() != null) {
                toEdit.setHousenumber(address.getHousenumber());
            }
            if (address.getPostalcode() != null) {
                toEdit.setPostalcode(address.getPostalcode());
            }
            if (address.getStreet() != null) {
                toEdit.setStreet(address.getStreet());
            }
        } else {
            job.setAddress(address);
        }
        jobRepo.update(id, job);
    }

    public void updateJobJobFieldOfCompany(String email, Long id, JobField jobfield) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        Job job = jobRepo.find(id);
        JobField toEdit = job.getJobfield();
        if (jobfield.getName() != null) {
            job.setJobfield(jobfieldRepo.findByName(jobfield.getName()));            
        }
        jobRepo.update(id, job);
    }

    //Erster Ansatz; Überarbeitet. Registration Controller übernimmt das Erstellen der Klassen.
    public boolean createLogin(String email, String password) throws Exception {
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

    public void createCompany(Login toInsert) throws EntityExistsException {
        if (!checkEmailExists(toInsert.getEmail())) {
            loginf.create(toInsert);
        } else {
            throw new EntityExistsException("Company already exists!");
        }
    }

    public boolean createCompanyProfile(/* General Information */String email, String name, String description, WorkerCount workercount,
            /* Address */ String street, String housenumber, String city, String postalcode, String country,
            /* Contact */ Salutation salutation, Title title, String firstname, String lastname, String contactemail, String phone) throws IllegalArgumentException {
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

    public void createCompanyProfile(String email, CompanyProfile profile) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        if (profile.getAddress() == null) {
            profile.setAddress(new Address());
        }
        if (profile.getContact() == null) {
            profile.setContact(new Contact());
        }
        login.getCompany().setProfile(profile);
        login.getCompany().setLogin(login);
        login.getCompany().setCompleted(true);
        loginf.edit(login);
    }

    public boolean updateCompanyProfile(String email, String name, String description, WorkerCount workercount, Address address, Contact contact) throws IllegalArgumentException {
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

    public boolean updateCompanyProfile(String email, String name, String description, WorkerCount workercount) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            CompanyProfile toEdit = login.getCompany().getProfile();
            toEdit.setName(name);
            toEdit.setDescription(description);
            toEdit.setWorkercount(workercount);
            companyprofilef.edit(toEdit);
            return true;
        }
        return false;
    }

    public void updateCompanyProfile(String email, CompanyProfile profile) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        CompanyProfile toEdit = login.getCompany().getProfile();
        if (profile.getDescription() != null) {
            toEdit.setDescription(profile.getDescription());
        }
        if (profile.getName() != null) {
            toEdit.setName(profile.getName());
        }
        if (profile.getWorkercount() != null) {
            toEdit.setWorkercount(profile.getWorkercount());
        }
        companyprofilef.edit(profile);
    }

    public boolean updateCompanyAddress(String email, String street, String housenumber, String city, String postalcode, String country) throws IllegalArgumentException {
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

    public void updateCompanyAddress(String email, Address address) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        Address toEdit = login.getCompany().getProfile().getAddress();
        if (address.getCity() != null) {
            toEdit.setCity(address.getCity());
        }
        if (address.getCountry() != null) {
            toEdit.setCountry(address.getCountry());
        }
        if (address.getHousenumber() != null) {
            toEdit.setHousenumber(address.getHousenumber());
        }
        if (address.getPostalcode() != null) {
            toEdit.setPostalcode(address.getPostalcode());
        }
        if (address.getStreet() != null) {
            toEdit.setStreet(address.getStreet());
        }
        addressf.edit(address);
    }

    public boolean updateCompanyContact(String email, String contactemail, Salutation salutation, Title title, String firstname, String lastname, String phone) {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            Contact toEdit = login.getCompany().getProfile().getContact();
            toEdit.setEmail(contactemail);
            toEdit.setSalutation(salutation);
            toEdit.setTitle(title);
            toEdit.setFirstname(firstname);
            toEdit.setLastname(lastname);
            toEdit.setPhone(phone);
            contactf.edit(toEdit);
            return true;
        }
        return false;
    }

    public void updateCompanyContact(String email, Contact contact) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        Contact toEdit = login.getCompany().getProfile().getContact();
        if (contact.getEmail() != null) {
            toEdit.setEmail(contact.getEmail());
        }
        if (contact.getFirstname() != null) {
            toEdit.setFirstname(contact.getFirstname());
        }
        if (contact.getLastname() != null) {
            toEdit.setLastname(contact.getLastname());
        }
        if (contact.getPhone() != null) {
            toEdit.setPhone(contact.getPhone());
        }
        if (contact.getSalutation() != null) {
            toEdit.setSalutation(contact.getSalutation());
        }
        if (contact.getTitle() != null) {
            toEdit.setTitle(contact.getTitle());
        }
        contactf.edit(contact);
    }

    public void updateCompanyQualifications(String email, List<Benefit> benefits) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        CompanyProfile toEdit = login.getCompany().getProfile();
        toEdit.setBenefits(benefits);
        companyprofilef.edit(toEdit);
    }

    public boolean addCompanyBenefit(String email, Benefit benefit) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        if (login != null) {
            CompanyProfile toEdit = login.getCompany().getProfile();
            for (Benefit bene : toEdit.getBenefits()) {
                if (benefit.getName().equals(bene.getName())) {
                    throw new IllegalArgumentException("\"" + benefit.getName() + "\" already exists in Companybenefits!");
                }
            }
            toEdit.addBenefit(benefitf.findByName(benefit.getName()));
            companyprofilef.edit(toEdit);
            return true;
        }
        return false;
    }

    public void removeCompanyBenefit(String email, Long id) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        CompanyProfile toEdit = login.getCompany().getProfile();
        toEdit.removeBenefit(benefitRepo.find(id));        
        companyprofilef.edit(toEdit);
    }

    public void updateCompanyCredentials(String oldEmail, String newEmail, String newPassword) throws IllegalArgumentException {
        Login login = loginf.findByEmail(oldEmail);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
        login.setEmail(newEmail);
        login.setPassword(passwordHash.generate(newPassword.toCharArray()));
        loginf.edit(login);
    }

    public void deleteCompany(Long id) throws IllegalArgumentException {
        Login login = loginf.find(id);
        loginf.remove(login);
    }

    public void deleteCompany(String email) throws IllegalArgumentException {
        Login login = loginf.findByEmail(email);
        loginf.remove(login);
    }

    public Collection<Company> getAllCompanies() {
        return companyf.findAll();
    }

    public Company getCompany(Long id) throws IllegalArgumentException {
        return companyf.find(id);
    }

    public Company getCompanyByEmail(String email) throws IllegalArgumentException {
        return loginf.findByEmail(email).getCompany();
    }

    public Collection<Benefit> getAllCompanyBenefits(String email) {
        return loginf.findByEmail(email).getCompany().getProfile().getBenefits();
    }

    public Collection<Job> getAllCompanyJobs(String email) {
        return loginf.findByEmail(email).getCompany().getJobs();
    }

    public void update(Company value) throws IllegalArgumentException {
        companyf.edit(value);
    }

}
