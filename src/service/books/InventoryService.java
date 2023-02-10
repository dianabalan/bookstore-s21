package service.books;

import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import exceptions.InsufficientStockException;
import exceptions.InvalidPriceException;
import model.Book;

public interface InventoryService {

    void add(String isbn, String title, String authors, double price, int stock) throws BookAlreadyExistsException;

    void add(Book book) throws BookAlreadyExistsException;

    void delete(String title) throws InexistentBookException;

    Book searchByTitle(String title) throws InexistentBookException;

    Book searchByIsbn(String isbn) throws InexistentBookException;

    void updateStock(String title, int quantity) throws InexistentBookException, InsufficientStockException;

    void updatePrice(String title, double price) throws InexistentBookException, InvalidPriceException;

    void displayAll();


    //display all

}
