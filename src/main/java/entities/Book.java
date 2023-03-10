package entities;


import exceptions.InsufficientStockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.CoverType;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    private String isbn;

    private String title;

    private double price;

    private int stock;

    @Column(name = "publish_date")
    private LocalDate publishDate;


    @Column(name = "cover_type")
    @Enumerated(EnumType.STRING)
    private CoverType coverType;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "authors_books",
            joinColumns = {@JoinColumn(name = "isbn_book", referencedColumnName = "isbn")},
            inverseJoinColumns = {@JoinColumn(name = "id_author", referencedColumnName = "id")}
    )
    private Set<Author> authors = new LinkedHashSet<>();

    public void checkStock(int quantity) throws InsufficientStockException {
        if (this.stock + quantity < 0) {
            throw new InsufficientStockException("Insufficient stock.");
        }
    }
}
