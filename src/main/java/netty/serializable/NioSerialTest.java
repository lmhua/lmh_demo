package netty.serializable;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Administrator
 *
 */
public class NioSerialTest
{
	public static void serial(int b)
	{
		// 序列化：4
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(b);
		byte[] tmp = buffer.array();
		System.out.println(Arrays.toString(tmp));

		// 反序列化：
		ByteBuffer buffer2 = ByteBuffer.wrap(tmp);
		System.out.println(buffer2.getInt());
	}
	
	public static void main(String[] args)
	{
		serial(8);
	}
}
