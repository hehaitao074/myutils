package com.iss.httpclient.core;

/**
 * An HTTP GET request.
 * 
 * @author David M. Chandler
 */
public class HttpGet extends HttpMethod {

    /**
     * Constructs an HTTP GET request.
     * 
     * @param path Partial URL
     * @param params Name-value pairs to be appended to the URL
     */
    public HttpGet(String path, ParameterList params) {
        super(path, params);
        this.methodType = MethodType.GET;
    }

}
