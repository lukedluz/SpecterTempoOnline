package com.sc4r.SpecterTempoOnline.Object;

public class PlayerTime {
	
	private Long time;
	private String name;

	public PlayerTime(String name, Long time) {
		this.time = time;
		this.name = name;
	}

	public Long getTime() {
		return this.time;
	}

	public String getName() {
		return this.name;
	}
}
