package services;

import dal.UserDao;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<WUser> getAllUsers() throws Exception {
        return userDao.getAll();
    }

    public void addUser(WUser user) throws Exception {
        List<WUser> users = getAllUsers();
        for(WUser curUser: users) {
        	if(curUser.getUsername().equals(user.getUsername())) {
        		throw new DuplicateUsernameException("username already exists");
        	}
        }
        userDao.add(user);
    }

    public void updateUser(WUser user) throws Exception {
        WUser existingUser = userDao.get(user.getId());
        if (existingUser == null) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found.");
        }
        userDao.update(user);
    }

    public void deleteUser(int userId) throws Exception {
        WUser user = userDao.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        userDao.delete(userId);
    }

    public WUser getUser(int userId) throws Exception {
        WUser user = userDao.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return user;
    }
    
    public WUser login(String username, String password) throws Exception {
        boolean authenticated = userDao.check_user(password, username);
        if (!authenticated) {
            throw new UserNotFoundException("Invalid username or password.");
        }
        return userDao.getUserByUsernameAndPassword(username, password);
    }

    
}
