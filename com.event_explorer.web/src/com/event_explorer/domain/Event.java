package com.event_explorer.domain;

import java.util.ArrayList;
import java.util.Date;

public class Event extends BaseEntity {
	/*
	 * AGENT The waiter spilled the soup.
	 * EXPERIENCER John has a headache.
	 * FORCE The wind blows debris from the mall into our yards.
	 * THEME Only after Benjamin Franklin broke the ice...
	 * RESULT The city built a regulation-size baseball diamond...
	 * CONTENT Mona asked “You met Mary Ann at a supermarket?”
	 * INSTRUMENT He poached catfish, stunning them with a shocking device...
	 * BENEFICIARY Whenever Ann Callahan makes hotel reservations for her boss...
	 * SOURCE I flew in from Boston.
	 * GOAL I drove to Portland.*/
	
	public Event()
	{
		super();
	}
	public Action action;
	public Time referenceTime;
	public ArrayList<Entity> agents;
	public ArrayList<Entity> patients;
	public ArrayList<Event> results;
	public ArrayList<Event> triggers;
}
