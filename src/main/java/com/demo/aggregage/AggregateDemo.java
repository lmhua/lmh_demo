package com.demo.aggregage;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
	db.collection.aggregate([
	  {$match: {"lastUpdateTime":{"$gte":ISODate("2017-09-18T16:00:00.000Z"), "$lte":ISODate("2017-09-20T16:00:00.000Z")}}},
	  {$group:{_id:{"lastEvent":"$lastEvent"}, count:{$sum:0}}},
	  {$sort:{_id:1}},
	  {$limit:100}
	])

 */
public class AggregateDemo
{
	public static void main(String[] args)
	{
		MongoClient client = new MongoClient("127.0.0.1", 27017);
		
		MongoDatabase database = client.getDatabase("persons");
		MongoCollection<Document> collection = database.getCollection("person");
		
		// match:
		BasicDBObject[] arr = {new BasicDBObject("lastUpdateTime", new BasicDBObject("$gte", "2017-09-18 16:00:00")), 
				new BasicDBObject("lastUpdateTime", "2017-09-20 16:00:00")};
		BasicDBObject cond = new BasicDBObject();
		cond.put("$and", arr);
		DBObject match = new BasicDBObject("$match", cond);
		
		// group:
		
		
		
	}
}
 