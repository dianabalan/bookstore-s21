package main;

import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import exceptions.InexistentItemException;
import exceptions.InsufficientStockException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQuantityException;
import model.Book;
import service.BookStore;
import service.books.DbInventoryService;
import service.books.InventoryService;
import service.books.MyInventoryService;
import service.shopping_cart.DbShoppingCartsService;
import service.shopping_cart.MyShoppingCartsService;
import service.shopping_cart.ShoppingCartsService;

import java.util.Map;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        String storageType = args[0];
        BookStore bookStore = null;
        switch (storageType) {
            case "persistent":
                System.out.println("Initializing persistent storage application");
                bookStore = new BookStore(new DbInventoryService(), new DbShoppingCartsService());
                break;
            case "non-persistent":
                System.out.println("Initializing non-persistent storage application");
                bookStore = new BookStore(new MyInventoryService(), new MyShoppingCartsService());
                break;
            default:
                System.out.println("Invalid program argument!!");
                System.exit(0);
        }


        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.println("***********MAIN MENU***********");
                System.out.println("0. Exit");
                System.out.println("1. Admin");
                System.out.println("2. Client");
                System.out.println("******************************");
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 0:
                        System.exit(0);
                    case 1:
                        boolean backToMainMenu;
                        //admin menu
                        do {
                            displayAdminMenu();
                            backToMainMenu = processAdminOption(scanner, bookStore.getInventoryService());
                        } while (!backToMainMenu);
                        break;

                    case 2:
                        //client menu
                        System.out.println("What is your client id?");
                        String clientId = scanner.nextLine();

                        do {
                            displayClientMenu();
                            backToMainMenu = processClientOption(scanner, clientId, bookStore.getInventoryService(), bookStore.getShoppingCartsService());
                        } while (!backToMainMenu);
                        break;
                    default:
                        System.out.println("Invalid option. Choose 0-2");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid value for option. Should be a number");
            }

        } while (true);


    }

    private static boolean processClientOption(Scanner scanner, String clientId, InventoryService inventoryService, ShoppingCartsService shoppingCartsService) {
        System.out.println("Input choice: ");
        int option = Integer.parseInt(scanner.nextLine());

        try {
            switch (option) {
                case 0:
                    return true;
                case 1:
                    //add to cart
                    System.out.println("Input isbn: ");
                    String isbn = scanner.nextLine();

                    Book book = inventoryService.searchByIsbn(isbn);

                    int quantity = shoppingCartsService.getQuantity(clientId, isbn);

                    book.checkStock(quantity == 0 ? -1 : -(quantity + 1));

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
                    quantity = Integer.parseInt(scanner.nextLine());

                    book = inventoryService.searchByIsbn(isbn);
                    book.checkStock(quantity == 0 ? -1 : -(quantity + 1));

                    shoppingCartsService.updateQuantity(clientId, isbn, quantity);

                    System.out.println("Successfully updated quantity!");
                    break;
                case 4:
                    //display shopping cart items
                    displayAllItemsInShoppingCart(clientId, inventoryService, shoppingCartsService);
                    //TODO - homework - check if this works
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

        } catch (InexistentBookException | InvalidQuantityException | InexistentItemException |
                 InsufficientStockException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private static void displayAllItemsInShoppingCart(String clientId, InventoryService inventoryService, ShoppingCartsService shoppingCartsService) throws InexistentBookException {
        for (Map.Entry<String, Integer> entry : shoppingCartsService.getShoppingCartItems(clientId).entrySet()) {
            System.out.println(inventoryService.searchByIsbn(entry.getKey()) + ", quantity in cart: " + entry.getValue());
        }
    }

    private static boolean processAdminOption(Scanner scanner, InventoryService inventoryService) {

        String isbn;
        String title;
        double price;
        int quantity;

        System.out.println("Input choice: ");
        int option = Integer.parseInt(scanner.nextLine());

        try {
            switch (option) {
                case 0:
                    return true;
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
                    //search by title
                    System.out.println("Input title: ");
                    title = scanner.nextLine();

                    System.out.println("Found: " + inventoryService.searchByTitle(title));
                    //search by title
                    break;
                case 4:
                    //search by isbn
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

        } catch (BookAlreadyExistsException | InexistentBookException |
                 InvalidPriceException | InvalidQuantityException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private static void displayAdminMenu() {

        System.out.println("********ADMIN MENU***********");
        System.out.println("0. Back to main menu");
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
        System.out.println("0. Back to main menu");
        System.out.println("1. Add to cart");
        System.out.println("2. Remove to cart");
        System.out.println("3. Update quantity");
        System.out.println("4. Display shopping cart items");
        System.out.println("5. Display books from inventory");
        System.out.println("6. Search book by title");
        System.out.println("*****************************");
    }
}
