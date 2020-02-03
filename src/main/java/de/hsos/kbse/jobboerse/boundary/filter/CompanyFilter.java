/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.filter;

import de.hsos.kbse.jobboerse.control.usermanagement.SessionController;
import java.io.IOException;
import javax.inject.Inject;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.REQUEST;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nilsgeschwinde
 */
@WebFilter(urlPatterns = "/faces/pages/members/company/*", dispatcherTypes = {REQUEST, FORWARD})
public class CompanyFilter implements Filter {

    @Inject
    private SessionController sessionCtrl;

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        if (sessionCtrl.userIsCompany()) {
            fc.doFilter(sr, sr1);
        } else {
            HttpServletResponse response = (HttpServletResponse) sr1;
            HttpServletRequest request = (HttpServletRequest) sr;
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }

    }
}
