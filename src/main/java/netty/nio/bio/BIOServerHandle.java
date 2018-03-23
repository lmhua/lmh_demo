package netty.nio.bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Administrator
 *
 */
public class BIOServerHandle implements Runnable
{
    private Socket socket;
    
	public BIOServerHandle(Socket socket)
	{
		super();
		this.socket = socket;
	}

	@Override
	public void run()
	{	
		BufferedReader reader = null;
		PrintWriter writer = null;
		try
		{
			InputStream is = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			
			writer = new PrintWriter(socket.getOutputStream(), true);
			//
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}	

}
