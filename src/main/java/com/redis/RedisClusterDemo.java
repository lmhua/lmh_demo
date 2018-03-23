package com.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis cluster demo test
 *
 */
public class RedisClusterDemo
{
	public static void main(String[] args) throws Exception
	{
		JedisPoolConfig config = new JedisPoolConfig();
		config = new JedisPoolConfig();
		config.setMaxTotal(60000);// 设置最大连接数
		config.setMaxIdle(1000); // 设置最大空闲数
		config.setMaxWaitMillis(3000);// 设置超时时间
		config.setTestOnBorrow(true);


		// 集群结点
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		jedisClusterNode.add(new HostAndPort("192.168.15.183", Integer.parseInt("7000")));
		jedisClusterNode.add(new HostAndPort("192.168.15.183", Integer.parseInt("7001")));
		jedisClusterNode.add(new HostAndPort("192.168.15.183", Integer.parseInt("7002")));
		jedisClusterNode.add(new HostAndPort("192.168.15.107", Integer.parseInt("7003")));
		jedisClusterNode.add(new HostAndPort("192.168.15.107", Integer.parseInt("7004")));
		jedisClusterNode.add(new HostAndPort("192.168.15.107", Integer.parseInt("7005")));

		JedisCluster jc = new JedisCluster(jedisClusterNode, config);
		
		System.out.println(jc.get("lwh"));
		System.out.println(jc.get("lwh:name"));
		System.out.println(jc.get("lwh:age"));
		System.out.println(jc.get("lwh:sex"));
		
		System.out.println(jc.get("wjy"));
		
		jc.close();
	}
}
