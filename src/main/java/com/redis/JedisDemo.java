package com.redis;

import java.io.IOException;
import java.net.Socket;

import redis.clients.jedis.Jedis;

/**
 * @author Administrator
 *
 */
public class JedisDemo
{
	private Socket socket ;
	
	public JedisDemo() throws Exception
	{
		socket = new Socket("localhost", 6379);
	}
	
	public void redisSet(String key, String value) throws Exception
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("*");
		buffer.append("3").append("\r\n");
		buffer.append("$3").append("\r\n");
		buffer.append("set").append("\r\n");
		
		buffer.append("$").append(key.getBytes().length).append("\r\n");
		buffer.append(key).append("\r\n");
		
		buffer.append("$").append(value.getBytes().length).append("\r\n");
		buffer.append(value).append("\r\n");
		
		String cmd = buffer.toString();
//		System.out.println(cmd);
		
		if (socket != null)
		{
			socket.getOutputStream().write(cmd.getBytes());
		}
		
		byte[] tmp = new byte[1024];
		socket.getInputStream().read(tmp);
		
		System.out.println(new String(tmp));
	}
	
	public void redisGet(String key) throws Exception
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("*");
		buffer.append("2").append("\r\n");
		buffer.append("$3").append("\r\n");
		buffer.append("get").append("\r\n");
		
		buffer.append("$").append(key.getBytes().length).append("\r\n");
		buffer.append(key).append("\r\n");
		
		String cmd = buffer.toString();
		if (socket != null)
		{
			socket.getOutputStream().write(cmd.getBytes());
		}
		
		byte[] tmp = new byte[1024];
		socket.getInputStream().read(tmp);
		
		System.out.println(new String(tmp));
	}
	
	public static void method() throws Exception, IOException
	{
		Socket socket = new Socket("localhost", 6379);
		
		socket.getOutputStream().write("*3\r\n$3\r\nset\r\n$3\r\nfoo\r\n$3\r\nbar\r\n".getBytes());
		
		byte[] b = new byte[1024];
		socket.getInputStream().read(b);
		
		System.out.println(new String(b));
		
		socket.close();
	}
	
	public static void test()
	{
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		//jedis.set("k2", "v2");
		
		System.out.println(jedis.get("foo"));
		
		jedis.close();
	}
	
	public static void main(String[] args) throws Exception, Exception
	{
		JedisDemo demo = new JedisDemo();
		
//		demo.redisSet("wjy", "xiaopiqiu");
		
		demo.redisGet("wjy");
		//method();
		
		//test();
	}
}
