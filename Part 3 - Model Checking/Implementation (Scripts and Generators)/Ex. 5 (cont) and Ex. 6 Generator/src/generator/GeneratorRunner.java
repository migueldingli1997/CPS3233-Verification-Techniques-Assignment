package generator;

import java.io.IOException;

public class GeneratorRunner {

    public static void main(String[] args) {

        if (args.length == 2) {
            try {
                final int lifts = Integer.parseInt(args[0]);
                final int floors = Integer.parseInt(args[1]);
                if (lifts > 0 & floors > 1) {
                    try {
                        new Generator(lifts, floors, String.format("out_%d_%d.smv", lifts, floors));
                        System.out.println("File successfully generated!");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else if (lifts == 0) {
                    System.out.println("Number of lifts must be 1 or more");
                } else if (floors <= 1) {
                    System.out.println("Number of floors must be 2 or more");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("At least one input from \"" + args[0] + "\" and \"" + args[1] + "\" is not an integer.");
            }
        } else {
            System.out.println("Incorrect number of arguments; expected two integers for no. of FLOORS and LIFTS.");
        }
    }
}
