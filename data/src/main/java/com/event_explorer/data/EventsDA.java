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
		setCollectionName("Events");
		// TODO Auto-generated constructor stub
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
		return ExecuteEdgeQueryInbound(idEntity, "Who");
	}
	
	public List<Event> Results(String idEntity)
	{
		return ExecuteBaseEdgeQueryOutbound(idEntity, "Trigger");
	}
	
	public List<Event> Triggers(String idEntity)
	{
		return ExecuteEdgeQueryInbound(idEntity, "Trigger");
	}
	
	public List<Event> GetEventsFromTime(String idTime)
	{
		return ExecuteEdgeQueryInbound(idTime, "When");
	}
	
	/*public Time HappenedIn(String idEntity)
	{
		List<Time> times = new TimesDA(Time.class).GetTimesFromEvent(idEntity);
		if(times != null && !times.isEmpty())
			return times.get(0);
		
		return null;
	}*/

}
