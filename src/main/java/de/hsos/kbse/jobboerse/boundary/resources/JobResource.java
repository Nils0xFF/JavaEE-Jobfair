/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author soere
 */

@RequestScoped
@Path("jobs")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class JobResource implements Serializable {  
    @Inject
    private JobRepository jobRepo;
    @Context
    UriInfo uriInfo;
    
    @GET
    public Response getAllJobs() {
        try {
            Collection<Job> all = jobRepo.findAll();
            if (all.isEmpty()) {
                return Response.noContent().build();
            }        
            return Response.ok(all).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("{id}")
    public Response getJob(@PathParam("id") Long id) {
        try {
            return Response.ok(jobRepo.find(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("jobfield/{name}")
    public Response getJobsByJobfield(@PathParam("name") String name) {
        try {
            return Response.ok(jobRepo.findJobsByJobField(name)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("create")
    @RolesAllowed({"ADMIN"})
    public Response createJob(Job job) {
        try {
            jobRepo.create(job);
            return Response.ok("Job created.").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/{id}")
    @RolesAllowed({"ADMIN"})
    public Response updateJob(@PathParam("id") Long id, Job job) {
        try {
            jobRepo.update(id, job);
            return Response.ok(jobRepo.find(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("remove/{id}")
    @RolesAllowed({"ADMIN"})
    public Response deleteJob(@PathParam("id") Long id) {
        try {
            jobRepo.delete(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
}
