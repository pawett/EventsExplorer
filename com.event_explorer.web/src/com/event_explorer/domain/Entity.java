package com.event_explorer.domain;

import java.util.ArrayList;

public class Entity extends BaseEntity
{
	public Entity()
	{
		super();
	}
	public String name;
	public EntityType type;
	public Entity generalization;
	public Entity specialization;
	public ArrayList<Event> isActorOf;
	public ArrayList<Event> isPatientOf;
}
