package com.vivian.Weather;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static void sendOKHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static Response sendOKHttpRequest(String address) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        Call call = client.newCall(request);
        Response res = null;
        try{
            res = call.execute();
        }
        catch (IOException exception)
        {

        }
        return res;
    }

    private static boolean isNetworkAvailable() {
        return true;
    }
}
