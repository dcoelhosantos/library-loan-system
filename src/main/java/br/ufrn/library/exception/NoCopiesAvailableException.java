package br.ufrn.library.exception;

/**
 * Exception thrown when no copies of a book are available for loan.
 */
public class NoCopiesAvailableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoCopiesAvailableException(String message) {
        super(message);
    }
}
