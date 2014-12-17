/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import webservice.User;

/**
 *
 * @author Jeroen
 */
public class RegistrationMgr {

    public static User getUser(java.lang.String arg0) {
        webservice.WebRegistrationService service = new webservice.WebRegistrationService();
        webservice.WebRegistration port = service.getWebRegistrationPort();
        return port.getUser(arg0);
    }

    public static User registerUser(java.lang.String arg0) {
        webservice.WebRegistrationService service = new webservice.WebRegistrationService();
        webservice.WebRegistration port = service.getWebRegistrationPort();
        return port.registerUser(arg0);
    }
}
