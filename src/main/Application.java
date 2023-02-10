package main;

import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import exceptions.InexistentItemException;
import exceptions.InsufficientStockException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQuantityException;
import service.BookStore;
import service.books.InventoryService;
import service.books.MyInventoryService;
import service.shopping_cart.MyShoppingCartsService;
import service.shopping_cart.ShoppingCartsService;

import java.util.Map;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        BookStore bookStore = new BookStore(new MyInventoryService(), new MyShoppingCartsService());
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.println("Admin(1) or client(2)?");
                int option = Integer.parseInt(scanner.nextLine());
                //homework - optimize menu: if user selects admin option, it should display
                //the admin menu and process the admin option until admin chooses 0
                //same for client

                if (option == 1) {
                    //admin menu
                    displayAdminMenu();
                    processAdminOption(scanner, bookStore.getInventoryService());

                } else {
                    //client menu
                    System.out.println("What is your client id?");
                    String clientId = scanner.nextLine();

                    displayClientMenu();
                    processClientOption(scanner, clientId, bookStore.getInventoryService(), bookStore.getShoppingCartsService());
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid value for option. Should be a number");
            }

        } while (true);


    }

    private static void processClientOption(Scanner scanner, String clientId, InventoryService inventoryService, ShoppingCartsService shoppingCartsService) {
        System.out.println("Input choice: ");
        int option = Integer.parseInt(scanner.nextLine());

        try {
            switch (option) {
                case 0:
                    System.exit(0);
                case 1:
                    //add to cart
                    System.out.println("Input isbn: ");
                    String isbn = scanner.nextLine();

                    inventoryService.searchByIsbn(isbn);

                    //homework - add validation:check if there is enough stock
                    shoppingCartsService.addToCart(clientId, isbn);

                    System.out.println("Added to cart successfully!");
                    break;
                case 2:
                    //remove from cart
                    System.out.println("Which item? ");
                    displayAllItemsInShoppingCart(clientId, inventoryService, shoppingCartsService);

                    System.out.println("Input isbn: ");
                    isbn = scanner.nextLine();

                    shoppingCartsService.removeFromCart(clientId, isbn);
                    System.out.println("Successfully removed!");
                    break;
                case 3:
                    //update quantity
                    System.out.println("Which item? ");
                    displayAllItemsInShoppingCart(clientId, inventoryService, shoppingCartsService);

                    System.out.println("Input isbn: ");
                    isbn = scanner.nextLine();

                    System.out.println("Input quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    //homework - add validation: check if there is enough stock
                    shoppingCartsService.updateQuantity(clientId, isbn, quantity);

                    System.out.println("Successfully updated quantity!");
                    break;
                case 4:
                    //display shopping cart items
                    displayAllItemsInShoppingCart(clientId, inventoryService, shoppingCartsService);
                    break;
                case 5:
                    //display all books in inventory
                    inventoryService.displayAll();
                    break;
                case 6:
                    //search book by title
                    System.out.println("Input title: ");
                    String title = scanner.nextLine();
                    System.out.println(inventoryService.searchByTitle(title));
                    break;
            }

        } catch (InexistentBookException | InvalidQuantityException | InexistentItemException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayAllItemsInShoppingCart(String clientId, InventoryService inventoryService, ShoppingCartsService shoppingCartsService) throws InexistentBookException {
        for (Map.Entry<String, Integer> entry : shoppingCartsService.getShoppingCart(clientId).getItems().entrySet()) {
            System.out.println(inventoryService.searchByIsbn(entry.getKey()) + ", quantity in cart: " + entry.getValue());
        }
    }

    private static void processAdminOption(Scanner scanner, InventoryService inventoryService) {

        String isbn;
        String title;
        double price;
        int quantity;

        System.out.println("Input choice: ");
        int option = Integer.parseInt(scanner.nextLine());

        try {
            switch (option) {
                case 0:
                    System.exit(0);
                case 1:
                    //add book
                    System.out.println("Input isbn: ");
                    isbn = scanner.nextLine();

                    System.out.println("Input title: ");
                    title = scanner.nextLine();

                    System.out.println("Input author names, separated by comma (,):");
                    String authors = scanner.nextLine();

                    System.out.println("Input price: ");
                    price = Double.parseDouble(scanner.nextLine());

                    System.out.println("Input stock: ");
                    quantity = Integer.parseInt(scanner.nextLine());

                    inventoryService.add(isbn, title, authors, price, quantity);

                    System.out.println("Book successfully added!");

                    break;
                case 2:
                    //remove book
                    System.out.println("Input title: ");
                    title = scanner.nextLine();

                    inventoryService.delete(title);

                    System.out.println("Book succcessfully deleted!");
                    break;
                case 3:
                    System.out.println("Input title: ");
                    title = scanner.nextLine();

                    System.out.println("Found: " + inventoryService.searchByTitle(title));
                    //search by title
                    break;
                case 4:
                    System.out.println("Input isbn: ");
                    isbn = scanner.nextLine();

                    System.out.println("Found: " + inventoryService.searchByIsbn(isbn));
                    //search by isbn
                    break;
                case 5:
                    //update quantity
                    System.out.println("Input title: ");
                    title = scanner.nextLine();

                    System.out.println("Input quantity: ");
                    quantity = Integer.parseInt(scanner.nextLine());

                    inventoryService.updateStock(title, quantity);
                    System.out.println("Quantity updated successfully!");
                    break;
                case 6:
                    //update price
                    System.out.println("Input title: ");
                    title = scanner.nextLine();

                    System.out.println("Input price: ");
                    price = Double.parseDouble(scanner.nextLine());

                    inventoryService.updatePrice(title, price);

                    System.out.println("Price updated successfully!");
                    break;
                case 7:
                    //display all
                    inventoryService.displayAll();
                    break;
                default:
                    System.out.println("Invalid option. Values 0-7");

            }

        } catch (BookAlreadyExistsException | InexistentBookException | InsufficientStockException | InvalidPriceException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayAdminMenu() {

        System.out.println("********ADMIN MENU***********");
        System.out.println("0. Exit");
        System.out.println("1. Add book");
        System.out.println("2. Remove book");
        System.out.println("3. Search by title");
        System.out.println("4. Search by isbn");
        System.out.println("5. Update quantity");
        System.out.println("6. Update price");
        System.out.println("7. Display all");
        System.out.println("*****************************");
    }


    private static void displayClientMenu() {
        System.out.println("********CLIENT MENU***********");
        System.out.println("0. Exit");
        System.out.println("1. Add to cart");
        System.out.println("2. Remove to cart");
        System.out.println("3. Update quantity");
        System.out.println("4. Display shopping cart items");
        System.out.println("5. Display books from inventory");
        System.out.println("6. Search book by title");
        System.out.println("*****************************");
    }
}
