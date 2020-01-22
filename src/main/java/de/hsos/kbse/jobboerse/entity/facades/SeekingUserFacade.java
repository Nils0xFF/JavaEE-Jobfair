/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class SeekingUserFacade extends AbstractFacade<SeekingUser> {

    @PersistenceContext(unitName = "JobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeekingUserFacade() {
        super(SeekingUser.class);
    }
    
}
