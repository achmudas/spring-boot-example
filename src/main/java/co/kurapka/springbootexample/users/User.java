package co.kurapka.springbootexample.users;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.kurapka.springbootexample.posts.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about the user")
@Entity
//@JsonFilter("postsFilter")
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2, message="Name should be at least 2 characters length")
	@ApiModelProperty(notes="Name should be at least 2 characters length")
	private String name;
	
	@Past
	@ApiModelProperty(notes="Date cannot be in the future")
	private Date birthDate;
	
	@OneToMany(targetEntity=Post.class, fetch=FetchType.EAGER)
	private List<Post> posts;
	
	@JsonIgnore
	private String password;

	protected User() {
	}

	public User(Integer id, String name, Date birthDate, List<Post> posts, String password) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.posts = posts;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
