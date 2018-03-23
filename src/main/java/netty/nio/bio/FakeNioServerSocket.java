package netty.nio.bio;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 *
 */
public class FakeNioServerSocket
{
	private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
	
	public static void main(String[] args)
	{
		int port = 8080;
		ServerSocket server = null;
		try
		{
			server = new ServerSocket(port);
			Socket socket = null;
			while (true)
			{
				socket = server.accept();
				executorService.execute(new BIOServerHandle(socket));
			}
		}
		catch (Exception e)
		{
		}
		
	}
}
