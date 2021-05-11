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
}
