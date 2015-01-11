/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.web;

import javax.xml.ws.Endpoint;

/**
 *
 * @author Lindy Hutz <l.hutz@student.fontys.nl>
 */
public class PublishWebService {

   private static final String url = "http://localhost:8080/";

    public static void main(String[] args) {
        Endpoint.publish(url + "registration", new Registration());
        Endpoint.publish(url + "auction", new Auction());
        DatabaseCleaner dbcl = new DatabaseCleaner();
        Endpoint.publish(url + "databasecleaner", dbcl);
    }
    
}
