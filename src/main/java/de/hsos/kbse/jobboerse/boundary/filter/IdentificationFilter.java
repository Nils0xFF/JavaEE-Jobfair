/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.filter;

import de.hsos.kbse.jobboerse.annotations.Identificate;
import java.io.IOException;
import java.util.Base64;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author soere
 */

@Identificate
@Provider
@Priority(1)
public class IdentificationFilter implements ContainerRequestFilter {
    
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

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
        
        requestContext.getHeaders().add("user", uap[0]);
        requestContext.getHeaders().add("password", uap[1]);
    }

    public void abort(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME)
                        .build());
    }
}
