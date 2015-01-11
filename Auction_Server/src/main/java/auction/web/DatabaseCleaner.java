package auction.web;

import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;

@WebService
public class DatabaseCleaner {

    private static final Class<?>[] ENTITY_TYPES = {
        Item.class,
        Bid.class,
        User.class
    };
    private EntityManager em;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");

    public DatabaseCleaner(EntityManager entityManager) {
        em = entityManager;
    }

    public DatabaseCleaner() {

        this.em = emf.createEntityManager();
    }

    public void clean() {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            for (Class<?> entityType : ENTITY_TYPES) {
                deleteEntities(entityType);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Sql-exception occured.");
        } finally {
            em.close();
        }
    }

    private void deleteEntities(Class<?> entityType) {
        em.createQuery("delete from " + getEntityName(entityType)).executeUpdate();
    }

    protected String getEntityName(Class<?> clazz) {
        EntityType et = em.getMetamodel().entity(clazz);
        return et.getName();
    }
}
