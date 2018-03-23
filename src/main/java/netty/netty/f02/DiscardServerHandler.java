package netty.netty.f02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 服务端处理通道:
 *   这是一个丢弃服务的处理方式，可以运行后通过telnet发送消息；只打印请求内容，不对请求进行任何响应
 *
 */
public class DiscardServerHandler extends ChannelHandlerAdapter
{

	/**
	 * ChannelHandler提供了很多事件处理的方法，根据需要自行实现相应方法；
	 * 覆盖channelRead()事务处理方法，每当从客户端接收到新的数据，这个方法会在接收到数据时调用
	 * 该示例中，收到的消息体实例时：ByteBuf
	 * @param msg：接收到的消息
	*/
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		try
		{
			ByteBuf in = (ByteBuf)msg;
//		    while(in.isReadable())
//		    {
//		    	System.out.println((char)in.readByte());
//		    	System.out.flush();
//		    }
		    
		    // 这一句效果和上面注释效果都是打印输入的字符串：
		    System.out.println(in.toString(CharsetUtil.US_ASCII));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			/**
			 * ByteBuf是一个引用计数对象，这个对象必须显示的调用release()来释放；
			 * 此处处理器的职责是释放所有传递到处理器的引用计数对象；
			 */
			//ReferenceCountUtil.release(msg);
		}
	}

	/**
	 * 发生异常后关闭连接
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}

	
}
