package dal;

import entities.WUser;

import java.util.List;

/**
 * Interface defining operations for interacting with users in the data access layer.
 */
public interface UserDao {
    /**
     * Retrieves all users from the data store.
     *
     * @return A list of all users.
     * @throws Exception If an error occurs while retrieving users.
     */
    List<WUser> getAll() throws Exception;

    /**
     * Checks if a user with the given password and name exists.
     *
     * @param password The password of the user to check.
     * @param name     The name of the user to check.
     * @return true if a user with the given password and name exists, otherwise false.
     */
    boolean check_user(String password, String name);

    /**
     * Adds a new user to the data store.
     *
     * @param user The user to be added.
     * @throws Exception If an error occurs while adding the user.
     */
    void add(WUser user) throws Exception;

    /**
     * Updates an existing user in the data store.
     *
     * @param user The user to be updated.
     * @throws Exception If an error occurs while updating the user.
     */
    void update(WUser user) throws Exception;

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user associated with the given ID.
     * @throws Exception If an error occurs while retrieving the user.
     */
    WUser get(int userId) throws Exception;

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @throws Exception If an error occurs while deleting the user.
     */
    void delete(int userId) throws Exception;

    /**
     * Retrieves a user by their username and password.
     *
     * @param username The username of the user to retrieve.
     * @param password The password of the user to retrieve.
     * @return The user associated with the given username and password.
     * @throws Exception If an error occurs while retrieving the user.
     */
    WUser getUserByUsernameAndPassword(String username, String password) throws Exception;
}
