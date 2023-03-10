package service.books;

import database.DbConnection;
import entities.Book;
import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class HibernateInventoryService implements InventoryService {

    private SessionFactory sessionFactory;

    public HibernateInventoryService() {
        this.sessionFactory = DbConnection.INSTANCE.getSessionFactory();
    }


    @Override
    public void add(Book book) throws BookAlreadyExistsException {
        //book should be in NEW state here
    }

    @Override
    public void delete(Book book) throws InexistentBookException {

        //book should be in detached state
    }

    @Override
    public Optional<Book> searchByTitle(String title) {
        //TODO - implementation
        return Optional.empty();
    }

    @Override
    public Book searchByIsbn(String isbn) throws InexistentBookException {
        Session session = this.sessionFactory.openSession();

        Book book = session.get(Book.class, isbn);
        //PERSISTENT


        session.close();

        //book will DETACHED
        if (book == null) {
            throw new InexistentBookException("Book does not exist");
        } else {
            return book;
        }


    }

    @Override
    public void update(Book book) {

        //book will be in detached state
    }


    @Override
    public void displayAll() {
            //think about a named query that gets all the books from the table
    }
}
