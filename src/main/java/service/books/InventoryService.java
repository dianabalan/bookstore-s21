package service.books;

import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQuantityException;
import model.Book;
import model.CoverType;

import java.time.LocalDate;
import java.util.Optional;

public interface InventoryService {

    void add(String isbn, String title, String authors, double price, int stock, LocalDate publishDate, CoverType coverType) throws BookAlreadyExistsException;

    void add(Book book) throws BookAlreadyExistsException;

    void delete(String title) throws InexistentBookException;

    Optional<Book> searchByTitle(String title);

    Book searchByIsbn(String isbn) throws InexistentBookException;

    void updateStock(String title, int quantity) throws InexistentBookException, InvalidQuantityException;

    void updatePrice(String title, double price) throws InexistentBookException, InvalidPriceException;

    void displayAll();

}
