package webService;

import auction.domain.User;
import auction.service.RegistrationMgr;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@WebService
public class WebRegistration {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em;
    private RegistrationMgr registrationMgr;
    
    public WebRegistration(){
        em = emf.createEntityManager();
        registrationMgr = new RegistrationMgr(em);
    }
    
    public User registerUser(String email) {
        return registrationMgr.registerUser(email);
    }
    
    public User getUser(String email) {
        return registrationMgr.getUser(email);
    }
}