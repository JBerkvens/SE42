package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.Money;

public class AuctionMgr  {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em;
    private ItemDAO itemDAO;

    public AuctionMgr() {
    }
    
    public AuctionMgr(EntityManager em) {
        this.em = em;
        itemDAO = new ItemDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
    }

   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Item returner = itemDAO.find(id);
        return returner;
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        List<Item> returner = itemDAO.findByDescription(description);
        return returner;
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Bid returner = item.newBid(buyer, amount);
        itemDAO.edit(item);
        return returner;
    }
}
