package service.shopping_cart;

import database.DbConnection;
import entities.Book;
import entities.Customer;
import entities.ShoppingCart;
import entities.ShoppingCartItem;
import entities.ShoppingCartItemPK;
import exceptions.InexistentItemException;
import exceptions.InvalidQuantityException;
import jakarta.persistence.Query;
import model.ShoppingCartStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class HibernateShoppingCartsService implements ShoppingCartsService {

    private SessionFactory sessionFactory;

    public HibernateShoppingCartsService() {
        this.sessionFactory = DbConnection.INSTANCE.getSessionFactory();
    }

    @Override
    public void addToCart(Customer customer, Book book) {

        Session session = this.sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        ShoppingCart openShoppingCart = null;
        for (ShoppingCart shoppingCart : customer.getShoppingCarts()) {
            if (shoppingCart.getStatus().equals(ShoppingCartStatus.OPEN)) {
                openShoppingCart = shoppingCart;
                break;
            }
        }

        ShoppingCart customerShoppingCart = null;
        if (openShoppingCart == null) {
            //we don't have an open shopping cart

            customerShoppingCart = new ShoppingCart();

            customerShoppingCart.setId(System.currentTimeMillis());
            customerShoppingCart.setStatus(ShoppingCartStatus.OPEN);
            customerShoppingCart.setLastEdited(LocalDateTime.now());
            customerShoppingCart.setCustomer(customer);

            //customer is in NEW state
            customer.getShoppingCarts().add(customerShoppingCart);

            session.merge(customerShoppingCart);


        } else {
            //customer already has an open shopping cart
            //insert or update just the item
            customerShoppingCart = openShoppingCart;
        }

        //here we have a shopping cart for sure

        boolean itemExists = false;
        for (ShoppingCartItem item : customerShoppingCart.getItems()) {
            if (item.getBook().getIsbn().equals(book.getIsbn())) {
                itemExists = true;
                int oldQuantity = item.getQuantity();
                item.setQuantity(oldQuantity + 1);

                session.merge(item);
                break;
            }
        }

        if (!itemExists) {
            //we need to add it
            ShoppingCartItem newShoppingCartItem = new ShoppingCartItem();
            newShoppingCartItem.setQuantity(1);
            newShoppingCartItem.setBook(book);
            newShoppingCartItem.setShoppingCart(customerShoppingCart);
            newShoppingCartItem.setId(new ShoppingCartItemPK(book.getIsbn(), customerShoppingCart.getId()));

            session.merge(newShoppingCartItem);

            customerShoppingCart.getItems().add(newShoppingCartItem);
        }


        transaction.commit();
        session.close();
    }

    @Override
    public void removeFromCart(Customer customer, Book book) throws InexistentItemException {

        //customer is in detached state => all the associated entities are in detached state

        //find the open shopping cart of the customer

        //if no cart => display info message and return
        //if there is an open cart, search for the item with same isbn as parameter book
        //if you find it, remove it
        //don't forget that there is a bidirectional relationship between ShoppingCart and ShoppingCartItem :)


    }

    @Override
    public void updateQuantity(Customer customer, Book book, int quantity) throws InvalidQuantityException, InexistentItemException {

        //customer is in detached state
        //and the associated entities (the list of ShoppingCart entities) are also in detached state
        //as the cascade is set to ALL

        //find the open shopping cart of the customer

        //if no cart => display info message and return

        //else, iterate over the items of the shopping cart
        //if you find the item, update it

    }

    @Override
    public int getQuantity(Customer customer, Book book) {
        Session session = this.sessionFactory.openSession();

        Query query = session.createNamedQuery("getQuantity", Integer.class);
        query.setParameter("isbn", book.getIsbn());
        query.setParameter("id", customer.getId());

        List<Integer> result = query.getResultList();
        if (result.isEmpty()) {
            return 0;
        } else {
            return result.get(0);
        }
    }

    @Override
    public void displayAll(Customer customer) {

        //think about a named query you can use that gets all the items of the OPEN shopping cart of this customer
    }
}
