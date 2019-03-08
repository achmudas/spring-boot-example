package co.kurapka.springbootexample.posts;

public class Post {
	
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
