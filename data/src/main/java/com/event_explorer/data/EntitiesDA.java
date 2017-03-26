package com.event_explorer.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.entity.BaseDocument;
import com.event_explorer.data.mappers.BaseVertex;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Event;
import com.event_explorer.domain.Time;

public class EntitiesDA extends BaseDA<Entity> {

	public EntitiesDA(Class<Entity> type) {
		super(type);
		setCollectionName("Entities");
		// TODO Auto-generated constructor stub
	}

	/*private List<Entity> BaseQuery(Map<String, Object> bindVars)
	{
		String collectionName = getCollectionName();
		ArangoCursor<Entity> cursor = DBConnection.GetDataBase().query(relationshipQuery, bindVars, null, Entity.class);
		List<Entity> entities = cursor.asListRemaining();
		return entities;
	}

	private List<Event> BaseEventQuery(Map<String, Object> bindVars)
	{
		String collectionName = getCollectionName();
		ArangoCursor<Event> cursor = DBConnection.GetDataBase().query(relationshipQuery, bindVars, null, Event.class);
		List<Event> entities = cursor.asListRemaining();
		return entities;
	}*/

	//TODO: the base query only will return the first level, change the query to allow to navigate to all
	public Entity Generalization(String idEntity)
	{
		List<Entity> entities = ExecuteEdgeQueryInbound(idEntity, "Hierarchy");
		if(entities != null && !entities.isEmpty())
			return entities.get(0);

		return new Entity();
	}

	//TODO: the base query only will return the first level, change the query to allow to navigate to all
	public Entity Specialization(String idEntity)
	{
		List<Entity> entities = ExecuteBaseEdgeQueryOutbound(idEntity, "Hierarchy");
		if(entities != null && !entities.isEmpty())
			return entities.get(0);

		return new Entity();
	}

	public List<Event> IsActorOf(String idEntity)
	{
		return new EventsDA(Event.class).GetEventsFromActor(idEntity);
	}

	public List<Entity> GetActorsFromEvent(String idEvent)
	{
		return ExecuteBaseEdgeQueryOutbound(idEvent, "Who");
	}

	public List<Event> IsPatientOf(String idEntity)
	{
		/*Map<String, Object> bindVars = getBindVars(idEntity, "Who", NavigationDirection.INBOUND);
		List<Event> entities = BaseEventQuery(bindVars);
		return entities;*/
		return null;
	}

	@Override
	protected Entity Map(BaseDocument document)
	{
		Entity entity = super.Map(document);
		Map<String, Object> properties = document.getProperties();
		Object name = properties.get("name");
		if(name != null)
			entity.name = name.toString();	

		return entity;
	}

}
