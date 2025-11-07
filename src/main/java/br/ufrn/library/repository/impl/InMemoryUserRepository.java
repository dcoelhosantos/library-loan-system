package br.ufrn.library.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import br.ufrn.library.model.User;
import br.ufrn.library.repository.UserRepository;

/**
 * The "Worker" (Implementation) of the UserRepository contract.
 * * This class handles the *how* of saving data, using an in-memory Map
 * as a simulated database.
 */
public class InMemoryUserRepository implements UserRepository {

    // Using ConcurrentHashMap for thread-safety, which is a good practice.
    private static final Map<String, User> database = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        // This simple implementation assumes the ID is provided.
        // A real implementation might generate the ID.
        database.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        // Optional.ofNullable handles the case where database.get(id) returns null.
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public boolean deleteById(String id) {
        return database.remove(id) != null;
    }
    
    @Override
    public boolean existsById(String id) {
        return database.containsKey(id);
    }
}