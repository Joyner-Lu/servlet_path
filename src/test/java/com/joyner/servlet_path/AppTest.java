package com.joyner.servlet_path;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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

    public static void main(String[] args)throws Exception {

        ByteBuffer headerBuffer = ByteBuffer.allocate(104857600);

    }
}
