package services;

import dal.UserDao;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing user-related operations.
 * This class provides methods to manage user entities such as adding, updating, deleting, and retrieving user information.
 */
@Service
public class UserService {
    private UserDao userDao;

    /**
     * Constructs a UserService with the necessary UserDao.
     *
     * @param userDao the DAO object that provides data access methods for user entities
     */
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of {@link WUser} objects representing all users in the database
     * @throws Exception if there is an error during database access
     */
    public List<WUser> getAllUsers() throws Exception {
        return userDao.getAll();
    }

    /**
     * Adds a new user to the database, ensuring the username is not already taken.
     *
     * @param user the {@link WUser} object to add to the database
     * @throws Exception if there is an error during database access or if the username already exists
     */
    public void addUser(WUser user) throws Exception {
        List<WUser> users = getAllUsers();
        for (WUser curUser : users) {
            if (curUser.getUsername().equals(user.getUsername())) {
                throw new DuplicateUsernameException("Username already exists");
            }
        }
        userDao.add(user);
    }

    /**
     * Updates an existing user's information in the database.
     *
     * @param user the {@link WUser} object containing updated information
     * @throws Exception if the user does not exist in the database
     */
    public void updateUser(WUser user) throws Exception {
        WUser existingUser = userDao.get(user.getId());
        if (existingUser == null) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found.");
        }
        userDao.update(user);
    }

    /**
     * Deletes a user from the database based on the user ID.
     *
     * @param userId the ID of the user to delete
     * @throws Exception if the user does not exist in the database
     */
    public void deleteUser(int userId) throws Exception {
        WUser user = userDao.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        userDao.delete(userId);
    }

    /**
     * Retrieves a user from the database based on the user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the {@link WUser} object corresponding to the requested user
     * @throws Exception if the user does not exist
     */
    public WUser getUser(int userId) throws Exception {
        WUser user = userDao.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return user;
    }

    /**
     * Authenticates a user's login credentials and retrieves their information from the database.
     *
     * @param username the username of the user trying to log in
     * @param password the password of the user trying to log in
     * @return the {@link WUser} object of the authenticated user
     * @throws Exception if the username or password is incorrect, or if there is an error during database access
     */
    public WUser login(String username, String password) throws Exception {
        boolean authenticated = userDao.check_user(password, username);
        if (!authenticated) {
            throw new UserNotFoundException("Invalid username or password.");
        }
        return userDao.getUserByUsernameAndPassword(username, password);
    }
}
