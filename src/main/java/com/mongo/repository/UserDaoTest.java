package com.mongo.repository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongo.model.User;

public class UserDaoTest
{
	private UserDao userDao;
	
	public UserDaoTest(UserDao userDao)
	{
		this.userDao = userDao;
	}
	
	public void testSave()
	{
		User user = new User();
		user.setUsername("skyLine1");
		user.setPassword("111111");
		userDao.insert(user);
	}
	
	public void testDeleteCollection(String collName)
	{
		userDao.deleteCollection(collName);
	}

	public void testUpdate()
	{
		User user = new User();
		user.setId("59cc6b76f0ace115dc1410de");
		user.setPassword("volvo");
		userDao.updateFirst(user);
	}
	
	public void delete()
	{
		userDao.delete("59cc6b76f0ace115dc1410de");
	}
	
	public void print()
	{
		System.out.println(userDao.findAll());
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		UserDaoImpl userDao = (UserDaoImpl) context.getBean("userDaoImpl");

		UserDaoTest t = new UserDaoTest(userDao);
		
		t.testSave();
		
//		t.testDeleteCollection("user");
		
//		t.testUpdate();
		
//		t.delete();
		
		t.print();
		
	}
}
