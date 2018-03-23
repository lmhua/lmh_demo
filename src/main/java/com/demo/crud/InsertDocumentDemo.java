package com.demo.crud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDocumentDemo
{
	 
	public static void main(String args[])
	{
		MongoClient mongoClient = null;
		try
		{
			ServerAddress serverAddress = new ServerAddress("localhost", 27017);

			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			MongoCredential credential =
					MongoCredential.createScramSha1Credential("lwh", "lwh", "lwh".toCharArray());
			credentials.add(credential);

			mongoClient = new MongoClient(serverAddress, credentials);

			MongoDatabase mongoDatabase = mongoClient.getDatabase("lwh");

			MongoCollection<Document> collection = mongoDatabase.getCollection("lwh");
			
			Document document =  new Document("name", "MongoDB")
	                .append("type", "database")
	                .append("count", 1)
	                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
	                .append("info", new Document("x", 203).append("y", 102));
			
			collection.insertOne(document);
			
			System.out.println("insert document successfully");
			 
		}
		catch (Exception e)
		{
			System.err.println("异常：" + e.getClass().getName() + ": " + e.getMessage());
		}
		finally
		{
			if (mongoClient != null)
			{
				mongoClient.close();
			}
		}
	}
}
