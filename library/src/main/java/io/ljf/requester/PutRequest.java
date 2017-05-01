package io.ljf.requester;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/request-custom.html
 */

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.util.Map;

public class PutRequest<TRes, TReq> extends BaseWithBodyRequest<TRes, TReq> {

    /**
     *
     * @param url
     * @param headers
     * @param requestObject
     * @param listener
     * @param errorListener
     */
    public PutRequest(String url, Map<String, String> headers, TReq requestObject,
                      Listener<TRes> listener, ErrorListener errorListener) {
        super(Method.PUT, url, headers, requestObject, listener, errorListener);

    }
}