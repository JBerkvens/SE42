/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.dao;

import auction.domain.Item;
import auction.domain.Item;
import auction.domain.Item_;
import auction.domain.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Jeroen
 */
public class ItemDAOJPAImpl implements ItemDAO {

    private final EntityManager em;
    private Item item;

    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Item.class));
        return em.createQuery(cq).getResultList().size();
    }

    @Override
    public void create(Item item) {
        em.persist(item);
    }

    @Override
    public void edit(Item item) {
        em.merge(item);
    }

    @Override
    public Item find(Long id) {
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Item.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root from = cq.from(Item.class);
        cq.select(from);
        cq.where(em.getCriteriaBuilder().equal(from.get(Item_.description), description));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public void remove(Item item) {
        em.remove(em.merge(item));
    }
}