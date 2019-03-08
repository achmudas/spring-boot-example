package co.kurapka.springbootexample.users;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import co.kurapka.springbootexample.exceptions.PostNotFoundException;
import co.kurapka.springbootexample.exceptions.UserNotFoundException;
import co.kurapka.springbootexample.posts.Post;

@RestController
public class UsersResource {
	
	@Autowired
	private UserDaoService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> getUser(@PathVariable Integer id) {
		if (userService.find(id).isPresent()) {
			User user = userService.find(id).get();
			ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
			Resource<User> resource = new Resource<User>(user);
			resource.add(linkTo.withRel("users"));
			return resource;
		}
		else
			throw new UserNotFoundException(id);
	}
	
	@GetMapping("/users/{id}/birthdate")
	public MappingJacksonValue getUserBirthdate(@PathVariable Integer id) {
		if (userService.find(id).isPresent()) {
			User user = userService.find(id).get();
			
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "birthDate");
			FilterProvider filters = new SimpleFilterProvider().addFilter("postsFilter", filter);
			
			MappingJacksonValue mapping = new MappingJacksonValue(user);
			mapping.setFilters(filters);
			return mapping;
		}
		else
			throw new UserNotFoundException(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User createdUser = userService.create(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
		return new ResponseEntity<User>(userService.delete(id), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> getUserPosts(@PathVariable Integer id) {
		if (userService.find(id).isPresent()) {
			User user = userService.find(id).get();
			return user.getPosts();
		}
		else
			throw new UserNotFoundException(id);
	}
	
	@GetMapping("/users/{id}/posts/{postId}")
	public Post getUserPost(@PathVariable Integer id, @PathVariable Integer postId) {
		if (userService.find(id).isPresent()) {
			User user = userService.find(id).get();
			Optional<Post> post = user.getPosts().stream().filter(pst -> pst.getId() == postId).findFirst();
			if (post.isPresent())
				return post.get();
			else
				throw new PostNotFoundException("PostId " + postId);
		}
		else
			throw new UserNotFoundException(id);
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable Integer id, @RequestBody Post post) {
		if (userService.find(id).isPresent()) {
			User user = userService.find(id).get();
			user.getPosts().add(post);
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(post.getId()).toUri();
			return ResponseEntity.created(location).build();
		}
		else
			throw new UserNotFoundException(id);
	}
	
}
