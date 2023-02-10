package service.books;

import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import exceptions.InsufficientStockException;
import exceptions.InvalidPriceException;
import model.Book;

import java.util.Map;
import java.util.TreeMap;

public class MyInventoryService implements InventoryService {

    private Map<String, Book> books = new TreeMap<>();

    @Override
    public void add(String isbn, String title, String authors, double price, int stock) throws BookAlreadyExistsException {

        if (exists(title)) {
            throw new BookAlreadyExistsException(String.format("Book with title %s already exists", title));
        } else {
            Book book = new Book(isbn, title, price, stock);
            book.addAuthors(authors);

            this.books.put(title, book);
        }

    }

    @Override
    public void add(Book book) throws BookAlreadyExistsException {
        System.out.println("TO BE IMPLEMENTED");
    }

    @Override
    public void delete(String title) throws InexistentBookException {
        if (exists(title)){
            this.books.remove(title);
        } else{
            throw new InexistentBookException(String.format("Book with title %s does not exist.", title));
        }
    }

    @Override
    public Book searchByTitle(String title) throws InexistentBookException {
        Book book = this.books.get(title);

        if (book == null){
            throw new InexistentBookException(String.format("Book with title %s does not exist.", title));
        }

        return book;

    }

    @Override
    public Book searchByIsbn(String isbn) throws InexistentBookException {

        for (Book book : this.books.values()){
            if (book.getIsbn().equals(isbn)){
                return book;
            }
        }

        throw new InexistentBookException(String.format("Book with isbn %s does not exist.",isbn));
    }

    @Override
    public void updateStock(String title, int quantity) throws InexistentBookException, InsufficientStockException {

        if (!exists(title)){
            throw new InexistentBookException(String.format("Book with title %s does not exist.", title));
        }

        Book book = this.books.get(title);

        book.updateStock(quantity);

    }

    @Override
    public void updatePrice(String title, double price) throws InexistentBookException, InvalidPriceException {

        if (!exists(title)){
            throw new InexistentBookException(String.format("Book with title %s does not exist.", title));
        }

        Book book = this.books.get(title);
        book.updatePrice(price);
    }

    @Override
    public void displayAll() {
        for (Map.Entry<String, Book> entry: this.books.entrySet()){
            System.out.println(entry.getValue());
        }
    }


    private boolean exists(String title) {
        return this.books.containsKey(title) ? true : false;
    }
}
