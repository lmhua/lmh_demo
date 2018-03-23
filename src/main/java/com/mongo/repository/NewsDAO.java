package com.mongo.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.Arrays;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongo.base.MongodbDAO;
import com.mongo.base.Pagination;
import com.mongo.model.News;
import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;


@Repository
public class NewsDAO extends MongodbDAO<News>
{

	public Pagination<News> getPageArticle(int pageNo, int pageSize, Query query)
	{
		return this.getPage(pageNo, pageSize, query);
	}

	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public News findOne(Map<String, String> params)
	{
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.where("id").is(params.get("id"));
		query.addCriteria(criteria);
		return super.findOne(query);
	}


	/**
	 * 暂时通过ＩＤ去修改title
	 * 
	 * @param id
	 * @param params
	 */
	public void updateEntity(String id, Map<String, String> params)
	{
		super.updateFirst(Query.query(Criteria.where("id").is(id)),
				Update.update("title", params.get("title")));
	}


	@Override
	protected Class<News> getEntityClass()
	{
		return News.class;
	}

	/**
	 * 
	 * 功能：使用Mongo本身提供的AggregationOutput进行分组查询 参数：  
	 */
	public void testGroup1()
	{
		//Aggregation.newAggregation(News.class, Aggregation.project("eval"))
		
		// 按照eval字段进行分组，注意$eval必须是存在mongodb里面的字段，不能写$evaluate(此字段是News类中定义的，和存入mongodb中的有区别)
		// {$group:{_id:{'AAA':'$BBB'},CCC:{$sum:1}}}固定格式：把要分组的字段放在_id:{}里面，BBB是mongodb里面的某个字段，AAA是BBB的重命名，CCC是$sum:1的重命名
		// 此查询语句== select eval as eval, count(*) as docsNum from news group by eval having
		// docsNum>=85 order by docsNum desc
		// 具体的mongodb和sql的对照可以参考：http://docs.mongodb.org/manual/reference/sql-aggregation-comparison/

		String groupStr = "{$group:{_id:{'eval':'$eval'},docsNum:{$sum:1}}}";
		DBObject group = BasicDBObject.parse(groupStr);
		String matchStr = "{$match:{docsNum:{$gte:9999}}}";
		DBObject match = BasicDBObject.parse(matchStr);
//		String sortStr = "{$sort:{_id.docsNum:-1}}";
//		DBObject sort = BasicDBObject.parse(sortStr);
		 
		Cursor cursor = mongoTemplate.getCollection("news").aggregate(Arrays.asList(group, match), AggregationOptions.builder().build());
		while (cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
		
//		CommandResult commandResult = mongoTemplate.getCollection("news").explainAggregate(Arrays.asList(group), AggregationOptions.builder().build());
//		System.out.println(commandResult.toJson());
		
		// 转换为执行原生的mongodb查询语句
		// { "aggregate" : "news" , "pipeline" : [ { "$group" : { "_id" : { "eval" : "$eval"} ,
		// "docsNum" : { "$sum" : 1}}} , { "$match" : { "docsNum" : { "$gte" : 85}}} , { "$sort" : {
		// "_id.docsNum" : -1}}]}
		//System.out.println(commandResult.getCommandResult());
		// 查询结果
		// { "serverUsed" : "localhost/127.0.0.1:47017" , "result" : [ { "_id" : { "evaluate" : 1} ,
		// "docsNum" : 9955} , { "_id" : { "evaluate" : 0} , "docsNum" : 10047}] , "ok" : 1.0}

		// 也可以把查询结果封装到NewsNumDTO，这样以一个dto对象返回前台操作就更容易了
		
//		NewsNumDTO dto = new NewsNumDTO();
//		for (Iterator<DBObject> it = output.results().iterator(); it.hasNext();)
//		{
//			BasicDBObject dbo = (BasicDBObject) it.next();
//			BasicDBObject keyValus = (BasicDBObject) dbo.get("_id");
//			int eval = keyValus.getInt("eval");
//			long docsNum = ((Integer) dbo.get("docsNum")).longValue();
//			if (eval == 1)
//			{
//				dto.setPositiveNum(docsNum);
//			}
//			else
//			{
//				dto.setNegativeNum(docsNum);
//			}
//		}

	}

	/**
	 * 
	 *  获取和testGroup1方法同样结果的另一种写法，Spring Data MongoDB隆重登场，语法更加简洁易懂 
	 *  
	 */
	public void testAggregation1()
	{
		TypedAggregation<News> agg = Aggregation.newAggregation(News.class, project("evaluate"),
				group("evaluate").count().as("totalNum"), match(Criteria.where("totalNum").gte(85)),
				sort(Sort.Direction.DESC, "totalNum"));
		System.out.println(agg.toString());

		AggregationResults<BasicDBObject> result =
				mongoTemplate.aggregate(agg, BasicDBObject.class);
		// 执行语句差不多
		// { "aggregate" : "__collection__" , "pipeline" : [ { "$project" : { "evaluate" : 1}} , {
		// "$group" : { "_id" : "$evaluate" , "totalNum" : { "$sum" : 1}}} , { "$match" : {
		// "totalNum" : { "$gte" : 85}}} , { "$sort" : { "totalNum" : -1}}]}
		System.out.println(result.getMappedResults());
		// 查询结果简洁明了
		// [{ "_id" : 0 , "totalNum" : 10047}, { "_id" : 1 , "totalNum" : 9955}]

		// 使用此方法，如果封装好了某一个类，类里面的属性和结果集的属性一一对应，那么，Spring是可以直接把结果集给封装进去的
		// 就是AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(agg,
		// BasicDBObject);中的BasicDBObject改为自己封装的类
		// 但是感觉这样做有点不灵活，其实吧，应该是自己现在火候还不到，还看不到他的灵活性，好处在哪里；等火候旺了再说呗
		// 所以，就用这个万能的BasicDBObject类来封装返回结果
//		List<BasicDBObject> resultList = result.getMappedResults();
//		NewsNumDTO dto = new NewsNumDTO();
//		for (BasicDBObject dbo : resultList)
//		{
//			int eval = dbo.getInt("_id");
//			long num = dbo.getLong("totalNum");
//			if (eval == 1)
//			{
//				dto.setPositiveNum(num);
//			}
//			else
//			{
//				dto.setNegativeNum(num);
//			}
//		}
//		System.out.println(dto.getPositiveNum());
	}



	/**
	 * 
	 * 功能：previousOperation的简单使用--为分组的字段（_id）建立别名 参数： 创建人：OnTheRoad_Lee 修改人：OnTheRoad_Lee
	 * 最后修改时间：2014-5-26
	 */
	public void testAggregation2()
	{
		TypedAggregation<News> agg = Aggregation.newAggregation(News.class,
				// match(Criteria.where("mediaType").is(100))
				project("evaluate"), group("evaluate").count().as("totalNum"),
				project("evaluate", "totalNum").and("eval").previousOperation()
		// 为分组的字段（_id）建立别名
		);
		AggregationResults<BasicDBObject> result =
				mongoTemplate.aggregate(agg, BasicDBObject.class);
		System.out.println(agg.toString());
		// { "aggregate" : "__collection__" , "pipeline" : [ { "$project" : { "evaluate" : 1}} , {
		// "$group" : { "_id" : "$evaluate" , "totalNum" : { "$sum" : 1}}} , { "$project" : {
		// "evaluate" : "$_id.evaluate" , "totalNum" : 1 , "_id" : 0 , "eval" : "$_id"}}]}
		System.out.println(result.getMappedResults());
		// [{ "totalNum" : 10047 , "eval" : 0}, { "totalNum" : 9955 , "eval" : 1}]
	}

	/**
	 * 
	 * 功能：unwind()的使用，通过Spring Data MongoDB unwind()就是$unwind这个命令的转换， $unwind - 可以将一个包含数组的文档切分成多个,
	 * 比如你的文档有 中有个数组字段 A, A中有10个元素, 那么 经过 $unwind处理后会产生10个文档，这些文档只有 字段 A不同
	 * 详见：http://my.oschina.net/GivingOnenessDestiny/blog/88006 参数： 创建人：OnTheRoad_Lee
	 * 修改人：OnTheRoad_Lee 最后修改时间：2014-5-26
	 */
	public void testAggregation3()
	{
		TypedAggregation<News> agg = Aggregation.newAggregation(News.class, unwind("classKey"),
				project("evaluate", "classKey")
				// 这里说明一点就是如果group>=2个字段，那么结果集的分组字段就没有_id了，取而代之的是具体的字段名（和testAggregation()最对比）
				, group("evaluate", "classKey").count().as("totalNum"),
				sort(Sort.Direction.DESC, "totalNum"));

		AggregationResults<BasicDBObject> result =
				mongoTemplate.aggregate(agg, BasicDBObject.class);
		System.out.println(agg.toString());
		// { "aggregate" : "__collection__" , "pipeline" : [ { "$unwind" : "$classKey"} , {
		// "$project" : { "evaluate" : 1 , "classKey" : 1}} , { "$group" : { "_id" : { "evaluate" :
		// "$evaluate" , "classKey" : "$classKey"} , "totalNum" : { "$sum" : 1}}} , { "$sort" : {
		// "totalNum" : -1}}]}
		System.out.println(result.getMappedResults());
		// 结果就是酱紫，一目了然，怎么操作，就交给你自己了
		// [{ "evaluate" : 0 , "classKey" : "A201" , "totalNum" : 4857}, { "evaluate" : 0 ,
		// "classKey" : "23" , "totalNum" : 4857}, { "evaluate" : 0 , "classKey" : "A101" ,
		// "totalNum" : 4842}, { "evaluate" : 0 , "classKey" : "24" , "totalNum" : 4806}, {
		// "evaluate" : 1 , "classKey" : "A101" , "totalNum" : 4787}, { "evaluate" : 0 , "classKey"
		// : "C201" , "totalNum" : 4787}, { "evaluate" : 0 , "classKey" : "A102" , "totalNum" :
		// 4783}, { "evaluate" : 1 , "classKey" : "22" , "totalNum" : 4776}, { "evaluate" : 1 ,
		// "classKey" : "C203" , "totalNum" : 4754}, { "evaluate" : 0 , "classKey" : "B103" ,
		// "totalNum" : 4751}, { "evaluate" : 0 , "classKey" : "C203" , "totalNum" : 4743}, {
		// "evaluate" : 0 , "classKey" : "22" , "totalNum" : 4718}, { "evaluate" : 1 , "classKey" :
		// "25" , "totalNum" : 4712}, { "evaluate" : 0 , "classKey" : "B101" , "totalNum" : 4708}, {
		// "evaluate" : 1 , "classKey" : "24" , "totalNum" : 4703}, { "evaluate" : 1 , "classKey" :
		// "26" , "totalNum" : 4703}, { "evaluate" : 1 , "classKey" : "23" , "totalNum" : 4702}, {
		// "evaluate" : 0 , "classKey" : "25" , "totalNum" : 4698}, { "evaluate" : 1 , "classKey" :
		// "A102" , "totalNum" : 4695}, { "evaluate" : 1 , "classKey" : "C202" , "totalNum" : 4692},
		// { "evaluate" : 1 , "classKey" : "A202" , "totalNum" : 4689}, { "evaluate" : 1 ,
		// "classKey" : "A203" , "totalNum" : 4688}, { "evaluate" : 1 , "classKey" : "B102" ,
		// "totalNum" : 4663}, { "evaluate" : 0 , "classKey" : "C202" , "totalNum" : 4653}, {
		// "evaluate" : 0 , "classKey" : "B102" , "totalNum" : 4653}, { "evaluate" : 1 , "classKey"
		// : "A201" , "totalNum" : 4647}, { "evaluate" : 1 , "classKey" : "B101" , "totalNum" :
		// 4645}, { "evaluate" : 1 , "classKey" : "B103" , "totalNum" : 4638}, { "evaluate" : 1 ,
		// "classKey" : "C201" , "totalNum" : 4637}, { "evaluate" : 0 , "classKey" : "26" ,
		// "totalNum" : 4635}, { "evaluate" : 0 , "classKey" : "A203" , "totalNum" : 4633}, {
		// "evaluate" : 0 , "classKey" : "A202" , "totalNum" : 4588}]
	}

	/**
	 * 
	 * 功能：Spring Data MongoDB，按照时间（字符串）分组 参数： 创建人：OnTheRoad_Lee 修改人：OnTheRoad_Lee 最后修改时间：2014-5-26
	 */
	public void testAggregation4()
	{
		TypedAggregation<News> agg = Aggregation.newAggregation(News.class,
				// project().andExpression()里面是一个表达式
				// 详见api：http://docs.spring.io/spring-data/data-mongodb/docs/current/reference/htmlsingle/#mongo.aggregation
				// 搜索 .andExpression 定位到具体的方法模块
				project("evaluate").andExpression("substr(publishTimeStr,0,10)").as("publishDate"),
				group("evaluate", "publishDate").count().as("totalNum"),
				sort(Sort.Direction.DESC, "totalNum"));

		AggregationResults<BasicDBObject> result =
				mongoTemplate.aggregate(agg, BasicDBObject.class);
		System.out.println(agg.toString());
		// { "aggregate" : "__collection__" , "pipeline" : [ { "$project" : { "evaluate" : 1 ,
		// "publishDate" : { "$substr" : [ "$publishTimeStr" , 0 , 10]}}} , { "$group" : { "_id" : {
		// "evaluate" : "$evaluate" , "publishDate" : "$publishDate"} , "totalNum" : { "$sum" : 1}}}
		// , { "$sort" : { "totalNum" : -1}}]}
		System.out.println(result.getMappedResults());
		// [{ "evaluate" : 0 , "publishDate" : "2014-03-09" , "totalNum" : 101}, { "evaluate" : 1 ,
		// "publishDate" : "2014-02-14" , "totalNum" : 100}, { "evaluate" : 1 , "publishDate" :
		// "2014-02-11" , "totalNum" : 99}, { "evaluate" : 0 , "publishDate" : "2014-03-17" ,
		// "totalNum" : 98}, { "evaluate" : 1 , "publishDate" : "2014-03-26" , "totalNum" : 98}, ……]
		// 这个查询结果貌似不是我们想要的效果，理想，一目了然的效果应该是，以日期为单位，日期底下正面多少，负面多少：
		// [
		// { "publishDate" : "2014-03-09" , "evalInfo" : [{"evaluate" : 0 , "totalNum" : 101},
		// {"evaluate" : 1 , "totalNum" : 44}]}
		// { "publishDate" : "2014-03-12" , "evalInfo" : [{"evaluate" : 0 , "totalNum" : 11},
		// {"evaluate" : 1 , "totalNum" : 32}]},
		// ……
		// ]
		// 无奈本人功力尚浅，查了N多资料，各种论坛，中文的，英文的都查了，就是找不到Spring Data MongoDB 分组方法
		// ，所以就引出了testAggregation5
	}



	/**
	 * 
	 * 功能：使用原生态mongodb语法，按照时间（字符串）分组，整合查询结果，使用$push命令 参数： 创建人：OnTheRoad_Lee 修改人：OnTheRoad_Lee
	 * 最后修改时间：2014-5-26
	 */
	public void testAggregation5()
	{

		/* Group操作 */
		String groupStr =
				"{$group:{_id:{'evaluate':'$eval','ptimes':{$substr:['$ptimes',0,10]}},totalNum:{$sum:1}}}";
		DBObject group = (DBObject) JSON.parse(groupStr);

		/* Reshape Group Result */
		DBObject projectFields = new BasicDBObject();
		projectFields.put("ptimes", "$_id.ptimes");
		projectFields.put("evalInfo",
				new BasicDBObject("evaluate", "$_id.evaluate").append("totalNum", "$totalNum"));
		DBObject project = new BasicDBObject("$project", projectFields);

		/* 将结果push到一起 */
		DBObject groupAgainFields = new BasicDBObject("_id", "$ptimes");
		groupAgainFields.put("evalInfo", new BasicDBObject("$push", "$evalInfo"));
		DBObject reshapeGroup = new BasicDBObject("$group", groupAgainFields);

		/* 查看Group结果 */
		AggregationOutput output =
				mongoTemplate.getCollection("news").aggregate(group, project, reshapeGroup);
//		System.out.println(output.getCommand());
		// { "aggregate" : "news" , "pipeline" : [ { "$group" : { "_id" : { "evaluate" : "$eval" ,
		// "ptimes" : { "$substr" : [ "$ptimes" , 0 , 10]}} , "totalNum" : { "$sum" : 1}}} , {
		// "$project" : { "ptimes" : "$_id.ptimes" , "evalInfo" : { "evaluate" : "$_id.evaluate" ,
		// "totalNum" : "$totalNum"}}} , { "$group" : { "_id" : "$ptimes" , "evalInfo" : { "$push" :
		// "$evalInfo"}}}]}
//		System.out.println(output.getCommandResult());
		// { "serverUsed" : "localhost/127.0.0.1:47017" , "result" : [
		// { "_id" : "2014-04-24" , "evalInfo" : [ { "evaluate" : 1 , "totalNum" : 67} , {
		// "evaluate" : 0 , "totalNum" : 76}]}
		// , { "_id" : "2014-02-05" , "evalInfo" : [ { "evaluate" : 1 , "totalNum" : 70} , {
		// "evaluate" : 0 , "totalNum" : 84}]}
		// , { "_id" : "2014-03-21" , "evalInfo" : [ { "evaluate" : 0 , "totalNum" : 82} , {
		// "evaluate" : 1 , "totalNum" : 89}]}}……]
		// , "ok" : 1.0}
	}

}
