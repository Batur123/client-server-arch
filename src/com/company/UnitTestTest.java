package com.company;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTestTest
{
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
    }
}