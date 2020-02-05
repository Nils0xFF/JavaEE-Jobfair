/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.facades.RequirementFacade;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

/**
 *
 * @author soere
 */

@RequestScoped
@Transactional (rollbackOn = SQLException.class)
public class RequirementRepository {
    @Inject
    RequirementFacade rf;
    
    public Requirement find(Long id) throws IllegalArgumentException {
        return rf.find(id);
    }
    
    public Requirement create(String name, String desc) throws EntityExistsException {
        Requirement req = Requirement.builder().name(name).description(desc).build();
        rf.create(req);
        return rf.findByName(name);
    }
    
    public Requirement findByName(String name) throws IllegalArgumentException {
        return rf.findByName(name);
    }
    
    public List<Requirement> findAll() {
        return rf.findAll();
    }
    
    public void update(Long id, String name, String desc) throws IllegalArgumentException {
        Requirement old = rf.find(id);
        old.setName(name);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void update(String name, String sub, String desc) throws IllegalArgumentException {
        Requirement old = rf.findByName(name);
        old.setName(sub);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void updateName(Long id, String name) throws IllegalArgumentException {
        Requirement old = rf.find(id);
        old.setName(name);
        rf.edit(old);
    }
    
    public void updateName(String name, String sub) throws IllegalArgumentException {
        Requirement old = rf.findByName(name);
        old.setName(name);
        rf.edit(old);
    }
    
    public void updateDescription(Long id, String desc) throws IllegalArgumentException {
        Requirement old = rf.find(id);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void updateDescription(String name, String desc) throws IllegalArgumentException {
        Requirement old = rf.findByName(name);
        old.setDescription(desc);
        rf.edit(old);
    }
    
    public void delete(Long id) throws IllegalArgumentException {
        rf.remove(rf.find(id));
    }
    
    public void delete(String name) throws IllegalArgumentException {
        rf.remove(rf.findByName(name));
    }
    
}