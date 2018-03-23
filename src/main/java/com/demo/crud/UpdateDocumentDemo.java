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
import com.mongodb.client.model.Filters;

public class UpdateDocumentDemo
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

			// 更新文档 将文档中age=0的文档修改为age=1
			collection.updateMany(Filters.eq("age", 0), new Document("$set", new Document("age", 1)));
			// 检索查看结果
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext())
			{
				System.out.println(mongoCursor.next());
			}
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
