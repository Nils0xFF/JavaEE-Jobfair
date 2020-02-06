/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.controllers.CompanyCreationController;
import de.hsos.kbse.jobboerse.controllers.UserCreationController;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author soere
 */
@RequestScoped
@Path("login")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LoginResource {

    @Inject
    private GeneralUserRepository userRepo;
    @Inject
    private CompanyRepository companyRepo;
    @Inject
    private UserCreationController userReg;
    @Inject 
    private CompanyCreationController compReg;

    @Inject
    private Pbkdf2PasswordHash passwordHash;
    
    @Inject
    private SecurityContext securityContext;
    

    // TO BE DELETED : Testing
    @POST
    @Path("register/admin")
    public Response registerAdmin(Login login) {
        login = Login.builder()
                .email(login.getEmail())
                .password(hashPassword(login.getPassword()))
                .group_name("ADMIN")
                .build();
        try {
            userRepo.createUser(login);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }

    @POST
    @Path("register/user")
    public Response registerUser(Login login) {
        if (userReg.createLogin(login.getEmail(), login.getPassword())) {
            userRepo.createUser(login.getEmail());
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "User already exists!").build();
        }
    }

    @POST
    @Path("register/company")
    public Response registerCompany(Login login) {
        if (compReg.createLogin(login.getEmail(), login.getPassword())) {
            companyRepo.createCompany(login.getEmail());
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "Company already exists!").build();
        }
    }

    @PUT
    @Path("update")
    @PermitAll
    public Response update(Login login) {
        String email = securityContext.getCallerPrincipal().getName();
        try {
            userRepo.editUserCredentials(email, login.getEmail(), login.getPassword());
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        }
    }
    
    // Utils
    private String hashPassword(String pw) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
        return passwordHash.generate(pw.toCharArray());
    }
    
}
