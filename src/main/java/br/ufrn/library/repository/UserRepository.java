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
     * @param user The user object to be saved.
     * @return The saved user (often with an ID generated).
     */
    User save(User user);

    /**
     * Finds a user by their unique ID.
     * @param id The unique identifier for the user.
     * @return An Optional containing the User if found, or Optional.empty() if not.
     * O uso de Optional é uma boa prática para métodos find... que podem não retornar nada,
     *  evitando NullPointerException.
     */
    Optional<User> findById(String id);

    /**
     * Retrieves all registered users.
     * @return A List of all users.
     */
    List<User> findAll();

    /**
     * Deletes a user by their unique ID.
     * @param id The unique identifier for the user to be deleted.
     * @return true if the user was found and deleted, false otherwise.
     */
    boolean deleteById(String id);
    
    /**
     * Checks if a user exists by their ID.
     * @param id The unique identifier for the user.
     * @return true if a user with this ID exists, false otherwise.
     */
    boolean existsById(String id);
}