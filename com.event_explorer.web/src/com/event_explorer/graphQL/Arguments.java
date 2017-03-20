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
	
	public static GraphQLArgument From()
	{
		return new GraphQLArgument("from",GraphQLString);
	}
	
	public static GraphQLArgument To()
	{
		return new GraphQLArgument("to",GraphQLString);
	}
	
	
	
	
}
