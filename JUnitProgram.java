package com.anz.testcase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class JUnitProgram {
   @Test
    public void test_JUnit() {
        System.out.println("This is the testcase in this class");
        String str1="This is the testcase in this class";
        assertEquals("This is the testcase in this class", str1);
    }
}