/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.company.JobField;
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
public class JobFieldFacade extends AbstractFacade<JobField> {

    @PersistenceContext(unitName = "JobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobFieldFacade() {
        super(JobField.class);
    }
    
    public JobField findByName(String name) {
        String queryString = "SELECT jf FROM JobField jf "
                + "WHERE jf.name IS NULL OR jf.name = :name";

        TypedQuery<JobField> query = em.createQuery(queryString, JobField.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }    
}
