/*
 * Copyright (c) 2017 Luke Fisher
 * This file is part of Requester which is released under MIT.
 * See file LICENSE.md or go to http://github.com/ljfio/Requester for full license details.
 */

package com.github.ljfio.requester;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/request-custom.html
 */

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.util.Map;

public class PostRequest<TRes, TReq> extends BaseWithBodyRequest<TRes, TReq> {

    /**
     *
     * @param url
     * @param headers
     * @param requestObject
     * @param listener
     * @param errorListener
     */
    public PostRequest(String url, Map<String, String> headers,
                       Class<TRes> responseClass, Class<TReq> requestClass, TReq requestObject,
                       Listener<TRes> listener, ErrorListener errorListener) {
        super(Method.POST, url, headers, responseClass, requestClass, requestObject, listener, errorListener);

    }
}