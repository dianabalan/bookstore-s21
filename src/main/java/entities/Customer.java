package entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "findByEmail", query = "select c from Customer c where c.email=:email")
}
)

public class Customer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL) //by default, one to one este EAGER
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private CustomerAddress address;

    @ToString.Exclude
    //by default one to many este LAZY
    //nu trebuie sa mai apelez getShoppingCarts pt a avea elemente in lista mea
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();
}
