package com.tuling.demo.test;

import org.junit.Test;

import com.javaagent.lmh.TransClass1;

/**
 * Created by Tommy on 2018/3/6.
 */
public class HelloAgentTest
{
	@Test
	public void helloTest()
	{
		System.out.println("hello word!");
		new UserServiceImpl().getUser();
	    //new TransClass1().getNumber();
	}
}
