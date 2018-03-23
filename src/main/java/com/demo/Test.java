package com.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class Test
{
	public static List<String> findNotExist(String[] a, String[] b)
	{
		List<String> rtn = new ArrayList<String>();
		
		for(String tmpa : a)
		{
			boolean exist = false;
			for(String tmpb : b)
			{
				if (tmpb.equals(tmpa))
				{
					exist = true;
					break;
				}
			}
			if (!exist)
			{
				rtn.add(tmpa);
			}
		}
		
		return rtn;
	}
	
	public static List<String> findNewAdd(String[] a, String[] b)
	{
		List<String> rtn = new ArrayList<String>();
		
		for(String tmpb : b)
		{
			boolean exist = false;
			for(String tmpa : a)
			{
				if (tmpb.equals(tmpa))
				{
					exist = true;
					break;
				}
			}
			if (!exist)
			{
				rtn.add(tmpb);
			}
		}
		
		return rtn;
	}

	public static void main(String[] args) throws Exception
	{
//		String[] a =
//		{"A", "B", "C"};
//		String[] b =
//		{"B", "C", "D"};
//
//		System.out.println("not exist:" + findNotExist(a, b));
//		
//		System.out.println("---" + findNotExist(b, a));
//		
//		
//		System.out.println("new add:" + findNewAdd(a, b));
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date d = f.parse("2999-12-31 23:59:50:999");
		
		System.out.println(d.getTime());
		
		
		System.out.println(System.currentTimeMillis());
		
	}
}
