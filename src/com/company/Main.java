package com.company;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.company.Miscellaneous.*;

import org.junit.Test;

public class Main
{

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException
    {
        //region WithoutUnitTestFunction

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            List<String> Users = Arrays.asList("Root", "Batuhan");
            User User1 = new User("31972875-4a74-4022-bb78-2c6028cb2d6a","12345","Root","20e095a6-02fa-48b7-8c41-24bfcf3c7fb0");
            User User2 = new User("88212c94-62b2-4cea-9996-04120c1ea87c","5544","Batuhan","1a55f567-43d1-4d4f-aa5a-e02972342a26");

            Scanner scanner = new Scanner(System.in);
            System.out.print(ANSI_PURPLE+"Users: ");
            for (int i = 0; i < Users.size(); i++)
            {
                System.out.print(ANSI_PURPLE+Users.get(i));
                if(i != Users.size()-1)
                {
                    System.out.print(",");
                }
            }
            System.out.println(" ");
            System.out.println(ANSI_WHITE+"Select user with typing its name: "+ANSI_RESET);
            String WhichUser = scanner.nextLine();

            if(!Users.contains(WhichUser))
            {
                System.out.println(ANSI_RED+"You selected wrong username.");
            }
            else
            {
                boolean check = true;

                while(check)
                {
                    System.out.print(ANSI_RESET+ANSI_GREEN+"Enter your command to the command line:"+ANSI_RESET);
                    String Command = scanner.nextLine();

                    //Store Args of the Command in List.
                    List<String> CommandArgs = new ArrayList<String>();
                    String[] arrOfStr = Command.split(" ", 0);

                    for (String a : arrOfStr)
                    {
                        CommandArgs.add(a);
                    }


                    Process process;

                    if (IS_WINDOWS)
                    {
                        //Terminates the command line
                        if(Command.equals("quit"))
                        {
                            InetAddress host = InetAddress.getLocalHost();
                            Socket socket = null;
                            ObjectOutputStream oos = null;
                            ObjectInputStream ois = null;
                            socket = new Socket(host.getHostName(), 9876);
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            System.out.println("Sending quit request to Socket Server");
                            User1.SetRecentCommand(Command);

                            //Send Variables to the Server
                            List<String> Info=new LinkedList<String>();
                            Info.add(User1.GetUserName());
                            Info.add(User1.GetRecentCommand());
                            oos.writeObject(Info);

                            ois = new ObjectInputStream(socket.getInputStream());
                            String ServerAnswer = (String) ois.readObject();
                            System.out.println(ServerAnswer);
                            ois.close();
                            oos.close();
                            check = false;
                            Thread.sleep(100);
                        }
                        else if(!Command.equals("cmd"))
                        {
                            try
                            {
                                InetAddress host = InetAddress.getLocalHost();
                                Socket socket = null;
                                ObjectOutputStream oos = null;
                                ObjectInputStream ois = null;

                                socket = new Socket(host.getHostName(), 9876);
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                System.out.println("Sending request to Socket Server");
                                User1.SetRecentCommand(Command); //User2


                                /* Important Note
                                    We dont use SQL in that project so you need to change User1 to User2 if you want to run through multiple users. If we use
                                    Database that would be very easy to optimize and use.
                                 */

                                //Send Variables to the Server
                                List<String> Info=new LinkedList<String>();
                                Info.add(User1.GetUserName()); //User2
                                Info.add(User1.GetRecentCommand()); //User2
                                oos.writeObject(Info);

                                //Server Response
                                ois = new ObjectInputStream(socket.getInputStream());
                                List<String> ServerAnswer = (List) ois.readObject();
                                for (String ServerAnswerAsString : ServerAnswer)
                                {
                                    System.out.println(ServerAnswerAsString);
                                }

                                //Collection
                                ois.close();
                                oos.close();
                                Thread.sleep(100);
                            }
                            catch(Exception ex)
                            {
                                System.out.println(ANSI_RED+"Error");
                            }
                            finally
                            {
                                if(WhichUser.contains("Root"))
                                {
                                    User1.SetRecentCommand(Command);
                                    User1.SetRecentCommandDate(dtf.format(now));
                                    User1.SetLog(User1.GetUserName()+" user wrote ("+User1.GetRecentCommand()+") command on "+User1.GetRecentCommandDate());
                                    User1.SetListofCommandArgs(CommandArgs);
                                    User1.SetOperatingSystem(OS);
                                }
                                else if(WhichUser.contains("Batuhan"))
                                {
                                    User2.SetRecentCommand(Command);
                                    User2.SetRecentCommandDate(dtf.format(now));
                                    User2.SetLog(User2.GetUserName()+" user wrote ("+User2.GetRecentCommand()+") command on "+User2.GetRecentCommandDate());
                                    User2.SetListofCommandArgs(CommandArgs);
                                    User2.SetOperatingSystem(OS);
                                }
                            }
                        }
                        else
                        {
                            System.out.println("You cant run this command");
                        }
                    }
                    else if (IS_MAC)
                    {
                        System.out.println("Mac");
                    }
                    else if (IS_UNIX)
                    {
                        if(Command.equals("quit"))
                        {
                            InetAddress host = InetAddress.getLocalHost();
                            Socket socket = null;
                            ObjectOutputStream oos = null;
                            ObjectInputStream ois = null;
                            socket = new Socket(host.getHostName(), 9876);
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            System.out.println("Sending quit request to Socket Server");
                            User1.SetRecentCommand(Command);

                            //Send Variables to the Server
                            List<String> Info=new LinkedList<String>();
                            Info.add(User1.GetUserName());
                            Info.add(User1.GetRecentCommand());
                            oos.writeObject(Info);
                            ois = new ObjectInputStream(socket.getInputStream());
                            String ServerAnswer = (String) ois.readObject();
                            System.out.println(ServerAnswer);
                            ois.close();
                            oos.close();
                            check = false;
                            Thread.sleep(100);
                        }
                        else
                        {
                            try
                            {
                                InetAddress host = InetAddress.getLocalHost();
                                Socket socket = null;
                                ObjectOutputStream oos = null;
                                ObjectInputStream ois = null;

                                socket = new Socket(host.getHostName(), 9876);
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                System.out.println("Sending request to Socket Server");
                                User1.SetRecentCommand(Command);

                                //Send Variables to the Server
                                List<String> Info=new LinkedList<String>();
                                Info.add(User1.GetUserName());
                                Info.add(User1.GetRecentCommand());
                                oos.writeObject(Info);

                                //Server Response
                                ois = new ObjectInputStream(socket.getInputStream());
                                List<String> ServerAnswer = (List) ois.readObject();
                                for (String ServerAnswerAsString : ServerAnswer)
                                {
                                    System.out.println(ServerAnswerAsString);
                                }

                                //Collection
                                ois.close();
                                oos.close();
                                Thread.sleep(100);
                            }
                            catch(Exception ex)
                            {
                                System.out.println(ANSI_YELLOW+"[System Message]:"+ANSI_RED+"An error has occured while getting the answer.");
                            }
                            finally
                            {
                                if(WhichUser.contains("Root"))
                                {
                                    User1.SetRecentCommand(Command);
                                    User1.SetRecentCommandDate(dtf.format(now));
                                    User1.SetLog(User1.GetUserName()+" user wrote ("+User1.GetRecentCommand()+") command on "+User1.GetRecentCommandDate());
                                    User1.SetListofCommandArgs(CommandArgs);
                                    User1.SetOperatingSystem(OS);
                                }
                                else if(WhichUser.contains("Batuhan"))
                                {
                                    User2.SetRecentCommand(Command);
                                    User2.SetRecentCommandDate(dtf.format(now));
                                    User2.SetLog(User2.GetUserName()+" user wrote ("+User2.GetRecentCommand()+") command on "+User2.GetRecentCommandDate());
                                    User2.SetListofCommandArgs(CommandArgs);
                                    User2.SetOperatingSystem(OS);
                                }
                            }
                        }
                    }
                    else if (IS_SOLARIS)
                    {
                        System.out.println("Solaris");
                    }
                    else
                    {
                        System.out.println(ANSI_RED+"NULL");
                    }
                }
            }

            //Get User Logs in the end of the program to see which command they used and when.
            if(User1.GetLog() != null)
            {
                System.out.println(ANSI_CYAN+User1.GetLog());
                User1.PrintUserCommands();
            }
            if(User2.GetLog() != null)
            {
                System.out.println(ANSI_CYAN+User2.GetLog());
                User1.PrintUserCommands();
            }

            int Choose = 0;
            System.out.println(ANSI_RED+"Do you want to go back? Press 1 for reset program. Press anything except 1 for exit."+ANSI_RESET);
            try
            {
                Choose = scanner.nextInt();
            }
            catch(Exception ex)
            {

            }

            switch(Choose)
            {
                case 1:
                {
                    main(args);
                }
                default:
                {
                    break;
                }
            }
            //endregion
    }
}
