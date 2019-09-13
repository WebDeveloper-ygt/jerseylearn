package com.learn.jersey.resource;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/connect")
public class DataBaseConnectionResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getDbConnection(){


       MongoClientURI uri = new MongoClientURI("mongodb+srv://quizdb:Mongo%40db19@cluster0-ol7kj.mongodb.net/test?w=majority");
       // MongoClientURI uri = new MongoClientURI("mongodb+srv://quizdb%3AMongo%40db19%40cluster0-ol7kj.mongodb.net%2Ftest%3FretryWrites%3Dtrue%26w%3Dmajority");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");

        System.out.println(database.getName());
    }
}
