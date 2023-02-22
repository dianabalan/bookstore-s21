package service;

import model.ShoppingCart;
import service.books.InventoryService;
import service.shopping_cart.ShoppingCartsService;

public class BookStore {

    private InventoryService inventoryService;
    private ShoppingCartsService shoppingCartsService;

    public BookStore(InventoryService inventoryService, ShoppingCartsService shoppingCartsService) {
        this.inventoryService = inventoryService;
        this.shoppingCartsService = shoppingCartsService;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public ShoppingCartsService getShoppingCartsService() {
        return shoppingCartsService;
    }
}
