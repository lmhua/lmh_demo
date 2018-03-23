package netty.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Administrator
 *
 */
public class NioClientDemo
{
	// 通道选择器
	private Selector selector;
	
	/**
	 * 获得socket通道，做一些初始化的工作
	 */
	public void initClient(String ip, int port) throws Exception
	{
		// 通道管理器
		this.selector = Selector.open();
		
		// 获得一个socket通道, 设置为非阻塞 
		SocketChannel client = SocketChannel.open();
		client.configureBlocking(false);
		
		client.connect(new InetSocketAddress(ip, port));
		// 通道与通道管理器绑定，并为该通道注册SelectionKey.OP_CONNECT事件
		client.register(this.selector, SelectionKey.OP_CONNECT);
	}
	
	/**
	 * 轮询的方式监听Selector上是否有需要处理的事件
	 */
	public void listen() throws Exception
	{
		// 轮询
		while(true)
		{
			// 多路利用Reactor模式，没有事件到达时，一直处理阻塞  
			selector.select(); 
			
			Iterator<SelectionKey> selectKeys = selector.selectedKeys().iterator();
			while (selectKeys.hasNext())
			{
				SelectionKey key = selectKeys.next();
				// 清除选择的key，防止得利选择
				selectKeys.remove();
				
				// 连接事件发生：
				if (key.isConnectable())
				{
					SocketChannel channel = (SocketChannel) key.channel();
					// 如果正在连接，则完成连接
					if (channel.isConnectionPending())
					{
						channel.finishConnect();
					}
					channel.configureBlocking(false);
					
					// 给服务端发送消息
					channel.write(ByteBuffer.wrap("Client发送消息到Server[first msg]".getBytes()));
					
					// 和服务端连接成功后，为了可以接收服务端响应的消息，需要给通道注册读的权限  
					channel.register(this.selector, SelectionKey.OP_READ);
				}
				// 读取服务端发送过来的消息
				else if (key.isReadable())
				{
					handler(key);
				}
			}
		}
	}
	
	public void handler(SelectionKey key) throws IOException
	{
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024); // 1kb
		int data = channel.read(buffer); // 1kb
		if (data > 0)
		{
			String recvMsg = new String(buffer.array()).trim();
			System.out.println("接收到服务端响应：" + recvMsg);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		NioClientDemo client = new NioClientDemo();
		client.initClient("127.0.0.1", 8888);
		client.listen();
	}
}
