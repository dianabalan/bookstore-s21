package service.authors;

import entities.Author;

import java.util.Optional;

public class HibernateAuthorsService implements AuthorsService{
    @Override
    public void add(Author author) {

    }

    @Override
    public void delete(Author author) {

    }

    @Override
    public void update(Author author) {

    }

    @Override
    public Optional<Author> get(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Author> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Author> getByEmail(String email) {
        return Optional.empty();
    }
}
