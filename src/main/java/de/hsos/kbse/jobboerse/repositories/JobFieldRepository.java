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
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.RollbackException;

/**
 *
 * @author soere
 */
@RequestScoped
public class JobFieldRepository implements Serializable {
    
    @Inject
    private JobFieldFacade jff;
    
    public JobFieldRepository() {}

    public void create(String name) {
        JobField jobfield = JobField.builder().name(name).build();
        jff.create(jobfield);
    }
    
    public JobField findById(Long id) {
        return jff.find(id);
    }
    
    public JobField findByName(String name) {
        return jff.findByName(name);
    }
    
    public List<JobField> findAll() {
        return jff.findAll();
    }
    
    public void updateName(int id, String name) throws RollbackException {
        JobField old = jff.find(id);
        old.setName(name);
        jff.edit(old);
    }
    
    public void update(int id, String name) throws RollbackException {
        JobField old = jff.find(id);
        old.setName(name);
        jff.edit(old);
    }
    
    public void updateName(String name, String sub) {
        JobField old = jff.findByName(name);
        old.setName(sub);
        jff.edit(old);
    }
    
    public void delete(Long id) {
        jff.remove(jff.find(id));
    }
    
    public void delete(String name) {
        jff.remove(jff.findByName(name));
    }    
}
