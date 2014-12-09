package auction.service;

import java.util.*;
import auction.domain.User;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistrationMgr {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em;
    private UserDAO userDAO;

    public RegistrationMgr() {
        em = emf.createEntityManager();
        userDAO = new UserDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
    }
    
    public RegistrationMgr(EntityManager em) {
        this.em = em;
        userDAO = new UserDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
    }

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     *
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        em = emf.createEntityManager();
        userDAO = new UserDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
        if (!email.contains("@")) {
            return null;
        }
        User user = userDAO.findByEmail(email);
        if (user != null) {
            return user;
        }
        user = new User(email);
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        userDAO.create(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        em = emf.createEntityManager();
        userDAO = new UserDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
        User returner = userDAO.findByEmail(email);
        em.close();
        return returner;
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        em = emf.createEntityManager();
        userDAO = new UserDAOJPAImpl(this.em);
        this.em.getTransaction().begin();
        List<User> returner = userDAO.findAll();
        em.close();
        return returner;
    }
}
