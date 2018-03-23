package netty.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;



/**
 * @author Administrator
 *
 */
public class NioSocketDemo
{
	/**
	 * 通道多路复用选择器， 所有客户端和服务端的连接都会创建一个SocketChannel注册到Selector上，然后Selector使用线程轮询检测所有注册的SocketChannel的状态，
	 *  根据不同状态执行不同操作；
	 *  NIO的本质就是避免原始TCP建立三次握手的操作，减少连接的开销；
	 */
	private Selector selector; 
	
	public void initServer(int port) throws Exception
	{
		// 创建 ServerSocketChannel通道，相对于传统的ServerSocket
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 设置为非阻塞模式
		serverChannel.configureBlocking(false);
		// 将通道对应的ServerSocket绑定端口：
		serverChannel.socket().bind(new InetSocketAddress(port));
		
		// 获得一个通道选择器:(管理者)
		this.selector = Selector.open();
		/**
		 * 将该通道选择器与该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，
		 *  注册事件后，当该事件到达时，selector.select()会返回，如果没有事件，则selector.select()会一直阻塞；
		 *  即：大门交给selecor看着，监听是否有accept事件；
		 */
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务端启动成功....");
		
		/**
		 * SelectionKey中定义的4件事：
		 *   OP_ACCEPT：接收连接就绪的事件，表示服务器监听到了客户端连接，可以接收这个连接了；
		 *   OP_CONNECT：连接就绪事件，表示客户端与服务端已经连接成功；
		 *   OP_READ：读就绪事件，表示通道中已经有可读数据，可以执行读操作了；
		 *   OP_WRITE：写就绪事件，表示已经可以向通道写数据了；
		 */
	}
	
	/**
	 * 采用轮询的方式监听Selector上是否有需要处理的事件，如果有，则进行处理
	 */
	public void listenSelector() throws IOException
	{
		// 轮询访问
		while (true)
		{
			//当注册的事件到达时，方法返回；否则，该方法会一直阻塞
			
			// 多路复用， Reactor模型
			this.selector.select();
			
			// 无论是否有读写事件，selector每隔1秒被唤醒1次：
			//this.selector.select(1);
			//this.selector.selectNow();  // 非阻塞，不管是否有通道就绪都立刻返回
			
			// 获得selector中选中的项的迭代器，选中的项为注册的事件
			Iterator<SelectionKey> iteratorKey = this.selector.selectedKeys().iterator();
			while (iteratorKey.hasNext())
			{
				final SelectionKey selectKey = iteratorKey.next();
				
				// 处理请求
				handler(selectKey);
				
				// 删除已选择的key，防止重复选择
				iteratorKey.remove();
			}
			
		}
	}
	
	/**
	 * 处理请求
	 */
	public void handler(SelectionKey selectKey) throws IOException
	{
		// 处理客户端连接请求事件
		if (selectKey.isAcceptable())
		{
			System.out.println("------新的客户端连接--------");
			ServerSocketChannel server = (ServerSocketChannel) selectKey.channel();
			
			// 完成该操作，意味着实现TCP三次握手
			SocketChannel channel = server.accept();
			// 设置为非阻塞模式
			channel.configureBlocking(false);
			// 完成和客户端的连接以后，为了可以接收到客户端的消息，需要给通道设置读的权限 
			channel.register(this.selector, SelectionKey.OP_READ);
		}
		// 处理读事件
		else if (selectKey.isReadable())
		{
			// 服务器可读取消息：得到事件发生的Socket通道
			SocketChannel channel = (SocketChannel) selectKey.channel();
			
			
			// 创建读取的缓冲区：
			ByteBuffer buffer = ByteBuffer.allocate(1024);  // 1kb
			
			int readData = channel.read(buffer);
			if (readData > 0)
			{
				//将缓冲区的数据转化为byte数组 ，再转化为string
				String msg = new String(buffer.array()).trim();
				System.out.println("服务端接收到的数据： " + msg);
				
				// 回写数据：
				ByteBuffer writeBackBuffer = ByteBuffer.wrap("receive data".getBytes());
				channel.write(writeBackBuffer); // 将消息发送给客户端
			}
			else
			{
				System.out.println("客户端已关闭。。。。。");
				
				// selectionKey对象失效，意味着selector再也不监听与该客户端相关的事件
				selectKey.cancel();
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		NioSocketDemo server = new NioSocketDemo();
		// 初始化服务端
		server.initServer(8888);
		// 服务端监听Selector事件：
		server.listenSelector();
	}
}
