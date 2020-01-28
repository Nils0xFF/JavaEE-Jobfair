/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.converter;

import de.hsos.kbse.jobboerse.entity.facades.RequirementFacade;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author lennartwoltering
 */
@FacesConverter(value = "RequirementConverter")
public class RequirementConverter implements Converter {

    @Inject
    RequirementFacade requirementRepo;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Object ret = null;
        if (uic instanceof PickList) {
            Object dualList = ((PickList) uic).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Object o : dl.getSource()) {
                String id = "" + ((Requirement) o).getId();
                if (string.equals(id)) {
                    ret = o;
                    break;
                }
            }
            if (ret == null) {
                for (Object o : dl.getTarget()) {
                    String id = "" + ((Requirement) o).getId();
                    if (string.equals(id)) {
                        ret = o;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        return ((Requirement) t).getId().toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
