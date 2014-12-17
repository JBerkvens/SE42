/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import webservice.Bid;
import webservice.Item;

/**
 *
 * @author Jeroen
 */
public class AuctionMgr {

    private static Item getItem(long arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getItem(arg0);
    }

    public static java.util.List<webservice.Item> findItemByDescription(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.findItemByDescription(arg0);
    }

    public static Bid newBid(webservice.Item arg0, webservice.User arg1, webservice.Money arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.newBid(arg0, arg1, arg2);
    }
}
