package com.javaagent.lmh;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * java内置的http server
 *
 */
public class JavaHttpServer
{
	public void startServer()
	{
		InetSocketAddress addr = new InetSocketAddress(9876);
		HttpServerHandler handler = new HttpServerHandler();
		try
		{
			HttpServer server = HttpServer.create(addr, 0);
			server.setExecutor(Executors.newCachedThreadPool());
			server.createContext("/server", handler);
			server.start();

			System.out.println("java inner server is running on 9876 port");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new JavaHttpServer().startServer();
	}

	private class HttpServerHandler implements HttpHandler
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/html;charset=utf-8");
			exchange.sendResponseHeaders(200, 0);
			String style="'border-top: silver 1px solid; border-right: silver 1px solid; border-collapse: collapse; border-bottom: silver 1px solid; padding-bottom: 3px; padding-top: 3px; padding-left: 3px; margin: 0px; border-left: silver 1px solid; padding-right: 3px'";
			String str = "<h3>hello world</h3><br><table><tr><td style=" + style+ ">first column</td><td style=" + style+ ">second column</td></tr></table>";
			OutputStream out = exchange.getResponseBody();
			out.write(str.getBytes());
			out.flush();
			
			exchange.close();
		}
	}
}
