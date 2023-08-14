package com.example.BookStoreManagement.Dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.BookStoreManagement.Category;

import java.util.List;

public class CategoryDAO {
    private final SessionFactory sessionFactory;

    public CategoryDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(category);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category getCategoryById(Long categoryId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Category.class, categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Category> getAllCategories() {
        try (Session session = sessionFactory.openSession()) {
            Query<Category> query = session.createQuery("FROM Category", Category.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(category);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(category);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
