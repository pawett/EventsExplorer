package com.event_explorer.graphQL;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import com.event_explorer.domain.Time;
import com.event_explorer.domain.Event;
import com.event_explorer.data.EventsDA;
import com.event_explorer.data.TimesDA;

import graphql.schema.*;

public class TimeSchema {

	public static GraphQLObjectType GetTimeSchema()
	{
		TimesDA timesDA = new TimesDA(Time.class);
		EventsDA eventsDA = new EventsDA(Event.class);
		
		return newObject()
				.name("time")
				.field(newFieldDefinition()
						.name("id")
						.type(GraphQLString).build())
				.field(newFieldDefinition()
						.name("from")
						.type(GraphQLString)//TODO:Create a timeSchema
						.build())
				.field(newFieldDefinition()
						.name("to")
						.type(GraphQLString)
						.build())
				.field(newFieldDefinition()
						.name("includedIn")
						.type(new GraphQLTypeReference("time"))
						.dataFetcher(Environment -> timesDA.Generalization(((Time)Environment.getSource()).id))
						.build())
				.field(newFieldDefinition()
						.name("isPartOf")
						.type(new GraphQLTypeReference("time"))
						.dataFetcher(Environment -> timesDA.Specialization(((Time)Environment.getSource()).id))
						.build())
				.field(newFieldDefinition()
						.name("happened")
						.type(new GraphQLList(new GraphQLTypeReference("event")))
						.dataFetcher(Environment -> eventsDA.GetEventsFromTime(((Time)Environment.getSource()).id))
						.build())
				.build();
	}
}
