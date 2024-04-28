package services;

/**
 * Exception class for handling cases where a duplicate username is encountered.
 * This class extends the standard Java {@link Exception} class to provide a specific
 * exception type that can be used to differentiate errors related to duplicate usernames
 * from other types of exceptions.
 */
public class DuplicateUsernameException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code DuplicateUsernameException} with the specified detail message.
     * The message can be retrieved later by the {@link Throwable#getMessage()} method.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *                {@link Throwable#getMessage()} method.
     */
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
