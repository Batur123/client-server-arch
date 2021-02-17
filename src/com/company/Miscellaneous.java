package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static com.company.Miscellaneous.*;

//SQLite
class SQLConnection
{

    /**
    Note:
    Do not forget to add sqlite-jbdc-3.34.0.jar to the libraries in any IDE.
     */

    /**
     * Connect to a sample database
     */
    public static void connect(String DatabaseName)
    {
        Connection conn = null;
        try
        {
            String url = "jdbc:sqlite:"+DatabaseName;
            conn = DriverManager.getConnection(url);
            System.out.println(ANSI_RESET+ANSI_RED+"[Server]: "+ANSI_RESET+ANSI_BLUE+"SQL connection is established.");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static Connection ReturnConnect(String DatabaseName)
    {
        String url = "jdbc:sqlite:"+DatabaseName;
        Connection conn = null;

        try
        {
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return conn;

    }

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public static void createNewDatabase(String fileName)
    {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url))
        {
            if (conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database (if not exist)
     */
    public static void createNewTable(String DatabaseName)
    {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DatabaseName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Users (id integer PRIMARY KEY,UUID text NOT NULL,Username text NOT NULL,\tPassword TEXT NOT NULL, OperatingSystem TEXT NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);Statement stmt = conn.createStatement())
        {
            stmt.execute(sql);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert User Info to the Users table in SQLite Database.
     */
    public static void InsertDatabase(String UUID,String Username, String Password, String OperatingSystem)
    {

        Boolean check = true;
        String query = "SELECT * FROM Users where Username=? and Password=?";

        try (Connection conn = SQLConnection.ReturnConnect("UsersDatabase.db");PreparedStatement pstmt = conn.prepareStatement(query))
        {
            pstmt.setString(1, Username);
            pstmt.setString(2, Password);
            ResultSet res = pstmt.executeQuery();

            if(!res.next())
            {
                check = false;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        if(!check)
        {
            String sql = "INSERT INTO Users(UUID,Username,Password,OperatingSystem) VALUES(?,?,?,?)";

            try (Connection conn = SQLConnection.ReturnConnect("UsersDatabase.db");PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(1, UUID);
                pstmt.setString(2, Username);
                pstmt.setString(3, Password);
                pstmt.setString(4, OperatingSystem);
                pstmt.executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
    public static void Login(String Username, String Password)
    {
        String query = "SELECT * FROM Users where Username=? and Password=?";

        try (Connection conn = SQLConnection.ReturnConnect("UsersDatabase.db");PreparedStatement pstmt = conn.prepareStatement(query))
        {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, Username);
            stmt.setString(2, Password);
            stmt.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    } */

    public static User LoginDatabase(String Username, String Password)
    {
        User user = null;

        String query = "SELECT * FROM Users where Username=? and Password=?";

        try (Connection conn = SQLConnection.ReturnConnect("UsersDatabase.db");PreparedStatement pstmt = conn.prepareStatement(query))
        {
            pstmt.setString(1, Username);
            pstmt.setString(2, Password);
            ResultSet res = pstmt.executeQuery();

            if(res.next())
            {
                String UUID = res.getString("UUID");
                String OperatingSystem = res.getString("OperatingSystem");

                user = new User(UUID,Password, Username,OperatingSystem);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static List<String> UsernamesAll = new ArrayList<>();
    public static void ListAllUsers()
    {
        String query = "SELECT DISTINCT Username FROM Users";

        try (Connection conn = SQLConnection.ReturnConnect("UsersDatabase.db");PreparedStatement pstmt = conn.prepareStatement(query))
        {
            ResultSet res = pstmt.executeQuery();

            String TempUsersList = "";

            while (res.next())
            {
                TempUsersList = TempUsersList + res.getString("Username")+",";
                UsernamesAll.add((res.getString("Username")));
            }

            TempUsersList = TempUsersList.substring(0, TempUsersList.length() - 1);
            System.out.println(ANSI_RESET+ANSI_RED+"[Users]: "+ANSI_PURPLE+TempUsersList);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}

//
public class Miscellaneous
{
    //region Variables


    /**
     * Check Operating System
     */
    public static String OS = System.getProperty("os.name").toLowerCase();
    public static final boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
    public static final boolean IS_MAC = (OS.indexOf("mac") >= 0);
    public static final boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    public static final boolean IS_SOLARIS = (OS.indexOf("sunos") >= 0);

    /**
     * Command Line Colors
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

    /** Print the Result of Shell Commands
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

    /**  Print the Result of Shell Commands
     *  @param process
     *  @return List<String>
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
    private String UUID,RecentCommand,Log,Password,UserName,RecentCommandDate,OperatingSystem;
    private List<String> ListofCommandArgs;

    //Constructor
    public User(String _UUID, String _Password, String _Username)
    {
        UUID = _UUID;
        Password = _Password;
        UserName = _Username;
    }

    public User(String _UUID, String _Password, String _Username,String _RecentCommand)
    {
        UUID = _UUID;
        Password = _Password;
        UserName = _Username;
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

    //Print User Info Function
    public void PrintUserCommands()
    {
        System.out.println("User: "+this.UUID);
        System.out.println("Latest Command: "+this.RecentCommand);
        System.out.println("Command Date: "+this.RecentCommandDate);
    }
}
