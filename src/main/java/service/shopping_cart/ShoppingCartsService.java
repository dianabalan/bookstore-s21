package service.shopping_cart;

import entities.Book;
import entities.Customer;
import exceptions.InexistentItemException;
import exceptions.InvalidQuantityException;


public interface ShoppingCartsService {

    void addToCart(Customer customer, Book book);

    void removeFromCart(Customer customer, Book book) throws InexistentItemException;

    void updateQuantity(Customer customer, Book book, int quantity) throws InvalidQuantityException, InexistentItemException;

    int getQuantity(Customer customer, Book book);

    //display all items in shopping cart - OPEN
    void displayAll(Customer customer);
}
