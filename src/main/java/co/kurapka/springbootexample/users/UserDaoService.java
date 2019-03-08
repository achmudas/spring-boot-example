package co.kurapka.springbootexample.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import co.kurapka.springbootexample.posts.Post;

@Component
public class UserDaoService {

	private static List<User> users;

	static {
		users = new ArrayList<User>();
		List<Post> johnPosts = new ArrayList<>();
		johnPosts.add(new Post(0, "Simple text"));
		johnPosts.add(new Post(1, "Simple text 2"));
		users.add(new User(0, "John", new Date(), johnPosts, "password"));
		List<Post> joanaPosts = new ArrayList<Post>();
		joanaPosts.add(new Post(0, "Simple text for Joana"));
		users.add(new User(1, "Joana", new Date(), joanaPosts, "pass"));
		users.add(new User(2, "Peter", new Date(), new ArrayList<Post>(), "pass"));
	}

	public List<User> findAll() {
		return users;
	}

	public Optional<User> find(Integer id) {
		return users.stream().filter((User user) -> user.getId().equals(id)).findFirst();
	}

	public User create(User user) {
		if (user.getId() == null) {
			user.setId(users.get(users.size() - 1).getId() + 1);
		}
		users.add(user);
		return user;
	}

	public User delete(Integer id) {
		for (User user : users) {
			if (user.getId().equals(id)) {
				users.remove(user);
				return user;
			}
		}
		return null;
	}

}
