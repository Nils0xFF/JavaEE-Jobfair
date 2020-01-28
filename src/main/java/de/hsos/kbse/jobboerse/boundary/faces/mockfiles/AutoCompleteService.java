/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces.mockfiles;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author nilsgeschwinde
 */
@Named
@ApplicationScoped
public class AutoCompleteService {

    private List<AutoCompleteData> themes;

    @PostConstruct
    public void init() {
        themes = new ArrayList<>();
        themes.add(new AutoCompleteData(0, "Nova-Light"));
        themes.add(new AutoCompleteData(1, "Nova-Dark"));
    }

    public List<AutoCompleteData> getThemes() {
        return themes;
    }
}
