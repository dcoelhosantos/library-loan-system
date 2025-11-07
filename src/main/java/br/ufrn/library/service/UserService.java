package br.ufrn.library.service;

import java.util.List;

import br.ufrn.library.model.User;
import br.ufrn.library.repository.UserRepository;

/**
 * The "Brain" (Service Layer) for User-related business logic.
 * * It depends on the UserRepository *interface* (the contract),
 * not on any specific implementation.
 */
public class UserService {

    private final UserRepository userRepository;

    // --- Constructor (Dependency Injection) ---
    
    /**
     * Constructor that receives the dependency (UserRepository).
     * This is a good practice called "Dependency Injection".
     * @param userRepository The repository implementation to be used.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- Business Methods ---

    /**
     * Registers a new user in the system.
     * @param id The user's unique ID.
     * @param name The user's name.
     * @return The newly created User.
     * @throws IllegalArgumentException if the ID or name is invalid,
     * or if the user ID already exists.
     */
    public User registerUser(String id, String name) {
        validateUserData(id, name);

        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }

        User newUser = new User(id, name);
        return userRepository.save(newUser);
    }

    /**
     * Finds a user by their ID.
     * @param id The user's ID.
     * @return The User object.
     * @throws IllegalArgumentException if no user is found with that ID.
     */
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    /**
     * Retrieves a list of all users.
     * @return A list of all users.
     */
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Updates a user's name.
     * @param id The ID of the user to update.
     * @param newName The new name for the user.
     * @return The updated User object.
     * @throws IllegalArgumentException if the user is not found or the name is invalid.
     */
    public User updateUser(String id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty.");
        }

        User userToUpdate = findUserById(id); // Re-uses our find method
        userToUpdate.setName(newName);
        
        return userRepository.save(userToUpdate);
    }
    
    // --- Private Helper Methods (Single Responsibility) ---

    /**
     * Validates the basic integrity of user data.
     * @param id The user ID.
     * @param name The user name.
     * @throws IllegalArgumentException if data is invalid.
     */
    private void validateUserData(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty.");
        }
    }
}