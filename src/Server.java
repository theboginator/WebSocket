import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class Server
{
    // The port number on which the server will be listening
    private static int port = 2014;
    // The server socket.
    private static ServerSocket listener = null;
    // The client socket.
    private static Socket clientSocket = null;
    public static void main(String[] args)throws Exception
    {

        boolean listening = true;
        try {
            listener = new ServerSocket(port);
            while (listening) {
                new Client(listener.accept()).run();
            }
        }
        catch (Exception e)
        {
            System.out.println("FAIL: " + e.getMessage());
        }
        listener.close();
        /**
         Open a server socket on the specified port number(2014)
         and monitor the port for connection requests. When a
         connection request is received, create a client request
         thread, passing to its constructor a reference to the
         Socket object that represents the established connection
         with the client.
         */
    }
}
