package com.example.BookStoreManagement.Dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.BookStoreManagement.Customer;

import java.util.List;

public class CustomerDAO {
    private final SessionFactory sessionFactory;

    public CustomerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveCustomer(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerById(Long customerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Customer.class, customerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Customer> getAllCustomers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Customer> query = session.createQuery("FROM Customer", Customer.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateCustomer(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
