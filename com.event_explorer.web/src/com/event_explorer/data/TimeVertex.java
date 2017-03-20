package com.event_explorer.data;

import java.util.Date;
import java.util.Objects;

import com.event_explorer.domain.Time;


public class TimeVertex extends BaseVertex<Time> {

	public String from;
	public String to;
	public TimeVertex(Time entity) {
		super(entity);
		this.from = Objects.toString(entity.from.toEpochMilli());
		this.to = Objects.toString(entity.to.toEpochMilli());
	}

}