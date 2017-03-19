package com.event_explorer.graphQL;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

import com.event_explorer.data.EntitiesDA;
import com.event_explorer.data.EventsDA;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Event;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;



public class EntitySchema {

	public static GraphQLObjectType GetEntitySchema()
	{
		EntitiesDA entitiesDa = new EntitiesDA(Entity.class);
		EventsDA eventsDA = new EventsDA(Event.class);
		
		 return newObject()
				 .name("entity")
				 .field(newFieldDefinition()
						 .name("id")
						 .type(GraphQLString).build())
				 .field(newFieldDefinition()
						 .name("name")
						 .type(GraphQLString)
						 .build())
				 .field(newFieldDefinition()
						 .name("type")
						 .type(GraphQLString)
						 .build())
				 .field(newFieldDefinition()
						 .name("generalization")
						 .type(new GraphQLTypeReference("entity"))
						 .dataFetcher(Environment -> entitiesDa.Generalization(((Entity) Environment.getSource()).id))
						 .build())
				 .field(newFieldDefinition()
						 .name("specialization")
						 .type(new GraphQLTypeReference("entity"))
						 .dataFetcher(Environment -> entitiesDa.Specialization(((Entity) Environment.getSource()).id))
						 .build())
				 .field(newFieldDefinition()
						 .name("isActorOf")
						 .argument(Arguments.Id())
						 .type(new GraphQLList(new GraphQLTypeReference("event")))
						 .dataFetcher(Environment -> FiltersHelper.ById(entitiesDa.IsActorOf(Arguments.GetIdFromEnvironment(Environment)), Environment))
						 .build())
				 .field(newFieldDefinition()
						 .name("isPatientOf")
						 .argument(Arguments.Id())
						 .type(new GraphQLList(new GraphQLTypeReference("event")))
						 .dataFetcher(Environment -> FiltersHelper.ById(entitiesDa.IsPatientOf(Arguments.GetIdFromEnvironment(Environment)), Environment))
						 .build())
				 .build();
		 
	}
	

}
