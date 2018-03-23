package com.mongo.repository;

import java.util.List;

import com.mongo.model.User;

public interface UserDao<T>
{
	void insert(User user);
	
	List<User> findAll();

	List<User> findList(int skip, int limit);

	User findOne(String id);

	User findOneByUsername(String username);

	void updateFirst(User user);

	void delete(String... ids);
	
	void deleteCollection(String collname);
	
	void deleteCollection(Class<T> entityClass);
}
