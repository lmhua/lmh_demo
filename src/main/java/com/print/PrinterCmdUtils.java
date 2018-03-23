package com.print;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * the printer commands kits
 * @author lvmingnua
 * attention: send the newline command before cut paper;
 * "gbk" supports the tranditional chinese, "gb2312" supports the simple chinese characters
 */
public class PrinterCmdUtils
{
	public static final byte ESC = 27;// 换码 0x1B
	// public static final byte FS = 28;// 文本分隔符
	public static final byte GS = 29;// 组分隔符
	// public static final byte DLE = 16;// 数据连接换码
	// public static final byte EOT = 4;// 传输结束
	// public static final byte ENQ = 5;// 询问字符
	// public static final byte SP = 32;// 空格
	// public static final byte HT = 9;// 横向列表
	public static final byte LF = 10;// 打印并换行（水平定位） 0x0A
	// public static final byte CR = 13;// 归位键
	// public static final byte FF = 12;// 走纸控制（打印并回到标准模式（在页模式下） ）
	public static final byte CAN = 24;// 作废（页模式下取消打印数据 ）

	/**
	 * init printer commands
	 */
	public static byte[] init_printer()
	{
		byte[] result = new byte[2];
		result[0] = ESC;
		result[1] = 64; // 0x40
		return result;
	}

	/**
	 * printer status commands
	 */
	public static byte[] printerStatus()
	{
		byte[] result = new byte[3];
		result[0] = 0x10;
		result[1] = 0x04;
		result[2] = 0x01;
		return result;
	}
	
	/**
	 * new line commands
	 */
	public static byte[] newLine(int lineNum)
	{
		byte[] result = new byte[lineNum];
		for (int i = 0; i < lineNum; i++)
		{
			result[i] = LF;
		}

		return result;
	}


