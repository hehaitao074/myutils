package com.iss.httpclient;

import java.net.HttpURLConnection;

import android.os.Build;

import com.iss.httpclient.core.AbsHttpClient;
import com.iss.httpclient.core.HttpDelete;
import com.iss.httpclient.core.HttpGet;
import com.iss.httpclient.core.HttpHead;
import com.iss.httpclient.core.HttpMethod;
import com.iss.httpclient.core.HttpPost;
import com.iss.httpclient.core.HttpPut;
import com.iss.httpclient.core.HttpRequestException;
import com.iss.httpclient.core.HttpResponse;
import com.iss.httpclient.core.ParameterList;

public class NormalHttpClient extends AbsHttpClient {

    public NormalHttpClient() {
        super();
    }

    static {
        disableConnectionReuseIfNecessary();
        // See http://code.google.com/p/basic-http-client/issues/detail?id=8
        // if (Build.VERSION.SDK_INT > 8)
        // ensureCookieManager();
    }

    /**
     * Work around bug in {@link HttpURLConnection} on older versions of
     * Android.
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    @SuppressWarnings("deprecation")
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    @Override
    public HttpResponse head(String path, ParameterList params) throws HttpRequestException{
        HttpMethod req = new HttpHead(path, params);
        return tryMany(req);
    }

    @Override
    public HttpResponse get(String path, ParameterList params) throws HttpRequestException {
        HttpMethod req = new HttpGet(path, params);
        return tryMany(req);
    }

    @Override
    public HttpResponse post(String path, ParameterList params) throws HttpRequestException {
        HttpMethod req = new HttpPost(path, params);
        return tryMany(req);
    }

    @Override
    public HttpResponse post(String path, String contentType, byte[] data)
            throws HttpRequestException {
        HttpMethod req = new HttpPost(path, null, contentType, data);
        return tryMany(req);
    }

    @Override
    public HttpResponse put(String path, String contentType, byte[] data)
            throws HttpRequestException {
        HttpMethod req = new HttpPut(path, null, contentType, data);
        return tryMany(req);
    }

    @Override
    public HttpResponse delete(String path, ParameterList params) throws HttpRequestException {
        HttpMethod req = new HttpDelete(path, params);
        return tryMany(req);
    }

}
