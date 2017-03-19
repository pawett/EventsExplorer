package com.event_explorer.web;
import static spark.Spark.get;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.event_explorer.graphQL.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.ExecutionResult;
import graphql.GraphQL;
import spark.servlet.SparkApplication;
import com.mongodb.util.JSON;

import static spark.Spark.*;

public class Main implements SparkApplication {
   
	@Override
	public void init() {
		 /*
         * enable CORS in our Spark server. CORS is the acronym for “Cross-origin resource sharing”: a mechanism that allows to access REST resources outside the original domain of the request.
         * http://www.mastertheboss.com/cool-stuff/create-a-rest-services-layer-with-spark
         */
        options("/graphql", (request,response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if(accessControlRequestMethod != null){
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            
            

            return "OK";
        });

        before((request,response) -> {
            response.header("Access-Control-Allow-Origin", "http://localhost:36667");
            response.header("Access-Control-Allow-Credentials", "true");
        });
        
		post("/graphql", (request, response) -> {
			Map<String, Object> payload;
			ObjectMapper mapper = new ObjectMapper();
			payload = mapper.readValue(request.body(), Map.class);
			Map<String,Object> variables = 
			(Map<String, Object>) payload.get("variables");
			if(variables == null)
				variables = new HashMap<String,Object>();
			GraphQL graphql = new GraphQL(Schema.getSchema());
			ExecutionResult executionResult = 
			graphql.execute(payload.get("query").toString(), null, null, variables);
			Map<String, Object> result = new LinkedHashMap<>();
			if (executionResult.getErrors().size() > 0) {
			result.put("errors", executionResult.getErrors());
			}
			result.put("data", executionResult.getData());
			response.type("application/json");
			return JSON.serialize(result);
			//return result;
		});
	}
}
