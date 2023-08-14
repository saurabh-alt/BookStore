package com.example.BookStoreManagement.Dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.BookStoreManagement.Publisher;

import java.util.List;

public class PublisherDAO {
    private final SessionFactory sessionFactory;

    public PublisherDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void savePublisher(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(publisher);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Publisher getPublisherById(Long publisherId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Publisher.class, publisherId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Publisher> getAllPublishers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Publisher> query = session.createQuery("FROM Publisher", Publisher.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatePublisher(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(publisher);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePublisher(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(publisher);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
