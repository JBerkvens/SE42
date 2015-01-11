/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import webservice.Item;

/**
 *
 * @author Jeroen
 */
public class SellerMgr {

    public static Item offerItem(webservice.User arg0, webservice.Category arg1, java.lang.String arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.offerItem(arg0, arg1, arg2);
    }
}
