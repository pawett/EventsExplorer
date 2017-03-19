package com.event_explorer.data;

import com.event_explorer.domain.Entity;
import com.event_explorer.domain.EntityType;


public class EntityVertex extends BaseVertex<Entity> {

	public String name;
	public EntityType type;

	
	public EntityVertex(Entity entity) {
		super(entity);
		this.name = entity.name;
		this.type = entity.type;
		// TODO Auto-generated constructor stub
	}

}
