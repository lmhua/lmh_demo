package com.javaagent.lmh;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

/**
 * @author Administrator
 *
 */
public class MyAgent implements ClassFileTransformer
{
	/**
	 * 该方法在main方法之前运行，与main方法运行在同一上jvm中，并被同一个System ClassLoader装载，
	 * 被统一的案例策略和上下文管理
	 */
    public static void premain(String agentOps, Instrumentation inst)
	{
    	System.out.println("===  premain  metthod(2 args)  executs, agentOps=[" + agentOps + "]");
    	inst.addTransformer(new MyAgent());
	}

    /**
     *  如果不存在premain(String agentOps, Instrumentation inst)方法，则会执行premain(String agentOps)
     */
    public static void premain(String agentOps)
    {
    	System.out.println("===  premain  metthod(1 args)  executs, agentOps=[" + agentOps + "]");
    }

	/**
	 * 所有的类加载之前执行该方法
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer)
			throws IllegalClassFormatException
	{
		 // 修改字节码
		 if (className.startsWith("com.javaagent"))
		 {
			 return new byte[0];
		 }
		 
		 // 返回修改后的字节码
		 
		// 如果返回null或者抛异常，则加载的是原来的字节码 
		return null;
	}
    
 
	public int add(int a, int b)
	{
	    return a+b;	
	}
	
	public static void main(String[] args)
	{
		try
		{
			Class c = Class.forName("com.javaagent.MyAgent");
			Object instance = c.newInstance();
			Method m = c.getDeclaredMethod("add", int.class, int.class);
			Object r = m.invoke(instance, 1,2);
			System.out.println(r);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
}
