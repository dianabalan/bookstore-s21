package entities;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
//TODO - please read
//there is a bidirectional relationship between ShoppingCartItem and ShoppingCart
//=> when calling equals or hashcode on any of the sides of the relationship,
//it will generate StackOverflow error -> equals/hashcode will be called infinitely :)
//that is why we should be mindful about the fields that we use on equals and hashcode
@EqualsAndHashCode(of="id") //uses just the id (which is unique) field to generate hashcode and equals
@Getter
@Setter
@Entity
@Table(name = "shopping_cart_items")
@NamedQueries({
        @NamedQuery(name = "getQuantity",
                query = "select sci.quantity from ShoppingCartItem sci where sci.book.isbn=:isbn and sci.shoppingCart.customer.id=:id")
})
public class ShoppingCartItem {

    @EmbeddedId
    private ShoppingCartItemPK id;

    private int quantity;

    @ToString.Exclude
    @ManyToOne
    @MapsId("shoppingCartId")
    @JoinColumn(name = "id_shopping_cart")
    private ShoppingCart shoppingCart;

    @ToString.Exclude
    @ManyToOne
    @MapsId("isbnBook")
    @JoinColumn(name = "isbn_book")
    private Book book;

}
