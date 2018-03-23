package com.print;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 

@Controller
@RequestMapping("/printer")
public class PrinteController
{

	@RequestMapping("/test")
	@ResponseBody
	public String test()
	{
		return "test....";
	}
	
	@RequestMapping("/print")
	@ResponseBody
	public String print()
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
				return "------打印机连接失败，请检查网络是否正常......------";
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
			itemInfo.setPurchaseName("豆角茄子");
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
			
			 
			
 			byte[] consumer = PrinterCmdUtils.print2Consumer(saleBill, itemList);
			out.write(consumer);
			/*---- 消费清单打印   end------*/
			
			return "打印完成";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			return "打印失败";
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
