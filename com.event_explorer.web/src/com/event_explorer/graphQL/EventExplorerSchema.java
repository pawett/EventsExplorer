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


public class EventExplorerSchema {

	public static GraphQLObjectType GetSchema()
	{
		EventsDA eventsDA = new EventsDA(Event.class);
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
						.type(new GraphQLTypeReference("time"))
						.dataFetcher(environment -> timesDA.GetTimeFromEvent(new EnvironmentHelper(environment).GetId()))
						.build())
				.field(newFieldDefinition()
						.name("agents")
						.argument(new GraphQLArgument("id", GraphQLString))
						.type(new GraphQLList(new GraphQLTypeReference("entity")))
						.dataFetcher(environment -> entitiesDA.GetActorsFromEvent(new EnvironmentHelper(environment).GetId()))
						.build())
				.field(newFieldDefinition()
						.name("patients")
						.type(new GraphQLList(new GraphQLTypeReference("entity")))
						.dataFetcher(environment -> eventsDA.Patients(new EnvironmentHelper(environment).GetId()))
						.build())
				.field(newFieldDefinition()
						.name("results")
						.type(new GraphQLList(new GraphQLTypeReference("event")))
						.dataFetcher(environment -> eventsDA.Results(new EnvironmentHelper(environment).GetId()))
						.build())
				.field(newFieldDefinition()
						.name("triggers")
						.type(new GraphQLList(new GraphQLTypeReference("event")))
						.dataFetcher(environment -> eventsDA.Triggers(new EnvironmentHelper(environment).GetId()))
						.build())
				.build();
	}
	

}
