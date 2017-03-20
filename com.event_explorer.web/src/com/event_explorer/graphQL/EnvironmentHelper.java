package com.event_explorer.graphQL;

import java.util.Objects;

import com.event_explorer.domain.BaseEntity;
import com.event_explorer.domain.Time;

import graphql.schema.DataFetchingEnvironment;

public class EnvironmentHelper {

	private DataFetchingEnvironment environment;
	public EnvironmentHelper(DataFetchingEnvironment environment)
	{
		this.environment = environment;
	}
	//TODO:Move to a helper class
	public <T extends BaseEntity> String GetId()
	{
		return ((T)environment.getSource()).id;
	}
	
	public <T extends Time> String GetFrom()
	{
		return Objects.toString(((T)environment.getSource()).from.toEpochMilli(), null);
	}
	
	public <T extends Time> String GetTo()
	{
		return Objects.toString(((T)environment.getSource()).to.toEpochMilli(), null);
	}
}
