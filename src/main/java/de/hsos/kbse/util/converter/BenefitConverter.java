/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.util.converter;

import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
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
@FacesConverter(value = "BenefitConverter", managed = true)
public class BenefitConverter implements Converter{
    
    @Inject
    private BenefitRepository benefitRepo;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Object ret = null;
        if (uic instanceof PickList) {
            Object dualList = ((PickList) uic).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Object o : dl.getSource()) {
                String id = "" + ((Benefit) o).getId();
                if (string.equals(id)) {
                    ret = o;
                    break;
                }
            }
            if (ret == null) {
                for (Object o : dl.getTarget()) {
                    String id = "" + ((Benefit) o).getId();
                    if (string.equals(id)) {
                        ret = o;
                        break;
                    }
                }
            }
        } else {
            if (string != null && string.trim().length() > 0) {

                ret = benefitRepo.find(Long.valueOf(string));

            }
            return ret;
        }
        return null;

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        return ((Benefit) t).getId().toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
