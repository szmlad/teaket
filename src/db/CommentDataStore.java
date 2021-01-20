package db;

import com.google.gson.Gson;
import model.Comment;

public class CommentDataStore extends DataStore<Comment> {
    public CommentDataStore(String filepath) {
        super(filepath);
        g = new Gson();
    }

    @Override
    public void put(Comment c) {
        data.put(c.getId(), c);
    }

    @Override
    public void delete(String key) {
        Comment c = data.get(key);
        if (c == null) return;
        c.setDeleted(true);
    }
}
