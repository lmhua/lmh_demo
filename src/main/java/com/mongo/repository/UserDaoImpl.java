package com.mongo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongo.model.User;

@Service
public class UserDaoImpl implements UserDao
{
	/**
	 *  MongoTemplate是代码和数据库之间的接口，对数据库的操作都在这里进行
	 */
	@Autowired
	private MongoTemplate mongoTemplate;
	 
	public List<User> findAll()
	{
		return mongoTemplate.find(new Query(), User.class);
	}

	public List<User> findList(int skip, int limit)
	{
		Query query = new Query();
		query.with(new Sort(Direction.ASC, "_id"));
		query.skip(skip).limit(limit);
		
		return mongoTemplate.find(query, User.class);
	}

	public User findOne(String id)
	{
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, User.class);
	}

	public User findOneByUsername(String username)
	{
		Criteria criteria = Criteria.where("username").is(username);
		Query query = new Query();
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, User.class);
	}

	public void updateFirst(User user)
	{
		if (user != null)
		{
			Query query = new Query();
			Update update = new Update();
			
			String username = user.getUsername();
			if (username != null)
			{
				update.set("username", user.getUsername());
			}
			
			String password = user.getPassword();
			if (password != null)
			{
				update.set("password", user.getPassword());
			}
			
			this.mongoTemplate.updateFirst(query.addCriteria(Criteria.where("_id").is(user.getId())), update, User.class);
		}
	}

	public void delete(String... ids)
	{
		if (ids == null || ids.length == 0)
		{
			return;
		}
		for (String id : ids)
		{
			Query query = new Query(Criteria.where("_id").is(id));
		    this.mongoTemplate.remove(query, User.class);
		}
	}

	public void insert(User user)
	{
		this.mongoTemplate.save(user);
	}
	
	public void deleteCollection(String collname)
	{
		this.mongoTemplate.dropCollection(collname);
	}
	
	public void deleteCollection(Class entityClass)
	{
		this.mongoTemplate.dropCollection(entityClass.getClass());
	}
  
}
