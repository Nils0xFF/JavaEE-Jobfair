package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.entity.facades.AddressFacade;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nilsgeschwinde
 */

@Named("loginview")
@RequestScoped
public class LoginFace {
    
    private int counter;
    // add repos and controllers later
    
    @Inject
    private Testr test;
    
    @Inject
    private AddressFacade af;
    
    public void login(){
        //jpa.create(new requirements(1L, "Test", 5.0f));
        // FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("login", "test", null);
        // FIXME: try to log in, set token on valid
        //FacesContext.getCurrentInstance().getExternalContext().se;
        //CookieService.addCookie((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse(), "userToken", "NilsIstToll", 2592000);
    }
    
    public void logout(){
        // userToken = "";
        CookieService.removeCookie((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse(), "userToken");
    }
    
    public String getUserToken(){
        return CookieService.getCookieValue((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(), "userToken");
    }
    
    public int count(){
        return counter++;
    }
    
    public int countSession(){
        return test.count();
    }
    
    
}
