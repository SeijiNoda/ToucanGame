package score;

public class Score {
	
	private String name;
	private long score;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getScore() {
		return score;
	}
	
	public void setScore(long score) {
		this.score = score;
	}
	
	public Score(String name, long score) {
		this.name = name;
		this.score = score;
	}
}
