package com.event_explorer.graphQL;

import static graphql.Scalars.GraphQLString;

import com.event_explorer.domain.BaseEntity;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLNonNull;

public class Arguments {

	public static GraphQLArgument IdRequired()
	{
		return new GraphQLArgument("id",new GraphQLNonNull(GraphQLString));
	}
	
	public static GraphQLArgument Id()
	{
		return new GraphQLArgument("id",GraphQLString);
	}
	
	//TODO:Move to a helper class
	public static <T extends BaseEntity> String GetIdFromEnvironment(DataFetchingEnvironment environment)
	{
		return ((T)environment.getSource()).id;
	}
	
	
}
