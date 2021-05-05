package edu.cuhk.csci3310.cupo;

import okhttp3.OkHttpClient;

// singleton
public final class Backend {

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
}
