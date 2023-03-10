package service.authors;

import entities.Author;

import java.util.Optional;

public interface AuthorsService {

    void add(Author author);

    void delete(Author author);

    void update(Author author);

    Optional<Author> get(int id);

    Optional<Author> getByName(String name);

    Optional<Author> getByEmail(String email);
}
