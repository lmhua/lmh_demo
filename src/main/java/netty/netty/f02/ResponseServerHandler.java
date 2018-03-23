package netty.netty.f02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * 
 *
 */
public class ResponseServerHandler extends ChannelHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		ByteBuf in = (ByteBuf)msg;
		System.out.println(in.toString(CharsetUtil.US_ASCII));
		
		ctx.write("server response:" + msg);
		//ctx.writeAndFlush("server response:" + msg);
		
		// 这里不需要显示的释放，因为在进入的时候，netty已经自动释放；
		//ReferenceCountUtil.release(msg);
	}
	
	
	/**
	 * ctx.write(object)方法不会使消息写入通道上，它被缓冲在内部，需要调用ctx.flush()方法把缓冲区数据强行输出；
	 * 或者可以使用在channelRead方法调用更简洁的writeFlush(msg)达到相同目的；
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		ctx.flush();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		// 抛出异常时，关闭连接
		ctx.close();
	}

}
