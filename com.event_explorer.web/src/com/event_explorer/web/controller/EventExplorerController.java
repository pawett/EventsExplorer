package com.event_explorer.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.event_explorer.graphQL.Schema;

import graphql.ExecutionResult;
import graphql.GraphQL;


	@Controller
	public class EventExplorerController {

		@RequestMapping(value = "/eventExplorer", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		  public Object executeOperation(Map body) {
	        String query = (String) body.get("query");
	        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
	        GraphQL graphql = new GraphQL(Schema.getSchema());
			ExecutionResult executionResult = graphql.execute(query, (Object) null, variables);
	        Map<String, Object> result = new LinkedHashMap<>();
	        if (executionResult.getErrors().size() > 0) {
	            result.put("errors", executionResult.getErrors());
	           // log.error("Errors: {}", executionResult.getErrors());
	        }
	        result.put("data", executionResult.getData());
	        return result;
	    }
		
		@RequestMapping(value = "/eventExplorer", method = RequestMethod.OPTIONS,  produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		  public Object executeOptions(@RequestBody String body) {
	        /*String query = (String) body.get("query");
	        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
	        GraphQL graphql = new GraphQL(Schema.getSchema());
			ExecutionResult executionResult = graphql.execute(query, (Object) null, variables);
	        Map<String, Object> result = new LinkedHashMap<>();
	        if (executionResult.getErrors().size() > 0) {
	            result.put("errors", executionResult.getErrors());
	           // log.error("Errors: {}", executionResult.getErrors());
	        }
	        result.put("data", executionResult.getData());*/
			 GraphQL graphql = new GraphQL(Schema.getSchema());
			
	        return body;
	    }
		
		
		@RequestMapping(value ="/welcome", method = RequestMethod.GET)
		@ResponseBody
		public String helloWorld() {
	 
			String message = "<br><div style='text-align:center;'>"
					+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
			return  message;
		}
	  
	}

