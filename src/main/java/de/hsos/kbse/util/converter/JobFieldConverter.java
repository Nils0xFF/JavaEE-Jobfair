/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.util.converter;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author lennartwoltering
 */
@FacesConverter(value = "JobFieldConverter", managed = true)
public class JobFieldConverter implements Converter{
    @Inject
    JobFieldRepository jobfieldRepo;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
         if (value != null && value.trim().length() > 0) {

                return jobfieldRepo.findById(Long.valueOf(value));

            }
         return null;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((JobField) value).getId().toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
