/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bank.domain.Account;
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
        em.close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
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
}
