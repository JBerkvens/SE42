package auction.service;

import auction.dao.*;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgr {

    private ItemDAO itemDAO;
    private UserDAO userDAO;
    private EntityManager em;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");

    public SellerMgr() {

        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(em);
        userDAO = new UserDAOJPAImpl(em);
    }

    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     * en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        Item it = new Item(seller, cat, description);
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(em);
        em.getTransaction().begin();
        itemDAO.create(it);
        em.getTransaction().commit();
        return it;
    }

    /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word
     * verwijderd. false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        //get recentste versie van t item
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(em);
        Item it = itemDAO.find(item.getId());
        if (it.getHighestBid() != null) {
            return false;
        } else {
            em.getTransaction().begin();
            itemDAO.remove(item);
            em.getTransaction().commit();
            return true;
        }
    }
}
