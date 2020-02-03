/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces.mockfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author nilsgeschwinde
 */
@Named
@RequestScoped
public class AutoCompleteView {

    @Inject
    private AutoCompleteService service;
    private List<AutoCompleteData> data;

    public List<AutoCompleteData> completeData(String query) {
        String queryLowerCase = query.toLowerCase();
        List<AutoCompleteData> allThemes = service.getThemes();
        return allThemes.stream().filter(t -> t.getName().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public List<AutoCompleteData> getData() {
        return data;
    }

    public void setData(List<AutoCompleteData> data) {
        this.data = data;
    }

}
