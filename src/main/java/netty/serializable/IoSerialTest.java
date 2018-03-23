package netty.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * @author Administrator
 *
 */
public class IoSerialTest
{
	public static byte[] serial(int b) throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(int2bytes(b));

		byte[] result = out.toByteArray();

		System.out.println(Arrays.toString(result));

		return result;
	}

	public static void deSerial(byte[] b) throws Exception
	{
		ByteArrayInputStream in = new ByteArrayInputStream(b);

		byte[] arr = new byte[4];
		in.read(arr);
		System.out.println("id : " + bytes2int(arr));
	}

	// 大端字节序列
	public static byte[] int2bytes(int i)
	{

		// 先写高位，再写低位
		// 高->低 [00000000 00000000 00000000 00000100]
		// 步骤1：把最高位移到最右边，转换为byte
		// 步骤2：把第二高位移到最右边，转为byte
		// 步骤3：把第三高位移到最右边，转为byte
		// 步骤4：把最后一位移到最右边，转为byte
		byte[] bytes = new byte[4];
		// 8的右移结果[00000000 00000000 00000000 00000000] 转byte值为[00000000]
		bytes[0] = (byte) (i >> 3 * 8);
		// 8的右移结果[00000000 00000000 00000000 00000000] 转byte值为[00000000]
		bytes[1] = (byte) (i >> 2 * 8);
		// 8的右移结果[00000000 00000000 00000000 00000000] 转byte值为[00000000]
		bytes[2] = (byte) (i >> 1 * 8);
		// 8的右移结果[00000000 00000000 00000000 00000100] 转byte值为[00000100]
		bytes[3] = (byte) (i >> 0 * 8);
		return bytes;
	}

	// 大端字节反序列
	public static int bytes2int(byte[] bytes)
	{
		// [00000000]左移，转为int结果为[00000000 00000000 00000000 00000000]
		int b0 = (int) (bytes[0] << 3 * 8);
		// [00000000]左移，转为int结果为[00000000 00000000 00000000 00000000]
		int b1 = (int) (bytes[1] << 2 * 8);
		// [00000000]左移，转为int结果为[00000000 00000000 00000000 00000000]
		int b2 = (int) (bytes[2] << 1 * 8);
		// [00000100]左移，转为int结果为[00000000 00000000 00000000 00000100]
		int b3 = (int) (bytes[3] << 0 * 8);
		// 位或结果[00000000 00000000 00000000 00000100]
		return b0 | b1 | b2 | b3;
	}
	
	/**
	 * 
	 */
	public static void main(String[] args) throws Exception
	{
		int i = 8;
		byte[] b = serial(i);
		
		deSerial(b);
				
	}
}
