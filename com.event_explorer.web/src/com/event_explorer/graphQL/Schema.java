package com.event_explorer.graphQL;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.*;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class Schema {
	
	 public static GraphQLSchema getEventExplorerSchema()
	 {
		 return GraphQLSchema.newSchema()
				 .query(Queries.getEventExplorerQuery())
				 .build();
	 }	 
}
