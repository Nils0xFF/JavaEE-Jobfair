/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.test;

import de.hsos.kbse.jobboerse.ApplicationConfig;
import de.hsos.kbse.jobboerse.controllers.CompanyRegistrationController;
import de.hsos.kbse.jobboerse.controllers.UserRegistrationController;
import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.facades.CompanyFacade;
import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.facades.SeekingUserFacade;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
public class UserBuilderTest {
    

    @Deployment
    public static WebArchive createDeployment() {

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage(Company.class.getPackage())
                .addPackage(ApplicationConfig.class.getPackage())
                .addPackage(CompanyRegistrationController.class.getPackage())
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
    UserRegistrationController userRegCntrl;

    @Inject
    GeneralUserRepository userRepo;
    
    @Inject
    SeekingUserFacade userFacade;

    @Inject
    LoginFacade loginFacade;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
    }

    private void clearData() throws Exception {
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
        Assert.assertTrue(userRegCntrl.checkEmailAvailability("test@test.de"));
    }

    @Test
    public void should_create_Login() throws Exception {
        String email = "test@test.de";
        String password = "12345";
        utx.begin();
        Assert.assertTrue(userRegCntrl.createLogin(email, password));
        utx.commit();
        Login login = (Login) loginFacade.findByEmail(email);
        Assert.assertTrue(email.equals(login.getEmail()));
        Assert.assertFalse(password.equals(login.getPassword()));
        Assert.assertTrue(login.getGroup_name().equals("USER"));
        Assert.assertFalse(userRegCntrl.checkEmailAvailability("test@test.de"));
    }

    @Test
    public void should_create_User() throws Exception {
        this.should_create_Login();
        utx.begin();
        userRegCntrl.createUserProfile(Salutation.mister, Title.Empty, "TestFirstName", "TestLastName", "TestTelefon1234", new Date())
                .createAddress("TestStreet", "TestHousenumber123", "TestCity", "TestPostal123", "TestCountry")
                .createQualifications(Graduation.Real, new ArrayList<>(), "TestDesc")
                .finishRegistration("test@test.de");
        SeekingUser toTest = userRepo.getUserByEmail("test@test.de");
        Assert.assertEquals("Firmenname stimmt nicht überein", "TestFirstName", toTest.getProfile().getFirstname());
        Assert.assertEquals("Addresse stimmt nicht überein", "TestStreet", toTest.getProfile().getAddress().getStreet());
        Assert.assertEquals("Kontakt stimmt nicht überein", "TestDesc", toTest.getProfile().getDescription());
        userFacade.remove(toTest);
        utx.commit();
    }

    @Test
    public void should_not_CreateTwoLoginsWithSameCredentials() throws Exception {
        Assert.assertTrue("Email nicht frei",userRegCntrl.checkEmailAvailability("test@test.de"));
        String email = "test@test.de";
        String password = "12345";
        utx.begin();
        Assert.assertTrue("Login gibt nicht true zurück" , userRegCntrl.createLogin(email, password));
        utx.commit();
        utx.begin();
        Assert.assertFalse("Login gibt nicht false zurück", userRegCntrl.createLogin(email, password));
        utx.commit();
    }
}
