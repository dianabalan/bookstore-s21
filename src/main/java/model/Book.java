package model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import exceptions.InsufficientStockException;
import exceptions.InvalidPriceException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Book {

    @CsvBindByName
    private String isbn;

    @CsvBindByName
    private String title;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",")
    private List<String> authors;

    @CsvBindByName
    private double price;

    @CsvBindByName
    private int stock;

    private LocalDate publishDate;

    private CoverType coverType;

    public Book() {

    }


    public Book(String isbn, String title, double price, int stock, LocalDate publishDate, CoverType coverType) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.authors = new ArrayList<>();
        this.publishDate = publishDate;
        this.coverType = coverType;

    }

    //comma separated author names
    public void addAuthors(String authors) {
        this.authors.addAll(Arrays.asList(authors.split(",")));
    }

    public void updateStock(int quantity) throws InsufficientStockException {
        checkStock(quantity);
        this.stock = quantity;

    }

    public void checkStock(int quantity) throws InsufficientStockException {
        if (this.stock + quantity < 0) {
            throw new InsufficientStockException("Insufficient stock.");
        }
    }

    //homework - display date with custom formatter dd-MM-yyyy
    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", price=" + price +
                ", stock=" + stock +
                ", publishDate=" + publishDate +
                ", coverType=" + coverType +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void updatePrice(double price) throws InvalidPriceException {
        if (price < 0) {
            throw new InvalidPriceException("Price should have positive value.");
        }
        this.price = price;
    }
}
