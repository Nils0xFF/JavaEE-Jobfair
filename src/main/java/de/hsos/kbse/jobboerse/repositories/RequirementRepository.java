/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.facades.RequirementFacade;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author soere
 */
@RequestScoped
public class RequirementRepository {
    @Inject
    RequirementFacade rf;
    
    public void create(String name, String desc) {
        Requirement req = Requirement.builder().name(name).description(desc).build();
        rf.create(req);
    }
    
    public void update(Long id, String name, String desc) {
        Requirement old = rf.find(id);
        old.setName(name);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void update(String name, String sub, String desc) {
        Requirement old = rf.findByName(name);
        old.setName(sub);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void updateName(Long id, String name) {
        Requirement old = rf.find(id);
        old.setName(name);
        rf.edit(old);
    }
    
    public void updateName(String name, String sub) {
        Requirement old = rf.findByName(name);
        old.setName(name);
        rf.edit(old);
    }
    
    public void updateDescription(Long id, String desc) {
        Requirement old = rf.find(id);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void updateDescription(String name, String desc) {
        Requirement old = rf.findByName(name);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void delete(Long id) {
        rf.remove(rf.find(id));
    }
    
    public void delete(String name) {
        rf.remove(rf.findByName(name));
    }
}
