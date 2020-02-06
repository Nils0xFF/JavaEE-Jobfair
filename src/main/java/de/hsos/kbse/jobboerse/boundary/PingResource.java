package de.hsos.kbse.jobboerse.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * 
 * @author airhacks.com
 */
@Path("ping")
@RequestScoped
public class PingResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;

    @GET
    public String ping() {
        return this.message + " Jakarta EE with MicroProfile 2+!";
    }

}
