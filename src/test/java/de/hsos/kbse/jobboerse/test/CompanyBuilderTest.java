/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.test;

import de.hsos.kbse.jobboerse.ApplicationConfig;
import de.hsos.kbse.jobboerse.controllers.CompanyCreationController;
import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.facades.CompanyFacade;
import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author lennartwoltering
 */
@RunWith(Arquillian.class)
public class CompanyBuilderTest {

    @Deployment
    public static WebArchive createDeployment() {

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage(Company.class.getPackage())
                .addPackage(ApplicationConfig.class.getPackage())
                .addPackage(CompanyCreationController.class.getPackage())
                .addPackage(CompanyFacade.class.getPackage())
                .addPackage(Address.class.getPackage())
                .addPackage(SeekingUser.class.getPackage())
                .addPackage(Graduation.class.getPackage())
                .addPackage(CompanyRepository.class.getPackage())
                .addAsResource(new File("src/test/java/resources/test-persistence.xml"), "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/test/java/resources/glassfish-resources.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return war;

    }

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    @Inject
    private CompanyCreationController cmpyRegCntrl;

    @Inject
    private CompanyRepository cmpyRepo;
    
    @Inject
    private CompanyFacade cmpyFacade;

    @Inject
    private LoginFacade loginFacade;


    @Before
    public void clearData() throws Exception {
        utx.begin();
        System.out.println("Löschen der alten Datensätze...");
        
        List<Login> logins = loginFacade.findAll();
        logins.forEach(login -> {
            loginFacade.remove(login);
            
        });
        utx.commit();
    }

    @Test
    public void DatabaseShouldNotContainTestUser() {
        Assert.assertTrue(cmpyRegCntrl.checkEmailAvailability("test@test.de"));
    }

    @Test
    public void should_create_Login() throws Exception {
        String email = "test@test.de";
        String password = "12345";
        utx.begin();
        Assert.assertTrue(cmpyRegCntrl.createLogin(email, password));
        utx.commit();
        Login login = loginFacade.findByEmail(email);
        Assert.assertTrue(email.equals(login.getEmail()));
        Assert.assertFalse(password.equals(login.getPassword()));
        Assert.assertTrue(login.getGroup_name().equals("COMPANY"));
        Assert.assertFalse(cmpyRegCntrl.checkEmailAvailability("test@test.de"));
    }

    @Test
    public void should_create_Company() throws Exception {
        this.should_create_Login();
        utx.begin();
        cmpyRegCntrl.createProfile("TestName", "TestBeschreibung", WorkerCount.low, null, null)
                .createAddress("TestStreet", "TesthouseNumber123", "TestCity", "TestPostalCode123", "TestCountry")
                .createContact(Salutation.mister, Title.Empty, "TestFirstname", "TestLastname", "123123", "contact@contact.de")
                .finishRegistration(new ArrayList<>(), "test@test.de");
        Company toTest = cmpyRepo.getCompanyByEmail("test@test.de");
        Assert.assertEquals("Firmenname stimmt nicht überein", "TestName", toTest.getProfile().getName());
        Assert.assertEquals("Addresse stimmt nicht überein", "TestStreet", toTest.getProfile().getAddress().getStreet());
        Assert.assertEquals("Kontakt stimmt nicht überein", "TestFirstname", toTest.getProfile().getContact().getFirstname());
        cmpyFacade.remove(toTest);
        utx.commit();
    }

    @Test
    public void should_not_CreateTwoLoginsWithSameCredentials() throws Exception {
        Assert.assertTrue("Email nicht frei",cmpyRegCntrl.checkEmailAvailability("test@test.de"));
        String email = "test@test.de";
        String password = "12345";
        utx.begin();
        Assert.assertTrue("Login gibt nicht true zurück" , cmpyRegCntrl.createLogin(email, password));
        utx.commit();
        utx.begin();
        Assert.assertFalse("Login gibt nicht false zurück", cmpyRegCntrl.createLogin(email, password));
        utx.commit();
    }
}
