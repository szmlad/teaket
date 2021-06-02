package app;

import db.DataStore;

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

        DataStore ds = new DataStore();
        ds.init();

        Auth.dataSource(ds);

        path("/auth", () -> {
            post("/login", Auth::login);
            get("", Auth::get);
            get("/logout", Auth::logout);
        });

        init();
    }
}
