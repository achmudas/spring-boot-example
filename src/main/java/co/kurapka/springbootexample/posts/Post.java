package co.kurapka.springbootexample.posts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Post {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String text;
	
	public Post(Integer id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public Integer getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	
	

}
