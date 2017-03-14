
import java.net.*;
import java.util.ArrayList;
import java.io.*;


//websites with sample code
//http://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KKMultiServer.java
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KKMultiServerThread.java
//http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java

//hello


public class ChattyChatChatServer {

	//to keep track of all the names in the server
	private static ArrayList<String> names = new ArrayList();
	
	//to keep track of where to send chats
	private static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>(); 
	
	static int ID = 0;
	
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
            	new ChatThread(serverSocket.accept()).start();
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
		private BufferedReader in;
		private String name;
		private PrintWriter out;
		private int LocalID = ID++;
		
		public ChatThread(Socket socket)
		{
			this.socket = socket;
		}
		
		public void run()
		{
			
			
			
			
			
			try
			{
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				name = "anonymous " + LocalID;
				
				
				
				//code for processing the thread
				writers.add(out);
				names.add(name);
				
				
				out.println("WELCOME TO SERVER");
				
				
				
				while(true)
				{
					for(int i = 0; i < names.size(); i++)
						System.out.println(names.get(i));
					
					String input = in.readLine();
					if (input.equals(null))
					{
						return;
					}
					
					System.out.println(input);
					String [] checkInput = input.split(" ");
					
					//if the user wants to set a nickname
					if (checkInput[0].equals("/nick"))
					{
						name = checkInput[1];
						names.set(LocalID, name);
						
					}
					else if (checkInput[0].equals("/dm") )
					{
						//direct message
						
						//iterate through the names array
						for (int i = 0; i < names.size(); i++)
						{
							//if the name matches the dm argument, then send rest of line to that writer
							if (names.get(i).equals(checkInput[1]) )
							{
								for (int j = 2; j < checkInput.length; i++)
								{
									writers.get(i).print(checkInput[j]);
								}
								writers.get(i).println();
							}
							
						}
						
					}
					//break out of loop if user wants to quit
					else if (checkInput[0].equals("/quit"))
					{
						break;
					}
					int j = 0;
					for (PrintWriter writer: writers)
					{
						if(j != LocalID)
							writer.println(name + ": " + input);
						j++;
					}
				}
				
				
			}
			catch(IOException e) {
				e.printStackTrace();

			}
		}
		
	
	
	}//END class ChatThread

}


