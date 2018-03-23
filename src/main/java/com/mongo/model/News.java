package com.mongo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "news")
public class News implements Serializable
{
	private static final long serialVersionUID = -5880890231790535891L;

	@Field("id")
	private String id;

	/**
	 * 标题
	 */
	@Field("title")
	private String title;

	/**
	 * 新闻地址
	 */
	@Field("url")
	private String url;

	/**
	 * 发布时间
	 */
	@Field("ptimes")
	private String publishTimeStr;

	/**
	 * 发布时间
	 */
	@Field("ptime")
	private long publishTime;

	/**
	 * 发布时间
	 */
	@Field("pdate")
	private Date publishDate;



	/**
	 * 出版媒体
	 */
	@Field("pmedia")
	private String publishMedia;



	/**
	 * 指向区域
	 */
	@Field("area")
	private String area;


	/**
	 * 信息库key
	 */
	@Field("ckey")
	private List<String> classKey;


	/**
	 * 正负面
	 */
	@Field("eval")
	private Integer evaluate;


	/**
	 * 媒体性质 1、媒体 2、口碑 100、境外中文网站
	 */
	@Field("mpro")
	private Integer mediaProperty;


	/**
	 * 媒体种类 1、网站 2、论坛 3、博客 4、微博 6、报纸 7、视频 8、搜索引擎 9、广播电视台 10、电视台 11、杂志
	 */
	@Field("mtype")
	private Integer mediaType;


	/**
	 * 新闻星级
	 */
	@Field("level")
	private Integer level;



	// evaluate 新闻性质
	public static final Integer EVALUATE_POSITIVE = 1;
	public static final Integer EVALUATE_NEGATIVE = 0;

	public static final Map<Integer, String> evaluateMap = new HashMap<Integer, String>();
	static
	{
		evaluateMap.put(EVALUATE_POSITIVE, "正面");
		evaluateMap.put(EVALUATE_NEGATIVE, "负面");
	}

	// mediaProperty 媒体性质
	public static final Integer MEDIA_MT = 1;
	public static final Integer MEDIA_KB = 2;
	public static final Map<Integer, String> mediaPropertyMap = new HashMap<Integer, String>();
	static
	{
		mediaPropertyMap.put(MEDIA_MT, "媒体");
		mediaPropertyMap.put(MEDIA_KB, "口碑");
	}


	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}


	public String getArea()
	{
		return area;
	}


	public Integer getLevel()
	{
		return level;
	}

	public void setArea(String area)
	{
		this.area = area;
	}


	public void setLevel(Integer level)
	{
		this.level = level;
	}

	public List<String> getClassKey()
	{
		return classKey;
	}

	public Integer getEvaluate()
	{
		return evaluate;
	}

	public Integer getMediaProperty()
	{
		return mediaProperty;
	}


	public Integer getMediaType()
	{
		return mediaType;
	}


	public void setUrl(String url)
	{
		this.url = url;
	}


	public void setPublishMedia(String publishMedia)
	{
		this.publishMedia = publishMedia;
	}


	public void setEvaluate(Integer evaluate)
	{
		this.evaluate = evaluate;
	}


	public void setMediaProperty(Integer mediaProperty)
	{
		this.mediaProperty = mediaProperty;
	}


	public void setMediaType(Integer mediaType)
	{
		this.mediaType = mediaType;
	}



	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setClassKey(List<String> classKey)
	{
		this.classKey = classKey;
	}


	public void setPublishTimeStr(String publishTimeStr)
	{
		this.publishTimeStr = publishTimeStr;
	}

	public long getPublishTime()
	{
		return publishTime;
	}

	public void setPublishTime(long publishTime)
	{
		this.publishTime = publishTime;
	}


	public long getDay()
	{
		Date dataEnd = new Date();
		long day = (dataEnd.getTime() - this.publishTime) / (3600 * 24 * 1000);
		return day;
	}

	public long getHour()
	{
		Date dataEnd = new Date();
		long millisecond = (dataEnd.getTime() - this.publishTime) % (3600 * 24 * 1000);
		long hour = (millisecond / (1000 * 3600));
		return hour;
	}

	public long getMinute()
	{
		Date dataEnd = new Date();
		long millisecond = (dataEnd.getTime() - this.publishTime) % (3600 * 24 * 1000);
		long minute = (millisecond / (1000 * 60));
		return minute;
	}



	public String getEvaluateStr()
	{
		return evaluateMap.get(evaluate);
	}

	public String getEvaluateStyle()
	{
		String evaluateStyle = evaluateMap.get(evaluate);
		if (evaluate != null)
		{
			if (evaluate == 1)
			{
				evaluateStyle = "<font color=\"blue\">" + evaluateStyle + "</font>";
			}
			else
			{
				evaluateStyle = "<font color=\"red\">" + evaluateStyle + "</font>";
			}
		}
		return evaluateStyle;
	}

	public String getMediaPropertyStr()
	{
		return mediaPropertyMap.get(mediaProperty);
	}


	public String getLevelStr()
	{
		String levelStr = "";
		if (level != null)
		{
			if (level == 2)
			{
				levelStr = "★";
			}
			else if (level == 3)
			{
				levelStr = "★☆";
			}
			else if (level == 4)
			{
				levelStr = "★★";
			}
			else if (level == 5)
			{
				levelStr = "★★☆";
			}
			else if (level == 6)
			{
				levelStr = "★★★";
			}
			else if (level == 7)
			{
				levelStr = "★★★☆";
			}
			else if (level == 8)
			{
				levelStr = "★★★★";
			}
			else if (level == 9)
			{
				levelStr = "★★★★☆";
			}
			else if (level == 10)
			{
				levelStr = "★★★★★";
			}
			else if (level == 11)
			{
				levelStr = "★★★★★☆";
			}
			else if (level == 12)
			{
				levelStr = "★★★★★★";
			}
			else
			{
				levelStr = "☆";
			}
		}
		else
		{
			levelStr = "☆";
		}
		return levelStr;
	}


	public String getLevelStyle()
	{
		String levelStr = "";
		if (level != null)
		{
			if (level == 2)
			{
				levelStr = "★";
			}
			else if (level == 3)
			{
				levelStr = "★☆";
			}
			else if (level == 4)
			{
				levelStr = "★★";
			}
			else if (level == 5)
			{
				levelStr = "★★☆";
			}
			else if (level == 6)
			{
				levelStr = "★★★";
			}
			else if (level == 7)
			{
				levelStr = "★★★☆";
			}
			else if (level == 8)
			{
				levelStr = "★★★★";
			}
			else if (level == 9)
			{
				levelStr = "★★★★☆";
			}
			else if (level == 10)
			{
				levelStr = "★★★★★";
			}
			else if (level == 11)
			{
				levelStr = "★★★★★☆";
			}
			else if (level == 12)
			{
				levelStr = "★★★★★★";
			}
			else
			{
				levelStr = "☆";
			}
		}
		else
		{
			levelStr = "☆";
		}
		if (evaluate != null)
		{
			if (evaluate == 1)
			{
				levelStr = "<span style=\"color:blue\">" + levelStr + "</span>";
			}
			else
			{
				levelStr = "<span style=\"color:red\">" + levelStr + "</span>";
			}
		}
		return levelStr;
	}

	public Date getPublishDate()
	{
		return publishDate;
	}

	public void setPublishDate(Date publishDate)
	{
		this.publishDate = publishDate;
	}
}
