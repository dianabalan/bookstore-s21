package service.books;

import entities.Book;
import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;

import java.util.Optional;

public interface InventoryService {

    void add(Book book) throws BookAlreadyExistsException;

    void update(Book book);

    void delete(Book book) throws InexistentBookException;

    Optional<Book> searchByTitle(String title) throws InexistentBookException;

    Book searchByIsbn(String isbn) throws InexistentBookException;

    void displayAll();
}


