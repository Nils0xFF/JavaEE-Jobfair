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

/**
 *
 * @author nilsgeschwinde
 *
 * Requires you to be logged in, in order to see the member pages
 */
@WebFilter(urlPatterns = "/faces/pages/admin/*", dispatcherTypes = {REQUEST, FORWARD})
public class AdminFilter implements Filter {

    @Inject
    private SessionController sessionCtrl;

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        if (sessionCtrl.userIsAdmin()) {
            fc.doFilter(sr, sr1);
        }

        fc.doFilter(sr, sr1);

        /*
       System.out.println("LoginFilter");
        
        if (CookieService.getCookieValue((HttpServletRequest) sr, "userToken") == null) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        // FixMe Check if user is valid
        fc.doFilter(sr, sr1);

         */
    }
}
