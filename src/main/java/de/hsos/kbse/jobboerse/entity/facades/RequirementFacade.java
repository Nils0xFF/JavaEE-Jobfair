
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.shared.Requirement;
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
    
    /**
     * Searches for a Requirement in the Database
     * 
     * @param name represents the name of a Requirement
     * @return returns the found Requirement
     * @throws IllegalArgumentException 
     */
    public Requirement findByName(String name) throws IllegalArgumentException {
        String queryString = "SELECT req FROM Requirement req "
                + "WHERE req.name = :name";

        TypedQuery<Requirement> query = em.createQuery(queryString, Requirement.class);
        query.setParameter("name", name);
        Requirement req = query.getSingleResult();
        if (req == null) {
            throw new IllegalArgumentException("Requirement not found!");
        }
        return req;
    } 
    
}
