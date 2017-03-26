package com.event_explorer.data;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.arangodb.ArangoCursor;
import com.arangodb.entity.BaseDocument;
import com.event_explorer.data.mappers.BaseVertex;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Event;
import com.event_explorer.domain.Time;

public class TimesDA extends BaseDA<Time> {

	public TimesDA(Class<Time> type) {
		super(type);
		setCollectionName("Times");
		// TODO Auto-generated constructor stub
	}


	public List<Time> Get(String id, String from, String to)
	{
		Map<String, String> filters = new HashMap<String, String>();
		if(id != null && !id.isEmpty())
		filters.put("_id", id);
		
		if(from != null && !from.isEmpty())
			filters.put("from", ISODateToLong(from));
		
		if(to != null && !to.isEmpty())
			filters.put("to", ISODateToLong(to));

		List<Time> entities = GetFiltered(filters);
				
		return entities;
	}
	
	private String ISODateToLong(String date)
	{
		OffsetDateTime odt = OffsetDateTime.parse(date);
		long ticks = odt.toEpochSecond();
		return Objects.toString(ticks, null);
	}

	
	//TODO: the base query only will return the first level, change the query to allow to navigate to all
	public Time Generalization(String idEntity)
	{	
		List<Time> times = ExecuteEdgeQueryInbound(idEntity, "Hierarchy");
		if(times != null && !times.isEmpty())
			return times.get(0);
		
		return null;
	}

	//TODO: the base query only will return the first level, change the query to allow to navigate to all
	public Time Specialization(String idEntity)
	{
		List<Time> times = ExecuteBaseEdgeQueryOutbound(idEntity, "Hierarchy");
		if(times != null && !times.isEmpty())
			return times.get(0);
		
		return null;
	}
	
	public Time GetTimeFromEvent(String idEvent)
	{
		List<Time> times = ExecuteBaseEdgeQueryOutbound(idEvent, "When");
		if(times != null && !times.isEmpty())
			return times.get(0);
		
		return null;
	}
	
	@Override
	protected Time Map(BaseDocument document)
	{
		Time t = super.Map(document);
		Map<String, Object> properties = document.getProperties();
		Object from = properties.get("from");
		if(from != null)
		t.from = Time.FromEpoch(from.toString());
		Object to = properties.get("to");
		if(to != null)
		t.to = Time.FromEpoch(to.toString());	
	
		return t;
	}
	

}
