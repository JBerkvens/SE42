/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.web;

import auction.domain.User;
import auction.service.RegistrationMgr;
import javax.jws.WebService;

/**
 *
 * @author Lindy Hutz <l.hutz@student.fontys.nl>
 */

@WebService
public class Registration{
    
    private final RegistrationMgr registrationMgr = new RegistrationMgr();
    
    public User registerUser(String email) {
        return this.registrationMgr.registerUser(email);
    }
    
    public User getUser(String email) {
        return this.registrationMgr.getUser(email);
    }
}
