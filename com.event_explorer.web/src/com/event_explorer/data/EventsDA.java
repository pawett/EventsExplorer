package com.event_explorer.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Event;
import com.event_explorer.domain.Time;

public class EventsDA extends BaseDA<Event> {

	public EventsDA(Class<Event> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	/*private List<Entity> BaseQuery(Map<String, Object> bindVars, NavigationDirection direction)
	{
		String query = "";
		switch(direction)
		{
		case INBOUND:
			query = inboundRelationshipQuery;
			break;
		case OUTBOUND:
			query = outboundRelationshipQuery;
			break;
		}
		String collectionName = getCollectionName();
		ArangoCursor<Entity> cursor = DBConnection.GetDataBase().query(query, bindVars, null, Entity.class);
		List<Entity> entities = cursor.asListRemaining();
		return entities;
	}
	
	private List<Event> BaseEventsQuery(Map<String, Object> bindVars, NavigationDirection direction)
	{
		String query = "";
		switch(direction)
		{
		case INBOUND:
			query = inboundRelationshipQuery;
			break;
		case OUTBOUND:
			query = outboundRelationshipQuery;
			break;
		}
		String collectionName = getCollectionName();
		ArangoCursor<Event> cursor = DBConnection.GetDataBase().query(query, bindVars, null, Event.class);
		List<Event> entities = cursor.asListRemaining();
		return entities;
	}
	
	private List<Time> BaseTimesQuery(Map<String, Object> bindVars, NavigationDirection direction)
	{
		String query = "";
		switch(direction)
		{
		case INBOUND:
			query = inboundRelationshipQuery;
			break;
		case OUTBOUND:
			query = outboundRelationshipQuery;
			break;
		}
		String collectionName = getCollectionName();
		ArangoCursor<Time> cursor = DBConnection.GetDataBase().query(query, bindVars, null, Time.class);
		List<Time> times = cursor.asListRemaining();
		return times;
	}*/
	
	public List<Entity> Actors(String idEntity)
	{
		return new EntitiesDA(Entity.class).GetActorsFromEvent(idEntity);
	}
	
	
	public List<Entity> Patients(String idEntity)
	{
		/*Map<String, Object> bindVars = getBindVars(idEntity, "Who", NavigationDirection.INBOUND);
		List<Entity> entities = BaseQuery(bindVars, NavigationDirection.INBOUND);
		return entities;*/
		return null;
	}
	
	public List<Event> GetEventsFromActor(String idEntity)
	{
		return BaseQuery(idEntity, "Who", NavigationDirection.INBOUND);
	}
	
	public List<Event> Results(String idEntity)
	{
		return BaseQuery(idEntity, "Trigger",NavigationDirection.OUTBOUND);
	}
	
	public List<Event> Triggers(String idEntity)
	{
		return BaseQuery(idEntity, "Trigger",NavigationDirection.INBOUND);
	}
	
	public List<Event> GetEventsFromTime(String idTime)
	{
		return BaseQuery(idTime, "When", NavigationDirection.INBOUND);
	}
	
	public Time HappenedIn(String idEntity)
	{
		List<Time> times = new TimesDA(Time.class).GetTimesFromEvent(idEntity);
		if(times != null && !times.isEmpty())
			return times.get(0);
		
		return null;
	}

}
