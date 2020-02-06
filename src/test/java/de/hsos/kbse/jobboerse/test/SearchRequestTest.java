
package de.hsos.kbse.jobboerse.test;

import de.hsos.kbse.jobboerse.ApplicationConfig;
import de.hsos.kbse.jobboerse.algorithm.BasicMatchingAlgorithm;
import de.hsos.kbse.jobboerse.algorithm.MatchingAlgorithm;
import de.hsos.kbse.jobboerse.algorithm.qualifiers.Basic;
import de.hsos.kbse.jobboerse.algorithm.qualifiers.Weighted;
import de.hsos.kbse.jobboerse.controllers.CompanyRegistrationController;
import de.hsos.kbse.jobboerse.controllers.JobCreationController;
import de.hsos.kbse.jobboerse.controllers.UserRegistrationController;
import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.facades.CompanyFacade;
import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.facades.SeekingUserFacade;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Sal_Relation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import de.hsos.kbse.jobboerse.repositories.SearchRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author lennartwoltering
 */
@RunWith(Arquillian.class)
@ApplicationScoped
public class SearchRequestTest {

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
                .addPackage(BasicMatchingAlgorithm.class.getPackage())
                .addPackage(Basic.class.getPackage())
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
    private CompanyRegistrationController cmpyRegCntrl;

    @Inject
    private UserRegistrationController userRegCntrl;

    @Inject
    private GeneralUserRepository userRepo;

    @Inject
    private SeekingUserFacade userFacade;

    @Inject
    private CompanyRepository cmpyRepo;

    @Inject
    private CompanyFacade cmpyFacade;

    @Inject
    private LoginFacade loginFacade;

    @Inject
    private JobFieldRepository jobFieldRepo;

    @Inject
    private RequirementRepository requirementRepo;

    @Inject
    private BenefitRepository benefitRepo;

    @Inject
    private JobCreationController jobCntrl;
    
    @Inject @Basic
    private MatchingAlgorithm basicMatching;
    
    @Inject @Weighted
    private MatchingAlgorithm weightedMatching;
    
    @Inject
    private SearchRepository searchRepo;
    

    @Test
    @InSequence(1)
    public void should_create_CompanyLogin() throws Exception {
        String email = "company@test.de";
        String password = "12345";
        utx.begin();
        Assert.assertTrue(cmpyRegCntrl.createLogin(email, password));
        utx.commit();
        Login login = loginFacade.findByEmail(email);
        Assert.assertTrue(email.equals(login.getEmail()));
        Assert.assertFalse(password.equals(login.getPassword()));
        Assert.assertTrue(login.getGroup_name().equals("COMPANY"));
        Assert.assertFalse(userRegCntrl.checkEmailAvailability("company@test.de"));
    }

    @Test
    @InSequence(2)
    public void should_create_UserLogin() throws Exception {
        String email = "user@test.de";
        String password = "12345";
        utx.begin();
        Assert.assertTrue(userRegCntrl.createLogin(email, password));
        utx.commit();
        
        Login login = loginFacade.findByEmail(email);
        Assert.assertTrue(email.equals(login.getEmail()));
        Assert.assertFalse(password.equals(login.getPassword()));
        Assert.assertTrue(login.getGroup_name().equals("USER"));
        Assert.assertFalse(userRegCntrl.checkEmailAvailability("user@test.de"));
    }

    @Test
    @InSequence(3)
    public void should_create_Benefits() throws Exception {
        utx.begin();
        List<Benefit> benefits = benefitRepo.findAll();
        Assert.assertTrue("Es dürfen zu Anfang keine Benefits vorhanden sein", benefits.isEmpty());
        benefitRepo.create("Benefit1", "Benefit1_DESC");
        benefitRepo.create("Benefit2", "Benefit2_DESC");
        benefits = benefitRepo.findAll();
        Assert.assertTrue("Anzahl der eingefügten Benefits stimmt nicht überein", benefits.size() == 2);
        utx.commit();
    }

    @Test
    @InSequence(4)
    public void should_create_Requirements() throws Exception {
        utx.begin();
        List<Requirement> requirements = requirementRepo.findAll();
        Assert.assertTrue("Es dürfen zu Anfang keine Requirements vorhanden sein", requirements.isEmpty());
        requirementRepo.create("Requirement1", "Requirement1_DESC");
        requirementRepo.create("Requirement2", "Requirement2_DESC");
        requirements = requirementRepo.findAll();
        Assert.assertTrue("Anzahl der eingefügten Requirements stimmt nicht überein", requirements.size() == 2);
        utx.commit();
    }

