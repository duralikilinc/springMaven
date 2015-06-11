package info.spring.maven.Service;





import info.spring.maven.Model.User;

import java.util.List;

public interface IUserService {
	public User addUser(User user);
	public List<User> listUser() ;
	public void deleteUser(String id);
	public void updateUser(String id, String name, String surname, String telephone);
}
