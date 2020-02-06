/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.facades.BenefitFacade;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
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
public class BenefitRepository {

    @Inject
    BenefitFacade bf;

    public BenefitRepository() {
    }

    public boolean exists(String name) {
        try {
            findByName(name);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Benefit find(Long id) throws IllegalArgumentException {
        return bf.find(id);
    }

    public Benefit create(String name, String desc) throws EntityExistsException {
        Benefit benefit = Benefit.builder().name(name).description(desc).build();
        if (!exists(name)) {
            bf.create(benefit);
        } else {
            throw new EntityExistsException("Benefit already exists!");
        }
        return bf.findByName(name);
    }

    public Benefit findByName(String name) throws IllegalArgumentException {
        return bf.findByName(name);
    }

    public List<Benefit> findAll() {
        return bf.findAll();
    }

    public void update(Long id, String name, String desc) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            Benefit old = bf.find(id);
            if (name != null) {
                old.setName(name);
            }
            if (desc != null) {
                old.setDescription(desc);
            }
            bf.edit(old);
        } else {
            throw new EntityExistsException("Benefit already exists!");
        }
    }

    public void update(String name, String sub, String desc) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            Benefit old = bf.findByName(name);
            if (name != null) {
                old.setName(name);
            }
            if (desc != null) {
                old.setDescription(desc);
            }
            bf.edit(old);
        } else {
            throw new EntityExistsException("Benefit already exists!");
        }
    }

    public void updateName(Long id, String name) throws IllegalArgumentException, EntityExistsException {
        if (!exists(name)) {
            Benefit old = bf.find(id);
            old.setName(name);
            bf.edit(old);
        } else {
            throw new EntityExistsException("Benefit already exists!");
        }
    }

    public void updateName(String name, String sub) throws IllegalArgumentException, EntityExistsException {
        if (!exists(sub)) {
            Benefit old = bf.findByName(name);
            old.setName(name);
            bf.edit(old);
        } else {
            throw new EntityExistsException("Benefit already exists!");
        }
    }

    public void updateDescription(Long id, String desc) throws IllegalArgumentException {
        Benefit old = bf.find(id);
        old.setDescription(desc);
        bf.edit(old);
    }

    public void updateDescription(String name, String desc) throws IllegalArgumentException {
        Benefit old = bf.findByName(name);
        old.setDescription(desc);
        bf.edit(old);
    }

    public void delete(Long id) throws IllegalArgumentException {
        bf.remove(bf.find(id));
    }

    public void delete(String name) throws IllegalArgumentException {
        bf.remove(bf.findByName(name));
    }
}
