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
 *
 * Requires you to be logged in, in order to see the member pages
 */
@WebFilter(urlPatterns = "/faces/pages/members/*", dispatcherTypes = {REQUEST, FORWARD})
public class LoginFilter implements Filter {
    
    @Inject
    private SessionController sessionCtrl;
    
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        
        HttpServletResponse response = (HttpServletResponse) sr1;
        HttpServletRequest request = (HttpServletRequest) sr;
        
        if (sessionCtrl.userIsLoggedIn()) {
            if (sessionCtrl.userHasSetup() || request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "").contains("setup")) {
                fc.doFilter(sr, sr1);
            } else {
                
                response.sendRedirect(request.getContextPath() + "/setup");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
        
    }
}
