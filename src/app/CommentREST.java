package app;

import db.Data;
import model.Comment;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommentREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getComments(Request req, Response res) {
        res.type("application/json");

        Map<String, Comment> allComments = data.comments.active()
                .collect(Collectors.toMap(Comment::getId, Function.identity()));
        return data.comments.toJson(allComments);
    }

    public static Object getComment(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        return data.comments.singleToJson(data.comments.getActive(id));
    }

    public static Object deleteComment(Request req, Response res) throws IOException {
        res.type("application/json");

        String id = req.params("id");

        Comment toDelete = data.comments.getActive(id);
        data.comments.delete(id);
        data.comments.toJson();
        return data.comments.singleToJson(toDelete);
    }

    public static Object newComment(Request req, Response res) throws IOException {
        res.type("application/json");

        String json = req.body();
        Comment c = data.comments.newFromJson(json);

        int status = validate(c);
        if (status != 200) return data.comments.singleToJson(c);

        data.comments.put(c);
        data.comments.toJson();
        return data.comments.singleToJson(c);
    }

    public static Object changeComment(Request req, Response res) {
        return null;
    }

    private static int validate(Comment c) {
        if (data.comments.getActive(c.getId()) == null) return 409;
        if (data.manifestations.getActive(c.getManifestationId()) == null) return 400;
        if (data.getActiveUser(c.getAuthorUsername()) == null) return 400;
        if (c.getText().isEmpty()) return 400;
        return 200;
    }
}
