package auction.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import managers.AuctionMgr;
import managers.RegistrationMgr;
import managers.SellerMgr;
import webservice.Bid;
import webservice.Category;
import webservice.Item;
import webservice.Money;
import webservice.User;

public class AuctionMgrTest {
    
    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;

    @Before
    public void setUp() throws Exception {
        DatabaseCleaner.clean();
        registrationMgr = new RegistrationMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
    }

    @Test
    public void getItem() {

        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = registrationMgr.registerUser(email);
        Category cat = new Category();
        Item item1 = sellerMgr.offerItem(seller1, cat, omsch);
        Item item2 = auctionMgr.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = registrationMgr.registerUser(email3);
        User seller4 = registrationMgr.registerUser(email4);
        Category cat = new Category();
        Item item1 = sellerMgr.offerItem(seller3, cat, omsch);
        Item item2 = sellerMgr.offerItem(seller4, cat, omsch);

        List<Item> res = auctionMgr.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = auctionMgr.findItemByDescription(omsch);
        assertEquals(2, res.size());

    }

    @Test
    public void newBid() {
        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = registrationMgr.registerUser(email);
        User buyer = registrationMgr.registerUser(emailb);
        User buyer2 = registrationMgr.registerUser(emailb2);
        // eerste bod
        Category cat = new Category();
        Money money = new Money();
        money.setCurrency("eur");
        money.setCents(10);
        Item item1 = sellerMgr.offerItem(seller, cat, omsch);
        Bid new1 = auctionMgr.newBid(item1, buyer, money);
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        Money money1 = new Money();
        money1.setCents(9);
        money1.setCurrency("eur");
        Bid new2 = auctionMgr.newBid(item1, buyer2, money1);
        assertNull(new2);

        // hoger bod
        Money money2 = new Money();
        money2.setCents(11);
        money2.setCurrency("eur");
        Bid new3 = auctionMgr.newBid(item1, buyer2, money2);
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
}