	/**
	 * draw the underline with one dot width 
	 */
	public static byte[] underlineWithOneDotWidthOn()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 45; // 0x2D
		result[2] = 1;
		return result;
	}

	/**
	 * draw the underline with two dot width 
	 */
	public static byte[] underlineWithTwoDotWidthOn()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 45; // 0x2D
		result[2] = 2;
		return result;
	}

	/**
	 * set the underline off
	 */
	public static byte[] underlineOff()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 45; // 0x2D
		result[2] = 0;
		return result;
	}

	/**
	 * set font bold on
	 */ 
	public static byte[] boldOn()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 69; // 0x45
		result[2] = 0xF; // 0 <= n(十进制) <= 255, n的最低位为0时，取消加粗模式； n的最低位为1时，选择加粗模式
		return result;
	}

	/**
	 * set font bold off
	 */
	public static byte[] boldOff()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 69; // 0x45
		result[2] = 0;
		return result;
	}

	/**
	 * align left 
	 */
	public static byte[] alignLeft()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 97; // 0x61
		result[2] = 0;
		return result;
	}

	/**
	 * align center
	 */
	public static byte[] alignCenter()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 97; // 0x61
		result[2] = 1;
		return result;
	}

	/**
	 * align right
	 */
	public static byte[] alignRight()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 97; // 0x61
		result[2] = 2;
		return result;
	}


	/**
	 * tab to right at horizontal direction 
	 */
	public static byte[] set_Tab_position(byte col)
	{
		byte[] result = new byte[4];
		result[0] = ESC;
		result[1] = 68; // 0x44 //result[1] = 92; 0x5C
		result[2] = col;
		result[3] = 0;
		return result;
	}

	/**
	 * set the font size big
	 */
	public static byte[] fontSizeSetBig(int num)
	{
		byte realSize = 0;
		switch (num)
		{
			case 1:
				realSize = 0;
				break;
			case 2:
				realSize = 17;
				break;
			case 3:
				realSize = 34;
				break;
			case 4:
				realSize = 51;
				break;
			case 5:
				realSize = 68;
				break;
			case 6:
				realSize = 85;
				break;
			case 7:
				realSize = 102;
				break;
			case 8:
				realSize = 119;
				break;
		}
		byte[] result = new byte[3];
		result[0] = 29; // 0x1D
		result[1] = 33; // 0x21
		result[2] = realSize; //
		return result;
	}


	/**
	 * restore the default font size
	 */
	public static byte[] fontSizeSetSmall()
	{
		byte[] result = new byte[3];
		result[0] = ESC;
		result[1] = 33;
		result[2] = 0;
		return result;
	}


	/**
	 * feed paper and cut
	 */
	public static byte[] feedPaperCutAll()
	{
		byte[] result = new byte[4];
		result[0] = GS;
		result[1] = 86; // 0x56
		result[2] = 65;
		result[3] = 0;
		return result;
	}

	/**
	 * feed paper and cut partital
	 */
	public static byte[] feedPaperCutPartial()
	{
		byte[] result = new byte[4];
		result[0] = GS;
		result[1] = 86;
		result[2] = 66;
		result[3] = 0;
		return result;
	}

	/**
	 * merge byte array
	 */
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2)
	{
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/**
	 * merge byte array
	 */
	public static byte[] byteMerger(byte[][] byteList)
	{

		int length = 0;
		for (int i = 0; i < byteList.length; i++)
		{
			length += byteList[i].length;
		}
		byte[] result = new byte[length];

		int index = 0;
		for (int i = 0; i < byteList.length; i++)
		{
			byte[] nowByte = byteList[i];
			for (int k = 0; k < byteList[i].length; k++)
			{
				result[index] = nowByte[k];
				index++;
			}
		}

		return result;
	}

	public static String getCurrentTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 *  消费清单打印
	 */
	public static byte[] print2Consumer(ScPresale saleBill, List<ScPresaleInfo> itemList) throws Exception
	{
		byte[] blankline = PrinterCmdUtils.newLine(1);  
		byte[] blankline2 = PrinterCmdUtils.newLine(2);  
		byte[] center = PrinterCmdUtils.alignCenter();
		byte[] unbold = PrinterCmdUtils.boldOff();
		String shopName = (saleBill.getShopName() == null) ? "" : (saleBill.getShopName());
		byte[] titleBytes = shopName.getBytes("gbk");
		byte[] bold = PrinterCmdUtils.boldOn();
		byte[] fontBig1 = PrinterCmdUtils.fontSizeSetBig(1);
		byte[] fontBig2 = PrinterCmdUtils.fontSizeSetBig(2);
		String facilityName = (saleBill.getFacilityName() == null) ? "" : (saleBill.getFacilityName());
		byte[] deskNum = facilityName.getBytes("gbk");
		byte[] fontSmall = PrinterCmdUtils.fontSizeSetSmall();
		byte[] creatTime = PrinterCmdUtils.getCurrentTime().getBytes("gbk");	
						
		byte[] left = PrinterCmdUtils.alignLeft();
		byte[] itemName = "商品名称        数量     单价     总价".getBytes("gbk");
		byte[] separator = "---------------------------------------".getBytes();
		
		byte[] right = PrinterCmdUtils.alignRight();
		Double shouldPay = (saleBill.getShouldPay() == null) ? 0.0 : saleBill.getShouldPay();
		byte[] sumPrice = ("合计："+shouldPay).getBytes("gbk"); 
		Double balance = (saleBill.getBalance() == null) ? 0.0 :saleBill.getBalance();
		Double disc = (saleBill.getDiscount() == null) ? 0.0 : saleBill.getDiscount();
		byte[] discount = ("优惠：" + (balance * (100-disc)/100) + "   实付：" + balance).getBytes("gbk");
		
		byte[] breakPartial = PrinterCmdUtils.feedPaperCutPartial();
		
		List<byte[]> list = new ArrayList<byte[]>();
		list.add(fontSmall); list.add(unbold); list.add(center); list.add(titleBytes); list.add(blankline2);
		list.add(bold); list.add(fontBig2); list.add(deskNum); list.add(blankline2); 
		list.add(fontSmall); list.add(creatTime); list.add(blankline2);
		list.add(bold); list.add(fontBig1);list.add(center);list.add(itemName);list.add(blankline);
		list.add(separator);list.add(blankline);list.add(fontSmall); list.add(left);
		
		ScPresaleInfo tmp = null;
		StringBuilder buffer = null;
		for(int i=0; i < itemList.size(); i++)
		{ 
			tmp = itemList.get(i);
			buffer = new StringBuilder();
			buffer.append("     "); //5个空格
			
			// 商品名,  打印时按最大8个汉字来填充空格
			String purName = tmp.getPurchaseName();
			buffer.append(purName);
			if (purName.length() < 8)
			{
				int first = (8 - purName.length()) * 2; // 1个汉字占2字节
				for(int j=0; j<first; j++)
				{
					buffer.append(" ");
				}
			}
			
			// 数量, 打印时按最大9个数字来填充
			int pnum = tmp.getPurchaseNum();
			buffer.append(pnum);
			if((pnum+"").length() < 9)
			{
				int second = 9 - (pnum+"").length();  //数字占1个字节
				for(int j=0; j<second; j++)
				{
					buffer.append(" ");
				}
			}
			
			// 单价, 打印时按最大9个数字来填充
			Double uprice = tmp.getUnitPrice();
			buffer.append(uprice);
			if((uprice+"").length() < 9)
			{
				int second = 9 - (uprice+"").length();  //数字占1个字节
				for(int j=0; j<second; j++)
				{
					buffer.append(" ");
				}
			}
			
			//总价
			buffer.append(tmp.getActualPayment());
			
			list.add(buffer.toString().getBytes("gbk"));
			list.add(blankline);
			list.add("     ---------------------------------------".getBytes());
			list.add(blankline);
		}
		
		list.add(blankline2); list.add(right);list.add(bold);list.add(sumPrice);list.add(blankline2);
		list.add(discount);list.add(blankline2);list.add(breakPartial);
		
         
		byte[][] cmds =  list.toArray(new byte[list.size()][]);
		return byteMerger(cmds);
	}
	
	/**
	 *  后厨打印(分开单独打每个菜品)
	 */
	public static byte[] print2Kitchen(String desk, ScPresaleInfo itemInfo) throws UnsupportedEncodingException
	{
		byte[] init = PrinterCmdUtils.init_printer();
		byte[] blankline = PrinterCmdUtils.newLine(1);  
		byte[] blankline2 = PrinterCmdUtils.newLine(2);  
		byte[] center = PrinterCmdUtils.alignCenter();
		byte[] unbold = PrinterCmdUtils.boldOff();
		byte[] bold = PrinterCmdUtils.boldOn();
		byte[] fontBig1 = PrinterCmdUtils.fontSizeSetBig(1);
		byte[] fontBig2 = PrinterCmdUtils.fontSizeSetBig(2);
		desk = (desk == null) ? "" : desk;
		byte[] deskNum = desk.getBytes("gbk");
		byte[] fontSmall = PrinterCmdUtils.fontSizeSetSmall();
		byte[] creatTime = PrinterCmdUtils.getCurrentTime().getBytes("gbk");	
//		byte[] left = PrinterCmdUtils.alignLeft();
		byte[] itemTitle = "  商品名称              数量  ".getBytes("gbk");
		byte[] separator = "--------------------------------------------".getBytes();
		String purName = (itemInfo.getPurchaseName() == null) ? "" : (itemInfo.getPurchaseName());
		Integer purNum = (itemInfo.getPurchaseNum() == null) ? 0 : (itemInfo.getPurchaseNum());
				
		byte[] item =  (purName + "     " + purNum + "份").getBytes("gbk");  
		 
		byte[] breakPartial = PrinterCmdUtils.feedPaperCutPartial();

		byte[][] cmds =
	    {
		     init, center, bold, fontBig2, deskNum, blankline2,
		     fontSmall, unbold, creatTime,blankline2,
		     bold, fontBig1, itemTitle, blankline2,
		     unbold, fontSmall, separator, blankline,
		     bold, fontBig2, item, blankline,
		     unbold, fontSmall, separator, blankline2,
		     blankline2,breakPartial
	    };
		
		return byteMerger(cmds);
	}
	
	public static void main(String[] args)
	{
		Socket printer = null;
		OutputStream out = null;
		try
		{
			printer = new Socket();
			SocketAddress address = new InetSocketAddress("192.168.11.95", 9100);
			try
			{
				printer.connect(address, 5 * 1000); // 设置超时时间为5秒，提高用户体验
			}
			catch (Exception e)
			{
				System.err.println("------打印机连接失败，请检查网络是否正常......------");
				return;
			}

			if (printer != null)
			{
				out = printer.getOutputStream();
			}
			
			/*---- 后厨打印   start------*/
//			ScPresaleInfo itemInfo = new ScPresaleInfo();
//			itemInfo.setPurchaseName("冬瓜排骨烫");
//			itemInfo.setPurchaseNum(new Integer(2));
//			 
//			byte[] item1 = print2Kitchen("百合" + "  ", itemInfo);
//			out.write(item1);
//			
//			itemInfo = new ScPresaleInfo();
//			itemInfo.setPurchaseName("干煽鱼" + "  ");
//			itemInfo.setPurchaseNum(new Integer(1));
//			byte[] item2 = print2Kitchen("饭" ,itemInfo);
//			out.write(item2);
			/*---- 后厨打印   end------*/
			
			/*---- 消费清单打印   start------*/
			String title = "刘胖子家常菜(消费单)";
			ScPresale saleBill = new ScPresale();
			saleBill.setShopName(title);
			saleBill.setFacilityName("3号桌");
			saleBill.setFacilityId(new Integer(24));
			saleBill.setShouldPay(new Double(28));
			saleBill.setBalance(new Double(28));
			saleBill.setDiscount(new Integer(100));
			
			List<ScPresaleInfo> itemList = new ArrayList<ScPresaleInfo>();
			ScPresaleInfo itemInfo = new ScPresaleInfo(); 
			
//			itemInfo = new ScPresaleInfo();
//			itemInfo.setPurchaseName("豆角肉沫茄子");
//			itemInfo.setPurchaseNum(new Integer(666666));
//			itemInfo.setUnitPrice(new Double(666666));
//			itemInfo.setActualPayment(new Double(666666));
//			itemList.add(itemInfo);
//			
//			itemInfo = new ScPresaleInfo();
//			itemInfo.setPurchaseName("豆角肉茄子");
//			itemInfo.setPurchaseNum(new Integer(55555));
//			itemInfo.setUnitPrice(new Double(55555));
//			itemInfo.setActualPayment(new Double(55555));
//			itemList.add(itemInfo);
//			
//			itemInfo = new ScPresaleInfo();
//			itemInfo.setPurchaseName("豆角茄子");
//			itemInfo.setPurchaseNum(new Integer(4444));
//			itemInfo.setUnitPrice(new Double(4444));
//			itemInfo.setActualPayment(new Double(4444));
//			itemList.add(itemInfo);

			itemInfo = new ScPresaleInfo();
			itemInfo.setPurchaseName("豆角子");
			itemInfo.setPurchaseNum(new Integer(333));
			itemInfo.setUnitPrice(new Double(333));
			itemInfo.setActualPayment(new Double(333));
			itemList.add(itemInfo);
			
			itemInfo = new ScPresaleInfo();
			itemInfo.setPurchaseName("干鱼");
			itemInfo.setPurchaseNum(new Integer(22));
			itemInfo.setUnitPrice(new Double(22));
			itemInfo.setActualPayment(new Double(22));
			itemList.add(itemInfo);
			
			itemInfo = new ScPresaleInfo();
			itemInfo.setPurchaseName("烫");
			itemInfo.setPurchaseNum(new Integer(1));
			itemInfo.setUnitPrice(new Double(1.00));
			itemInfo.setActualPayment(new Double(1));
			itemList.add(itemInfo);
			
			 
			
 			byte[] consumer = print2Consumer(saleBill, itemList);
			out.write(consumer);
			/*---- 消费清单打印   end------*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (printer != null)
				{
					printer.close();
				}
				if (out != null)
				{
					out.close();
				}
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}
	 
}
