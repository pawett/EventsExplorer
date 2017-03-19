package com.event_explorer.data;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.entity.BaseDocument;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Event;
import com.event_explorer.domain.Time;

public class TimesDA extends BaseDA<Time> {

	public TimesDA(Class<Time> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	/*private List<Time> BaseQuery(String idEntity, String dbRelationName, NavigationDirection direction)
	{
		Map<String, Object> bindVars = getBindVars(idEntity, dbRelationName, direction);
		String collectionName = getCollectionName();
		ArangoCursor<Time> cursor = DBConnection.GetDataBase().query(relationshipQuery, bindVars, null, Time.class);
		List<Time> entities = cursor.asListRemaining();
		return entities;
	}
	
	private List<Event> BaseEventQuery(String idEntity, String dbRelationName, NavigationDirection direction)
	{
		Map<String, Object> bindVars = getBindVars(idEntity, dbRelationName, direction);
		String collectionName = getCollectionName();
		ArangoCursor<Event> cursor = DBConnection.GetDataBase().query(relationshipQuery, bindVars, null, Event.class);
		List<Event> events = cursor.asListRemaining();
		return events;
	}*/

	
	//TODO: the base query only will return the first level, change the query to allow to navigate to all
	public Time Generalization(String idEntity)
	{	
		List<Time> times = BaseQuery(idEntity, "Hierarchy", NavigationDirection.INBOUND);
		if(times != null && !times.isEmpty())
			return times.get(0);
		
		return null;
	}

	//TODO: the base query only will return the first level, change the query to allow to navigate to all
	public Time Specialization(String idEntity)
	{
		List<Time> times = BaseQuery(idEntity, "Hierarchy", NavigationDirection.OUTBOUND);
		if(times != null && !times.isEmpty())
			return times.get(0);
		
		return null;
	}
	
	public List<Time> GetTimesFromEvent(String idEvent)
	{
		return BaseQuery(idEvent, "When", NavigationDirection.OUTBOUND);
	}
	
	@Override
	protected Time Map(BaseDocument document)
	{
		Time t = super.Map(document);
		Map<String, Object> properties = document.getProperties();
		Object from = properties.get("from");
		if(from != null)
		t.from = MapDate(from.toString());
		Object to = properties.get("to");
		if(to != null)
		t.to = MapDate(to.toString());	
	
		return t;
	}
	
	private Date MapDate(String date)
	{
		return new Date(Long.parseLong(date));
	}

}
