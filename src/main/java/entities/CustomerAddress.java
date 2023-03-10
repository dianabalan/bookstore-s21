package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "customer_address")
public class CustomerAddress {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String city;

    private String county;

    private String street;
}
