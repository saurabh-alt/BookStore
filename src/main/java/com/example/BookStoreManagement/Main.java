package com.example.BookStoreManagement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create the SessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // Initialize entities
        Category category = new Category();
        category.setName("Programming");

        Publisher publisher = new Publisher();
        publisher.setName("Saurabh And Publising House");
        publisher.setAddress("Mumbradevi Colony ,Diva West,Mumbai");
        publisher.setContactNumber("887900041");

        
        Book book = new Book();
        book.setTitle("JAVA Programming");
        book.setAuthor("Saurabh Nishad");
        book.setPublicationDate(new Date());
        book.setPrice(499.00);
        book.setQuantityAvailable(50);
        book.setCategory(category);
        book.setPublisher(publisher);
        
        Book b1=new Book();
        b1.setTitle("Python Programming");
        b1.setAuthor("Amit Bind");
        b1.setPublicationDate(new Date());
        b1.setPrice(595.50);
        b1.setQuantityAvailable(40);
        b1.setCategory(category);
        b1.setPublisher(publisher);
        
        Book b2=new Book();
        b2.setTitle("C Programming");
        b2.setAuthor("Abhimanyu");
        b2.setPublicationDate(new Date());
        b2.setPrice(695.50);
        b2.setQuantityAvailable(60);
        b2.setCategory(category);
        b2.setPublisher(publisher);
        
        Customer customer = new Customer();
        customer.setFirstName("Amit");
        customer.setLastName("Bind");
        customer.setEmail("amit@gmail.com");
        customer.setPhoneNumber("9372473515");
        
        
        
        Orders o1 = new Orders();
        o1.setCustomer(customer);
        Date d = new Date();
        o1.setOrderDate(d);
       

        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setOrders(o1);
        orderItem.setQuantity(3);

       

        // Perform CRUD operations
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save entities
            session.persist(category);
            session.persist(publisher);
            session.persist(book);
            session.persist(b1);
            session.persist(customer);
            session.persist(o1);
            session.persist(orderItem);
            transaction.commit();
            
            
            // Fetch and display data
            
            List<Book> books = session.createQuery("FROM Book", Book.class).getResultList();
            for (Book b : books) {
                System.out.println(b.getTitle() + " by " + b.getAuthor() + ", Price: RS:" + b.getPrice());
            }
                 } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
        
    }
}
