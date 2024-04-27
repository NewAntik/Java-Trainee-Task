package com.shapovalov.trainee.model.criteria;

import java.time.LocalDateTime;

public class SearchDateCriteria {

	private LocalDateTime fromBirthday;

	private LocalDateTime toBirthday;

	public SearchDateCriteria(){}

	public SearchDateCriteria(final LocalDateTime fromBirthday, final LocalDateTime toBirthday){
		this.fromBirthday = fromBirthday;
		this.toBirthday = toBirthday;
	}

	public LocalDateTime getFromBirthday() {
		return fromBirthday;
	}

	public void setFromBirthday(final LocalDateTime fromBirthday) {
		this.fromBirthday = fromBirthday;
	}

	public LocalDateTime getToBirthday() {
		return toBirthday;
	}

	public void setToBirthday(final LocalDateTime toBirthday) {
		this.toBirthday = toBirthday;
	}
}
