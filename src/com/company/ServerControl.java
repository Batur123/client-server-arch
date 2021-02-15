package com.company;
import java.io.*;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ServerControl
{
        /*
        Check Operating System
        */
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static final boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
    public static final boolean IS_MAC = (OS.indexOf("mac") >= 0);
    public static final boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    public static final boolean IS_SOLARIS = (OS.indexOf("sunos") >= 0);

    /*
     Command Line Colors
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    /*  Print the Result of Shell Commands
     *  @param process
     *  @return returns the string of shell command output
     */
    public static List<String> printResults(Process process) throws IOException
    {
        List<String> lines = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null)
        {
            lines.add(line);
        }

        return lines;
    }

    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String args[]) throws IOException, ClassNotFoundException
    {

        server = new ServerSocket(port);

        while(true)
        {
            try
            {
                System.out.println("Waiting for the client request.");
                Socket socket = server.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                //Temporary Message
                String message = (String) ois.readObject();
                System.out.println("Client message received: "+message);

                if(message.equalsIgnoreCase("quit"))
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
                    process = Runtime.getRuntime().exec(message, null, null);
                    List<String> ResponseMessage;
                    ResponseMessage = printResults(process);

                    //Send Message to Server Itself
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                    //Send Message to Client
                    oos.writeObject(ResponseMessage);
                    ois.close();
                    oos.close();
                    socket.close();
                }
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
        }
        System.out.println("Shutting down the server.");
        server.close();
    }
}