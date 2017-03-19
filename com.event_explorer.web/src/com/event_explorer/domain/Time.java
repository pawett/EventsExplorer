package com.event_explorer.domain;

import java.util.ArrayList;
import java.util.Date;

public class Time extends BaseEntity {

	public Date from;
	public Date to;
	public Time ispartOf;
	public Time includedIn;
}
