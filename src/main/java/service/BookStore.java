package service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import service.authors.AuthorsService;
import service.books.InventoryService;
import service.customers.CustomersService;
import service.reports.ReportsService;
import service.shopping_cart.ShoppingCartsService;

@Getter
@AllArgsConstructor
public class BookStore {

    private InventoryService inventoryService;
    private ShoppingCartsService shoppingCartsService;
    private AuthorsService authorsService;
    private CustomersService customersService;

    private ReportsService reportsService;




}
