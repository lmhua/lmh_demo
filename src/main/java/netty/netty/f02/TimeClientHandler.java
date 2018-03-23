package netty.netty.f02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author Administrator
 *
 */
public class TimeClientHandler extends ChannelHandlerAdapter
{
	private ByteBuf firstMessage;

	public TimeClientHandler()
	{
		byte[] req ="QUERY TIME ORDER".getBytes();
		this.firstMessage = Unpooled.buffer(req.length);
		this.firstMessage.writeBytes(req);
	}

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		ctx.writeAndFlush(firstMessage);
		System.out.println("客户端active");
	}

	/**
	 * 开始处理的时候触发
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception
	{
		//firstMessage = ctx.alloc().buffer(4);
	}

	/**
	 * 处理结束的时候触发
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
	{
		firstMessage.release();
		firstMessage = null;
	}

	/**
	 * 处理接收数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		try
		{
			ByteBuf buf = (ByteBuf)msg;
			byte[] req = new byte[buf.readableBytes()];
			
			System.out.println("Now is :" + new String(req, "UTF-8"));
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ReferenceCountUtil.release(msg);
		}
		
		
		/**
		buf.readBytes(req);
		 * 处理时必须检查ByteBuf是否有足够的值，否则Netty会重复调用channelRead()，直到更多数据达到4个字节被积累；
		 */
//		if (buf.readableBytes() >= 4)
//		{
//			long currentTime = (buf.readInt()) * 1000L;
//			System.out.println(new Date(currentTime));
//			
//			ctx.close();
//		}
	}

	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		ctx.flush();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		
		ctx.close();
	}
	
	
	
}
