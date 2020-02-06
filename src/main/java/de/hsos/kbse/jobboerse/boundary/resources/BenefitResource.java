/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import java.util.Collection;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.Consumes;
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
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@RolesAllowed({"ADMIN"})
public class BenefitResource {
    
    @Inject
    private BenefitRepository benefitRepo;
    
    @Inject
    private Jsonb jsonb;
    
    @GET
    @PermitAll
    public Response getAllBenefits() {        
        try {
            Collection<Benefit> all = benefitRepo.findAll();
            if (all.isEmpty()) {
                return Response.noContent().build();
            }
            
            return Response.ok(jsonb.toJson(all)).build();
        } catch (Exception ex) {            
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("{id}")
    @PermitAll
    public Response getBenefit(@PathParam("id") Long id) {
        try {            
            return Response.ok(jsonb.toJson(benefitRepo.find(id))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("name/{name}")
    @PermitAll
    public Response getBenefit(@PathParam("name") String name) {
        try {            
            return Response.ok(jsonb.toJson(benefitRepo.findByName(name))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("create")
    public Response createBenefit(Benefit benefit) {
        try {            
            return Response.ok(jsonb.toJson(benefitRepo.create(benefit.getName(), benefit.getDescription()))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/{id}")
    public Response updateBenefit(@PathParam("id") Long id, Benefit benefit) {
        try {
            benefitRepo.update(id, benefit.getName(), benefit.getDescription());
            return Response.ok(jsonb.toJson(benefitRepo.find(id))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
}
