/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class RequirementFacade extends AbstractFacade<Requirement> {

    @PersistenceContext(unitName = "JobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RequirementFacade() {
        super(Requirement.class);
    }
    
    public Requirement findByName(String name) {
        String queryString = "SELECT bf FROM Benefit bf "
                + "WHERE bf.name = :name";

        TypedQuery<Requirement> query = em.createQuery(queryString, Requirement.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    } 
    
}
