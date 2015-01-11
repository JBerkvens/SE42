package auction.service;

import java.util.List;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import auction.web.*;

public class SellerMgrTest {

    @Before
    public void setUp() throws Exception {
        clean();
    }

    /**
     * Test of offerItem method, of class SellerMgr.
     */
    @Test
    public void testOfferItem() {
        String omsch = "omsch";

        User user1 = registerUser("xx@nl");
        Category cat = new Category();
        cat.setDescription("cat1");
        Item item1 = offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";

        User seller = registerUser("sel@nl");
        User buyer = registerUser("buy@nl");
        Category cat = new Category();
        cat.setDescription("cat1");

        // revoke before bidding
        Item item1 = offerItem(seller, cat, omsch);
        boolean res = revokeItem(item1);
        assertTrue(res);
        int count = findItemByDescription(omsch).size();
        assertEquals(0, count);

        // revoke after bid has been made
        Item item2 = offerItem(seller, cat, omsch2);
        Money euro = new Money();
        euro.setCents(100);
        euro.setCurrency("euro");
        newBid(item2, buyer, euro);
        boolean res2 = revokeItem(item2);
        assertFalse(res2);
        int count2 = findItemByDescription(omsch2).size();
        assertEquals(1, count2);

    }

    // <editor-fold defaultstate="collapsed" desc="Webservice methods">
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

    private static auction.web.User getUser(java.lang.String arg0) {
        auction.web.RegistrationService service = new auction.web.RegistrationService();
        auction.web.Registration port = service.getRegistrationPort();
        return port.getUser(arg0);
    }

    private static auction.web.User registerUser(java.lang.String arg0) {
        auction.web.RegistrationService service = new auction.web.RegistrationService();
        auction.web.Registration port = service.getRegistrationPort();
        return port.registerUser(arg0);
    }

    //</editor-fold>
}
