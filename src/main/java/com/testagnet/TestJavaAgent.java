package com.testagnet;

import java.lang.instrument.Instrumentation;

/**
 * @author Administrator
 *
 */
public class TestJavaAgent
{
	/**
	 *  预处理方法
	 */
	public static void premain(String args, Instrumentation inst)
	{
		System.out.println("premain");
	}
	
	public static void main(String[] args)
	{
		System.out.println("main");
		
		System.out.println(System.getProperty("java.class.path"));
	}
}
