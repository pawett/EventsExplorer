package com.event_explorer.graphQL;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.event_explorer.domain.BaseEntity;

import graphql.schema.DataFetchingEnvironment;

public class FiltersHelper {

	private static <T extends BaseEntity> Predicate<T> filterById(String id) {
		return t -> t.id == id;
		
	}
	public static <T extends BaseEntity> List<T> ById(List<T> list, DataFetchingEnvironment environment)
	{	
		String id = environment.getArgument("id");
		if(id == null)
			return list;
		List<T> returnList = list.stream().filter(t -> t.id.equals(id)).collect(Collectors.toList());
		return returnList;
	}
}
