package auction.dao;

import auction.domain.User;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Jeroen
 */
public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager em;

    public UserDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void edit(User user) {
        em.merge(user);
    }

    @Override
    public List<User> findAll() {
        Query q = em.createNamedQuery("User.getAll", User.class);
        try {
            List<User> users = q.getResultList();
            return users;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        try {
            User u = (User) q.getSingleResult();
            return u;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void remove(User user) {
        em.remove(em.merge(user));
    }
}
