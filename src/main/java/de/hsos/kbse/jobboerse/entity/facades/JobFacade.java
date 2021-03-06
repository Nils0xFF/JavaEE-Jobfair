/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class JobFacade extends AbstractFacade<Job> {

    @PersistenceContext(unitName = "JobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobFacade() {
        super(Job.class);
    }
    
    /**
     * Searches for Jobs that have the given name as Jobfield
     * @param name name of the Jobfield that should be searched
     * @return returns the List of found Jobs
     * @throws IllegalArgumentException 
     */
    public List<Job> findJobsByJobField(String name) throws IllegalArgumentException {
        String queryString = 
        "SELECT j FROM Job j WHERE (SELECT jf.id FROM JobField jf WHERE jf.name = :name) = j.jobfield";
        TypedQuery<Login> query = em.createQuery(queryString, Login.class);
        query.setParameter("name", name);
        List results = query.getResultList();
        if(results.isEmpty()) {
            throw new IllegalArgumentException("No jobs found!");
        }
        return results;
    }
    
}
