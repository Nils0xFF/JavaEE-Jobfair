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

    public JobField create(String name) throws Exception {
        JobField jobfield = JobField.builder().name(name).build();
        jff.create(jobfield);
        return jff.findByName(name);
    }
    
    public JobField findById(Long id) throws Exception {
        return jff.find(id);
    }
    
    public JobField findByName(String name) throws Exception {
        return jff.findByName(name);
    }
    
    public List<JobField> findAll() throws Exception {
        return jff.findAll();
    }
    
    public void update(Long id, JobField jobfield) throws Exception {
        JobField old = jff.find(id);
        jobfield.setId(old.getId());
        jff.edit(jobfield);
    }
    
    public void update(String name, JobField jobfield) throws Exception {
        JobField old = jff.findByName(name);
        jobfield.setId(old.getId());
        jff.edit(jobfield);
    }
    
    public void updateName(Long id, String name) throws Exception {
        JobField old = jff.find(id);
        old.setName(name);
        jff.edit(old);
    }
    
    public void updateName(String name, String sub) throws Exception {
        JobField old = jff.findByName(name);
        old.setName(sub);
        jff.edit(old);
    }
    
    public void delete(Long id) throws Exception {
        jff.remove(jff.find(id));
    }
    
    public void delete(String name) throws Exception {
        jff.remove(jff.findByName(name));
    }    
}
