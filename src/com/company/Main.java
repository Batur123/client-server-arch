package com.company;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.company.Miscellaneous.*;

public class Main
{
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException
    {
        //Create User Table
        SQLConnection.createNewTable("UsersDatabase.db");

        //Establish SQL connection.
        SQLConnection.connect("UsersDatabase.db");

        //Insert Users
        SQLConnection.InsertDatabase("31972875-4a74-4022-bb78-2c6028cb2d6a","Root","12345","NULLForNow");
        SQLConnection.InsertDatabase("88212c94-62b2-4cea-9996-04120c1ea87c","Batuhan","111","NULLForNow");
        SQLConnection.InsertDatabase("fae15d70-65f1-40be-87e5-9ca1d61728fc","Test1","222","NULLForNow");
        SQLConnection.InsertDatabase("8279b24d-222e-4eba-b80c-8cc6d9546757","User1","333","NULLForNow");
        SQLConnection.InsertDatabase("e5466c97-68b6-4867-bfe5-403e01ca89b3","DBAdmin","444","NULLForNow");
        SQLConnection.InsertDatabase("3252a19b-e5e0-4970-8658-2114cbc9fddc","Faraday","555","NULLForNow");
        SQLConnection.InsertDatabase("ee7000e6-11ee-45e8-8b89-70ac9e8eb4b9","User2","666","NULLForNow");

        //List All Users for Login
        SQLConnection.ListAllUsers();

        //Login
        System.out.print(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_BLUE+"You need to login. Enter your username: "+ANSI_RESET);

        Scanner scanner = new Scanner(System.in);

        //Get Username Input
        String UsernameInput = scanner.nextLine();

        if(SQLConnection.UsernamesAll.indexOf(UsernameInput) != -1)
        {
            System.out.print(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_BLUE+"Enter your password: "+ANSI_RESET);

            //Get Password Input
            String PasswordInput = scanner.nextLine();

            //Login Function
            User user = SQLConnection.LoginDatabase(UsernameInput,PasswordInput);

            //Check If Username/Password True
            if(user != null)
            {
                System.out.println(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_BLUE+"Confirmation succesfull. "+ANSI_RESET);

                //Datetime - Now
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                boolean check = true;

                while(check)
                {
                    System.out.print(ANSI_RESET+ANSI_GREEN+"Enter your command to the command line: "+ANSI_RESET);
                    String Command = scanner.nextLine();

                    //Store Args of the Command in List.
                    List<String> CommandArgs = new ArrayList<>();
                    String[] arrOfStr = Command.split(" ", 0);

                    for (String a : arrOfStr)
                    {
                        CommandArgs.add(a);
                    }

                    Process process;

                    if (IS_WINDOWS)
                    {
                        try
                        {
                            InetAddress host = InetAddress.getLocalHost();
                            Socket socket = null;
                            ObjectOutputStream oos = null;
                            ObjectInputStream ois = null;

                            socket = new Socket(host.getHostName(), 9876);
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            //System.out.println(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_BLUE+"Sending request to Socket Server"+ANSI_RESET);
                            user.SetRecentCommand(Command); //User2

                            //Send Variables to the Server
                            List<String> Info=new LinkedList<String>();
                            Info.add(user.GetUserName()); //User2
                            Info.add(user.GetRecentCommand()); //User2
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
                            user.SetRecentCommand(Command);
                            user.SetRecentCommandDate(dtf.format(now));
                            user.SetLog(user.GetUserName()+" user wrote ("+user.GetRecentCommand()+") command on "+user.GetRecentCommandDate());
                            user.SetListofCommandArgs(CommandArgs);
                            user.SetOperatingSystem(OS);
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
                            user.SetRecentCommand(Command);

                            //Send Variables to the Server
                            List<String> Info=new LinkedList<String>();
                            Info.add(user.GetUserName());
                            Info.add(user.GetRecentCommand());
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
                                user.SetRecentCommand(Command);

                                //Send Variables to the Server
                                List<String> Info=new LinkedList<String>();
                                Info.add(user.GetUserName());
                                Info.add(user.GetRecentCommand());
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
                                user.SetRecentCommand(Command);
                                user.SetRecentCommandDate(dtf.format(now));
                                user.SetLog(user.GetUserName()+" user wrote ("+user.GetRecentCommand()+") command on "+user.GetRecentCommandDate());
                                user.SetListofCommandArgs(CommandArgs);
                                user.SetOperatingSystem(OS);
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

                //Get User Logs in the end of the program to see which command they used and when.
                if(user.GetLog() != null)
                {
                    System.out.println(ANSI_CYAN+user.GetLog());
                    user.PrintUserCommands();
                }
            }
            else
            {
                System.out.println(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_BLUE+"Wrong password."+ANSI_RESET);
            }
        }
        else
        {
            System.out.println(ANSI_RED+"You selected wrong username.");
        }

        int Choose = 0;

        System.out.println(ANSI_RED+"[Server]: "+ANSI_YELLOW+"Do you want to go back? Press 1 for reset program. Press anything except 1 for exit."+ANSI_RESET);
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
    }
}
