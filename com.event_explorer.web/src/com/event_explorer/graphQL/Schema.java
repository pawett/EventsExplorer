package com.event_explorer.graphQL;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.*;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;



public class Schema {

	 private static GraphQLSchema schema;
	
	 public static GraphQLSchema getSchema()
	 {
		 schema = GraphQLSchema.newSchema()
				 .query(Queries.getEntitiesQuery())
				 .build();
		 return schema;
	 }
	 
	 public static String Query(String query)
	 {
		 if(query == null || query.length() == 0)
		 {
			 query ="{entity {id, generalizations{id}}}";
		 }
		 GraphQLObjectType queryType = newObject()
	                .name("helloWorldQuery")
	                .field(newFieldDefinition()
	                        .type(GraphQLString)
	                        .name("hello")
	                        .staticValue("world"))
	                .build();
		

	        schema = GraphQLSchema.newSchema()
	                .query(Queries.getEntitiesQuery())
	                .build();

	        GraphQL graphQL = new GraphQL(schema);
	        //.build();

	        ExecutionResult result = graphQL.execute(query);
	        Object data = result.getData();
	       return data.toString();
	 }
	
	 
	
	
}
