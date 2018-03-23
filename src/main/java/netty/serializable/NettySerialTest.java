package netty.serializable;

import java.nio.ByteBuffer;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Administrator
 *
 */
public class NettySerialTest
{
	public static void main(String[] args)
	{
		// 创建缓冲区
		ByteBuf buffer = Unpooled.buffer(4);
		// 序列化：
		buffer.writeInt(6);
		byte[] b = buffer.array();
		System.out.println(Arrays.toString(b));

		// 反序列化:
		
		ByteBuf readBytes = buffer.readBytes(b);
		ByteBuffer bb = readBytes.nioBuffer();
		System.out.println(bb.getInt());
		//byte[] result = new byte[readBytes.readableBytes()];
	    //System.out.println(Arrays.toString(result)); 
		
	}
}
