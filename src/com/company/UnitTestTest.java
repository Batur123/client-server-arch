package com.company;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.company.Miscellaneous.*;

public class UnitTestTest
{
/*
    @Test
    public void UnitTestingForFunction() throws Exception
    {
        UnitTest test = new UnitTest();
        test.UnitTestFunction("Root","/cmd /c dir");
        test.UnitTestFunction("Batuhan","md");
        test.UnitTestFunction("Batuhan","sh -ls");
        test.UnitTestFunction("Batuhan","/cd/usr/bin");
        test.UnitTestFunction("Batuhan","pwd");
        test.UnitTestFunction("Root","D:\\Temp>copy ^");
        test.UnitTestFunction("Root","auditpol");

        Assert.assertEquals("Wrong command","-");
    } */
}



//This class is only for Unit Testing.
class UnitTest2
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
}

