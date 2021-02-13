package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;

// User Class
class User
{
    //Variables
    private String UUID,RecentCommand,Log,Password,UserName,RecentCommandDate,UserSessionID,OperatingSystem;
    private List<String> ListofCommandArgs;

    //Constructor
    public User(String _UUID, String _Password, String _Username, String _UserSessionID)
    {
        UUID = _UUID;
        Password = _Password;
        UserName = _Username;
        UserSessionID = _UserSessionID;
    }

    //Getter Setters
    public String GetUUID()
    {
        return UUID;
    }

    public String GetRecentCommand() {
        return RecentCommand;
    }

    public String GetRecentCommandDate()
    {
        return RecentCommandDate;
    }

    public String GetLog()
    {
        return Log;
    }

    public String GetOperatingSystem(){ return  OperatingSystem; }

    public String GetPassword()
    {
        return Password;
    }

    public String GetUserName()
    {
        return UserName;
    }

    public String GetUserSessionID() {return UserSessionID; }

    public List<String> GetListofCommandArgs()
    {
        return ListofCommandArgs;
    }

    public void SetListofCommandArgs(List<String> _CommandArgs)
    {
        this.ListofCommandArgs = _CommandArgs;
    }

    public void SetRecentCommand(String _RecentCommand)
    {
        this.RecentCommand = _RecentCommand;
    }

    public void SetRecentCommandDate(String _RecentCommandDate)
    {
        this.RecentCommandDate = _RecentCommandDate;
    }

    public void SetOperatingSystem(String _OperatingSystem) { this.OperatingSystem = _OperatingSystem; }

    public void SetLog(String _Log)
    {
        this.Log = _Log;
    }

    public void SetUUID(String _UUID)
    {
        this.UUID = _UUID;
    }

    public void SetPassword(String _Password)
    {
        this.Password = _Password;
    }

    public void SetUserName(String _UserName)
    {
        this.UserName = _UserName;
    }

    public void SetUserSessionID(String _SessionID) {this.UserSessionID = _SessionID; }

    //Print User Info Function
    public void PrintUserCommands()
    {
        System.out.println("User: "+this.UUID);
        System.out.println("Latest Command: "+this.RecentCommand);
        System.out.println("Command Date: "+this.RecentCommandDate);
    }
}

public class Main
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
     Misc
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
    public static void printResults(Process process) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null)
        {
            System.out.println(ANSI_YELLOW+line);
        }
    }

    public static void main(String[] args)
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
            System.out.println("");
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
                            check = false;
                        }
                        else if(!Command.equals("cmd"))
                        {
                            try
                            {
                                process = Runtime.getRuntime().exec(Command, null, null);
                                System.out.println("Command: "+Command);
                                printResults(process);
                            }
                            catch(IOException IOExp)
                            {
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+IOExp);
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+"System: Wrong command.");
                            }
                            catch(NullPointerException NormEx)
                            {
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+NormEx);
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+"You cannot leave command line blank.");
                            }
                            catch(UnsupportedOperationException OpEx)
                            {
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+OpEx);
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+"Operating system not supporting that command.");
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
                            check = false;
                        }
                        else
                        {
                            try
                            {
                                process = Runtime.getRuntime().exec(Command, null, null);
                                printResults(process);
                            }
                            catch(IOException IOExp)
                            {
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+IOExp);
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+"System: You cant run this command.");
                            }
                            catch(NullPointerException NormEx)
                            {
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+NormEx);
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+"You cannot leave command line blank.");
                            }
                            catch(UnsupportedOperationException OpEx)
                            {
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+OpEx);
                                System.out.println(ANSI_RED+"-------");
                                System.out.println(ANSI_RED+"Operating system not supporting that command.");
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

//This class is only for Unit Testing.
class UnitTest
{
    @Test
    public void UnitTestFunction()
    {
        UnitTestFunction("Root","cmd /cd /dir" );
    }

    @Test
    public void UnitTestFunction(String _Username, String Command)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        List<String> Users = Arrays.asList("Root", "Batuhan");
        User User1 = new User("31972875-4a74-4022-bb78-2c6028cb2d6a","12345","Root","20e095a6-02fa-48b7-8c41-24bfcf3c7fb0");
        User User2 = new User("88212c94-62b2-4cea-9996-04120c1ea87c","5544","Batuhan","1a55f567-43d1-4d4f-aa5a-e02972342a26");

