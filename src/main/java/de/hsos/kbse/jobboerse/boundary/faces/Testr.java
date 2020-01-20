package de.hsos.kbse.jobboerse.boundary.faces;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author nilsgeschwinde
 */
@SessionScoped
public class Testr implements Serializable {

    private int counter = 0;

    public int count() {
        return counter++;
    }
}
