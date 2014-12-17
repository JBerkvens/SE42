
import webservice.Bid;
import webservice.Category;
import webservice.Item;
import webservice.Money;
import webservice.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeroen
 */
public class main {

    public static void main(String[] args) {
        User user = new User();
        Category category = new Category();
        String description = "Description";
        Money money = new Money();
        Item item = offerItem(user, category, description);
        System.out.println("Item: " + item);
        System.out.println("getItem: " + getItem(1));
        System.out.println("findItem: " + findItemByDescription(description));
        System.out.println("newBid: " + newBid(item, user, money));
    }

    private static Item offerItem(webservice.User arg0, webservice.Category arg1, java.lang.String arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.offerItem(arg0, arg1, arg2);
    }
    
    private static Item getItem(long arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getItem(arg0);
    }

    private static java.util.List<webservice.Item> findItemByDescription(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.findItemByDescription(arg0);
    }

    private static Bid newBid(webservice.Item arg0, webservice.User arg1, webservice.Money arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.newBid(arg0, arg1, arg2);
    }
}
