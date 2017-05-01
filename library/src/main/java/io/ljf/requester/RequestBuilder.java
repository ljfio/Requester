package io.ljf.requester;

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

    private Map<String, String> params;
    private Map<String, String> headers;

    private Listener<TRes> listener;
    private ErrorListener errorListener;

    private String url;

    private TReq requestObject;

    public RequestBuilder(int method) {
        this.method = method;
    }

    public static <URes, UReq> RequestBuilder<URes, UReq> get() {
        RequestBuilder<URes, UReq> getRequest = new RequestBuilder<>(Method.GET);
        return getRequest;
    }

    public static <URes, UReq> RequestBuilder<URes, UReq> post() {
        RequestBuilder<URes, UReq> getRequest = new RequestBuilder<>(Method.POST);
        return getRequest;
    }

    public static <URes, UReq> RequestBuilder<URes, UReq> put() {
        RequestBuilder<URes, UReq> getRequest = new RequestBuilder<>(Method.PUT);
        return getRequest;
    }

    public static <URes, UReq> RequestBuilder<URes, UReq> delete() {
        RequestBuilder<URes, UReq> deleteRequest = new RequestBuilder<>(Method.DELETE);
        return deleteRequest;
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
            return new GetRequest<TRes>(url, headers, params, listener, errorListener);
        } else if(method == Method.POST) {
            return new PostRequest<TRes, TReq>(url, headers, requestObject, listener, errorListener);
        } else if(method == Method.PUT) {
            return new PutRequest<TRes, TReq>(url, headers, requestObject, listener, errorListener);
        } else if(method == Method.DELETE) {
            return new DeleteRequest<TRes>(url, headers, listener, errorListener);
        } else {
            return null;
        }
    }

    public void execute(RequestWorker worker) {
        worker.addToRequestQueue(build());
    }
}

