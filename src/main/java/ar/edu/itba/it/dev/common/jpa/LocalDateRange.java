package ar.edu.itba.it.dev.common.jpa;

import java.io.Serializable;

import org.joda.time.LocalDate;

import com.google.common.base.Preconditions;

public class LocalDateRange implements Serializable {
	private LocalDate from;
	private LocalDate to;
	
	public LocalDateRange(LocalDate from, LocalDate to) {
		this.from = from;
		this.to = to;
	}

	public LocalDateRange() {
	}

	public LocalDate getFrom() {
		return from;
	}
	
	public LocalDate getTo() {
		return to;
	}
	
	public boolean contains(LocalDate date) {
		Preconditions.checkNotNull(date);
		return date.isAfter(from) && date.isBefore(to);
	}
}
