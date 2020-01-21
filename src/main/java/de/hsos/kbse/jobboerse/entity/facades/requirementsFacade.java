package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.requirements;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class requirementsFacade extends AbstractFacade<requirements> {

    @PersistenceContext(unitName = "JobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public requirementsFacade() {
        super(requirements.class);
    }
    
}
