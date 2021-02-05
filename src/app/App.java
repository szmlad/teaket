package app;

import db.Data;

import java.io.File;
import java.io.IOException;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) throws IOException {
        port(8080);
        staticFiles.externalLocation(new File("./static").getCanonicalPath());

        Data data = new Data();

        Auth.dataStore(data);
        AdminREST.dataSource(data);
        CommentREST.dataSource(data);
        CustomerREST.dataSource(data);
        SalespersonREST.dataSource(data);
        ManifestationREST.dataSource(data);
        TicketREST.dataSource(data);

        data.read();

        path("/rest/admins", () -> {
            get("", AdminREST::getAdmins);
            get("/:username", AdminREST::getAdmin);
            put("/:username", AdminREST::changeAdmin);
            post("", AdminREST::newAdmin);
            delete("/:username", AdminREST::deleteAdmin);
        });

        path("/rest/comments", () -> {
           get("", CommentREST::getComments);
           get("/:id", CommentREST::getComment);
           put("/:id", CommentREST::changeComment);
           post("", CommentREST::newComment);
           delete("/:id", CommentREST::deleteComment);
        });

        path("/rest/customers", () -> {
            get("", CustomerREST::getCustomers);
            get("/:username", CustomerREST::getCustomer);
            put("/:username", CustomerREST::changeCustomer);
            post("", CustomerREST::newCustomer);
            delete("/:username", CustomerREST::deleteCustomer);
        });

        path("/rest/manifestations", () -> {
            get("", ManifestationREST::getManifestations);
            get("/:id", ManifestationREST::getManifestation);
            put("/:id", ManifestationREST::changeManifestation);
            post("", ManifestationREST::newManifestation);
            delete("/:id", ManifestationREST::deleteManifestation);
        });

        path("/rest/salespeople", () -> {
            get("", SalespersonREST::getSalespeople);
            get("/:username", SalespersonREST::getSalesperson);
            put("/:username", SalespersonREST::changeSalesperson);
            post("", SalespersonREST::newSalesperson);
            delete("/:username", SalespersonREST::deleteSalesperson);
        });

        path("/rest/tickets", () -> {
            get("", TicketREST::getTickets);
            get("/:id", TicketREST::getTicket);
            put("/:id", TicketREST::changeTicket);
            post("", TicketREST::newTicket);
            delete("/:id", TicketREST::deleteTicket);
        });

        path("/auth/", () -> {
            post("login", Auth::login);
            get("get", Auth::get);
            get("logout", Auth::logout);
        });

        init();
    }
}
