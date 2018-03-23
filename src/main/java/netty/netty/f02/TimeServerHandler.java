package netty.netty.f02;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Administrator
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		/*
		System.out.println("服务器读取到客户端请求...");
        ByteBuf buf=(ByteBuf) msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("the time server receive order: "+body);

        String curentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ByteBuf resp = Unpooled.copiedBuffer(curentTime.getBytes());
        ctx.writeAndFlush(resp);
        System.out.println("服务器做出了响应");
        */
        
     // do something msg
		    System.out.println("服务器读取到客户端请求...");
     		ByteBuf buf = (ByteBuf) msg;
     		byte[] data = new byte[buf.readableBytes()];
     		buf.readBytes(data);
     		String request = new String(data, "utf-8");
     		System.out.println("Server: " + request);
     		// 写给客户端
     		String response = "服务端反馈的时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
     		ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
     		
     		// 结束客户端的连接：
     		f.addListener(ChannelFutureListener.CLOSE);
     		
     		System.out.println("服务器做出了响应");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		
		ctx.close();
	}
	
}
