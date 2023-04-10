package com.zerobase.m1.util;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {
    final OkHttpClient client = new OkHttpClient();
    public String get(String url) throws IOException {
        Request request = new Request.Builder()
            .url(url)
            .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
