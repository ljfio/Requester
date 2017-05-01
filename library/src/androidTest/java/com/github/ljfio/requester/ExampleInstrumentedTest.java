package com.github.ljfio.requester;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Semaphore;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("io.ljf.requester", appContext.getPackageName());
    }

    @Test
    public void builder_works() throws Exception {
        final Semaphore mutex = new Semaphore(0);

        Context context = InstrumentationRegistry.getTargetContext();

        RequestBuilder<JsonObject, ?> builder = RequestBuilder.get(JsonObject.class);

        builder.setUrl("http://echo.jsontest.com/key/value");

        builder.setListener(new Response.Listener<JsonObject>() {
                @Override
                public void onResponse(JsonObject response) {
                    JsonElement key = response.get("key");

                    String value = key.getAsString();

                    assertEquals("value", value);

                    mutex.release();
                }
            }
        );

        builder.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fail(error.getMessage());
                mutex.release();
            }
        });

        Request<JsonObject> request = builder.build();

        RequestWorker worker = RequestWorker.getInstance(context);

        worker.addToRequestQueue(request);

        mutex.acquire();
    }
}
