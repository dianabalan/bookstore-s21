package service.books;

import database.DbConnection;
import exceptions.BookAlreadyExistsException;
import exceptions.InexistentBookException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQuantityException;
import model.Book;
import model.CoverType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DbInventoryService implements InventoryService {

    //made this field final, because we don't need to modify it
    private final Connection connection;

    public DbInventoryService(String url, String username, String password) {
        this.connection = DbConnection.getConnection(url, username, password);
    }

    private static Book extractBookInfo(ResultSet resultSet) throws SQLException {
        String dbIsbn = resultSet.getString("isbn");
        String dbTitle = resultSet.getString("title");
        String authors = resultSet.getString("authors");
        int stock = resultSet.getInt("stock");
        double price = resultSet.getDouble("price");

        Date publishDate = resultSet.getDate("publish_date");
        String coverTypeStr = resultSet.getString("cover_type");

        //homework - safevalueof

        Book book = new Book(dbIsbn, dbTitle, price, stock, publishDate.toLocalDate(), CoverType.valueOf(coverTypeStr));
        book.addAuthors(authors);
        return book;
    }

    @Override
    public void add(String isbn, String title, String authors, double price, int stock, LocalDate publishDate, CoverType coverType) throws BookAlreadyExistsException {
        PreparedStatement insertStatement;
        PreparedStatement selectStmt;
        try {

            selectStmt = this.connection.prepareStatement("SELECT * FROM books WHERE isbn=?");
            selectStmt.setString(1, isbn);

            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {
                throw new BookAlreadyExistsException(String.format("Book with isbn %s already exists!", isbn));
            } else {
                insertStatement = this.connection.prepareStatement("INSERT INTO books(isbn, stock, price, authors, title, publish_date, cover_type) VALUES (?,?,?,?,?,?,?)");
                insertStatement.setString(1, isbn);
                insertStatement.setInt(2, stock);
                insertStatement.setDouble(3, price);
                insertStatement.setString(4, authors);
                insertStatement.setString(5, title);
                insertStatement.setDate(6, Date.valueOf(publishDate));
                insertStatement.setString(7, coverType.name());

                insertStatement.executeUpdate();
                insertStatement.close();
            }

        } catch (SQLException e) {
            System.out.println("db error: " + e.getMessage());
        }


    }

    @Override
    public void add(Book book) throws BookAlreadyExistsException {
        System.out.println("N/A");
    }

    @Override
    public void delete(String title) throws InexistentBookException {
        PreparedStatement deleteStmt;
        PreparedStatement selectStmt;
        try {

            selectStmt = this.connection.prepareStatement("SELECT * FROM books WHERE title=?");
            selectStmt.setString(1, title);

            ResultSet resultSet = selectStmt.executeQuery();
            if (!resultSet.next()) {
                throw new InexistentBookException(String.format("Book with title %s does not exist!", title));
            } else {
                //delete book
                deleteStmt = this.connection.prepareStatement("DELETE FROM books WHERE title=?");
                deleteStmt.setString(1, title);

                deleteStmt.executeUpdate();
                deleteStmt.close();
                selectStmt.close();
            }
        } catch (SQLException e) {
            System.out.println("db error: " + e.getMessage());
        }
    }

    @Override
    public Book searchByTitle(String title) throws InexistentBookException {
        PreparedStatement selectStmt;
        try {

            selectStmt = this.connection.prepareStatement("SELECT * FROM books WHERE title=?");
            selectStmt.setString(1, title);

            ResultSet resultSet = selectStmt.executeQuery();
            if (!resultSet.next()) {
                throw new InexistentBookException(String.format("Book with title %s does not exist!", title));
            } else {
                Book book = extractBookInfo(resultSet);

                selectStmt.close();

                return book;

            }
        } catch (SQLException e) {
            System.out.println("db error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Book searchByIsbn(String isbn) throws InexistentBookException {
        PreparedStatement selectStmt;
        try {

            selectStmt = this.connection.prepareStatement("SELECT * FROM books WHERE isbn=?");
            selectStmt.setString(1, isbn);

            ResultSet resultSet = selectStmt.executeQuery();
            if (!resultSet.next()) {
                throw new InexistentBookException(String.format("Book with isbn %s does not exist!", isbn));
            } else {
                Book book = extractBookInfo(resultSet);
                selectStmt.close();
                return book;
            }
        } catch (SQLException e) {
            System.out.println("db error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateStock(String title, int quantity) throws InexistentBookException, InvalidQuantityException {

        searchByTitle(title);

        if (quantity < 0) {
            throw new InvalidQuantityException("Negative quantity. Cannot update.");
        }

        try {
            PreparedStatement updateStmt = this.connection.prepareStatement("UPDATE books SET stock=? WHERE title=?");
            updateStmt.setInt(1, quantity);
            updateStmt.setString(2, title);

            updateStmt.executeUpdate();

            updateStmt.close();
        } catch (SQLException e) {
            System.out.println("db error: " + e.getMessage());
        }

    }

    @Override
    public void updatePrice(String title, double price) throws InexistentBookException, InvalidPriceException {
        //TODO - homework
    }

    @Override
    public void displayAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books ORDER BY title");
            ResultSet resultSet = statement.executeQuery();

            boolean empty = true;
            while (resultSet.next()) {
                empty = false;
                System.out.println(extractBookInfo(resultSet));
            }
            statement.close();
            if (empty) {
                System.out.println("No books in db!");
            }

        } catch (SQLException e) {
            System.out.println("DB ERROR: " + e.getMessage());
        }
    }
}
