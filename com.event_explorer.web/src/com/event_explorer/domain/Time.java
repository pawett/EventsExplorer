package com.event_explorer.domain;

import java.util.ArrayList;
import java.util.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Time extends BaseEntity {

	public Instant from;
	public Instant to;
	public Time ispartOf;
	public Time includedIn;
	
	public static Instant FromISOString(String isoDateTime)
	{
		TemporalAccessor creationAccessor = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse( isoDateTime );
		Instant instant = Instant.from( creationAccessor );
		return instant;
	}
	
	public static Instant FromEpoch(String epoch)
	{
		return Instant
		.ofEpochMilli(Long.parseLong(epoch));
	}
}
