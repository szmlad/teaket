package app;

import db.Data;
import model.Comment;
import spark.Request;
import spark.Response;
import util.Func;

import java.util.function.Function;
import java.util.stream.Collectors;

public class CommentREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getComments(Request req, Response res) {
        res.type("application/json");

        return data.comments.toJson(
                data.comments.active()
                        .collect(Collectors.toMap(Comment::getId, Function.identity()))
        );
    }

    public static Object getComment(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        return data.comments.singleToJson(
                data.comments.active()
                        .filter(Func.equality(Comment::getId, id))
                        .findFirst()
                        .orElse(null)
        );
    }

    public static Object deleteComment(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        Comment toDelete = data.comments.active()
                .filter(Func.equality(Comment::getId, id))
                .findFirst()
                .orElse(null);
        data.comments.delete(id);
        return data.comments.singleToJson(toDelete);
    }

    public static Object newComment(Request req, Response res) {
        res.type("application/json");

        String json = req.body();
        Comment c = data.comments.newFromJson(json);

        if (data.comments.get(c.getId()) != null) {
            return data.comments.singleToJson(null);
        }

        data.comments.put(c);
        return data.comments.singleToJson(c);
    }

    public static Object changeComment(Request req, Response res) {
        return null;
    }
}
