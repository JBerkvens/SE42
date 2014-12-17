package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgr {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em;
    private ItemDAO itemDAO;
    private UserDAO userDAO;

    public SellerMgr() {
    }

    public SellerMgr(EntityManager em) {
        this.em = em;
    }

    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     * en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(this.em);
        userDAO = new UserDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
        Item offer = new Item(seller, cat, description);
        seller.addItem(offer);
        itemDAO.create(offer);
        userDAO.edit(seller);
        em.getTransaction().commit();
        em.close();
        return offer;
    }

    /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word
     * verwijderd. false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        if (item.getHighestBid() == null) {
            em = emf.createEntityManager();
            itemDAO = new ItemDAOJPAImpl(this.em);
            this.em.getTransaction().begin();
            itemDAO.remove(item);
            em.getTransaction().commit();
            em.close();
            return true;
        } else {
            return false;
        }
    }
}
