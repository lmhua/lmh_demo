package com.javaagent.lmh;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author Administrator
 *
 */
public class TransAgent 
{
    public static void premain(String agentArgs, Instrumentation inst) throws ClassNotFoundException, UnmodifiableClassException
	{
    	System.out.println("ececute premain method<2 args>..." + agentArgs);
    	inst.addTransformer(new MyTransformer());
	}
    
    public static void premain(String agentArgs) throws ClassNotFoundException, UnmodifiableClassException
	{
    	System.out.println("ececute premain method<1 args>..." + agentArgs);
	}
}
