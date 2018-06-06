package com.telappoint.onlineresv.model.restws;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Seat {
	// online
	private String seatId;
	private String seatNumber;

	// ivr
	private String seatAudio;
	private String seatTTS;

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatAudio() {
		return seatAudio;
	}

	public void setSeatAudio(String seatAudio) {
		this.seatAudio = seatAudio;
	}

	public String getSeatTTS() {
		return seatTTS;
	}

	public void setSeatTTS(String seatTTS) {
		this.seatTTS = seatTTS;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
}
