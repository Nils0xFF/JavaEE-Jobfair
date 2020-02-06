/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;

/**
 *
 * @author soere
 */

@Provider
public class LogoutFilter implements ContainerResponseFilter {
    
    private static final String AUTHENTICATION_SCHEME = "Basic";
    
    @Context
    HttpServletRequest servRequest;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        try {
            servRequest.logout();
        } catch (ServletException ex) {
            Logger.getLogger(LogoutFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}