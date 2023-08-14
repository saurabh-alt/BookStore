package com.example.BookStoreManagement.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.BookStoreManagement.OrderItem;

import java.util.List;

public class OrderItemDAO {
    private final SessionFactory sessionFactory;

    public OrderItemDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveOrderItem(OrderItem orderItem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(orderItem);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrderItem getOrderItemById(Long orderItemId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(OrderItem.class, orderItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderItem> getAllOrderItems() {
        try (Session session = sessionFactory.openSession()) {
            Query<OrderItem> query = session.createQuery("FROM OrderItem", OrderItem.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateOrderItem(OrderItem orderItem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(orderItem);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderItem(OrderItem orderItem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(orderItem);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
