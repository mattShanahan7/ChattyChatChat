
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
        
        try( Socket chatSocket = new Socket(hostName, portNumber);
        		PrintWriter out = new PrintWriter(chatSocket.getOutputStream(), true);
        		BufferedReader in = new BufferedReader(
        				new InputStreamReader (chatSocket.getInputStream()) );
        		)
        {
        	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        	String userInput = "";
        	String fromServer;
        	String userName = "anonymous";
        	
        	while((fromServer = in.readLine()) != null || (userInput = stdIn.readLine()) != null)
        	{
        		out.println(userInput);
        		//print out chats from other users
        		System.out.println(in.readLine() );
        		
        		
        		
        	}
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
		
	}//END main

}
