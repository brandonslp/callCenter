package calls;

public class Call {
	
	private Integer duration;
	
	

	public Call(Integer duration) {
		super();
		//Valid call duration, must be greater than zero
		if(duration == null || duration < 1)
			throw new IllegalArgumentException("Duration is null or less than zero");
		this.duration = duration;
	}



	public Integer getDuration() {
		return duration;
	}

	

}
