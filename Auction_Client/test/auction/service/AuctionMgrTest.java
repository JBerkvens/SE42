package auction.service;

import auction.web.Bid;
import auction.web.Category;
import auction.web.Item;
import auction.web.Money;
import auction.web.User;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class AuctionMgrTest {

    @Before
    public void setUp() throws Exception {
        clean();
    }

    @Test
    public void getItem() {

        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = registerUser(email);
        Category cat = new Category();
        cat.setDescription("cat1");
        Item item1 = offerItem(seller1, cat, omsch);
        Item item2 = getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = registerUser(email3);
        User seller4 = registerUser(email4);
        Category cat = new Category();
        cat.setDescription("cat3");
        Item item1 = offerItem(seller3, cat, omsch);
        Item item2 = offerItem(seller4, cat, omsch);

        ArrayList<Item> res = (ArrayList<Item>) findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = (ArrayList<Item>) findItemByDescription(omsch);
        assertEquals(2, res.size());

    }

    @Test
    public void newBid() {

        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = registerUser(email);
        User buyer = registerUser(emailb);
        User buyer2 = registerUser(emailb2);
        // eerste bod
        Category cat = new Category();
        cat.setDescription("cat9");
        Item item1 = offerItem(seller, cat, omsch);
        Money geld = new Money();
        geld.setCents(10);
        geld.setCurrency("eur");
        Bid new1 = newBid(getItem(item1.getId()), buyer, geld);
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        geld.setCents(9);
        Bid new2 = newBid(getItem(item1.getId()), buyer2, geld);
        assertNull(new2);

        // hoger bod
        geld.setCents(11);
        Bid new3 = newBid(getItem(item1.getId()), buyer2, geld);
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }

    private static void clean() {
        auction.web.DatabaseCleanerService service = new auction.web.DatabaseCleanerService();
        auction.web.DatabaseCleaner port = service.getDatabaseCleanerPort();
        port.clean();
    }

    private static java.util.List<auction.web.Item> findItemByDescription(java.lang.String arg0) {
        auction.web.AuctionService service = new auction.web.AuctionService();
        auction.web.Auction port = service.getAuctionPort();
        return port.findItemByDescription(arg0);
    }

    private static Item getItem(java.lang.Long arg0) {
        auction.web.AuctionService service = new auction.web.AuctionService();
        auction.web.Auction port = service.getAuctionPort();
        return port.getItem(arg0);
    }

    private static Bid newBid(auction.web.Item arg0, auction.web.User arg1, auction.web.Money arg2) {
        auction.web.AuctionService service = new auction.web.AuctionService();
        auction.web.Auction port = service.getAuctionPort();
        return port.newBid(arg0, arg1, arg2);
    }

    private static Item offerItem(auction.web.User arg0, auction.web.Category arg1, java.lang.String arg2) {
        auction.web.AuctionService service = new auction.web.AuctionService();
        auction.web.Auction port = service.getAuctionPort();
        return port.offerItem(arg0, arg1, arg2);
    }

    private static boolean revokeItem(auction.web.Item arg0) {
        auction.web.AuctionService service = new auction.web.AuctionService();
        auction.web.Auction port = service.getAuctionPort();
        return port.revokeItem(arg0);
    }

    private static User getUser(java.lang.String arg0) {
        auction.web.RegistrationService service = new auction.web.RegistrationService();
        auction.web.Registration port = service.getRegistrationPort();
        return port.getUser(arg0);
    }

    private static User registerUser(java.lang.String arg0) {
        auction.web.RegistrationService service = new auction.web.RegistrationService();
        auction.web.Registration port = service.getRegistrationPort();
        return port.registerUser(arg0);
    }
}
