package com.example.BookStoreManagement.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.example.BookStoreManagement.Book;
import com.example.BookStoreManagement.Category;
import com.example.BookStoreManagement.Customer;
import com.example.BookStoreManagement.OrderItem;
import com.example.BookStoreManagement.Orders;
import com.example.BookStoreManagement.Publisher;
import com.example.BookStoreManagement.Dao.BookDAO;
import com.example.BookStoreManagement.Dao.CustomerDAO;
import com.example.BookStoreManagement.Dao.OrdersDAO;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Create the SessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bookstore Management System");
        System.out.println("--------------------------");

        try {
            while (true) {
                System.out.println("\n1. Add a Book");
                System.out.println("2. View All Books");
                System.out.println("3. Update a Book");
                System.out.println("4. Delete a Book");
                System.out.println("5. Add a Customer");
                System.out.println("6. Add an Order");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addBook(sessionFactory, scanner);
                        break;
                    case 2:
                        viewAllBooks(sessionFactory);
                        break;
                    case 3:
                        updateBook(sessionFactory, scanner);
                        break;
                    case 4:
                        deleteBook(sessionFactory, scanner);
                        break;
                    case 5:
                        addCustomer(sessionFactory, scanner);
                        break;
                    case 6:
                        addOrder(sessionFactory, scanner);
                        break;
                    case 7:
                        System.out.println("Exiting the program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } finally {
            sessionFactory.close();
            scanner.close();
        }
    }

    // ... (other methods remain the same)
    
    private static void addCustomer(SessionFactory sessionFactory, Scanner scanner) {
        System.out.println("\nAdding a new customer:");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        // Create Customer entity and perform CRUD operation within a transaction
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            session.persist(customer);

            transaction.commit();
            System.out.println("New customer added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void addOrderItem(SessionFactory sessionFactory, Scanner scanner) {
        System.out.println("\nAdding a new order item:");
        System.out.print("Enter order ID: ");
        Long orderId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter book ID: ");
        Long bookId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        // Fetch the order and book by their IDs
        OrdersDAO orderDAO = new OrdersDAO(sessionFactory);
        Orders order = orderDAO.getOrderById(orderId);

        BookDAO bookDAO = new BookDAO(sessionFactory);
        Book book = bookDAO.getBookById(bookId);

        if (order != null && book != null) {
            // Create OrderItem entity and perform CRUD operation within a transaction
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();

                OrderItem orderItem = new OrderItem();
                orderItem.setOrders(order);
                orderItem.setBook(book);
                orderItem.setQuantity(quantity);
                session.persist(orderItem);

                transaction.commit();
                System.out.println("New order item added successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (order == null) {
                System.out.println("Order with ID " + orderId + " not found.");
            }
            if (book == null) {
                System.out.println("Book with ID " + bookId + " not found.");
            }
        }
    }

    private static void addBook(SessionFactory sessionFactory, Scanner scanner) {
        System.out.println("\nAdding a new book:");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter publication date (yyyy-MM-dd): ");
        String publicationDateStr = scanner.nextLine();
        Date publicationDate = java.sql.Date.valueOf(publicationDateStr);

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity available: ");
        int quantityAvailable = scanner.nextInt();

        // Create entities and perform CRUD operation within a transaction
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Category category = new Category();
            category.setName("Fiction"); // Assuming all added books belong to the "Fiction" category
            session.persist(category);

            Publisher publisher = new Publisher();
            publisher.setName("Example Publisher"); // Assuming all added books have the same publisher
            publisher.setAddress("123 Main St");
            publisher.setContactNumber("123-456-7890");
            session.persist(publisher);

            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublicationDate(publicationDate);
            book.setPrice(price);
            book.setQuantityAvailable(quantityAvailable);
            book.setCategory(category);
            book.setPublisher(publisher);
            session.persist(book);

            transaction.commit();
            System.out.println("New book added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void viewAllBooks(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            List<Book> books = session.createQuery("FROM Book", Book.class).getResultList();

            System.out.println("\nAll Books in the Bookstore:");
            for (Book book : books) {
                System.out.println(book.getTitle() + " by " + book.getAuthor() + ", Price: Rs." + book.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateBook(SessionFactory sessionFactory, Scanner scanner) {
        System.out.println("\nUpdating a book:");
        System.out.print("Enter the ID of the book to update: ");
        Long bookId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        // Fetch the book by ID
        BookDAO bookDAO = new BookDAO(sessionFactory);
        Book bookToUpdate = bookDAO.getBookById(bookId);

        if (bookToUpdate != null) {
            System.out.print("Enter new title (press Enter to keep the current title): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()) {
                bookToUpdate.setTitle(newTitle);
            }

            System.out.print("Enter new author (press Enter to keep the current author): ");
            String newAuthor = scanner.nextLine();
            if (!newAuthor.isEmpty()) {
                bookToUpdate.setAuthor(newAuthor);
            }

            System.out.print("Enter new publication date (yyyy-MM-dd) (press Enter to keep the current publication date): ");
            String newPublicationDateStr = scanner.nextLine();
            if (!newPublicationDateStr.isEmpty()) {
                Date newPublicationDate = java.sql.Date.valueOf(newPublicationDateStr);
                bookToUpdate.setPublicationDate(newPublicationDate);
            }

            System.out.print("Enter new price (press Enter to keep the current price): ");
            String newPriceStr = scanner.nextLine();
            if (!newPriceStr.isEmpty()) {
                double newPrice = Double.parseDouble(newPriceStr);
                bookToUpdate.setPrice(newPrice);
            }

            System.out.print("Enter new quantity available (press Enter to keep the current quantity): ");
            String newQuantityAvailableStr = scanner.nextLine();
            if (!newQuantityAvailableStr.isEmpty()) {
                int newQuantityAvailable = Integer.parseInt(newQuantityAvailableStr);
                bookToUpdate.setQuantityAvailable(newQuantityAvailable);
            }

            // Perform update within a transaction
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(bookToUpdate);
                transaction.commit();
                System.out.println("Book updated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    private static void deleteBook(SessionFactory sessionFactory, Scanner scanner) {
        System.out.println("\nDeleting a book:");
        System.out.print("Enter the ID of the book to delete: ");
        Long bookId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        // Fetch the book by ID
        BookDAO bookDAO = new BookDAO(sessionFactory);
        Book bookToDelete = bookDAO.getBookById(bookId);

        if (bookToDelete != null) {
            // Perform deletion within a transaction
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.delete(bookToDelete);
                transaction.commit();
                System.out.println("Book deleted successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Book with ID " + bookId + " not found.");
        }
    }
    private static void addOrder(SessionFactory sessionFactory, Scanner scanner) {
        System.out.println("\nAdding a new order:");
        System.out.print("Enter customer ID: ");
        Long customerId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        // Fetch the customer by ID
        CustomerDAO customerDAO = new CustomerDAO(sessionFactory);
        Customer customer = customerDAO.getCustomerById(customerId);

        if (customer != null) {
            // Create Order entity and perform CRUD operation within a transaction
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();

                Orders order = new Orders();
                order.setCustomer(customer);
                order.setOrderDate(new Date());
                session.persist(order);

                transaction.commit();
                System.out.println("New order added successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }

}

