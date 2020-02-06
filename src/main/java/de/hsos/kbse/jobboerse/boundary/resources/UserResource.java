/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.algorithm.MatchingAlgorithm;
import de.hsos.kbse.jobboerse.algorithm.qualifiers.Basic;
import de.hsos.kbse.jobboerse.algorithm.qualifiers.Weighted;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.User_Profile;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import de.hsos.kbse.jobboerse.repositories.SearchRepository;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.security.enterprise.SecurityContext;
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
    @Inject
    @Basic
    private MatchingAlgorithm basicMatching;
    @Inject
    @Weighted
    private MatchingAlgorithm weightedMatching;

    @Inject
    private Jsonb jsonb;

    @Inject
    private SecurityContext securityContext;

    @GET
    @Path("email/{email}")
    @RolesAllowed({"ADMIN"})
    public Response getUser(@PathParam("email") String email) {
        try {
            SeekingUser user = userRepo.getUserByEmail(email);
            return Response.ok(jsonb.toJson(user)).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
    public Response getUser(@PathParam("id") Long id) {
        try {
            return Response.ok(jsonb.toJson(userRepo.getUser(id))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed({"ADMIN"})
    public Response getAllUsers() {
        return Response.ok(jsonb.toJson(userRepo.getAllUsers())).build();
    }

    @GET
    @Path("profile")
    public Response getUserProfile() {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            return Response.ok(jsonb.toJson(userRepo.getUserProfile(email))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @Path("benefits")
    public Response getUserBenefits() {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            return Response.ok(jsonb.toJson(userRepo.getUserBenefits(email))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @Path("requirements")
    public Response getUserRequirements() {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            return Response.ok(jsonb.toJson(userRepo.getUserRequirements(email))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @Path("jobfields")
    public Response getUserJobFields() {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            return Response.ok(jsonb.toJson(userRepo.getUserJobFields(email))).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @POST
    @Path("create/profile")
    public Response createUserProfile(User_Profile profile) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.createUserProfile(email, profile);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @PUT
    @Path("update/profile")
    public Response updateUserProfile(User_Profile profile) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.editUserProfile(email, profile.getSalutation(), profile.getTitle(), profile.getFirstname(), profile.getLastname(), profile.getDescription(), profile.getTelefon());
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @PUT
    @Path("update/profile/address")
    public Response updateUserProfileAddress(Address address) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.editUserAddress(email, address.getStreet(), address.getHousenumber(), address.getCity(), address.getPostalcode(), address.getCountry());
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @PUT
    @Path("update/profile/graduation")
    @Consumes({MediaType.TEXT_PLAIN})
    public Response updateUserProfileGraduation(String grad) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.editUserQualifications(email, Graduation.valueOf(grad), userRepo.getUserByEmail(email).getProfile().getFullfiledRequirements());
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @POST
    @Path("benefits/add")
    public Response addUserBenefit(Benefit benefit) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.addUserBenefit(email, benefit);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }

    }

    @DELETE
    @Path("benefits/remove/{id}")
    public Response removeUserBenefit(@PathParam("id") Long id) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.removeUserBenefit(email, id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @POST
    @Path("requirements/add")
    public Response addUserRequirement(Requirement requirement) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.addUserRequirement(email, requirement);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }

    }

    @DELETE
    @Path("requirements/remove/{id}")
    public Response removeUserRequirement(@PathParam("id") Long id) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.removeUserBenefit(email, id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @POST
    @Path("jobfields/add")
    public Response addUserJobfield(JobField field) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.addUserJobField(email, field);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }

    }

    @DELETE
    @Path("jobfields/remove/{id}")
    public Response removeUserJobfield(@PathParam("id") Long id) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.removeUserJobField(email, id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @Path("match/basic")
    public Response matchUserBasic() {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            if (userRepo.getUserByEmail(email).getSearchrequest() != null && userRepo.getUserByEmail(email).getProfile() != null) {
                return Response.ok(jsonb.toJson(basicMatching.findSuitableJobs(email))).build();
            } else {
                throw new IllegalArgumentException("No SearchRequest/Profile!");
            }
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @Path("match/weighted")
    public Response matchUserWeighed() {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            if (userRepo.getUserByEmail(email).getSearchrequest() != null && userRepo.getUserByEmail(email).getProfile() != null) {
                return Response.ok(jsonb.toJson(weightedMatching.findSuitableJobs(email))).build();
            } else {
                throw new IllegalArgumentException("No SearchRequest/Profile!");
            }
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
}
