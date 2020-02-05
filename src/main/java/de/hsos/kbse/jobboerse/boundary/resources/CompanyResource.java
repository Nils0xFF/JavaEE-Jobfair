/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

// import de.hsos.kbse.jobboerse.annotations.Secured;
import de.hsos.kbse.jobboerse.annotations.Identificate;
import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.company.CompanyProfile;
import de.hsos.kbse.jobboerse.entity.company.Contact;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import java.util.Collection;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
    
    @GET
    @Path("id/{id}")
    @RolesAllowed({"ADMIN"})
    public Response getCompany(@PathParam("id") Long id) {
        try {
            return Response.ok(companyRepo.getCompany(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build(); 
        }
    }
    
    @GET
    @Path("email/{email}")
    @RolesAllowed({"ADMIN"})
    public Response getCompany(@PathParam("email") String email) {
        try {
            return Response.ok(companyRepo.getCompanyByEmail(email)).build();
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
            
            return Response.ok(all).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/id/{id}")
    @RolesAllowed({"ADMIN"})
    public Response updateCompany(@PathParam("id") Long id, Company comp) {
        try {
            companyRepo.update(comp);
            return Response.ok(companyRepo.getCompany(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/email/{email}")
    @RolesAllowed({"ADMIN"})
    public Response updateCompany(@PathParam("email") String email, Company comp) {
        try {
            companyRepo.update(comp);
            return Response.ok(companyRepo.getCompanyByEmail(email)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/login")
    @Identificate
    public Response updateCompanyCredentials(@HeaderParam("user") String email, Login newLogin) {
        try {
            companyRepo.updateCompanyCredentials(email, newLogin.getEmail(), newLogin.getPassword());
            return Response.ok(newLogin).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile")
    @Identificate
    public Response updateCompanyProfile(@HeaderParam("user") String email, CompanyProfile profile) {
        try {
            companyRepo.updateCompanyProfile(email, profile);
            return Response.ok(companyRepo.getCompanyByEmail(email).getProfile()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile/address")
    @Identificate
    public Response updateCompanyAddress(@HeaderParam("user") String email, Address address) {
        try {
            companyRepo.updateCompanyAddress(email, address);
            return Response.ok(companyRepo.getCompanyByEmail(email).getProfile().getAddress()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile/contact")
    @Identificate
    public Response updateCompanyContact(@HeaderParam("user") String email, Contact contact) {
        try {
            companyRepo.updateCompanyContact(email, contact);
            return Response.ok(companyRepo.getCompanyByEmail(email).getProfile().getContact()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
        
    }
    
    @PUT
    @Path("update/profile/benefits")
    @Identificate
    public Response updateCompanyQualifications(@HeaderParam("user") String email, List<Benefit> benefits) {
        try {
            companyRepo.updateCompanyQualifications(email, benefits);
            return Response.ok(companyRepo.getCompanyByEmail(email).getProfile().getBenefits()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile/benefits/add")
    @Identificate
    public Response addCompanyBenefit(@HeaderParam("user") String email, Benefit benefit) {
        try {
            companyRepo.addCompanyBenefit(email, benefit);
            return Response.ok(companyRepo.getCompanyByEmail(email).getProfile().getBenefits()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }        
    }
    
    @DELETE
    @Path("delete/profile/benefits/remove")
    @Identificate
    public Response removeCompanyBenefit(@HeaderParam("user") String email, Benefit benefit) {
        try {
            companyRepo.removeCompanyBenefit(email, benefit);
            return Response.ok(companyRepo.getCompanyByEmail(email).getProfile().getBenefits()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("create/profile")
    @Identificate
    public Response createCompanyProfile(@HeaderParam("user") String email, CompanyProfile profile) {
        try {
            companyRepo.createCompanyProfile(email, profile);
            return Response.ok(companyRepo.getCompanyByEmail(email).getProfile()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("delete/id/{id}")
    @RolesAllowed({"ADMIN"})
    public Response deleteCompany(@PathParam("id") Long id) {
        try {
            companyRepo.deleteCompany(id);
            return Response.ok("Company deleted!").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("delete")
    @Identificate
    public Response deleteCompany(@HeaderParam("email") String email) {
        try {
            companyRepo.deleteCompany(email);
            return Response.ok("Company deleted!").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    
    @POST
    @Path("create/job")
    @Identificate
    public Response addJob(@HeaderParam("email") String email, Job job) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    
    @DELETE
    @Path("delete/job/{email}")
    @Identificate
    public Response removeJob(@PathParam("email") String email) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
