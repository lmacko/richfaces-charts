/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Macko
 */
@Stateless
public class GameFacade extends AbstractFacade<Game> {
    @PersistenceContext(unitName = "PaintballPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GameFacade() {
        super(Game.class);
    }
    
}
