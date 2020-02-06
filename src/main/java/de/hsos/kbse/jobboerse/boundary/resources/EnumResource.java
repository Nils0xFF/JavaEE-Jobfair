/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Sal_Relation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author soere
 */
@RequestScoped
@Path("enums")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EnumResource {
    
    @Inject Jsonb jsonb;
    
    @GET
    @Path("graduations")
    public Response getGraduations() {
        return Response.ok(jsonb.toJson(Graduation.values())).build();
    }
    
    @GET
    @Path("relations")
    public Response getRelations() {
        return Response.ok(jsonb.toJson(Sal_Relation.values())).build();
    }
    
    @GET
    @Path("salutations")
    public Response getSalutations() {
        return Response.ok(jsonb.toJson(Salutation.values())).build();
    }
    
    @GET
    @Path("titles")
    public Response getTitles() {
        return Response.ok(jsonb.toJson(Title.values())).build();
    }
    
    @GET
    @Path("workercounts")
    public Response getWorkerCounts() {
        return Response.ok(jsonb.toJson(WorkerCount.values())).build();
    }
}
