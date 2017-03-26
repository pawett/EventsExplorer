package com.event_explorer.graphQL;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.Scalars.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.event_explorer.data.EntitiesDA;
import com.event_explorer.data.EventsDA;
import com.event_explorer.data.TimesDA;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.Event;
import com.event_explorer.domain.Time;

import graphql.schema.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLNonNull.*;

public class Queries {

	
	public static GraphQLObjectType getEventExplorerQuery()
	{
		GraphQLObjectType entity = EntitySchema.GetEntitySchema();
		GraphQLObjectType event = EventExplorerSchema.GetSchema();
		GraphQLObjectType time = TimeSchema.GetTimeSchema();
		
		EntitiesDA entitiesDA = new EntitiesDA(Entity.class);
		EventsDA eventsDA = new EventsDA(Event.class);
		TimesDA timesDA = new TimesDA(Time.class);
		
				
		return newObject()
		 .name("eventExplorer")
		 .field(newFieldDefinition()
				 .name("entities")
				 .argument(Arguments.Id())
				 .type(new GraphQLList(entity))
				 .dataFetcher(Environment -> FiltersHelper.ById(entitiesDA.GetAll(), Environment))
				 .build())
		 .field(newFieldDefinition()
				 .name("entity")
				 .argument(Arguments.IdRequired())
				 .type(entity)
				 .dataFetcher(Environment -> entitiesDA.Get(Environment.getArgument("id")))
				 .build())
		 .field(newFieldDefinition()
				 .name("events")
				 .argument(Arguments.Id())
				 .type(new GraphQLList(event))
				 .dataFetcher(Environment -> FiltersHelper.ById(eventsDA.GetAll(), Environment))
				 .build())
		 .field(newFieldDefinition()
				 .name("times")
				 .argument(Arguments.Id())
				 .argument(Arguments.From())
				 .argument(Arguments.To())
				 .type(new GraphQLList(time))
				 .dataFetcher(environment -> (timesDA.Get(environment.getArgument("id"), 
														  environment.getArgument("from"),
														  environment.getArgument("to"))))
				 .build())
		 .build();
	}
	
	
}
