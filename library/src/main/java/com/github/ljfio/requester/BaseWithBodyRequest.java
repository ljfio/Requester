package com.github.ljfio.requester;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by luke on 01/05/2017.
 */

public class BaseWithBodyRequest<TRes, TReq> extends BaseRequest<TRes> {
    private final TReq requestObject;
    private final Class<TReq> requestClass;

    public BaseWithBodyRequest(int method, String url, Map<String, String> headers,
                               Class<TRes> responseClass, Class<TReq> requestClass, TReq requestObject,
                               Response.Listener<TRes> listener, Response.ErrorListener errorListener) {
        super(method, url, headers, responseClass, listener, errorListener);

        this.requestObject = requestObject;
        this.requestClass = requestClass;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String json = gson.toJson(requestObject, requestClass);

        try {
            return json.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }

        return null;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}
