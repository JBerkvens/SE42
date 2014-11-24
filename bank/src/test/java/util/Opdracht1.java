/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eveko
 */
public class Opdracht1 {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU");
    EntityManager em;

    public Opdracht1() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        try {
            DatabaseCleaner.clean(em);
        } catch (SQLException ex) {
            Logger.getLogger(Opdracht1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void persistCommitTest() {
        Account account = new Account(112L);
        em.getTransaction().begin();
        em.persist(account);
        /**
         * Bij persist() maakt hij allen de instantie en wordt er nog niet
         * gecommit. Het komt hier dus nog niet in de database.
         */
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        /**
         * Bij commit() probeert hij het naar de database te schrijven. Hier
         * komen dus ook alle database errors naar boven (zoals Duplicate Key).
         */
        assertTrue(account.getId() > 0L);
    }

    @Test
    public void rollbackTest() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
        AccountDAOJPAImpl accountDAOJPAImpl = new AccountDAOJPAImpl(em);
        List<Account> findAll = accountDAOJPAImpl.findAll();
        assertEquals(findAll.size(), 0);
        /**
         * Met de functie findAll() laad hij alles wat hij kan vinden. Als daar
         * dus geen records gevonden worden (size=0) is de database dus leeg.
         */
    }
}
