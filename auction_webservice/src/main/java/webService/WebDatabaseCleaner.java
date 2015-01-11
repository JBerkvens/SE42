package webService;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@WebService
public class WebDatabaseCleaner {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");

    public void clean() {
        try {
            DatabaseCleaner.clean(emf.createEntityManager());
        } catch (SQLException ex) {
            Logger.getLogger(WebDatabaseCleaner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
