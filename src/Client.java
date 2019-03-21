import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

class Client extends Thread {
    Socket socket;

    //constructor
    public Client(Socket socket) {
        this.socket = socket;
    }

    //implement the run method
    public void run() {
        handleConnection(socket);
    }
    //implement the handleConnection method here.
    public void handleConnection(Socket socket){
        Vector<Contact> directory = new Vector<Contact>();

        try {
            int attempts = 0;
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Set up input stream
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true); //Set up output stream
            output.println("Checking output stream... OK");

            String request= " ";

            while (!input.ready() || attempts >= 20) { //Wait for the input to be ready. Delay maximum of 20 seconds
                output.println("Waiting for input...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException e){
                    output.println(e);
                }
                attempts ++;
                if(attempts >= 20){
                    System.out.println("No connection. Exiting.");
                    System.exit(0); //Exit
                }
            }

            while ((request = input.readLine()) != null) {
                System.out.println("New Request: '" + request); // Read one line and output it
                String[] instruction = request.split(" "); //Split the request into a command a parameter
                if(instruction[0].equalsIgnoreCase("quit")) {
                    System.exit(0);
                }
                else if(instruction[0].equalsIgnoreCase("get")) { //Return the phone number from the directory
                    for(Contact c : directory) {
                        if(c.getName().equals(instruction[1])) {
                            output.println("200 " + c.getNumber());
                            break;
                        }
                    }
                }
                else if(instruction[0].equalsIgnoreCase("remove")) { //Remove a contact from the directory
                    for(Contact c : directory) {
                        if(c.getName().equals(instruction[1])) {
                            output.println("100 OK");
                            directory.remove(c);
                            break;
                        }
                    }
                }
                else if(instruction[0].equalsIgnoreCase("store")) {
                    directory.add(new Contact(instruction[1], instruction[2])); //Add the contact listed to the directory
                    output.println("100 OK");
                }
                else {
                    output.println("400 Bad request"); //Something else went wrong...
                }
            }
            input.close();
        }
        catch (IOException err) {

        }
    }
}
