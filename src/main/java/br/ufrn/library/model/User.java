package br.ufrn.library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User in the library system.
 * This is a data class (POJO) and holds no business logic.
 */
public class User {

    private String id; // Unique ID (could be CPF, registration number, etc.)
    private String name;
    private List<Loan> loanHistory;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.loanHistory = new ArrayList<>(); // Starts with an empty history
    }

    // --- Getters and Setters ---
    // Note: We only provide getters for fields that should be read,
    // and setters for fields that should be modified *after* creation.

    public String getId() {
        return id;
    }

    // ID is usually immutable, so no setId() unless required

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loan> getLoanHistory() {
        return loanHistory;
    }

    // The history is managed externally (by a Service),
    // so we don't provide a public setLoanHistory().
    // We can provide a helper method to add a loan,
    // which will be called by the LoanService.
    public void addLoanToHistory(Loan loan) {
        this.loanHistory.add(loan);
    }

    // --- Object methods ---
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", loans=" + loanHistory.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}