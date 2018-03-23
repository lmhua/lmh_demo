package mongo_web_demo;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;

import com.mongo.model.User;
import com.mongo.repository.UserDao;

@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:springmvc-servlet.xml"})
public class UserDaoTest
{
	@Resource
	private UserDao userDao;
	
	public void save()
	{
		User user = new User();
		user.setUsername("skyLine1");
		user.setPassword("111111");
		
		userDao.insert(user);
	}
	
	public static void main(String[] args)
	{
//		UserDaoTest t = new UserDaoTest();
//		t.save();
		
		String str = "18672999860";
		
		System.out.println(str.substring(str.length()-6, str.length()));
	}
}
