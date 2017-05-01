package com.github.ljfio.requester;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/request-custom.html
 */

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.util.Map;

public class DeleteRequest<T> extends BaseRequest<T> {

    /**
     * Make a DELETE request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param headers Map of request headers
     * @param listener Listener to receive the request
     * @param errorListener Error Listener for Volley
     */
    public DeleteRequest(String url, Map<String, String> headers, Class<T> responseClass,
                         Listener<T> listener, ErrorListener errorListener) {
        super(Method.DELETE, url, headers, responseClass, listener, errorListener);
    }
}