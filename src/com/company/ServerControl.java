package com.company;
import java.io.*;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.company.Miscellaneous.*;

public class ServerControl
{
    //Variables
    private static ServerSocket server;
    private static int port = 9876;

    //Server
    public static void main(String args[]) throws IOException, ClassNotFoundException, ParseException
    {
        server = new ServerSocket(port);
        System.out.println(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_YELLOW+"Server started. Waiting for client request."+ANSI_RESET);

        while(true)
        {
            //DateTime as Day/Month/Year Hour/Minute/Second
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            Socket socket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            //Get Client Request to the List
            // List[0] Username , List[1] Sender Message
            List<String> InfoReceive;
            InfoReceive = (List) ois.readObject();
            String message = InfoReceive.get(1);
            String Username = InfoReceive.get(0);

            //Console Output -> "[Username]: Command"
            System.out.println(ANSI_RESET+ANSI_RED+"["+dtf.format(now)+"]"+ANSI_RESET+ANSI_CYAN+"["+ANSI_YELLOW+Username+ANSI_CYAN+"]: "+ANSI_BLUE+message+ANSI_RESET);

            if(message.equalsIgnoreCase("quit"))
            {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                if(Username.equals("Root"))
                {
                    oos.writeObject("Server shut down.");
                    ois.close();
                    oos.close();
                    socket.close();
                    break;
                }
                else
                {
                    oos.writeObject(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_YELLOW+"You dont have permission to use this command."+ANSI_RESET);
                    ois.close();
                    oos.close();
                    socket.close();
                }
            }
            else
            {
                Process process;
                List<String> ResponseMessage = new ArrayList<>();

                try
                {
                    process = Runtime.getRuntime().exec(message, null, null);
                    ResponseMessage = printResults(process);
                }
                catch(Exception ex)
                {
                    ResponseMessage.add(ANSI_RESET+ANSI_RED+"[System Error]: "+ANSI_YELLOW+ex+ANSI_RESET);
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