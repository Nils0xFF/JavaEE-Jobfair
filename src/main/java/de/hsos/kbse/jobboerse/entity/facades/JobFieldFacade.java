
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.company.JobField;
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
    
    public JobField findByName(String name) throws IllegalArgumentException {
        String queryString = "SELECT jf FROM JobField jf "
                + "WHERE jf.name = :name";

        TypedQuery<JobField> query = em.createQuery(queryString, JobField.class);
        query.setParameter("name", name);
        JobField field = query.getSingleResult();
        if (field == null) {
            throw new IllegalArgumentException("Jobfield not found!");
        }
        return field;
    }    
}
