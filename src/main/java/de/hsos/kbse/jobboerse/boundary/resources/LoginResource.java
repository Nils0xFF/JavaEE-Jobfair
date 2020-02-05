/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.resources;

import de.hsos.kbse.jobboerse.annotations.Identificate;
import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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
    private Pbkdf2PasswordHash passwordHash;
    
    @Inject
    private SecurityContext securityContext;
    @Context
    private HttpServletRequest servRequest;
    @Context
    private HttpServletResponse servResponse;
    

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
        login = Login.builder()
                .email(login.getEmail())
                .password(hashPassword(login.getPassword()))
                .seekingUser(new SeekingUser())
                .group_name("USER")
                .build();
        try {
            userRepo.createUser(login);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }

    @POST
    @Path("register/company")
    public Response registerCompany(Login login) {
        login = Login.builder()
                .email(login.getEmail())
                .password(hashPassword(login.getPassword()))
                .company(new Company())
                .group_name("COMPANY")
                .build();
        try {
            companyRepo.createCompany(login);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        }
    }

    @GET
    @Path("authenticate")
    @Identificate
    public Response authenticate(@HeaderParam("user") String email, @HeaderParam("password") String password) {
        System.out.println(email);
        System.out.println(password);
        
        Credential credential = new UsernamePasswordCredential(email, new Password(password));

        AuthenticationStatus status = securityContext
                .authenticate(
                        servRequest,
                        servResponse,
                        withParams().newAuthentication(true).credential(credential));

        switch (status) {
            case SEND_CONTINUE:
                return Response.ok().build();
            case SEND_FAILURE:
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), status.name()).build();
            case SUCCESS:
                return Response.ok().build();
            case NOT_DONE:
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), status.name()).build();
            default:
                return Response.serverError().build();
        }
    }

    @PUT
    @Path("update")
    @Identificate
    public Response update(@HeaderParam("user") String email, Login login) {        
        try {
            userRepo.editUserCredentials(email, login.getEmail(), login.getPassword());
            return Response.ok(userRepo.getUserByEmail(login.getEmail())).build();
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
