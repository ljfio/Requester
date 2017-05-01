package io.ljf.requester;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/request-custom.html
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class PostOrPutRequest<TReq, TRes> extends Request<TRes> {
    private final Gson gson = new Gson();
    
    private final Class<TRes> responseClass;
    private final Class<TReq> requestClass;
    
    private final Map<String, String> headers;
    private final Listener<TRes> listener;

    private final TReq requestObject;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url      URL of the request to make
     * @param requestClass Relevant class object, for Gson's reflection
     * @param headers  Map of request headers
     */
    public PostOrPutRequest(boolean postNotPut, String url, Class<TReq> requestClass, Class<TRes> responseClass,
                            Map<String, String> headers, TReq requestObject,
                            Listener<TRes> listener, ErrorListener errorListener) {
        super(postNotPut ? Method.POST : Method.PUT, url, errorListener);

        this.requestClass = requestClass;
        this.responseClass = responseClass;

        this.requestObject = requestObject;

        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(TRes response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String json = gson.toJson(requestObject, requestClass);

        Log.i("PostOrPutRequest", "request: " + json);

        try {
            return json.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }

        return null;
    }

    @Override
    protected Response<TRes> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            Log.i("PostOrPutRequest", "response: " + json);

            return Response.success(gson.fromJson(json, responseClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}