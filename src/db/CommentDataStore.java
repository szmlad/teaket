package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Comment;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CommentDataStore extends DataStore<Comment> {
    public CommentDataStore(String filepath) {
        super(filepath);
        g = new Gson();
    }

    private static class NewCommentData {
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
    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, Comment>>() {}.getType());
            if (data == null) data = new HashMap<>();
        }
    }

    @Override
    public void put(Comment c) {
        data.put(c.getId(), c);
    }
}
