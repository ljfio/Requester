package io.ljf.requester;

/**
 * Created by luke on 27/04/2017.
 * https://developer.android.com/training/volley/request-custom.html
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

public class PostOrPutRequest<TRes, TReq> extends BaseRequest<TRes> {
    private final TReq requestObject;
    
    /**
     *
     * @param postNotPut
     * @param url
     * @param headers
     * @param requestObject
     * @param listener
     * @param errorListener
     */
    public PostOrPutRequest(boolean postNotPut, String url, Map<String, String> headers, TReq requestObject,
                            Listener<TRes> listener, ErrorListener errorListener) {
        super(postNotPut ? Method.POST : Method.PUT, url, headers, listener, errorListener);

        this.requestObject = requestObject;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String json = gson.toJson(requestObject, getRequestClass());

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

    protected Class<TReq> getRequestClass() {
        return (Class<TReq>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
    }
}