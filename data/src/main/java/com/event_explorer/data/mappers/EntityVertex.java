package com.event_explorer.data.mappers;

import com.arangodb.entity.*;
import com.event_explorer.domain.*;



public class EntityVertex extends BaseVertex<Entity> { 
	
	public String name;
	public EntityType type;

	
	public EntityVertex(Entity entity) 
	{
		super(entity);
		this.name = entity.name;
		this.type = entity.type;
	}

}
