/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.service;

import webservice.WebAuction;
import webservice.WebAuctionService;

/**
 *
 * @author Jeroen
 */
public class WebServiceMethods {

    private static final WebAuctionService service = new WebAuctionService();

    public static int add(int x, int y) {
        WebAuction port = service.getWebAuctionPort();
        return y;
        //port.getItem(y);
    }
}
