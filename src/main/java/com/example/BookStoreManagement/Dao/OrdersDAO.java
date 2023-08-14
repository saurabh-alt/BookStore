package com.example.BookStoreManagement.Dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.BookStoreManagement.Orders;

import java.util.List;

public class OrdersDAO {
    private final SessionFactory sessionFactory;

    public OrdersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveOrder(Orders orders) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(orders);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Orders getOrderById(Long orderId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Orders.class, orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Orders> getAllOrders() {
        try (Session session = sessionFactory.openSession()) {
            Query<Orders> query = session.createQuery("FROM Order", Orders.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateOrder(Orders orders) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(orders);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(Orders orders) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(orders);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
