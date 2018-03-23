package netty.netty.f02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Administrator
 *
 */
public class NettTimeClient
{
	public static void main(String[] args)
	{
		String host = "127.0.0.1";
		int port = 6789;
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try
		{
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			
			b.channel(NioSocketChannel.class);
			
			// 客户端socketChannel没有父channel的概念，不需要调用childOption();
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>()
			{
				@Override
				protected void initChannel(SocketChannel ch) throws Exception
				{
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
			
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			workerGroup.shutdownGracefully();
		}
	}
}
