package generator;

import java.io.IOException;

public class GeneratorRunner {

    public static void main(String[] args) {

        if (args.length == 1) {
            try {
                final int floors = Integer.parseInt(args[0]);
                if (floors > 2) {
                    try {
                        new Generator(floors, String.format("out_%d.smv", floors));
                        System.out.println("File successfully generated!");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else {
                    System.out.println("Number of floors must be 3 or more");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("The input \"" + args[0] + "\" is not an integer.");
            }
        } else {
            System.out.println("Incorrect number of arguments; expected one integers for no. of FLOORS.");
        }
    }
}
