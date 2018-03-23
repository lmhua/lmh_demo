package com.demo.crud;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ConnectDemo
{
	public static void connectWithNoPwd()
	{
		MongoClient mongoClient = null;
		try
		{
			mongoClient = new MongoClient("127.0.0.1", 27017);
			MongoDatabase mongoDatabase = mongoClient.getDatabase("lwh");

			System.out.println("Connect to database successfully");

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

	public static void connectWithPwd()
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

			System.out.println("Connect to database successfully");
 
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

	public static void main(String args[])
	{
		connectWithNoPwd();
//		connectWithPwd(); 
	}
}
