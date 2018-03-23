package com.demo.crud;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ShardsDemo
{
	public static void main(String[] args)
	{
		try
		{
			List<ServerAddress> addresses = new ArrayList<ServerAddress>();
			ServerAddress address1 = new ServerAddress("127.0.0.1", 20000);
			ServerAddress address2 = new ServerAddress("127.0.0.1", 20000);
			ServerAddress address3 = new ServerAddress("127.0.0.1", 20000);
			addresses.add(address1);
			addresses.add(address2);
			addresses.add(address3);

			MongoClient client = new MongoClient(addresses);
			MongoDatabase db = client.getDatabase("");
			MongoCollection coll = db.getCollection("table1");

			BasicDBObject object = new BasicDBObject();
			object.append("id", 1);

			 
			FindIterable dbObject = coll.find(object);
			MongoCursor iterator = dbObject.iterator();
			while(iterator.hasNext())
			{
				System.out.println(iterator.next());
			}

			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
