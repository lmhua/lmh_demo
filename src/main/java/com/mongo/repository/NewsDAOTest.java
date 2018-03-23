package com.mongo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongo.base.DateUtil;
import com.mongo.base.Pagination;
import com.mongo.model.News;

public class NewsDAOTest
{
	private static ApplicationContext app;

	private static NewsDAO newsDAO;

	@BeforeClass
	public static void initSpring()
	{
		app = new ClassPathXmlApplicationContext(new String[]
		{"classpath:spring-context.xml"});
		
		newsDAO = (NewsDAO) app.getBean("newsDAO");
	}

	@Test
	public void save()
	{
		long start = System.currentTimeMillis();
		
		News n = null;
		for (int i = 0; i < 10000; i++)
		{
			n = new News();
			n.setTitle("title_" + i);
			n.setUrl("url_" + i);

			// 2017-01-01到2017-10-01之间的随机时间
			Date randomDate = DateUtil.randomDate("2014-10-01", "2017-01-01");
			// MongoDB里如果时间类型存的是Date，那么会差8个小时的时区，因为MongoDB使用的格林威治时间，中国所处的是+8区，so……
			// 比如我保存的是2014-05-01 00:00:00，那么保存到MongoDB里则是2014-05-01
			// 08:00:00，所以为了统一方面，那就保存字符串类型，底下保存的long类型
			n.setPublishTimeStr(DateUtil.formatDateTimeByDate(randomDate));
			// long类型在查询速度中肯定会比较快
			n.setPublishTime(randomDate.getTime());
			n.setPublishDate(randomDate);

			n.setPublishMedia("publishMedia_" + i);

			String[] areaArr =
			{"1024", "102401", "102402", "102403", "102404", "102405", "102406", "102407", "102408",
					"10240101", "10240102", "10240201", "10240202", "10240301", "10240302",
					"10240401", "10240402", "10240501", "10240502", "10240601", "10240602",
					"10240701", "10240702", "10240801", "10240802"};
			int areaNum = (int) (Math.random() * areaArr.length);// 产生0-strs.length的整数随机数
			String area = areaArr[areaNum];
			n.setArea(area);

			String[] ckeyArr =
			{"A101", "A102", "A201", "A202", "A203", "B101", "B102", "B103", "C201", "C202", "C203",
					"22", "23", "24", "25", "26"};
			int ckeyNum = (int) (Math.random() * ckeyArr.length);// 产生0-strs.length的整数随机数
			List<String> list = new ArrayList<String>();
			for (int j = 0; j < ckeyNum; j++)
			{
				int ckeyNum1 = (int) (Math.random() * ckeyArr.length);// 产生0-strs.length的整数随机数
				list.add(ckeyArr[ckeyNum1]);
			}
			n.setClassKey(list);

			Integer[] evalArr =
			{1, 0};
			int evalNum = (int) (Math.random() * evalArr.length);// 产生0-strs.length的整数随机数
			n.setEvaluate(evalArr[evalNum]);

			Integer[] mproArr =
			{1, 2, 100};
			int mproNum = (int) (Math.random() * mproArr.length);// 产生0-strs.length的整数随机数
			n.setMediaProperty(mproArr[mproNum]);

			Integer[] mtypeArr =
			{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
			int mtypeNum = (int) (Math.random() * mtypeArr.length);// 产生0-strs.length的整数随机数
			n.setMediaType(mtypeArr[mtypeNum]);

			Integer[] levelArr =
			{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
			int levelNum = (int) (Math.random() * levelArr.length);// 产生0-strs.length的整数随机数
			n.setLevel(levelArr[levelNum]);

			newsDAO.save(n);
		}
	
		long end = System.currentTimeMillis();
		
		System.out.println("OK, cost: " + (end-start));
	}
	
	@Test
	public void queryList()
	{
	 
		// db.news.find({$and:[{title:{$regex:/title_895.*/, $options:"si"}}, {level:{$gte:4}}, {level:{$lt:12}}, {ckey:{$in:['A203', '22']}}]}, {_id:0, title:1, level:1, ckey:1, area:1, ptime:1})
	 
		Query query = new Query();
		query.addCriteria(Criteria.where("title").regex("title_895.*", "si"));
//		query.addCriteria(Criteria.where("level").gte(4).lt(12));  // level >=4 and level < 12
//		query.addCriteria(Criteria.where("ckey").in(Arrays.asList("A203", "22")));  //ckey in ('A203', 22)
//		query.addCriteria(Criteria.where("area").regex("^1024")); //area like '1024%'
//		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "ptime")));  //order by ptime desc
		
		Pagination<News> page = newsDAO.getPage(1, 20, query);
		System.out.println(page.getTotalCount());
		
		List<News> datas = page.getDatas();
		for(News n : datas)
		{
			System.out.println(n.getId() + "-" + n.getLevel() + "-" +n.getClassKey() + " - "+ n.getArea() + "-" + n.getTitle() + "- " + n.getPublishTime());
		}
	}
	
	@Test
	public void testGroup1()
	{	// 根据eval属性进行分组：
		newsDAO.testGroup1();
	}
	
	@Test
	public void testAggregation1()
	{
		newsDAO.testAggregation1();
		
	}
}
