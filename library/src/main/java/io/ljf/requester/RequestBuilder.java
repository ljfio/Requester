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

public class RequestBuilder<TReq, TRes> {
    private int method;

    private Class<TReq> reqClass;
    private Class<TRes> resClass;

    private Map<String, String> params;
    private Map<String, String> headers;

    private Listener<TRes> listener;
    private ErrorListener errorListener;

    private String url;

    private TReq requestObject;

    public RequestBuilder(Class<TReq> requestClass, Class<TRes> responseClass) {
        this.reqClass = requestClass;
        this.resClass = responseClass;
    }

    public static <UReq, URes> RequestBuilder<UReq, URes> get(Class<URes> resClass) {
        RequestBuilder<UReq, URes> getRequest = new RequestBuilder<>(null, resClass);
        getRequest.setMethod(Method.GET);
        return getRequest;
    }

    public static <UReq, URes> RequestBuilder<UReq, URes> post(Class<UReq> reqClass, Class<URes> resClass) {
        RequestBuilder<UReq, URes> getRequest = new RequestBuilder<>(reqClass, resClass);
        getRequest.setMethod(Method.POST);
        return getRequest;
    }

    public static <UReq, URes> RequestBuilder<UReq, URes> put(Class<UReq> reqClass, Class<URes> resClass) {
        RequestBuilder<UReq, URes> getRequest = new RequestBuilder<>(reqClass, resClass);
        getRequest.setMethod(Method.PUT);
        return getRequest;
    }

    public static <UReq, URes> RequestBuilder<UReq, URes> delete(Class<URes> resClass) {
        RequestBuilder<UReq, URes> deleteRequest = new RequestBuilder<>(null, resClass);
        deleteRequest.setMethod(Method.DELETE);
        return deleteRequest;
    }

    private RequestBuilder<TReq, TRes> setMethod(int method) {
        this.method = method;
        return this;
    }

    public RequestBuilder<TReq, TRes> setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder<TReq, TRes> setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public RequestBuilder<TReq, TRes> setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder<TReq, TRes> setRequestObject(TReq requestObject) {
        this.requestObject = requestObject;
        return this;
    }

    public RequestBuilder<TReq, TRes> setListener(Listener<TRes> listener) {
        this.listener = listener;
        return this;
    }

    public RequestBuilder<TReq, TRes> setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public Request<TRes> build() {
        if (method == Method.GET) {
            return new GetRequest<TRes>(url, resClass, headers, params, listener, errorListener);
        } else if(method == Method.POST || method == Method.PUT) {
            boolean isPost = method == Method.POST;
            return new PostOrPutRequest<TReq, TRes>(isPost, url, reqClass, resClass, headers, requestObject, listener, errorListener);
        } else if(method == Method.DELETE) {
            return new DeleteRequest<TRes>(url, resClass, headers, listener, errorListener);
        } else {
            return null;
        }
    }

    public void execute(RequestWorker worker) {
        worker.addToRequestQueue(build());
    }
}

