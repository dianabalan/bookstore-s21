package entities.test;

import database.DbConnection;
import entities.Author;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;

public class AuthorsTest {

    public static void main(String[] args) {


        Session session = DbConnection.INSTANCE.getSessionFactory().openSession();

        Author author = session.get(Author.class, 1);
        //author is in PERSISTENT

        Transaction transaction = session.beginTransaction();

        Author author1 = Author.builder()
                .email("myemail@mail.com")
                .name("nameeee")
                .birthDate(LocalDate.now())
                .build();

        //entity is in state NEW/TRANSIENT

        session.persist(author1);
        //author1 e in PERSISTENT

        transaction.commit();
        //now author1 is in db

        System.out.println(author);
        session.close();
    }
}
