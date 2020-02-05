/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.facades.JobFieldFacade;
import java.io.Serializable;
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
@Transactional(rollbackOn = SQLException.class)
public class JobFieldRepository implements Serializable {

    @Inject
    private JobFieldFacade jff;

    public JobFieldRepository() {
    }

    public boolean exists(String name) {
        try {
            findByName(name);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public JobField create(String name) throws EntityExistsException {
        JobField jobfield = JobField.builder().name(name).build();
        if (!exists(name)) {
            jff.create(jobfield);
        } else {
            throw new EntityExistsException("Jobfield already exists!");
        }
        return jff.findByName(name);
    }

    public JobField findById(Long id) throws IllegalArgumentException {
        return jff.find(id);
    }

    public JobField findByName(String name) throws IllegalArgumentException {
        return jff.findByName(name);
    }

    public List<JobField> findAll() {
        return jff.findAll();
    }

    public void update(Long id, JobField jobfield) throws IllegalArgumentException, EntityExistsException {
        if (!exists(jobfield.getName())) {
            JobField old = jff.find(id);
            jobfield.setId(old.getId());
            jff.edit(jobfield);
        } else {
            throw new EntityExistsException("Jobfield already exists!");
        }
    }

    public void update(String name, JobField jobfield) throws IllegalArgumentException, EntityExistsException {
        if (!exists(jobfield.getName())) {
            JobField old = jff.findByName(name);
            jobfield.setId(old.getId());
            jff.edit(jobfield);
        } else {
            throw new EntityExistsException("Jobfield already exists!");
        }
    }

    public void updateName(Long id, String name) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            JobField old = jff.find(id);
            old.setName(name);
            jff.edit(old);
        } else {
            throw new EntityExistsException("Jobfield already exists!");
        }
    }

    public void updateName(String name, String sub) throws IllegalArgumentException, EntityExistsException {
        if (!exists(sub)) {
            JobField old = jff.findByName(name);
            old.setName(sub);
            jff.edit(old);
        } else {
            throw new EntityExistsException("Jobfield already exists!");
        }
    }

    public void delete(Long id) throws IllegalArgumentException {
        jff.remove(jff.find(id));
    }

    public void delete(String name) throws IllegalArgumentException {
        jff.remove(jff.findByName(name));
    }
}
