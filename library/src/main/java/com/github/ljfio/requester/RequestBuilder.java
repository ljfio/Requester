package com.github.ljfio.requester;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.util.Map;

/**
 * Created by Luke on 28/04/2017.
 * Built this to speed up making requests or something
 */

public class RequestBuilder<TRes, TReq> {
    private int method;

    private Class<TRes> responseClass;
    private Class<TReq> requestClass;

    private Map<String, String> params;
    private Map<String, String> headers;

    private Listener<TRes> listener;
    private ErrorListener errorListener;

    private String url;

    private TReq requestObject;

    public RequestBuilder(int method, Class<TRes> responseClass) {
        this.method = method;
        this.responseClass = responseClass;
    }

    public RequestBuilder(int method, Class<TRes> responseClass, Class<TReq> requestClass) {
        this(method, responseClass);
        this.requestClass = requestClass;
    }

    public static <URes> RequestBuilder<URes, ?> get(Class<URes> responseClass) {
        RequestBuilder<URes, ?> getRequest = new RequestBuilder<>(Method.GET, responseClass);
        return getRequest;
    }

    public static <URes> RequestBuilder<URes, ?> delete(Class<URes> responseClass) {
        RequestBuilder<URes, ?> deleteRequest = new RequestBuilder<>(Method.DELETE, responseClass);
        return deleteRequest;
    }

    public static <URes, UReq> RequestBuilder<URes, UReq> post(Class<URes> responseClass, Class<UReq> requestClass) {
        RequestBuilder<URes, UReq> getRequest = new RequestBuilder<>(Method.POST, responseClass, requestClass);
        return getRequest;
    }

    public static <URes, UReq> RequestBuilder<URes, UReq> put(Class<URes> responseClass, Class<UReq> requestClass) {
        RequestBuilder<URes, UReq> getRequest = new RequestBuilder<>(Method.PUT, responseClass, requestClass);
        return getRequest;
    }

    public RequestBuilder<TRes, TReq> setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder<TRes, TReq> setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public RequestBuilder<TRes, TReq> setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder<TRes, TReq> setRequestObject(TReq requestObject) {
        this.requestObject = requestObject;
        return this;
    }

    public RequestBuilder<TRes, TReq> setListener(Listener<TRes> listener) {
        this.listener = listener;
        return this;
    }

    public RequestBuilder<TRes, TReq> setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public Request<TRes> build() {
        if (method == Method.GET) {
            return new GetRequest<TRes>(url, headers, params, responseClass, listener, errorListener);
        } else if(method == Method.POST) {
            return new PostRequest<TRes, TReq>(url, headers, responseClass, requestClass, requestObject, listener, errorListener);
        } else if(method == Method.PUT) {
            return new PutRequest<TRes, TReq>(url, headers, responseClass, requestClass, requestObject, listener, errorListener);
        } else if(method == Method.DELETE) {
            return new DeleteRequest<TRes>(url, headers, responseClass, listener, errorListener);
        } else {
            return null;
        }
    }

    public void execute(RequestWorker worker) {
        worker.addToRequestQueue(build());
    }
}

