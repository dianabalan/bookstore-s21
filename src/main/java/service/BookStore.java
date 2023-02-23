package service;

import service.books.InventoryService;
import service.reports.ReportsService;
import service.shopping_cart.ShoppingCartsService;

public class BookStore {

    private InventoryService inventoryService;
    private ShoppingCartsService shoppingCartsService;

    private ReportsService reportsService;

    public BookStore(InventoryService inventoryService, ShoppingCartsService shoppingCartsService) {
        this.inventoryService = inventoryService;
        this.shoppingCartsService = shoppingCartsService;
    }

    public BookStore(InventoryService inventoryService, ShoppingCartsService shoppingCartsService, ReportsService reportsService) {
        this.inventoryService = inventoryService;
        this.shoppingCartsService = shoppingCartsService;
        this.reportsService = reportsService;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public ShoppingCartsService getShoppingCartsService() {
        return shoppingCartsService;
    }

    public ReportsService getReportsService() {
        return reportsService;
    }
}
