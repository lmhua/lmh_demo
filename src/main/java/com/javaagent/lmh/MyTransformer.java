package com.javaagent.lmh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author Administrator
 *
 */
public class MyTransformer implements ClassFileTransformer
{
	public static final String classNumberReturns2 = System.getProperty("user.dir")+"/target/classes/com/javaagent/TransClass2.class".replaceAll("/", ".");

	public static byte[] getBytesFromFile(String fileName)
	{
		try
		{
			File file = new File(fileName);
			InputStream is = new FileInputStream(file);
			long length = file.length();
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
			{
				offset += numRead;
			}
			if (offset < bytes.length)
			{
				throw new IOException("Could not completely read file " + file.getName());
			}
			is.close();
			System.out.println("   bytes length: " + bytes.length);
			return bytes;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("error occurs in _ClassTransformer!" + e.getClass().getName());
			return null;
		}
	}

	public byte[] transform(ClassLoader l, String className, Class<?> c, ProtectionDomain pd,
			byte[] b) throws IllegalClassFormatException
	{
		className = className.replace("/", ".");
		System.out.println("*****" + className);
		
		if (!className.endsWith("TransClass1"))
		{
			return null;
		}
		return getBytesFromFile(classNumberReturns2);
	}
	
	public static void main(String[] args)
	{
//		System.out.println(MyTransformer.class.getResource("") + "TransClass2.class");
		
//		System.out.println(System.getProperty("user.dir")+"/target/classes/com/javaagent/TransClass2.class");
		
		File file = new File(System.getProperty("user.dir")+"/target/classes/com/javaagent/TransClass2.class");
		System.out.println(file.exists());
	}
}
