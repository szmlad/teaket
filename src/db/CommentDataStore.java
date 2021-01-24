package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Comment;

public class CommentDataStore extends DataStore<Comment> {
    public CommentDataStore(String filepath) {
        super(filepath);
        g = new Gson();
    }

    private class NewCommentData {
        String id;
        String authorUsername;
        String manifestationId;
        String text;
        int rating;
    }

    public Comment newFromJson(String json) {
        NewCommentData data = g.fromJson(json, new TypeToken<NewCommentData>() {}.getType());

        return new Comment(
                data.id,
                data.authorUsername,
                data.manifestationId,
                data.text,
                data.rating
        );
    }

    @Override
    public void put(Comment c) {
        data.put(c.getId(), c);
    }
}
