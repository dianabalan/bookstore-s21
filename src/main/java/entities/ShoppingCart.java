package entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import model.ShoppingCartStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//TODO - please read
//there is a bidirectional relationship between ShoppingCartItem and ShoppingCart
//=> when calling equals or hashcode on any of the sides of the relationship,
//it will generate StackOverflow error -> equals/hashcode will be called infinitely :)
//that is why we should be mindful about the fields that we use on equals and hashcode
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    private Long id;

    @Column(name = "last_edited")
    private LocalDateTime lastEdited;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    @ToString.Exclude
    //lazy by default
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ShoppingCartItem> items = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Customer customer;


}
