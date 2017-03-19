package com.event_explorer.graphQL;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

import com.event_explorer.data.EntitiesDA;
import com.event_explorer.data.EventsDA;
import com.event_explorer.data.TimesDA;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Event;
import com.event_explorer.domain.Time;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;


public class EventSchema {

	public static GraphQLObjectType GetEventSchema()
	{
		EventsDA eventsMapper = new EventsDA(Event.class);
		TimesDA timesDA = new TimesDA(Time.class);
		EntitiesDA entitiesDA = new EntitiesDA(Entity.class);
		
		 return newObject()
				  .name("event")
				  .field( newFieldDefinition()
							 .name("id")
							 .type(GraphQLString).build())
				  .field(newFieldDefinition()
					         .name("action")
					         .type(GraphQLString)
					         .build())
				  .field(newFieldDefinition()
						  .name("referenceTime")
						  .type(new GraphQLList(new GraphQLTypeReference("time")))
						  .dataFetcher(Environment -> timesDA.GetTimesFromEvent(((Event)Environment.getSource()).id))
						  .build())
				  .field(newFieldDefinition()
						  .name("agents")
						  .argument(new GraphQLArgument("id",GraphQLString))
						  .type(new GraphQLList(new GraphQLTypeReference("entity")))
						  .dataFetcher(Environment -> entitiesDA.GetActorsFromEvent(((Event)Environment.getSource()).id))
						  .build())
				  .field(newFieldDefinition()
						  .name("patients")
						  .type(new GraphQLList(new GraphQLTypeReference("entity")))
						  .dataFetcher(Environment -> eventsMapper.Patients(((Event)Environment.getSource()).id))
						  .build())
				  .field(newFieldDefinition()
						  .name("results")
						  .type(new GraphQLList(new GraphQLTypeReference("event")))
						  .dataFetcher(Environment -> eventsMapper.Results(((Event)Environment.getSource()).id))
						  .build())
				  .field(newFieldDefinition()
						  .name("triggers")
						  .type(new GraphQLList(new GraphQLTypeReference("event")))
						  .dataFetcher(Environment -> eventsMapper.Triggers(((Event)Environment.getSource()).id))
						  .build())
				.build();
	}
	

}
