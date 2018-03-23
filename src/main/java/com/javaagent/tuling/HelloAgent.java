package com.javaagent.tuling;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/**
 * Created by Tommy on 2018/3/6.
 */
public class HelloAgent
{

	public static void premain(String arg, Instrumentation instrumentation)
	{
		System.out.println("装载成功 方法 premain 参数:" + arg);
		
		instrumentation.addTransformer(new ClassFileTransformer()
		{
			@Override
			public byte[] transform(ClassLoader loader, String className,
					Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
					byte[] classfileBuffer) throws IllegalClassFormatException
			{
				String cname = "com.tuling.demo.test.UserServiceImpl";
				if (className.replaceAll("/", ".").equals(cname))
				{
					ClassPool pool = new ClassPool();
					pool.insertClassPath(new LoaderClassPath(loader));
					try
					{
						CtClass ctl = pool.get(cname);
						CtMethod mehod = ctl.getDeclaredMethod("getUser");
						mehod.insertBefore("System.out.println(System.currentTimeMillis());");
						mehod.insertAfter("System.out.println(System.currentTimeMillis());");
						
						// 返回新的字节码进行jvm运行时的装载
						return ctl.toBytecode();
					}
					catch (NotFoundException e)
					{
						e.printStackTrace();
					}
					catch (CannotCompileException e)
					{
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}

				// 返回null时，还是装载原来的字节码
				return null;
			}
		});
	}
}

