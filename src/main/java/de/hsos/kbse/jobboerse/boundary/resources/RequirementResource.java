/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
@Path("requirements")
@RolesAllowed({"ADMIN"})
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class RequirementResource {

    @Inject
    private RequirementRepository reqRepo;

    @GET
    public Response getAllRequirements() {
        try {
            Collection<Requirement> all = reqRepo.findAll();
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
    public Response getRequirement(@PathParam("id") Long id) {
        try {
            return Response.ok(reqRepo.find(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("name/{name}")
    public Response getRequirement(@PathParam("name") String name) {
        try {
            return Response.ok(reqRepo.findByName(name)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("create")
    public Response createRequirement(Requirement req) {
        try {
            return Response.ok(reqRepo.create(req.getName(), req.getDescription())).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    } 
    
    @PUT
    @Path("update/{id}")
    public Response updateRequirement(@PathParam("id") Long id, Requirement req) {
        try {
            reqRepo.update(id, req.getName(), req.getDescription());
            return Response.ok(reqRepo.find(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }        
    }
    
    @PUT
    @Path("update/name/{name}")
    public Response updateRequirement(@PathParam("name") String name, Requirement req) {
        try {
            reqRepo.update(name, req.getName(), req.getDescription());
            return Response.ok(reqRepo.findByName(name)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }        
    }
    
    @DELETE
    @Path("delete/{id}")
    public Response deleteRequirement(@PathParam("id") Long id, Requirement req) {
        try {
            reqRepo.delete(id);
            return Response.ok("Requirement deleted!").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("delete/name/{name}")
    public Response deleteRequirement(@PathParam("name") String name, Requirement req) {
        try {
            reqRepo.delete(name);
            return Response.ok("Requirement deleted!").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
}
