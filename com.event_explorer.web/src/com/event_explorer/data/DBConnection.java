package com.event_explorer.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoGraph;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.entity.CollectionType;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.entity.EdgeEntity;
import com.arangodb.entity.GraphEntity;
import com.arangodb.entity.VertexEntity;
import com.arangodb.model.CollectionCreateOptions;
import com.event_explorer.domain.Action;
import com.event_explorer.domain.Entity;
import com.event_explorer.domain.EntityType;
import com.event_explorer.domain.Event;
import com.event_explorer.domain.Time;

public class DBConnection {

	public static ArangoDatabase GetDataBase()
	{
		ArangoDB arangoDB = new ArangoDB.Builder().user("writer@eventExplorer").password("writerEventExplorer").build();
		ArangoDatabase db = arangoDB.db("eventExplorer");
		/*ArangoGraph graphToDelete = db.graph("EventExplorer");
		if (graphToDelete != null)
			graphToDelete.drop();*/
		/*
		Collection<EdgeDefinition> edgeDefinitions = new ArrayList<EdgeDefinition>();
		InitializeEdges(db, edgeDefinitions);
		GraphEntity graph = db.createGraph("EventExplorer", edgeDefinitions);
		
		try {
			//InitializeVertexs(db);
			AddVertexData(db);
			AddRelationships(db);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return db;
	}
	
	private static void AddRelationships(ArangoDatabase db)
	{
		//EdgeEntity edge = new EdgeEntity();
		
		BaseEdgeDocument edge = new BaseEdgeDocument("Events/teach", "Actions/teach");
		db.graph("EventExplorer").edgeCollection("What").insertEdge(edge);
		edge = new BaseEdgeDocument("Events/run", "Actions/run");
		db.graph("EventExplorer").edgeCollection("What").insertEdge(edge);
		edge = new BaseEdgeDocument("Events/study", "Actions/study");
		db.graph("EventExplorer").edgeCollection("What").insertEdge(edge);
		
		edge = new BaseEdgeDocument("Events/run","Entities/john");
		db.graph("EventExplorer").edgeCollection("Who").insertEdge(edge);
		
		edge = new BaseEdgeDocument("Events/run", "Times/2016");
		db.graph("EventExplorer").edgeCollection("When").insertEdge(edge);
	}

	private static void InitializeVertexs(ArangoDatabase db) throws ParseException {
		CollectionCreateOptions options = new CollectionCreateOptions();
		options.type(CollectionType.DOCUMENT);
		
		/*ArangoCollection collection = db.collection("Entities");
		if(collection!= null)
			collection.drop();
		
		collection = db.collection("Actions");
		if(collection != null)
			collection.drop();
		
		collection = db.collection("Events");
		if(collection != null)
			collection.drop();
		
		collection = db.collection("Times");
		if(collection != null)
			collection.drop();*/
		
		db.createCollection("Entities", options);
		db.createCollection("Actions", options);
		db.createCollection("Events", options);
		db.createCollection("Times", options);
	}

	private static void AddVertexData(ArangoDatabase db) throws ParseException {
		db.collection("Entities").insertDocument(CreatePerson("john"));
		db.collection("Entities").insertDocument(CreatePerson("mary"));
		db.collection("Entities").insertDocument(CreatePerson("abigail"));
		db.collection("Entities").insertDocument(CreatePerson("bill"));
		
		db.collection("Actions").insertDocument(CreateAction("teach"));
		db.collection("Actions").insertDocument(CreateAction("run"));
		db.collection("Actions").insertDocument(CreateAction("study"));
		
		db.collection("Events").insertDocument(CreateEvent("teach"));
		db.collection("Events").insertDocument(CreateEvent("run"));
		db.collection("Events").insertDocument(CreateEvent("study"));
		
		
		db.collection("Times").insertDocument(CreateTime("2016", "01/01/2016", "31/12/2016"));
		db.collection("Times").insertDocument(CreateTime("2015", "01/01/2015", "31/12/2015"));

	}
	
	private static TimeVertex CreateTime(String name, String from, String to) throws ParseException
	{
		Time time = new Time();
		time.id = name;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sdf.parse(from);
		time.from = sdf.parse(from);
		time.to = sdf.parse(to);
		TimeVertex entityVertex = new TimeVertex(time);
		return entityVertex;
	}
	
	private static EntityVertex CreatePerson(String name)
	{
		Entity entity = new Entity();
		entity.id= name;
		entity.name = name;
		entity.type = EntityType.Person;
		EntityVertex entityVertex = new EntityVertex(entity);
		return entityVertex;
	}
	
	private static BaseVertex<Event> CreateEvent(String name)
	{
		Event event = new Event();
		event.id = name;
		BaseVertex<Event> entityVertex = new BaseVertex<Event>(event);
		return entityVertex;
	}
	
	private static BaseVertex<Action> CreateAction(String name)
	{
		Action action = new Action();
		action.id = name;
		BaseVertex<Action> entityVertex = new BaseVertex<Action>(action);
		return entityVertex;
	}

	private static void InitializeEdges(ArangoDatabase db, Collection<EdgeDefinition> edgeDefinitions) {
				
		EdgeDefinition hierarchy = new EdgeDefinition()
				.collection("Hierarchy")
				.from("Entities")
				.to("Entities");
		edgeDefinitions.add(hierarchy);					
		
		EdgeDefinition who = new EdgeDefinition()
				.collection("Who")
				.from("Events")
				.to("Entities");
		edgeDefinitions.add(who);
		
		EdgeDefinition when = new EdgeDefinition()
				.collection("When")
				.from("Events")
				.to("Times");
		edgeDefinitions.add(when);
		
		EdgeDefinition what = new EdgeDefinition()
				.collection("What")
				.from("Events")
				.to("Actions");
				edgeDefinitions.add(what);
				
		EdgeDefinition trigger = new EdgeDefinition()
				.collection("Trigger")
				.from("Events")
				.to("Events");
		edgeDefinitions.add(trigger);
		
	}
}
