package io.ljf.requester;

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
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 *
 */
public abstract class BaseRequest<T> extends Request<T> {
    protected final Gson gson = new Gson();

    private Map<String, String> headers;
    private Listener<T> listener;

    public BaseRequest(int method, String url, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);

        this.listener = listener;
    }

    public BaseRequest(int method, String url, Map<String, String> headers, Listener<T> listener, ErrorListener errorListener) {
        this(method, url, listener, errorListener);

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

            return Response.success(gson.fromJson(json, getResponseClass()), HttpHeaderParser.parseCacheHeaders(response));
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

    protected Class<T> getResponseClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
