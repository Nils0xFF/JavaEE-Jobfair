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
@Transactional(rollbackOn = SQLException.class)
public class RequirementRepository {

    @Inject
    RequirementFacade rf;

    public boolean exists(String name) {
        try {
            findByName(name);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Requirement find(Long id) throws IllegalArgumentException {
        return rf.find(id);
    }

    public Requirement create(String name, String desc) throws EntityExistsException {
        Requirement req = Requirement.builder().name(name).description(desc).build();
        if (!exists(name)) {
            rf.create(req);
        } else {
            throw new EntityExistsException("Requirement already exists!");
        }
        return rf.findByName(name);
    }

    public Requirement findByName(String name) throws IllegalArgumentException {
        return rf.findByName(name);
    }

    public List<Requirement> findAll() {
        return rf.findAll();
    }

    public void update(Long id, String name, String desc) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            Requirement old = rf.find(id);
            if (name != null)
                old.setName(name);
            if (desc != null)
                old.setDescription(desc);
            rf.edit(old);
        } else {
            throw new EntityExistsException("Requirement already exists!");
        }
    }

    public void update(String name, String sub, String desc) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            Requirement old = rf.findByName(name);
            if (sub != null)
                old.setName(sub);
            if (desc != null)
                old.setDescription(desc);
            rf.edit(old);
        } else {
            throw new EntityExistsException("Requirement already exists!");
        }
    }

    public void updateName(Long id, String name) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            Requirement old = rf.find(id);
            old.setName(name);
            rf.edit(old);
        } else {
            throw new EntityExistsException("Requirement already exists!");
        }
    }

    public void updateName(String name, String sub) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            Requirement old = rf.findByName(name);
            old.setName(name);
            rf.edit(old);
        } else {
            throw new EntityExistsException("Requirement already exists!");
        }
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
