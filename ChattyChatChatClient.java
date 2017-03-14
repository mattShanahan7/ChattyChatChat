
import java.net.*;
import java.io.*;


public class ChattyChatChatClient {

	public static void main(String [] args) throws IOException {

		if (args.length != 2) {
			System.err.println(
					"Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		ChattyChatChatClient client = new ChattyChatChatClient(hostName, portNumber);
		client.runClient();

	} //end main

	String hostName;
	int portNumber;
	static PrintWriter out;
	static BufferedReader in;
	Socket chatSocket;
	static BufferedReader stdIn;

	public ChattyChatChatClient(String hostName, int portNumber)
	{
		this.hostName = hostName;
		this.portNumber = portNumber;

	}

	private void runClient()
	{

		try
		{

			this.chatSocket = new Socket(hostName, portNumber);
			this.out = new PrintWriter(chatSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader (chatSocket.getInputStream()) );
			this.stdIn = new BufferedReader(new InputStreamReader(System.in));

			KeyboardThread kThread = new KeyboardThread();
			ServerThread sThread = new ServerThread();

			kThread.start();
			sThread.start();



			/*if((userInput = stdIn.readLine()) != null)
				{
					//userInput = stdIn.readLine();
					out.println(userInput);
				}
				if((fromServer = in.readLine()) != null)
				{
					System.out.println(fromServer);
				}*/

			//print out chats from other users

		}
		catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " +
					hostName);
			System.exit(1);
		}

	}

	public static class ServerThread extends Thread
	{
		public void run()
		{
			String fromServer;
			try 
			{
				while(true)
				{
					if((fromServer = in.readLine()) != null)
					{
						
						if (fromServer.equals("/quit"))
						{
							System.out.println("Leaving Chat...");
							break;
						}
						
						System.out.println(fromServer);
					}
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class KeyboardThread extends Thread
	{
		public void run()
		{
			String userInput;
			try 
			{
				while (true)
				{
					
					if((userInput = stdIn.readLine()) != null)
					{
					
						if (userInput.equals("/quit"))
						{
							out.println(userInput);
							break;
						}
						else
						{
							out.println(userInput);
						}
						//userInput = stdIn.readLine();
					}
				}
			} 

			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
