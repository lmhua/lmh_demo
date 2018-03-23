package com.demo.replSet;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @author Administrator
 *
 */
public class ReplSetTest
{
	public static void main(String[] args)
	{
		MongoClient client = null;
		try
		{
			List<ServerAddress> addresses = new ArrayList<ServerAddress>();
			ServerAddress address1 = new ServerAddress("192.168.15.104", 1111);
			ServerAddress address2 = new ServerAddress("192.168.15.104", 2222);
			ServerAddress address3 = new ServerAddress("192.168.15.104", 3333);
			ServerAddress address4 = new ServerAddress("192.168.15.104", 4444);
			
			addresses.add(address1);
			addresses.add(address2);
			addresses.add(address3);
			addresses.add(address4);
			
			client = new MongoClient(addresses);
			MongoDatabase db = client.getDatabase("lmh");
			MongoCollection coll = db.getCollection("first"); 
			
			Document doc1 = new Document();
			doc1.append("_id", 2).append("name", "bbb").append("age", 2);
			
			coll.insertOne(doc1);
			
			
			MongoCursor dbCursor = coll.find().iterator();
			while (dbCursor.hasNext())
			{
				Object obj = dbCursor.next();
				System.out.println(obj.toString());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (client != null)
			{
				client.close();
			}
		}
	}
}
