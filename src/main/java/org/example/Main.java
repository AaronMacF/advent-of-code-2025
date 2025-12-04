package org.example;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Expected two args: <day> <part>");
            System.exit(1);
        }
        var day = Integer.parseInt(args[0]);
        var part = Integer.parseInt(args[1]);

        try {
            var className = "org.example.day" + day + ".Day" + day + "Part" + part;
            Class<?> dayCls = Class.forName(className);
            Object clsInstance = dayCls.getDeclaredConstructor().newInstance();
            var run = dayCls.getMethod("run");
            run.invoke(clsInstance);
            System.exit(0);
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            System.exit(2);
        }
    }
}