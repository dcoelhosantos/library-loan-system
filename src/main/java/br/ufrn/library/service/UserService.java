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
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- Business Methods ---

    /**
     * Registers a new user in the system.
     */
    public User registerUser(String id, String name) {
    // 1. Validação de Regra de Negócio (depende do repositório)
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }

        User newUser = new User(id, name);
        return userRepository.save(newUser);
    }

    /**
     * Finds a user by their ID.
     */
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    /**
     * Retrieves a list of all users.
     */
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Updates a user's name.
     */
    public User updateUser(String id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty.");
        }

        User userToUpdate = findUserById(id); // Re-uses our find method
        userToUpdate.setName(newName);
        
        return userRepository.save(userToUpdate);
    }
    
}