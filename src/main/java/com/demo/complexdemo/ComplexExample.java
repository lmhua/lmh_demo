package com.demo.complexdemo;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

/**
 * @author Administrator
 *
 */
public class ComplexExample
{
	private MongoClient client;
	
	private MongoDatabase db;
	
	private MongoCollection<Document> coll;
	
	public void init()
	{
		client = new MongoClient("127.0.0.1", 27017);
		db = client.getDatabase("lesson1");
		coll = db.getCollection("blog");
	}
	
	public void insert()
	{
		 
		Document doc1 = new Document("title", "good day").append("owner", "tom").append("words", 300);
		Document comm1 = new Document("author", "joe").append("score", 3).append("comment", "good");
		Document comm2 = new Document("author", "white").append("score", 1).append("comment", "oh no");
		List<Document> comments = Arrays.asList(comm1, comm2);
		doc1.append("comments", comments);
		 
		Document doc2 = new Document("title", "good").append("owner", "john").append("words", 400);
		doc2.put("comments", Arrays.asList(new Document("author", "william").append("score", 4).append("comment", "good"), 
				new Document("author", "white").append("score", 6).append("comments", "very good")));
 
		Document doc3 = new Document("title", "good night").append("owner", "mike").append("words", 200).append("tag", Arrays.asList(1,2,3,4));
		 
		Document doc4 = new Document("title", "happiness").append("owner", "tome").append("words", 1480).append("tag", Arrays.asList(2,3,4));
 
		Document doc5 = new Document("title", "a good thing").append("owner", "tom").append("words", 180).append("tag", Arrays.asList(1,2,3,4,5));
		
		coll.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5));
	}
	
	public void createIndex()
	{
		// 创建单字段索引：
		coll.createIndex(new Document("words", 1));
		
		// 创建组合索引，同样遵循最左前缀原则
		coll.createIndex(new Document("title", 1).append("owner", -1));
		
		// 创建全文索引：
		coll.createIndex(new Document("title", "text"));
		
	}
	
	public void print(Bson filter)
	{
		MongoCursor<Document> cursor = coll.find(filter).iterator();
		while (cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
		System.out.println("-------------------------------------------");
	}
	
	public void sortPrint(Bson filter, Bson sortDoc)
	{
		MongoCursor<Document> cursor = coll.find(filter).sort(sortDoc).iterator();
		while (cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
		System.out.println("-------------------------------------------");
	}
	
	public void projectPrint(Bson filter, Bson project)
	{
		MongoCursor<Document> cursor = coll.find(filter).projection(project).iterator();
		while (cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
		System.out.println("-------------------------------------------");
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
		ComplexExample example = new ComplexExample();
		
		example.init();
		example.insert();
		
		example.createIndex();
		
		// 查询 title=good
		example.print(new Document("title", "good"));
		
		//查询title=good and owner=tom  
		example.print(new Document("title", "good").append("owner", "tom"));
		
		//查询title like %good% and owner=tom  
		example.print(new Document("title", new Document("$regex", "good")).append("owner", "tom"));
		
		//查询全部按title排序  
		example.sortPrint(new Document(), new Document("title", 1));
		
		//查询全部按owner,title排序  
		example.sortPrint(new Document(), new Document("title", 1).append("owner", 1));
		
		//查询全部按words倒序排序
		example.sortPrint(new Document(), new Document("words", -1));
		
		//查询owner=tom or words>350
		example.print(new Document("$or", Arrays.asList(new Document("owner", "tom"), new Document("words", new Document("$gt", 350)))));
		
		//返回title和owner字段  
		example.projectPrint(new Document(), new Document("title", 1).append("owner", 1).append("_id", 0));
		
		//返回除title外的其他字段  
		example.projectPrint(new Document(), new Document("title", 0));
		
		//内嵌文档匹配  
		example.print(new Document("comments.author", "white"));
		
		//一个错误的示例, 想查询评论中包含作者是white且分值>2的, 返回结果不符合预期  
		example.print(new Document("comments.author", "white").append("comments.score", new Document("$gt", "2")));
		// 正确示例：
		example.print(Projections.elemMatch("comments", Filters.and(Filters.eq("author", "white"), Filters.gt("score", 2))));
		
		//查找title以good开头的, 并且comments只保留一个元素 ($slice) 
		example.projectPrint(Filters.regex("title", "^good"), new Document("comments", new Document("$slice", 1)));
		
		// 全文索引查找：
		example.print(new Document("title", "good"));
		
		//Filters实现$in 操作：
		example.print(Filters.in("owner", Arrays.asList("mike", "john")));
		
		//Filters实现$nin操作：
		example.print(Filters.nin("owner", Arrays.asList("mike", "john")));
		
		// $and条件组合 ：
		example.print(Filters.and(Filters.eq("owner", "tom"), Filters.gte("words", 300)));
		
		//较复杂的组合: (words=300 or words=400) and (owner=joe or size(comments)=2)
		example.print(Filters.and(Filters.or(Filters.eq("words", 300), Filters.eq("words", 400)), Filters.eq("owner", "john")));
		
		example.close();
	}
}
