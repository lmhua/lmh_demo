package com.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author Administrator
 *
 */
public class AgentLoader implements ClassFileTransformer
{

	public static void premain(String agentArgs, Instrumentation inst)
	{
		String remoteUrl = agentArgs;
		if (remoteUrl == null || remoteUrl.trim().length() == 0)
		{
			throw new RuntimeException("监听参数为空");
		}
		
		System.setProperty("bit-agent-url", remoteUrl);
		
		//添加监听器
		inst.addTransformer(new AgentLoader());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer)
			throws IllegalClassFormatException
	{
		
		return null;
	}

}
