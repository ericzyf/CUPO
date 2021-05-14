package edu.cuhk.csci3310.cupo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

// singleton
public final class Backend {

    private static final MediaType JSON = MediaType.parse("application/json");
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static Backend Instance = null;

    private Backend() {

    }

    public static Backend getInstance() {
        if (Instance == null) {
            Instance = new Backend();
        }
        return Instance;
    }

    public String api(final String route) {
        return "http://118.195.184.108:5000" + route;
    }

    public static class User {
        public String email;
        public String username;
        private long timestamp;
        public String gender;
        public String phone;
        public String bio;

        public User(final String email, final String username, final long timestamp) {
            this.email = email;
            this.username = username;
            this.timestamp = timestamp;
            this.gender = "";
            this.phone = "";
            this.bio = "";
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    public static class Post implements Parcelable {
        private long id;
        private String email;
        private String username;
        private String title;
        private String content;
        private long timestamp;

        public Post(
                final long id,
                final String email,
                final String username,
                final String title,
                final String content,
                final long timestamp
        ) {
            this.id = id;
            this.email = email;
            this.username = username;
            this.title = title;
            this.content = content;
            this.timestamp = timestamp;
        }

        protected Post(Parcel in) {
            id = in.readLong();
            email = in.readString();
            username = in.readString();
            title = in.readString();
            content = in.readString();
            timestamp = in.readLong();
        }

        public static final Creator<Post> CREATOR = new Creator<Post>() {
            @Override
            public Post createFromParcel(Parcel in) {
                return new Post(in);
            }

            @Override
            public Post[] newArray(int size) {
                return new Post[size];
            }
        };

        public long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(id);
            dest.writeString(email);
            dest.writeString(username);
            dest.writeString(title);
            dest.writeString(content);
            dest.writeLong(timestamp);
        }
    }

    public static class PostReply {
        private String email;
        private String username;
        private String content;
        private long timestamp;

        public PostReply(final String email, final String username, final String content, final long timestamp) {
            this.email = email;
            this.username = username;
            this.content = content;
            this.timestamp = timestamp;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getContent() {
            return content;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    public void verificationPost(final String email, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/v"))
                .post(body)
                .build();

        Log.d("cupo", "POST /v " + email);

        httpClient.newCall(request).enqueue(cb);
    }

    public void createUser(final String email, final String password, final String code, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
            json.put("code", code);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/users"))
                .post(body)
                .build();

        Log.d("cupo", "POST /users\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void auth(final String email, final String password, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/auth"))
                .post(body)
                .build();

        Log.d("cupo", "POST /auth\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void updateUsername(final String email, final String username, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("username", username);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/users/username"))
                .put(body)
                .build();

        Log.d("cupo", "PUT /users/username\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void updatePassword(final String email, final String password, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/users/password"))
                .put(body)
                .build();

        Log.d("cupo", "PUT /users/password\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void updateGender(final String email, final String gender, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("gender", gender);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/users/gender"))
                .put(body)
                .build();

        Log.d("cupo", "PUT /users/gender\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void updatePhone(final String email, final String phone, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("phone", phone);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/users/phone"))
                .put(body)
                .build();

        Log.d("cupo", "PUT /users/phone\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void updateBio(final String email, final String bio, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("bio", bio);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/users/bio"))
                .put(body)
                .build();

        Log.d("cupo", "PUT /users/bio\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void userInfo(final String email, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/user_info"))
                .put(body)
                .build();

        Log.d("cupo", "PUT /user_info\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void createPost(final String email, final String title, final String content, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("title", title);
            json.put("content", content);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/posts"))
                .post(body)
                .build();

        Log.d("cupo", "POST /posts\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void getAllPosts(Callback cb) {
        Request request = new Request.Builder()
                .url(api("/posts"))
                .build();

        Log.d("cupo", "GET /posts");

        httpClient.newCall(request).enqueue(cb);
    }

    public void createPostReply(final long postId, final String email, final String content, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("post_id", postId);
            json.put("email", email);
            json.put("content", content);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/post_replies"))
                .post(body)
                .build();

        Log.d("cupo", "POST /post_replies\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }

    public void getPostReplies(final long postId, Callback cb) {
        Request request = new Request.Builder()
                .url(api("/post_replies/" + postId))
                .build();

        Log.d("cupo", "GET /post_replies/" + postId);

        httpClient.newCall(request).enqueue(cb);
    }
}
