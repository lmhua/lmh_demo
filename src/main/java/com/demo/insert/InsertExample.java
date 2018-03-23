package com.demo.insert;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @author Administrator
 *
 */
public class InsertExample
{
	private MongoClient client;
	
	private MongoDatabase db;
	
	
	
	public void init()
	{
		client = new MongoClient("127.0.0.1", 27017);
		db = client.getDatabase("lesson1");
	}
	
	public void insert()
	{
		MongoCollection<Document> coll = db.getCollection("blog");
		coll.drop();
		
		/**
		    {
			  "title":"good day", 
			  "owner":"tom", 
			  "words":300, 
			  "comments":
			  [
				 {"author":"joe", "score":3, "comment":"good"},
				 {"author":"white", "score":1, "comment":"oh no"}
			  ]
			}
		 */
		Document doc1 = new Document("title", "good day").append("owner", "tome").append("words", 300);
		Document comm1 = new Document("author", "joe").append("score", 3).append("comment", "good");
		Document comm2 = new Document("author", "white").append("score", 1).append("comment", "oh no");
		List<Document> comments = Arrays.asList(comm1, comm2);
		doc1.append("comments", comments);
		
		/**
		{
		  title:"good",
		  owner:"john",
		  words:400,
		  comments:
		  [
			{author:"william", score:4, comment:good"},
			{author:"white", score:6, comments:very good"}
		  ]
		}
		 */
		Document doc2 = new Document("title", "good").append("owner", "john").append("words", 400);
		doc2.put("comments", Arrays.asList(new Document("author", "william").append("score", 4).append("comment", "good"), 
				new Document("author", "white").append("score", 6).append("comments", "very good")));

		/**
			{
			  title:"good night",
			  owner:"mike",
			  words:200,
			  tag:[1,2,3,4]
			}
		 */
		Document doc3 = new Document("title", "good night").append("owner", "mike").append("words", 200).append("tag", Arrays.asList(1,2,3,4));
		
		/**
	       {
			  title:"happiness",
			  owner:"tom",
			  words:1480,
			  tag:[2,3,4]
			}
		 */
		Document doc4 = new Document("title", "happiness").append("owner", "tome").append("words", 1480).append("tag", Arrays.asList(2,3,4));

		/**
		    {
			  title:"a good thing",
			  owner:"tom",
			  words:180,
			  tag:[1,2,3,4,5]
			}
		 */
		Document doc5 = new Document("title", "a good thing").append("owner", "tom").append("words", 180).append("tag", Arrays.asList(1,2,3,4,5));
		
		coll.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5));
		 
	}
	
	public void print(Document filter)
	{
		MongoCollection<Document> coll = db.getCollection("blog");
		MongoCursor<Document> cursor = coll.find(filter).iterator();
		while (cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
	}
	
	public void close()
	{
		if (client != null)
		{
			client.close();
		}
	}
	
	public static void main(String[] args)
	{
		InsertExample example = new InsertExample();
		example.init();
		
		example.insert();
		
		example.print(new Document());
		
		example.close();
	}
}
