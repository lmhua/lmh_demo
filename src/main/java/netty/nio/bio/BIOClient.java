package netty.nio.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Administrator
 *
 */
public class BIOClient
{
	public static void main(String[] args)
	{
		int port= 8080;
		Socket socket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		try
		{
			socket = new Socket("127.0.0.1", port);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
			
			while(true)
			{
				Scanner scanner = new Scanner(System.in);
				String command = scanner.next();
				if ("bye".equals(command))
				{
					break;
				}
				
				writer.println(command);
				
				String response = reader.readLine();
				System.out.println(response);
				
			}
		}
		catch (Exception e)
		{
		}
	}
}
