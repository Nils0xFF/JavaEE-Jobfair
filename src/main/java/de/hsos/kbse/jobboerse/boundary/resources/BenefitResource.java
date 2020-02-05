/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
@Path("benefits")
@RolesAllowed({"ADMIN"})
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class BenefitResource {
    
    @Inject
    private BenefitRepository benefitRepo;
    
    @GET
    public Response getAllBenefits() {        
        try {
            Collection<Benefit> all = benefitRepo.findAll();
            if (all.isEmpty()) {
                return Response.noContent().build();
            }
            
            return Response.ok(all).build();
        } catch (Exception ex) {            
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("id/{id}")
    public Response getBenefit(@PathParam("id") Long id) {
        try {            
            return Response.ok(benefitRepo.find(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("name/{name}")
    public Response getBenefit(@PathParam("name") String name) {
        try {            
            return Response.ok(benefitRepo.findByName(name)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("create")
    public Response createBenefit(Benefit benefit) {
        try {            
            return Response.ok(benefitRepo.create(benefit.getName(), benefit.getDescription())).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/id/{id}")
    public Response updateBenefit(@PathParam("id") Long id, Benefit benefit) {
        try {
            benefitRepo.update(id, benefit.getName(), benefit.getDescription());
            return Response.ok(benefitRepo.find(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/name/{name}")
    public Response updateBenefit(@PathParam("name") String name, Benefit benefit) {
        try {
            benefitRepo.update(name, benefit.getName(), benefit.getDescription());
            return Response.ok(benefitRepo.findByName(name)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("delete/id/{id}")
    public Response deleteBenefit(@PathParam("id") Long id) {
        try {
            benefitRepo.delete(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("delete/name/{name}")
    public Response deleteBenefit(@PathParam("name") String name) {
        try {
            benefitRepo.delete(name);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
}
