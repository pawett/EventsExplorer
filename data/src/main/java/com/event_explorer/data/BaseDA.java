package com.event_explorer.data;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;
import com.event_explorer.data.mappers.BaseVertex;
import com.event_explorer.domain.BaseEntity;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Time;

public class BaseDA<T extends BaseEntity> {
	protected Class<T> genericType;
	protected String collectionName = null;

	
	public BaseDA(Class<T> type)
	{
		this.genericType = type;
	}
	
	protected void setCollectionName(String cName)
	{
		collectionName = cName;
	}
	
	protected String getCollectionName()
	{	
		return collectionName;
		
	}
	
	protected List<T> ExecuteEdgeQueryInbound(String idEntity, String relation)
	{
		return ExecuteEdgeQuery(idEntity, relation, NavigationDirection.INBOUND);
	}
	
	protected List<T> ExecuteBaseEdgeQueryOutbound(String idEntity, String relation)
	{
		return ExecuteEdgeQuery(idEntity, relation, NavigationDirection.OUTBOUND);
	}
	
	public  void Add(T entity){
		DBConnection.GetDataBase().collection(getCollectionName()).insertDocument(entity);
	}
	private List<T> ExecuteCollectionQuery(Map<String, String> filters)
	{
		Map<String, Object> bindVars = new HashMap<String, Object>();
		String query = QueryBuilder.GetCollectionQuery(getCollectionName(),filters);
		List<T> entities = ExecuteQuery(query, bindVars);
		return entities;
	}
	
	private List<T> ExecuteEdgeQuery(String idEntity, String relation, NavigationDirection direction)
	{
		String query = QueryBuilder.GetEdgeQuery(relation, direction);
		Map<String, Object> bindVars = QueryBuilder.GetBindVars(idEntity);
		
		List<T> entities = ExecuteQuery(query, bindVars);
		//List<T> entities = cursor.asListRemaining();
		return entities;
	}

	private List<T> ExecuteQuery(String query, Map<String, Object> bindVars) {
		ArangoCursor<BaseDocument> cursor = DBConnection.GetDataBase().query(query, bindVars, null, BaseDocument.class);
		List<T> entities = new ArrayList<T>();
		 for(; cursor.hasNext();) {
			 BaseDocument obj = cursor.next();
			  entities.add(Map(obj));
		  }
		return entities;
	}
	
	public T Get(String id)
	{
		Map<String, String> filters = new HashMap<String, String>();
		filters.put("_id", id);
		String query = QueryBuilder.GetCollectionQuery(getCollectionName(),filters);
		List<T> entities = ExecuteCollectionQuery(filters);
		if(entities != null && !entities.isEmpty())
			return entities.get(0);
		
		return null;
	}
	
	public List<T> GetAll()
	{
		List<T> entities = ExecuteCollectionQuery(null);
		return entities;
	}
	
	public List<T> GetFiltered(Map<String, String> filters)
	{
		List<T> entities = ExecuteCollectionQuery(filters);
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
