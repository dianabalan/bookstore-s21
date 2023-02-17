package model;

import exceptions.InsufficientStockException;
import exceptions.InvalidPriceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Book {

    private String isbn;
    private String title;
    private List<String> authors;
    private double price;
    private int stock;

    public Book(String isbn, String title, List<String> authors, double price, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.stock = stock;
    }

    public Book(String isbn, String title, double price, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.authors = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", price=" + price +
                ", stock=" + stock +
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
