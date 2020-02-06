/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

// import de.hsos.kbse.jobboerse.annotations.Secured;
import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.company.CompanyProfile;
import de.hsos.kbse.jobboerse.entity.company.Contact;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author soere
 */
@RequestScoped
@Path("company")
@RolesAllowed({"ADMIN", "COMPANY"})
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CompanyResource {

    @Inject
    private CompanyRepository companyRepo;
    
    @Inject
    private Jsonb jsonb;
    @Inject
    private SecurityContext securityContext; 
    
    @GET
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
    public Response getCompany(@PathParam("id") Long id) {
        try {
            return Response.ok(jsonb.toJson(companyRepo.getCompany(id))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build(); 
        }
    }
    
    @GET
    @Path("email/{email}")
    @RolesAllowed({"ADMIN"})
    public Response getCompany(@PathParam("email") String email) {
        try {
            return Response.ok(jsonb.toJson(companyRepo.getCompanyByEmail(email))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @RolesAllowed({"ADMIN"})
    public Response getAllCompanies() {
        try {
            Collection<Company> all = companyRepo.getAllCompanies();
            if (all.isEmpty()) {
                return Response.noContent().build();
            }
            
            return Response.ok(jsonb.toJson(all)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("benefits")
    public Response getCompanyBenefits() {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            Collection<Benefit> all = companyRepo.getAllCompanyBenefits(email);
            if (all.isEmpty()) {
                return Response.noContent().build();
            }
            
            return Response.ok(jsonb.toJson(all)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }        
    }
    
    @PUT
    @Path("update/profile")
    public Response updateCompanyProfile(CompanyProfile profile) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.updateCompanyProfile(email, profile);
            return Response.ok(jsonb.toJson(companyRepo.getCompanyByEmail(email).getProfile())).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile/address")
    public Response updateCompanyAddress(Address address) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.updateCompanyAddress(email, address);
            return Response.ok(jsonb.toJson(companyRepo.getCompanyByEmail(email).getProfile().getAddress())).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile/contact")
    public Response updateCompanyContact(Contact contact) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.updateCompanyContact(email, contact);
            return Response.ok(jsonb.toJson(companyRepo.getCompanyByEmail(email).getProfile().getContact())).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }        
    }    
    
    @PUT
    @Path("benefits/add")
    public Response addCompanyBenefit(Benefit benefit) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.addCompanyBenefit(email, benefit);
            return Response.ok(jsonb.toJson(companyRepo.getAllCompanyBenefits(email))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }        
    }
    
    @DELETE
    @Path("benefits/remove/{id}")
    public Response removeCompanyBenefit(@PathParam("id") Long id) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.removeCompanyBenefit(email, id);
            return Response.ok().build();
        } catch (Exception ex) {            
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("create/profile")
    public Response createCompanyProfile(CompanyProfile profile) {
        String email = securityContext.getCallerPrincipal().getName();
        try {            
            companyRepo.createCompanyProfile(email, profile);
            return Response.ok(jsonb.toJson(companyRepo.getCompanyByEmail(email).getProfile())).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }    
    
    @GET
    @Path("jobs")
    public Response getAllCompanyJobs()  {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            Collection<Job> all = companyRepo.getAllCompanyJobs(email);
            if (all.isEmpty()) {
                return Response.noContent().build();
            }
            
            return Response.ok(jsonb.toJson(all)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }   
    }
    
    @POST
    @Path("jobs/create")
    public Response addJob(Job job) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.addJobtoCompany(email, job);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("jobs/update/{id}")
    public Response updateJob(@PathParam("id") Long id, Job job) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.updateJobOfCompany(email, id, job);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("jobs/update/address/{id}")
    public Response updateJobAddress(@PathParam("id") Long id, Address address) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.updateJobAddressOfCompany(email, id, address);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("jobs/update/jobfield/{id}")
    public Response updateJobJobField(@PathParam("id") Long id, JobField jobfield) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.updateJobJobFieldOfCompany(email, id, jobfield);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("jobs/remove/{id}")
    public Response removeJob(@PathParam("id") Long id) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            companyRepo.removeJobFromCompany(email, id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
}
