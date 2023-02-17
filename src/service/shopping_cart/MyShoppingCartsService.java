package service.shopping_cart;

import exceptions.InexistentItemException;
import exceptions.InvalidQuantityException;
import model.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class MyShoppingCartsService implements ShoppingCartsService {

    private Map<String, ShoppingCart> shoppingCartMap = new HashMap<>();

    @Override
    public void addToCart(String clientId, String isbn) {

        if (!this.shoppingCartMap.containsKey(clientId)) {
            this.shoppingCartMap.put(clientId, new ShoppingCart());
        }

        ShoppingCart shoppingCart = this.shoppingCartMap.get(clientId);
        shoppingCart.add(isbn);
    }

    @Override
    public void removeFromCart(String clientId, String isbn) throws InexistentItemException {
        this.shoppingCartMap.get(clientId).remove(isbn);
    }

    @Override
    public void updateQuantity(String clientId, String isbn, int quantity) throws InvalidQuantityException, InexistentItemException {
        this.shoppingCartMap.get(clientId).updateQuantity(isbn, quantity);
    }

    @Override
    public Map<String, Integer> getShoppingCartItems(String clientId) {
        return this.shoppingCartMap.get(clientId).getItems();
    }

    @Override
    public int getQuantity(String clientId, String isbn) {
        if (this.shoppingCartMap.get(clientId) != null) {
            return this.shoppingCartMap.get(clientId).getItems().get(isbn);
        } else {
            return 0;
        }
    }
}
