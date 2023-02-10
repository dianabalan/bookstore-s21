package service.shopping_cart;

import exceptions.InexistentItemException;
import exceptions.InsufficientStockException;
import exceptions.InvalidQuantityException;
import model.Book;
import model.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class MyShoppingCartsService implements ShoppingCartsService {

    private Map<String, ShoppingCart> shoppingCartMap = new HashMap<>();

    @Override
    public void addToCart(String clientId, String isbn) throws InvalidQuantityException, InexistentItemException {

        if (!this.shoppingCartMap.containsKey(clientId)){
            this.shoppingCartMap.put(clientId, new ShoppingCart());
        }

        ShoppingCart shoppingCart = this.shoppingCartMap.get(clientId);
        shoppingCart.add(isbn);
    }

    @Override
    public void removeFromCart(String clientId, String isbn) throws InexistentItemException {
        //validate that clientId key exists in map. if not, throw a custom exception (InexistentClientException)
        this.shoppingCartMap.get(clientId).remove(isbn);
    }

    @Override
    public void updateQuantity(String clientId, String isbn, int quantity) throws InvalidQuantityException, InexistentItemException {
        //validate that clientId key exists in map. if not, throw a custom exception (InexistentClientException)
        this.shoppingCartMap.get(clientId).updateQuantity(isbn,quantity);
    }

    @Override
    public ShoppingCart getShoppingCart(String clientId) {
        return this.shoppingCartMap.get(clientId);
    }
}
