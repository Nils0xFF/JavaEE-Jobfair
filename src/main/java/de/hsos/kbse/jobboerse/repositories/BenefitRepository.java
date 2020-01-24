/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.facades.BenefitFacade;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author soere
 */
@RequestScoped
public class BenefitRepository {
    
    @Inject
    BenefitFacade bf;
    
    public BenefitRepository() { }
    
    public void create(String name, String desc) {
        Benefit benefit = Benefit.builder().name(name).description(desc).build();
        bf.create(benefit);
    }
    
    public void updateName(Long id, String name) {
        Benefit old = bf.find(id);
        old.setName(name);
        bf.edit(old);
    }
    
    public void updateName(String name, String sub) {
        Benefit old = bf.findByName(name);
        old.setName(name);
        bf.edit(old);
    }
    
    public void updateDescription(Long id, String desc) {
        Benefit old = bf.find(id);
        old.setDescription(desc);
        bf.edit(old);
    }
    
    public void updateDescription(String name, String desc) {
        Benefit old = bf.findByName(name);
        old.setDescription(desc);
        bf.edit(old);
    }
    
    public void delete(Long id) {
        bf.remove(bf.find(id));
    }
    
    public void delete(String name) {
        bf.remove(bf.findByName(name));
    }
}
