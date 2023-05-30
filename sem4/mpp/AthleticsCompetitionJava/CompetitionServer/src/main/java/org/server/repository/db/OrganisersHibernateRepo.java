package org.server.repository.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.model.Organiser;
import org.model.exception.DuplicatedException;
import org.server.repository.OrganisersRepo;

import java.util.List;
import java.util.UUID;

public class OrganisersHibernateRepo implements OrganisersRepo {
    SessionFactory sessionFactory;

    public OrganisersHibernateRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Organiser findByUsername(String username) {
        Organiser organiser = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                organiser = session.createQuery("from Organiser where username = :username", Organiser.class)
                        .setParameter("username", username).setMaxResults(1)
                        .uniqueResult();
                tx.commit();
            } catch(RuntimeException ex) {
                System.err.println("Select Exception " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return organiser;
    }

    @Override
    public void store(Organiser obj) throws DuplicatedException {

    }

    @Override
    public void remove(Organiser obj) {

    }

    @Override
    public void update(UUID uuid, Organiser newObj) {

    }

    @Override
    public Iterable<Organiser> getAll() {
        List<Organiser> organisers = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                organisers = session.createQuery("from Organiser", Organiser.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Select Exception " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return organisers;
    }

    @Override
    public Organiser findById(UUID uuid) {
        return null;
    }
}
