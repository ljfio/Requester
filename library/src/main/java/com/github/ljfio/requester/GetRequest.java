package com.github.ljfio.requester;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/request-custom.html
 */

import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class GetRequest<T> extends BaseRequest<T> {
    private Map<String, String> params = null;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param headers Map of request headers
     * @param listener Listener to receive the request
     * @param errorListener Error Listener for Volley
     */
    public GetRequest(String url, Map<String, String> headers, Class<T> responseClass,
                      Listener<T> listener, ErrorListener errorListener) {
        super(Method.GET, url, headers, responseClass, listener, errorListener);
    }

    /**
     * Make a GET request with parameters and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param headers Map of request headers
     * @param params Map of parameter string key values
     * @param listener Listener to receive the request
     * @param errorListener Error Listener for Volley
     */
    public GetRequest(String url, Map<String, String> headers, Map<String, String> params,
                      Class<T> responseClass,
                      Listener<T> listener, ErrorListener errorListener) {
        this(url, headers, responseClass, listener, errorListener);

        this.params = params;
    }

    @Override
    public String getUrl() {
        if (getParams() != null && !getParams().isEmpty()) {
            String encoded = "";

            try {
                for (Map.Entry<String, String> pair : params.entrySet()) {
                    // Check if this is the first key add question mark to start or ampersand to continue
                    encoded += !encoded.contains("?") ? "?" : "&";
                    // Add the key value pair
                    encoded += pair.getKey() + "=" + URLEncoder.encode(pair.getValue(), "UTF-8");
                }
            } catch(UnsupportedEncodingException ex) {
                Log.e(getClass().getSimpleName(), ex.getMessage());
            }

            return super.getUrl() + encoded;
        }

        return super.getUrl();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}