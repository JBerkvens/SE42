/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.service;

/**
 *
 * @author Jeroen
 */
public class DatabaseCleaner {

    public static void clean() {
        webservice.WebDatabaseCleanerService service = new webservice.WebDatabaseCleanerService();
        webservice.WebDatabaseCleaner port = service.getWebDatabaseCleanerPort();
        port.clean();
    }
}
