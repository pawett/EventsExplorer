package com.event_explorer.data;

import java.util.HashMap;
import java.util.Map;

public class QueryBuilder {


	public static String GetEdgeQuery(String relation, NavigationDirection direction)
	{
		String query = "FOR edge, vertex, path "+
				"IN 1..2 "+
				direction.toString()+" @idDoc " + relation+
				" RETURN edge";
		return query;
	}
	
	public static String GetInboundEdgeQuery(String relation, NavigationDirection direction)
	{
		return GetEdgeQuery(relation, NavigationDirection.INBOUND);
	}
	
	public static String GetOutboundEdgeQuery(String relation, NavigationDirection direction)
	{
		return GetEdgeQuery(relation, NavigationDirection.OUTBOUND);
	}

	public static String GetCollectionQuery(String collectionName, Map<String, String> filters)

	{
		if(filters == null || filters.isEmpty())
			return "FOR doc IN "+collectionName+" RETURN doc";

		StringBuilder sb = new StringBuilder();
		sb.append("FOR doc IN ");
		sb.append(collectionName);
		sb.append(" FILTER 1 == 1");
		for(String key : filters.keySet())
		{
			String comparator = "==";
			if(key == "from")
				comparator =">=";
			if(key == "to")
				comparator="<=";
			sb.append(" AND doc."+key+" "+comparator+" '"+filters.get(key)+ "' ");
		}
		sb.append(" RETURN doc");
		String query = sb.toString();

		return query;
	}

	public static Map<String, Object> GetBindVars(String idEntity) {
		Map<String, Object> bindVars = new HashMap<String, Object>();
		bindVars.put("idDoc", idEntity);
		//bindVars.put("relation", dbRelationName);
		//bindVars.put("direction", direction);
		return bindVars;
	}
}
