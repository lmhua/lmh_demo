package com.demo.geo;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;

import com.mongo.base.MongodbDAO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

/**
 * @author Administrator
 *
 */
public class GeoSearch
{
private MongoClient client;
	
	private MongoDatabase db;
	
	private MongoCollection<Document> coll;
	
	public void init()
	{
		client = new MongoClient("127.0.0.1", 27017);
		db = client.getDatabase("test");
		coll = db.getCollection("map");
	}
	
	public void near()
	{
		// 查找[70, 80]最近的三个点
		//Bson query = new BasicDBObject("gis", "${near:[70,80]}");
		
		//Bson query = new BasicDBObject("gis", "{x:70, y:80}");
		
		
		Point refPoint = new Point(new Position(70, 80));
		MongoCursor<Document> result = coll.find(Filters.near("gis", refPoint, 0d, 180d)).limit(3).iterator();
		while (result.hasNext())
		{
			System.out.println(result.next().get("gis"));
		}
	}
	
	public static void main(String[] args)
	{
		GeoSearch gs = new GeoSearch();
		gs.init();
		
		gs.near();
	}
}
