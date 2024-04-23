package dal;

import entities.WUser;

import java.util.List;

public interface UserDao {
    List<WUser> getAll() throws Exception;
    boolean check_user(String password, String name);
    void add(WUser user) throws Exception;
    void update(WUser user) throws Exception;
    WUser get(int userId) throws Exception;
	void delete(int userId) throws Exception;
	WUser getUserByUsernameAndPassword (String username, String password) throws Exception;

}