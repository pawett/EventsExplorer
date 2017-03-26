package com.event_explorer.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.entity.BaseDocument;
import com.event_explorer.data.mappers.BaseVertex;
import com.event_explorer.domain.BaseEntity;

public class QueryExecutor<T> {

	public  T getInstance(Class<T> aClass)
	{
		try {
			return aClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}  
	
	
	
}

