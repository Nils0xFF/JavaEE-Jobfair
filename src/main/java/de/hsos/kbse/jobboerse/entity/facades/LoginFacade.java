
package de.hsos.kbse.jobboerse.entity.facades;

import de.hsos.kbse.jobboerse.entity.shared.Login;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class LoginFacade extends AbstractFacade<Login> {

    @PersistenceContext(unitName = "JobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginFacade() {
        super(Login.class);
    }
    
    /**
     * Searches for a Login with the email
     * @param email that should be searched
     * @return returns the found Login
     * @throws IllegalArgumentException 
     */
    public Login findByEmail(String email) throws IllegalArgumentException {
        String queryString = "SELECT l from Login l "+
                "WHERE l.email = :email";
        TypedQuery<Login> query = em.createQuery(queryString, Login.class);
        query.setParameter("email", email);
        List results = query.getResultList();
        if(results.isEmpty()) {
            throw new IllegalArgumentException("Login not found!");
        }
        if(results.size() == 1) {
            return (Login) results.get(0);
        }
        throw new NonUniqueResultException();
    }
    
}
