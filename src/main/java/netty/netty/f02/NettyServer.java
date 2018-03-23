package netty.netty.f02;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Administrator
 *
 */
public class NettyServer
{
	private int port;

	public NettyServer(int port)
	{
		super();
		this.port = port;
	}
	
	public void run() throws Exception
	{
		/**
		 * NioEventLoopGroup用来处理I/O操作的多线程事件循环器；
		 * 
		 * Netty提供了许多不同的EventLoopGroup的实现，用来处理不同的传输协议；
		 * 在这个例子中实现了一个服务端的应用，服务器线程组，用于网络事件的处理, 因些会有2个NioEventLoopGroup会被使用：
		 *     第一个经常被叫做'boss'，用于服务器接收客户端的连接；
		 *     第二个经常被叫做'worker'，用来处理socketChannel的网络读写；
		 *  一旦boss接收到连接，就会把信息注册到worker上；
		 *  如何知道多少个线程已经被使用，如何映射到已经创建的channels上都需要依赖EventLoopGroup的实现；
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try
		{
			/**
			 * ServerBootStrap是一个启动NIO服务的辅助启动类，可以在这个服务中直接使用Channel
			 */
			ServerBootstrap b = new ServerBootstrap();
			
			/**
			 * 这一句是必须的，如果没有设置group，将会报java.lang.IllegalStateException：group not set异常
			 */
			b = b.group(bossGroup, workerGroup) 
			/**
			 * ServerSocketChannel以NIO的Selector为基础进行实现的，用来接收新的连接；		
			 */
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)       // 设置指定的通道的配置参数：
			.option(ChannelOption.SO_SNDBUF, 32 * 1024)  // 设置发送数据缓冲大小
			.option(ChannelOption.SO_RCVBUF, 32 * 1024)  // 设置接受数据缓冲大小
			/**
			 * 这里的事件处理类经常被用来处理一个最近的已经接收的Channel；
			 * ChannelInitializer是一个特殊的处理类，它的目的是帮助使用者配置一个新的Channel；
			 * 当程序变得复杂时，可以增加更多的处理类到pipeline上；
			 */
			.childHandler(new ChannelInitializer<SocketChannel>()
			{
				@Override
				protected void initChannel(SocketChannel ch) throws Exception
				{
					ch.pipeline()
//					.addLast(new DiscardServerHandler());
//					.addLast(new ResponseServerHandler());
					.addLast(new TimeServerHandler());
					
				}
			}); 
			
			
			/**
			 * option()是提供给NioServerSocketChannel用来接收进来的连接；
			 * childOption()是提供给由父管道ServerChannel接收的连接；
			 */
			//b.childOption(ChannelOption.SO_KEEPALIVE, true); 
			
			 
			
			// 服务器启动后，绑定监听端口，同步等待成功，主要用于异步操作的通知回调，回调处理用到的childChannelHandler：
			ChannelFuture sync = b.bind(port).sync();
			
			// 这里会一直等待，直到socket被关闭：
			sync.channel().closeFuture().sync();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new NettyServer(6789).run();
	}
}
