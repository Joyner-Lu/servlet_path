package com.joyner.servlet_path;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * The Carriage Return ASCII character value.
     */
    public static final byte CR = 0x0D;//13

    /**
     * The Line Feed ASCII character value.
     */
    public static final byte LF = 0x0A;//10

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public static void main(String[] args) {
        System.out.println("============");
        System.out.println(CR);
        System.out.println(LF);
        System.out.println("============");

    }
}
