/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.facades.JobFieldFacade;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.RollbackException;
import javax.transaction.Transactional;

/**
 *
 * @author soere
 */
@Named
@RequestScoped
public class JobFieldRepository implements Serializable {
    @Inject
    private JobFieldFacade jff;
    
    public JobFieldRepository() {}

    public void create(String name, List<Requirement> requirements) {
        JobField jobfield = JobField.builder().name(name).requirements(requirements).build();
        jff.create(jobfield);
    }
    
    public JobField findById(Long id) {
        return jff.find(id);
    }
    
    public JobField findByName(String name) {
        return jff.findByName(name);
    }
    
    public void update(int id, String name) throws RollbackException {
        JobField old = jff.find(id);
        old.setName(name);
        jff.edit(old);
    }
    
    public void update(int id, List<Requirement> reqs) throws RollbackException {
        JobField old = jff.find(id);
        old.setRequirements(reqs);
        jff.edit(old);
    }
    
    public void update(int id, String name, List<Requirement> reqs) throws RollbackException {
        JobField old = jff.find(id);
        old.setName(name);
        old.setRequirements(reqs);
        jff.edit(old);
    }
    
    public void update(String name, String n) {
        JobField old = jff.findByName(name);
        old.setName(n);
        jff.edit(old);
    }
    
    public void addRequirement(Long id, Requirement req) throws RollbackException {
        JobField old = jff.find(id);
        old.addRequirement(req);
        jff.edit(old);
    }
    
    public void removeRequirement(Long id, Requirement req) throws RollbackException {
        JobField old = jff.find(id);
        old.removeRequirement(req);
        jff.edit(old);
    }
    
    public void delete(Long id) {
        jff.remove(jff.find(id));
    }
    
    public void deleteByName(String name) {
        jff.remove(jff.findByName(name));
    }    
}
