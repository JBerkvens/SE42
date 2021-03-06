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

    @Test
    public void flushTest() {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
        /**
         * Omdat er hier nog niet gecommit is heeft account dus ook nog geen ID
         * gekregen van de database dus blijft die gelijk aan de -100L.
         */
        assertEquals(expected, account.getId());
        em.flush();
        /**
         * Flush controlleerd in de database, maar zet het er nog niet
         * daadwerkelijk in.
         */
        assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
        /**
         * Geen idee wat ik hier moet verklaren / aanpassen...
         */
    }

    @Test
    public void editAfterPersistTest() {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        /**
         * account.getBalance() is gelijk aan expectedBalance wat inhoud dat
         * ofwel commit() alleen de gegevens naar de database stuurt die gegeven
         * waren in persist() en de gegevens niet ophaalt uit de database ofwel
         * dat hij de aanpassingen na persist() gewoon doorvoerd naar de
         * database.
         */
        Long acId = account.getId();
        account = null;
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);
        assertEquals(expectedBalance, found.getBalance());
        /**
         * found.getBalance() is gelijk aan expectedBalance wat inhoud dat ook
         * de aanpassingen die gedaan worden na persist() doorgevoerd worden
         * naar de Database bij commit().
         */
    }

    @Test
    public void refreshTest() {
        Long expectedBalance = 400L;
        Long changedBalance = 200L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        Long acId = account.getId();
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);
        assertEquals(expectedBalance, found.getBalance());

        account.setBalance(changedBalance);
        em.getTransaction().begin();
        em.getTransaction().commit();
        em2.refresh(found);
        assertEquals(changedBalance, found.getBalance());
        /**
         * In commit wordt het aangepaste account naar de database gegooit, en
         * bij refresh wordt found weer geupdate om de database te matchen. Dit
         * kan je zien omdat de aanpassing die bij account gedaan is terug te
         * vinden is bij found.
         */
    }

    @Test
    public void mergeTest() {
        Account acc = new Account(1L);
        Account acc2;

        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        assertEquals(balance1, acc.getBalance());
        /**
         * Er is hier nog maar een account waardoor er dus ook maar een assert
         * nodig is.
         */

        // scenario 2
        Long balance2a = 211L;
        Long balance2b = 222L;
        em.getTransaction().begin();
        acc2 = em.merge(acc);
        acc.setBalance(balance2a);
        acc2.setBalance(balance2b);
        em.getTransaction().commit();
        assertEquals(balance2b, acc.getBalance());
        assertEquals(balance2b, acc2.getBalance());
        /**
         * Hier wordt de balance van acc naar balance2a gezet waar de balance
         * van acc2 naar balance2b gezet wordt. Maar omdat acc2 later geset is
         * wordt de balance van acc weer overschreven.
         */

        // scenario 3
        Long balance3c = 333L;
        Long balance3d = 344L;
        em.getTransaction().begin();
        acc2 = em.merge(acc);
        assertTrue(em.contains(acc));
        assertTrue(em.contains(acc2));
        assertEquals(acc, acc2);
        acc2.setBalance(balance3c);
        acc.setBalance(balance3d);
        em.getTransaction().commit();
        assertEquals(balance3d, acc.getBalance());
        assertEquals(balance3d, acc2.getBalance());
        /**
         * Ik snap niet waarom dit scenario verschilt van scenario 2...
         */

        // scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650l);
        assertEquals((Long) 650L, account2.getBalance());
        /**
         * deze zijn in java gelinked.
         */
        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);
        assertSame(account, account2);
        /**
         * ze hebben de zelfde id en de gegevens worden dus correct gesynced bij
         * merge().
         */
        assertTrue(em.contains(account2));
        /**
         * account2 is gelijk aan account en die is al gecommit.
         */
        assertFalse(em.contains(tweedeAccountObject));
        /**
         * tweedeAccountObject is niet meer gelinked aan account2 omdat account2
         * geset is naar de waarde van merge().
         */
        tweedeAccountObject.setBalance(850l);
        assertEquals((Long) 650L, account.getBalance());
        assertEquals((Long) 650L, account2.getBalance());
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void findAndClearTest() {
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account.

        // scenario 1        
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        // scenario 2        
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        //assertSame(accF1, accF2);
        /**
         * clear() koppelt alleen alle locale componenten los van em, en cleared
         * alle persists enzo. Maar dat heeft geen verdere betrekking tot
         * find(). Wel betekend dit dat aanpassingen aan accF1 niet meer
         * gesynced worden.
         */
    }

    @Test
    public void removeTest() {
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId());
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);
        /**
         * assertEquals() is heel duidelijk: id is geset naar de waarde van
         * acc1.getID(). assertNull() komt omdat hij nadat het account uit em
         * verwijderd is hij dat account niet meer in em kan vinden.
         */
    }

    @Test
    public void generationTypeTest() {
        /**
         * Voor deze test zijn er aanpassingen gemaakt in de Account class,
         * rebuild en opnieuw getest. SEQUENCE deed alles precies het zelfde,
         * maar TABLE crashed bij bijna alle tests.
         */
    }
}
