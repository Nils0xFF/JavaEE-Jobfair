/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class BenefitFacade extends AbstractFacade<Benefit> {

    @PersistenceContext(unitName = "JobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BenefitFacade() {
        super(Benefit.class);
    }
    
    public Benefit findByName(String name) throws IllegalArgumentException {
        String queryString = "SELECT bf FROM Benefit bf "
                + "WHERE bf.name = :name";

        TypedQuery<Benefit> query = em.createQuery(queryString, Benefit.class);
        query.setParameter("name", name);
        Benefit benefit = query.getSingleResult();
        if (benefit == null) {
            throw new IllegalArgumentException("Benefit not found!");
        }
        return benefit;
    } 
    
}
