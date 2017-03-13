
import java.net.*;
import java.util.ArrayList;
import java.io.*;


//websites with sample code
//http://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KKMultiServer.java
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KKMultiServerThread.java
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java




public class ChattyChatChatServer {

	//to keep track of all the names in the server
	//private ArrayList<String> names = new ArrayList();
	
	//to keep track of where to send chats
	private static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>(); 
	
	
	public static void main(String [] args) throws IOException {
		
		if (args.length != 1) {
            System.err.println("Usage: java ChattyChatChatServer <port number>");
            System.exit(1);
        }
		
		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) 
            {
            	new ChatThread(serverSocket.accept() ).start();
	            //new KKMultiServerThread(serverSocket.accept()).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }

		
	}//END main
	
	//class to run the threads for each user
	public static class ChatThread extends Thread
	{
		//private 
		private Socket socket = null;
		
		public ChatThread(Socket socket)
		{
			this.socket = socket;
		}
		
		public void run()
		{
			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		            BufferedReader in = new BufferedReader(
		                    new InputStreamReader(socket.getInputStream()));
					)
			{
				//code for processing the thread
				writers.add(out);
				String name = "anonymous";
				
				while(true)
				{
					String input = in.readLine();
					if (input == null)
					{
						return;
					}
					String [] checkInput = input.split(" ");
					
					//if the user wants to set a nickname
					if (checkInput[0].equals("/nick"))
					{
						name = checkInput[1];
					}
					else if (checkInput[0].equals("/dm") )
					{
						//protocol for sliding into dms
					}
					//break out of loop if user wants to quit
					else if (checkInput[0].equals("/quit"))
					{
						break;
					}
					for (PrintWriter writer: writers)
					{
						writer.println(name + ": " + input);
					}
				}
				
				
			}
			catch(IOException e) {
				e.printStackTrace();

			}
		}
		
	
	
	}//END class ChatThread

}


