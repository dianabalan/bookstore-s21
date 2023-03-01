package service.books;

import com.opencsv.bean.CsvToBeanBuilder;
import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import exceptions.InsufficientStockException;
import exceptions.InvalidPriceException;
import model.Book;
import model.CoverType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class MyInventoryService implements InventoryService {

    //title,book
    private Map<String, Book> books = new TreeMap<>();

    public MyInventoryService() {
        //populate books with values from csv

        try {
            List<Book> csvBooks = new CsvToBeanBuilder(new FileReader("src/main/resources/books.csv"))
                    .withType(Book.class)
                    .withSeparator(';')
                    .build()
                    .parse();

            for (Book book : csvBooks){
                this.books.put(book.getTitle(), book);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File books.csv not found!");
            System.exit(0);
        }
    }

    @Override
    public void add(String isbn, String title, String authors, double price, int stock, LocalDate publishDate, CoverType coverType) throws BookAlreadyExistsException {

        if (exists(title)) {
            throw new BookAlreadyExistsException(String.format("Book with title %s already exists", title));
        } else {
            Book book = new Book(isbn, title, price, stock, publishDate, coverType);
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
        if (exists(title)) {
            this.books.remove(title);
        } else {
            throw new InexistentBookException(String.format("Book with title %s does not exist.", title));
        }
    }

    @Override
    public Optional<Book> searchByTitle(String title){
        return Optional.ofNullable(this.books.get(title));
    }

    @Override
    public Book searchByIsbn(String isbn) throws InexistentBookException {

        for (Book book : this.books.values()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        throw new InexistentBookException(String.format("Book with isbn %s does not exist.", isbn));
    }

    @Override
    public void updateStock(String title, int quantity) throws InexistentBookException {

        if (!exists(title)) {
            throw new InexistentBookException(String.format("Book with title %s does not exist.", title));
        }

        Book book = this.books.get(title);

        try {
            book.updateStock(quantity);
        } catch (InsufficientStockException e) {
            System.out.println("This will never execute.");
        }

    }

    @Override
    public void updatePrice(String title, double price) throws InexistentBookException, InvalidPriceException {

        if (!exists(title)) {
            throw new InexistentBookException(String.format("Book with title %s does not exist.", title));
        }

        Book book = this.books.get(title);
        book.updatePrice(price);
    }

    @Override
    public void displayAll() {
        for (Map.Entry<String, Book> entry : this.books.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private boolean exists(String title) {
        return this.books.containsKey(title) ? true : false;
    }
}
