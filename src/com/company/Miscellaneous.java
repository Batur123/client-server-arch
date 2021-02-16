package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Miscellaneous
{
    //region Variables
    /*
  Check Operating System
  */
    public static String OS = System.getProperty("os.name").toLowerCase();
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
    //endregion

    /*  Print the Result of Shell Commands
     *  @param process
     *  @return returns the string of shell command output
     */
    public static void printResultsVoid(Process process) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null)
        {
            System.out.println(ANSI_YELLOW+line);
        }
    }

    /*  Print the Result of Shell Commands
     *  @param process
     *  @return returns the string of shell command output
     */
    public static List<String> printResults(Process process) throws IOException
    {
        List<String> lines = new LinkedList<>();

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                lines.add(line);
            }

            return lines;
        }
        catch(Exception ex)
        {
            lines.clear();
            lines.add("System error.");
            return lines;
        }
    }
}

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

    public User(String _UUID, String _Password, String _Username, String _UserSessionID,String _RecentCommand)
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
