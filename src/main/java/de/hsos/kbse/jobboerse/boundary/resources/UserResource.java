/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.annotations.Identificate;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.User_Profile;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import de.hsos.kbse.jobboerse.repositories.SearchRepository;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
@Path("user")
@RolesAllowed({"ADMIN", "USER"})
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Transactional
public class UserResource {
    
    @Inject
    private GeneralUserRepository userRepo;
    
    @Inject
    private SearchRepository searchRepo;
    
    @GET
    @Path("email/{email}")
    @RolesAllowed({"ADMIN"})
    public Response getUser(@PathParam("email") String email) {
        try {
            SeekingUser user = userRepo.getUserByEmail(email);
            return Response.ok(user).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();        
        }
    }
    
    @GET
    @Path("id/{id}")
    @RolesAllowed({"ADMIN"})
    public Response getUser(@PathParam("id") Long id) {
        try {
            SeekingUser user = userRepo.getUser(id);
            return Response.ok(user).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();        
        }
    }
    
    @GET
    @RolesAllowed({"ADMIN"})
    public Response getAllUsers() {
        return Response.ok(userRepo.getAllUsers()).build();
    }
    
    @POST
    @Path("create/profile")
    @Identificate
    public Response createUserProfile(@HeaderParam("user") String email, User_Profile profile) {
        try {
            userRepo.createUserProfile(email, profile);
            return Response.ok(userRepo.getUserByEmail(email).getProfile()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("create/search")
    @Identificate
    public Response createSearchRequest(@HeaderParam("user") String email, SearchRequest search) {
        try {
            searchRepo.createSearchRequirements(email, search);
            return Response.ok(search).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), "User not found! " + ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/email/{email}")
    @RolesAllowed({"ADMIN"})
    public Response updateUser(@PathParam("email") String email, SeekingUser user) {
        try {
            userRepo.edit(user);
            return Response.ok(userRepo.getUserByEmail(email)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/id/{id}")
    @RolesAllowed({"ADMIN"})
    public Response updateUser(@PathParam("id") Long id, SeekingUser user) {
        try {
            userRepo.edit(user);
            return Response.ok(userRepo.getUser(id)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/login")
    @Identificate
    public Response updateUserCredentials(@HeaderParam("user") String email, Login newLogin) {
        try {
            userRepo.editUserCredentials(email, newLogin.getEmail(), newLogin.getPassword());
            return Response.ok(newLogin).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile")
    public Response updateUserProfile(@HeaderParam("user") String email, User_Profile profile) {
        try {
            userRepo.editUserProfile(email, profile.getSalutation(), profile.getTitle(), profile.getFirstname(), profile.getLastname(), profile.getDescription(), profile.getTelefon());
            return Response.ok(userRepo.getUserByEmail(email).getProfile()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }        
    }
    
    @PUT
    @Path("update/profile/address")
    public Response updateUserProfileAddress(@HeaderParam("user") String email, Address address) {
        try {
            userRepo.editUserAddress(email, address.getStreet(), address.getHousenumber(), address.getCity(), address.getPostalcode(), address.getCountry());
            return Response.ok(userRepo.getUserByEmail(email).getProfile()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile/graduation")
    public Response updateUserProfileQualifications(@HeaderParam("user") String email, List<Requirement> fullfiledRequirements) {
        try {
            userRepo.editUserQualifications(email, userRepo.getUserByEmail(email).getProfile().getGrad(), fullfiledRequirements);
            return Response.ok(userRepo.getUserByEmail(email).getProfile()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    @PUT
    @Path("update/profile/requirements")
    public Response updateUserProfileQualifications(@HeaderParam("user") String email, Graduation grad) {
        try {
            userRepo.editUserQualifications(email, grad, userRepo.getUserByEmail(email).getProfile().getFullfiledRequirements());
            return Response.ok(userRepo.getUserByEmail(email).getProfile()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }        
    }
    
    @DELETE
    @Path("delete")
    public Response deleteUser(@PathParam("email") String email) {
        try {
            userRepo.deleteUser(email);
            return Response.ok("User deleted!").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
}
