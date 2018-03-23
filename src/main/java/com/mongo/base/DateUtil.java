package com.mongo.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateUtil
{
	/**
	 * 生成随机时间
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date randomDate(String beginDate, String endDate)
	{
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse(beginDate);// 构造开始日期
			Date end = format.parse(endDate);// 构造结束日期
			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
			if (start.getTime() >= end.getTime())
			{
				return null;
			}
			long date = random(start.getTime(), end.getTime());
			return new Date(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static long random(long begin, long end)
	{
		long rtn = begin + (long) (Math.random() * (end - begin));
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
		if (rtn == begin || rtn == end)
		{
			return random(begin, end);
		}
		return rtn;
	}

	@Test
	public void testDate()
	{
		// Date randomDate=randomDate("2014-01-01","2014-05-11");
		// System.out.println(randomDate);
		Integer[] evalArr =
		{1, 0};
		int evalNum = (int) (Math.random() * evalArr.length);// 产生0-strs.length的整数随机数
		System.out.println(evalArr.length);
		System.out.println(evalNum);
	}

	public static String formatDateTime(Date date, String format)
	{
		if (date == null)
			return "";
		if (format == null)
			return date.toString();
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String formatY0M0D(Date date)
	{
		return date == null ? "" : formatDateTime(date, "yyyyMMdd");
	}

	public static String formatYMD(Date date)
	{
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd");
	}

	public static String formatYMDHM(Date date)
	{
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm");
	}

	public static String formatDateTimeByDate(Date date)
	{
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatTimeStampByDate(Date date)
	{
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String formatTimeStampSByDate(Date date)
	{
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss.S");
	}
}