        if(!Users.contains(_Username))
        {
            System.out.println(ANSI_RED+"You selected wrong username.");
        }
        else
        {
            Process process;
            if (IS_WINDOWS)
            {
                if(!Command.equals("cmd"))
                {
                    try
                    {
                        process = Runtime.getRuntime().exec(Command, null, null);
                        System.out.println("Command: "+Command);
                        printResults(process);
                    }
                    catch(IOException IOExp)
                    {
                        System.out.println(ANSI_RED+"-------");
                        System.out.println(ANSI_RED+IOExp);
                        System.out.println(ANSI_RED+"-------");
                        System.out.println(ANSI_RED+"System: Wrong command.");
                    }
                    catch(NullPointerException NormEx)
                    {
                        System.out.println(ANSI_RED+"-------");
                        System.out.println(ANSI_RED+NormEx);
                        System.out.println(ANSI_RED+"-------");
                        System.out.println(ANSI_RED+"You cannot leave command line blank.");
                    }
                    catch(UnsupportedOperationException OpEx)
                    {
                        System.out.println(ANSI_RED+"-------");
                        System.out.println(ANSI_RED+OpEx);
                        System.out.println(ANSI_RED+"-------");
                        System.out.println(ANSI_RED+"Operating system not supporting that command.");
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ANSI_RED+"Error");
                    }
                    finally
                    {
                        if(_Username.contains("Root"))
                        {
                            User1.SetRecentCommand(Command);
                            User1.SetRecentCommandDate(dtf.format(now));
                            User1.SetLog(User1.GetUserName()+" user wrote ("+User1.GetRecentCommand()+") command on "+User1.GetRecentCommandDate());
                        }
                        else if(_Username.contains("Batuhan"))
                        {
                            User2.SetRecentCommand(Command);
                            User2.SetRecentCommandDate(dtf.format(now));
                            User2.SetLog(User2.GetUserName()+" user wrote ("+User2.GetRecentCommand()+") command on "+User2.GetRecentCommandDate());
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
                try
                {
                    process = Runtime.getRuntime().exec(Command, null, null);
                    printResults(process);
                }
                catch(IOException IOExp)
                {
                    System.out.println(ANSI_RED+"-------");
                    System.out.println(ANSI_RED+IOExp);
                    System.out.println(ANSI_RED+"-------");
                    System.out.println(ANSI_RED+"System: You cant run this command.");
                }
                catch(NullPointerException NormEx)
                {
                    System.out.println(ANSI_RED+"-------");
                    System.out.println(ANSI_RED+NormEx);
                    System.out.println(ANSI_RED+"-------");
                    System.out.println(ANSI_RED+"You cannot leave command line blank.");
                }
                catch(UnsupportedOperationException OpEx)
                {
                    System.out.println(ANSI_RED+"-------");
                    System.out.println(ANSI_RED+OpEx);
                    System.out.println(ANSI_RED+"-------");
                    System.out.println(ANSI_RED+"Operating system not supporting that command.");
                }
                catch(Exception ex)
                {
                    System.out.println(ANSI_RED+"Error");
                }
                finally
                {
                    if(_Username.contains("Root"))
                    {
                        User1.SetRecentCommand(Command);
                        User1.SetRecentCommandDate(dtf.format(now));
                        User1.SetLog(User1.GetUserName()+" user wrote ("+User1.GetRecentCommand()+") command on "+User1.GetRecentCommandDate());
                    }
                    else if(_Username.contains("Batuhan"))
                    {
                        User2.SetRecentCommand(Command);
                        User2.SetRecentCommandDate(dtf.format(now));
                        User2.SetLog(User2.GetUserName()+" user wrote ("+User2.GetRecentCommand()+") command on "+User2.GetRecentCommandDate());
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
    }


    /*
     Check Operating System
     */
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static final boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
    public static final boolean IS_MAC = (OS.indexOf("mac") >= 0);
    public static final boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    public static final boolean IS_SOLARIS = (OS.indexOf("sunos") >= 0);

    /*
     Misc
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
    public void printResults(Process process) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null)
        {
            System.out.println(ANSI_YELLOW+line);
        }
    }
}
