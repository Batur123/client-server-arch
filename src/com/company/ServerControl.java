package com.company;
import java.io.*;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static com.company.Miscellaneous.*;


public class ServerControl
{
    //Variables
    private static ServerSocket server;
    private static int port = 9876;

    //Server
    public static void main(String args[]) throws IOException, ClassNotFoundException
    {
        server = new ServerSocket(port);

        while(true)
        {
            System.out.println("Waiting for the client request.");
            Socket socket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            List<String> InfoReceive;

            //Get Client Request to the List
            // List[0] Username , List[1] Sender Message
            InfoReceive = (List) ois.readObject();
            String message = InfoReceive.get(1);
            String Username = InfoReceive.get(0);

            //Console Output -> "[Username]: Command"
            System.out.println(ANSI_RESET+ANSI_CYAN+"["+ANSI_YELLOW+Username+ANSI_CYAN+"]: "+ANSI_BLUE+message+ANSI_RESET);

            if(message.equalsIgnoreCase("quit") && Username.equals("Root"))
            {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("Server shut down.");
                ois.close();
                oos.close();
                socket.close();
                break;
            }
            else
            {
                Process process;
                List<String> ResponseMessage = new ArrayList<>();;
                try
                {
                    process = Runtime.getRuntime().exec(message, null, null);
                    ResponseMessage = printResults(process);
                }
                catch(Exception ex)
                {
                    ResponseMessage.add(ANSI_RESET+ANSI_RED+"[System Error]: "+ANSI_RESET+ANSI_YELLOW+ex+ANSI_RESET);
                }
                finally
                {
                    //Send Message to Server Itself
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                    //Send Message to Client
                    oos.writeObject(ResponseMessage);
                    ois.close();
                    oos.close();
                    socket.close();
                }
            }
        }

        //Initiate Shut Down
        System.out.println("Shutting down the server.");
        server.close();
    }
}