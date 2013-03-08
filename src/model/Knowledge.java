package model;

public class Knowledge {

	private Integer id;
	private String word;
	private int count;
	private String label;
	
	
	public Knowledge(Integer id, String word, int count, String label) {
		super();
		this.id = id;
		this.word = word;
		this.count = count;
		this.label = label;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
