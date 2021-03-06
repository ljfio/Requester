/*
 * Copyright (c) 2017 Luke Fisher
 * This file is part of Requester which is released under MIT.
 * See file LICENSE.md or go to http://github.com/ljfio/Requester for full license details.
 */

package com.github.ljfio.requester;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *
 */
public abstract class BaseRequest<T> extends Request<T> {
    protected final Gson gson = new Gson();

    private final Class<T> responseClass;

    private Map<String, String> headers;
    private Listener<T> listener;

    public BaseRequest(int method, String url, Class<T> responseClass,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);

        this.listener = listener;
        this.responseClass = responseClass;
    }

    public BaseRequest(int method, String url, Map<String, String> headers, Class<T> responseClass,
                       Listener<T> listener, ErrorListener errorListener) {
        this(method, url, responseClass, listener, errorListener);

        this.headers = headers;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(gson.fromJson(json, responseClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }
}
