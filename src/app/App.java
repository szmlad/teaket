package app;

import java.io.File;
import java.io.IOException;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(8080);

        try {
            staticFiles.externalLocation(
                    new File("./static").getCanonicalPath());
        } catch (IOException e) {
            System.err.println("Cannot find static files. Aborting...");
            return;
        }

        init();
    }
}
