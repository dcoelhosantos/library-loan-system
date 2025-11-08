package br.ufrn.library.repository;

import java.util.List;
import java.util.Optional;

import br.ufrn.library.model.User;

/**
 * The Contract (Interface) for User persistence operations.
 * * Defines *what* operations can be performed on User data,
 * without specifying *how* they are implemented.
 */
public interface UserRepository {

    /**
     * Saves a new user or updates an existing one.
     */
    User save(User user);

    /**
     * Finds a user by their unique ID.
     * O uso de Optional é uma boa prática para métodos find... que podem não retornar nada,
     *  evitando NullPointerException.
     */
    Optional<User> findById(String id);

    /**
     * Retrieves all registered users.
     */
    List<User> findAll();

    /**
     * Deletes a user by their unique ID.
     */
    boolean deleteById(String id);
    
    /**
     * Checks if a user exists by their ID.
     */
    boolean existsById(String id);
}