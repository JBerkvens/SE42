package webService;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import java.util.List;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.Money;

@WebService
public class WebAuction {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em;
    private AuctionMgr auctionMgr;
    private SellerMgr sellerMgr;
    
    public WebAuction(){
        em = emf.createEntityManager();
        auctionMgr = new AuctionMgr(em);
        sellerMgr = new SellerMgr(em);
    }
    
    public Item getItem(long id) {
        return auctionMgr.getItem(id);
    }
    
    public List<Item> findItemByDescription(String description){
        return auctionMgr.findItemByDescription(description);
    }
    
    public Bid newBid(Item item, User buyer, Money amount){
        return auctionMgr.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category category, String description){
        return sellerMgr.offerItem(seller, category, description);
    }
}