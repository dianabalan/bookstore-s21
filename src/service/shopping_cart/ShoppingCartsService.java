package service.shopping_cart;

import exceptions.InexistentItemException;
import exceptions.InsufficientStockException;
import exceptions.InvalidQuantityException;
import model.Book;
import model.ShoppingCart;

import java.util.Map;

public interface ShoppingCartsService {

    void addToCart(String clientId, String isbn) throws InvalidQuantityException, InexistentItemException;

    void removeFromCart(String clientId, String isbn) throws InexistentItemException;

    void updateQuantity(String clientId, String isbn, int quantity) throws InvalidQuantityException, InexistentItemException;

    ShoppingCart getShoppingCart(String clientId);

}
