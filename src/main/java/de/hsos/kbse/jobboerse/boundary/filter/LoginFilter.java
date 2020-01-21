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
@WebFilter(urlPatterns = "/faces/members/*", dispatcherTypes = {REQUEST, FORWARD})
public class LoginFilter implements Filter {

    @Inject
    private SessionController sessionCtrl;

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        if (sessionCtrl.userIsLoggedIn()) {
            fc.doFilter(sr, sr1);
        } else {
            HttpServletResponse response = (HttpServletResponse) sr1;
            HttpServletRequest request = (HttpServletRequest) sr;
            response.sendRedirect(request.getContextPath() + "/");
        }

    }
}
