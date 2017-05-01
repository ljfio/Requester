package io.ljf.requester;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by luke on 01/05/2017.
 */

public class BaseWithBodyRequest<TRes, TReq> extends BaseRequest<TRes> {
    private final TReq requestObject;

    public BaseWithBodyRequest(int method, String url, Map<String, String> headers, TReq requestObject,
                               Response.Listener<TRes> listener, Response.ErrorListener errorListener) {
        super(method, url, headers, listener, errorListener);

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
