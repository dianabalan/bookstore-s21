package service.shopping_cart;

import database.DbConnection;
import exceptions.InexistentItemException;
import exceptions.InvalidQuantityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DbShoppingCartsService implements ShoppingCartsService {

    private final Connection connection;

    public DbShoppingCartsService(String url, String username, String password) {
        this.connection = DbConnection.getConnection(url, username, password);
    }

    @Override
    public void addToCart(String clientId, String isbn) {
        //check if item already exists in cart

        PreparedStatement selectStatement;
        try {
            this.connection.setAutoCommit(false);
            selectStatement = this.connection.prepareStatement("SELECT * FROM shopping_carts sc JOIN shopping_cart_items sci ON sc.id=sci.id_shopping_cart WHERE sc.client_id=? AND sci.isbn_book=?");
            selectStatement.setString(1, clientId);
            selectStatement.setString(2, isbn);

            ResultSet resultSet = selectStatement.executeQuery();

            if (!resultSet.next()) {

                PreparedStatement selectClientStatement = this.connection.prepareStatement("SELECT * FROM shopping_carts WHERE client_id=?");
                selectClientStatement.setString(1, clientId);
                ResultSet shoppingCartsRS = selectClientStatement.executeQuery();

                long shoppingCartId;

                if (!shoppingCartsRS.next()) {
                    shoppingCartId = System.currentTimeMillis();
                    PreparedStatement insertSCStatement = this.connection.prepareStatement("INSERT INTO shopping_carts (id,client_id) VALUES (?,?)");
                    insertSCStatement.setLong(1, shoppingCartId);
                    insertSCStatement.setString(2, clientId);

                    insertSCStatement.executeUpdate();
                    insertSCStatement.close();
                } else {
                    shoppingCartId = shoppingCartsRS.getLong("id");
                }

                PreparedStatement insertSCIStatement = this.connection.prepareStatement("INSERT INTO shopping_cart_items (isbn_book, id_shopping_cart, quantity) VALUES (?,?,?)");
                insertSCIStatement.setString(1, isbn);
                insertSCIStatement.setLong(2, shoppingCartId);
                insertSCIStatement.setInt(3, 1);

                insertSCIStatement.executeUpdate();

                insertSCIStatement.close();
                selectStatement.close();

            } else {
                int quantity = resultSet.getInt("quantity");
                long idShoppingCart = resultSet.getLong("id_shopping_cart");

                PreparedStatement updateStatement = this.connection.prepareStatement("UPDATE shopping_cart_items SET quantity=? WHERE id_shopping_cart=? AND isbn_book=?");
                updateStatement.setInt(1, quantity + 1);
                updateStatement.setLong(2, idShoppingCart);
                updateStatement.setString(3, isbn);

                updateStatement.executeUpdate();
                updateStatement.close();
            }
            selectStatement.close();

            this.connection.commit();
        } catch (SQLException e) {
            System.out.println("db error: " + e.getMessage());
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                this.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void removeFromCart(String clientId, String isbn) throws InexistentItemException {

        //TODO - homework


    }

    @Override
    public void updateQuantity(String clientId, String isbn, int quantity) throws InvalidQuantityException, InexistentItemException {
        //TODO - homework
    }

    @Override
    public Map<String, Integer> getShoppingCartItems(String clientId) {

        Map<String, Integer> resultMap = new LinkedHashMap<>();

        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT sci.isbn_book, sci.quantity FROM shopping_cart_items sci JOIN shopping_carts sc ON sc.id=sci.id_shopping_cart WHERE sc.client_id=?");
            statement.setString(1,clientId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                resultMap.put(resultSet.getString("isbn_book"), resultSet.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println("db error: " + e.getMessage());
        }
        return resultMap;

    }

    @Override
    public int getQuantity(String clientId, String isbn) {
        //TODO - homework
        return 0;
    }
}