    @Test
    @InSequence(5)
    public void should_create_Jobfields() throws Exception {
        utx.begin();
        List<JobField> jobfields = jobFieldRepo.findAll();
        Assert.assertTrue("Es dürfen zu Anfang keine Branchen vorhanden sein", jobfields.isEmpty());
        jobFieldRepo.create("JobField1");
        jobFieldRepo.create("JobField2");
        jobfields = jobFieldRepo.findAll();
        Assert.assertTrue("Anzahl der eingefügten Branchen stimmt nicht überein", jobfields.size() == 2);
        utx.commit();
    }

    @Test
    @InSequence(6)
    public void should_create_CompanyAndJob() throws Exception {
        utx.begin();
        cmpyRegCntrl.createProfile("TestName", "TestBeschreibung", WorkerCount.low, null, null)
                .createAddress("TestStreet", "TesthouseNumber123", "TestCity", "TestPostalCode123", "TestCountry")
                .createContact(Salutation.mister, Title.Empty, "TestFirstname", "TestLastname", "123123", "contact@contact.de")
                .finishRegistration(benefitRepo.findAll(), "company@test.de");
        List<NeededRequirement> requirements = new ArrayList<>();
        List<Requirement> plainRequirements = requirementRepo.findAll();
        plainRequirements.forEach(req -> {
            NeededRequirement needed = NeededRequirement.builder().requirement(req).weight(2).build();
            requirements.add(needed);
        });
        jobCntrl.createInfo("TestJob", "TestJobDesc", jobFieldRepo.findAll().get(0), requirements, 10.0d, Sal_Relation.HT)
                .createAddress("TestJobStreet", "TestJobHousenumber", "TestJobCity", "TestJobPostal", "TestJobCountry")
                .finishCreation("company@test.de");
        Company toTest = cmpyRepo.getCompanyByEmail("company@test.de");
        Assert.assertTrue(toTest.getJobs().size() == 1);
        Assert.assertEquals("TestJob", toTest.getJobs().get(0).getName());
        utx.commit();

    }
    
    @Test
    @InSequence(7)
    public void should_create_SearchingUser() throws Exception{
        List<Requirement> fullfilledRequirements = new ArrayList<>();
        fullfilledRequirements.add(requirementRepo.findAll().get(0));
        utx.begin();
        userRegCntrl.createUserProfile(Salutation.mister, Title.Empty, "TestFirstName", "TestLastName", "TestTelefon1234", new Date())
                .createAddress("TestStreet", "TestHousenumber123", "TestCity", "TestPostal123", "TestCountry")
                .createQualifications(Graduation.Real, fullfilledRequirements, "TestDesc")
                .setupSearchParameters(benefitRepo.findAll(), jobFieldRepo.findAll())
                .finishRegistration("user@test.de");
        utx.commit();
        SeekingUser toTest = userRepo.getUserByEmail("user@test.de");
        Assert.assertTrue(toTest.getSearchrequest().getJobfield().size() == 2);
        Assert.assertTrue(toTest.getSearchrequest().getWishedBenefits().size() == 2);
        
    }
    
    @Test
    @InSequence(8)
    public void should_retrieve_SearchResults() throws Exception{
        List<WeightedJob> jobs = basicMatching.findSuitableJobs("user@test.de");
        Assert.assertTrue(jobs.size() == 1);
        Assert.assertEquals(100.0, jobs.get(0).getBenefitPercentage(), 0.1);
        Assert.assertEquals(50.0, jobs.get(0).getRequirementPercentage(), 0.1);
        Assert.assertEquals(75.0, jobs.get(0).getTotalPercentage(), 0.1);
    }
    
    @Test
    @InSequence(9)
    public void should_retrieve_WeightedSearchResults() throws Exception{
        List<WeightedJob> jobs = weightedMatching.findSuitableJobs("user@test.de");
        Assert.assertTrue(jobs.size() == 1);
        Assert.assertEquals(100.0, jobs.get(0).getBenefitPercentage(), 0.1);
        Assert.assertEquals(75.0, jobs.get(0).getRequirementPercentage(), 0.1);
        Assert.assertEquals(87.5, jobs.get(0).getTotalPercentage(), 0.1);
    }

}
