package com.example.BookStoreManagement.Dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.BookStoreManagement.Book;
import com.example.BookStoreManagement.Category;
import com.example.BookStoreManagement.Customer;
import com.example.BookStoreManagement.OrderItem;
import com.example.BookStoreManagement.Orders;
import com.example.BookStoreManagement.Publisher;

public class App {
    public static void main(String[] args) {
        // Create the SessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // Create DAO instances
        BookDAO bookDAO = new BookDAO(sessionFactory);
        CategoryDAO categoryDAO = new CategoryDAO(sessionFactory);
        PublisherDAO publisherDAO = new PublisherDAO(sessionFactory);
        CustomerDAO customerDAO = new CustomerDAO(sessionFactory);
        OrdersDAO orderDAO = new OrdersDAO(sessionFactory);
        OrderItemDAO orderItemDAO = new OrderItemDAO(sessionFactory);

        // Perform CRUD operations using DAOs
        try {
            // Save entities
            Category category = new Category();
            category.setName("Fiction");
            categoryDAO.saveCategory(category);

            Publisher publisher = new Publisher();
            publisher.setName("Example Publisher");
            publisher.setAddress("123 Main St");
            publisher.setContactNumber("123-456-7890");
            publisherDAO.savePublisher(publisher);

            Book book = new Book();
            book.setTitle("Sample Book");
            book.setAuthor("John Doe");
            book.setPublicationDate(new Date());
            book.setPrice(19.99);
            book.setQuantityAvailable(50);
            book.setCategory(category);
            book.setPublisher(publisher);
            bookDAO.saveBook(book);

            Customer customer = new Customer();
            customer.setFirstName("Alice");
            customer.setLastName("Smith");
            customer.setEmail("alice@example.com");
            customer.setPhoneNumber("555-123-4567");
            customerDAO.saveCustomer(customer);

            Orders orders = new Orders();
            orders.setCustomer(customer);
            orders.setOrderDate(new Date());
            orderDAO.saveOrder(orders);

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setOrders(orders);
            orderItem.setQuantity(3);
            orderItemDAO.saveOrderItem(orderItem);

            // Fetch and display data (optional)
            List<Book> books = bookDAO.getAllBooks();
            for (Book b : books) {
                System.out.println(b.getTitle() + " by " + b.getAuthor() + ", Price: $" + b.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
