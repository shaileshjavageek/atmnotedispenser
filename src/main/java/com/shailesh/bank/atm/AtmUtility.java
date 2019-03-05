package com.shailesh.bank.atm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import static java.lang.String.format;

/**
 * Utility class for ATM app
 */
public class AtmUtility {

    /**
     * ConsoleWriter is used for utility methods
     */
    public static class ConsoleWriter{

        PrintWriter writer;

        BufferedReader reader;

        ConsoleWriter() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(System.out);
        }

        void write(String s) {
            writer.print(s);
            writer.flush();
        }

        void writeline(String s) {
            writer.println(s);
            writer.flush();
        }

        String read(String s) throws IOException {
            writer.println();
            write(s);
            return reader.readLine();
        }

        char readAsChar(String s, List<Character> c) throws IOException {
            boolean valid = false;
            String str = null;
            do {
                write(s);
                str = reader.readLine();
                final String iStr = str;
                valid = c.stream().anyMatch((i)->Character.toString(i).equalsIgnoreCase(iStr));
                if (! valid) {
                    writeline(format("invalid input must be either %s", c));
                }
            } while(!valid);
            return str.charAt(0);
        }

        int readAsInt(String s) throws IOException {
            int i = 0;
            boolean valid = false;
            do {
                write(s);
                String str = reader.readLine();
                try {
                    i = Integer.parseInt(str);
                    if (i < 0) {
                        valid = false;
                        writeline(format("Invalid input %s is not a positive integer", s));
                    }
                    valid = true;
                } catch (NumberFormatException e) {
                    valid = false;
                    writeline(format("Invalid input %s is not a positive integer", s));
                }
            } while (!valid);
            return i;
        }
    }

    /**
     * Method to check null object
     * @param o
     */
    public static void checkNullArgs(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(format("Argument %s cannot be null.", o));
        }
    }

}
