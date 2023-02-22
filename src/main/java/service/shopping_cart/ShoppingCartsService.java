package service.shopping_cart;

import exceptions.InexistentItemException;
import exceptions.InvalidQuantityException;

import java.util.Map;

public interface ShoppingCartsService {

    void addToCart(String clientId, String isbn);

    void removeFromCart(String clientId, String isbn) throws InexistentItemException;

    void updateQuantity(String clientId, String isbn, int quantit8y) throws InvalidQuantityException, InexistentItemException;

    //isbn, quantity
    Map<String, Integer> getShoppingCartItems(String clientId);

    int getQuantity(String clientId, String isbn);
}
