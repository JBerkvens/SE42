package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAOJPAImpl;
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
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
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
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
        Item returner = itemDAO.find(id);
        em.close();
        return returner;
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
        List<Item> returner = itemDAO.findByDescription(description);
        em.close();
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
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
        Bid returner = item.newBid(buyer, amount);
        itemDAO.edit(item);
        em.close();
        return returner;
    }
}
