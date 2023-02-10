package model;

import exceptions.InexistentItemException;
import exceptions.InvalidQuantityException;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {

    private long id;

    //key = isbn, value= quantity
    private Map<String, Integer> items = new LinkedHashMap<>();

    public ShoppingCart() {
        id = System.currentTimeMillis();
    }

    public void add(String isbn) throws InvalidQuantityException, InexistentItemException {
        if (exists(isbn)) {
            Integer oldQuantity = this.items.get(isbn);
            updateQuantity(isbn, oldQuantity + 1);
        } else {
            this.items.put(isbn, 1);
        }
    }

    public void updateQuantity(String isbn, int quantity) throws InvalidQuantityException, InexistentItemException {
        if (exists(isbn)) {
            if (quantity == 0) {
                remove(isbn);
            } else if (quantity < 0) {
                throw new InvalidQuantityException("Quantity is a negative value.");
            } else {
                this.items.put(isbn, quantity);
            }
        } else {
            throw new InexistentItemException(String.format("Book from shopping cart with isbn %s does not exist!", isbn));
        }
    }

    public void remove(String isbn) throws InexistentItemException {
        if (!exists(isbn)) {
            throw new InexistentItemException(String.format("Book from shopping cart with isbn %s does not exist!", isbn));
        }
        this.items.remove(isbn);
    }

    private boolean exists(String isbn) {
        return this.items.containsKey(isbn) ? true : false;

        //return this.items.containsKey(isbn);
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}
