/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.filter;

import java.io.IOException;
import java.util.Base64;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author soere
 */

@Provider
@Priority(1)
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Basic";
    
    @Inject
    private SecurityContext securityContext;
    @Context
    private HttpServletRequest servRequest;
    @Context
    private HttpServletResponse servResponse;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        UriInfo info = requestContext.getUriInfo();
        if (info.getPath().contains("register") || info.getPath().contains("enums") 
                || info.getPath().startsWith("benefits") 
                || info.getPath().startsWith("jobfields")
                || info.getPath().startsWith("requirements")
                || info.getPath().startsWith("jobs")) {
            System.out.println(info.getPath());
            return;
        }
        
        String authorizationHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!(authorizationHeader
                != null && authorizationHeader.toLowerCase()
                        .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " "))) {
            abort(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String credentials = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();
        String[] uap = new String(Base64.getDecoder().decode(credentials)).split(":");
        
        Credential credential = new UsernamePasswordCredential(uap[0], new Password(uap[1]));

        AuthenticationStatus status = securityContext
                .authenticate(
                        servRequest,
                        servResponse,
                        withParams().newAuthentication(true).credential(credential));

        switch (status) {
            case SEND_CONTINUE:
                break;
            case SEND_FAILURE:
                abort(requestContext);
                break;
            case SUCCESS:
                break;
            case NOT_DONE:
                abort(requestContext);
                break;
            default:
                abort(requestContext);
                break;
        }
        
    }

    public void abort(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME)
                        .build());
    }
    
}
