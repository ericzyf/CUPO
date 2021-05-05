package edu.cuhk.csci3310.cupo;

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
        private int id;
        public String email;
        public String username;
        public String password;
        private long timestamp;

        public User(final int id, final String email, final String username, final String password, final long timestamp) {
            this.id = id;
            this.email = email;
            this.username = username;
            this.password = password;
            this.timestamp = timestamp;
        }

        public int getId() {
            return id;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    public void createUser(final String email, final String password, Callback cb) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {

        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(api("/create_user"))
                .post(body)
                .build();

        Log.d("cupo", "POST /create_user\n" + json.toString());

        httpClient.newCall(request).enqueue(cb);
    }
}
