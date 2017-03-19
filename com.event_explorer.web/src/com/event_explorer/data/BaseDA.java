package com.event_explorer.data;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;
import com.event_explorer.domain.BaseEntity;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Time;

public class BaseDA<T extends BaseEntity> {
	protected Class<T> genericType;


	public String GetQuery(String relation, NavigationDirection direction)
	{
		String query = "FOR edge, vertex, path "+
				"IN 1..2 "+
				direction.toString()+" @idDoc " + relation+
				" RETURN edge";
		return query;
	}
	
	private String GetCollectionQuery(Map<String, String> filters)
	{
		if(filters == null || filters.isEmpty())
			return "FOR doc IN "+getCollectionName()+" RETURN doc";
		
		StringBuilder sb = new StringBuilder();
		sb.append(" FILTER ");
		for(String key : filters.keySet())
		{
			sb.append("doc."+key+" == '"+filters.get(key)+ "' &");
		}
		String filter = sb.toString();
		if(filter.charAt(filter.length()-1) == '&')//remove the last &
			filter = filter.substring(0,filter.length()-1);

		return "FOR doc IN "+getCollectionName()+filter+" RETURN doc";
	}
	
	public BaseDA(Class<T> type)
	{
		this.genericType = type;
	}
	
	protected String getCollectionName()
	{	
		switch(genericType.getTypeName())
		{
		case "com.event_explorer.domain.Entity":
			return "Entities";
		case "com.event_explorer.domain.Event":
			return "Events";
		case "com.event_explorer.domain.Time":
			return "Times";			
		}
		
		return genericType.getName();
	}
	
	protected Map<String, Object> getBindVars(String idEntity) {
		Map<String, Object> bindVars = new HashMap<>();
		bindVars.put("idDoc", idEntity);
		//bindVars.put("relation", dbRelationName);
		//bindVars.put("direction", direction);
		return bindVars;
	}
	
	protected List<T> BaseQuery(String idEntity, String relation, NavigationDirection direction)
	{
		String collectionName = getCollectionName();
		ArangoCursor<BaseDocument> cursor = DBConnection.GetDataBase().query(GetQuery(relation, direction), getBindVars(idEntity), null, BaseDocument.class);
		List<T> entities = new ArrayList<T>();
		 for(; cursor.hasNext();) {
			  BaseDocument obj = cursor.next();
			  entities.add(Map(obj));
		  }
		//List<T> entities = cursor.asListRemaining();
		return entities;
	}
	
	public  void Add(T entity){
		DBConnection.GetDataBase().collection(getCollectionName()).insertDocument(entity);
	}
	private List<T> QueryExecutor(Map<String, String> filters)
	{
		Map<String, Object> bindVars = new HashMap<>();
		String query = GetCollectionQuery(filters);
		ArangoCursor<BaseDocument> cursor = DBConnection.GetDataBase().query(query, bindVars, null, BaseDocument.class);

		List<T> entities = new ArrayList<T>();
		for(; cursor.hasNext();) 
		{
			BaseDocument obj = cursor.next();
			entities.add(Map(obj));
		}
		return entities;
	}
	
	public  T Get(String id)
	{
		Map<String, String> filters = new HashMap<String, String>();
		filters.put("_id", id);

		List<T> entities = QueryExecutor(filters);
		if(entities != null && !entities.isEmpty())
			return entities.get(0);
		
		return null;
	}
	
	public List<T> GetAll()
	{
		List<T> entities = QueryExecutor(null);
		return entities;
	}
	
	protected T Map(BaseDocument doc)
	{
		T entity;
		try {
			entity = genericType.newInstance();
			entity.id = doc.getId();
			return entity;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 return null;
	}
	
	
	/*public  T Update (T entity)
	{
		//return (T)DBConnection.GetDataBase().collection(getCollectionName()).updateDocument("test", entity);
	}*/

}
