package mongo_web_demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.print.ScPresale;

/**
 * @author Administrator
 *
 */
public class ListRemoveAllTest
{

	public static void main(String[] args)
	{
	  List<ScPresale> list = new ArrayList<ScPresale>();
	  List<ScPresale> list2 = new ArrayList<ScPresale>();
	  
	   ScPresale obj1 = new ScPresale();
	   obj1.setBalance(123.0);
	   list.add(obj1);
	   
	   obj1 = new ScPresale();
	   obj1.setBalance(123.0);
	   list2.add(obj1);
	   
	   obj1 = new ScPresale();
	   obj1.setCashId(99);
	   list.add(obj1);
	   
	   obj1 = new ScPresale();
	   obj1.setCashId(99);
	   list2.add(obj1);
	   
	   
	   obj1 = new ScPresale();
	   obj1.setBusinessName("bname");
	   list2.add(obj1);
	   
	   System.out.println("-----------------------------");
	   System.out.println(list.size() + " ---- " + list2.size());
	   
	   boolean flag = list2.removeAll(list);
	   System.out.println("remove result:" + flag);
	   
	   System.out.println("-----------------------------");
	   
	   Iterator<ScPresale> iter = list.iterator();
	   while(iter.hasNext())
	   {
		   System.out.println(iter.next());
	   }
	   
	   
	   System.out.println("-----------------------------");
	   
	   
	   Iterator<ScPresale> iter2 = list2.iterator();
	   while(iter2.hasNext())
	   {
		   System.out.println(iter2.next());
	   }
	}
	
	
}
