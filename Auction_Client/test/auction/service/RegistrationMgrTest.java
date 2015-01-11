package auction.service;

import auction.web.Bid;
import auction.web.Item;
import auction.web.User;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RegistrationMgrTest {


    @Before
    public void setUp() throws Exception {
        clean();
    }

    @Test
    public void registerUser() {
        User user1 = registerUser("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = registerUser("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = registerUser("xxx2@yyy2");
        assertEquals(user2bis.getEmail(), user2.getEmail());
        assertEquals(user2bis.getId(), user2.getId());
        //geen @ in het adres
        assertNull(registerUser("abc"));
    }

    @Test
    public void getUserTest() {
        User user1 = registerUser("xxx5@yyy5");
        User userGet = getUser("xxx5@yyy5");
        assertEquals(userGet.getEmail(), user1.getEmail());
        assertEquals(userGet.getId(), user1.getId());
        assertNull(getUser("aaa4@bb5"));
        registerUser("abc");
        assertNull(getUser("abc"));
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
}
