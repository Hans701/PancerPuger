package com.example.pancerpuger;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class volleyconnection {

    private static volleyconnection vInstance;
    private RequestQueue requestQueue;
    private static Context vCtx;

    private volleyconnection (Context context) {
        vCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(vCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized volleyconnection getInstance(Context context) {
        if (vInstance == null) {
            vInstance = new volleyconnection(context);
        }
        return vInstance;
    }

    public<T> void addToRequestQue (Request<T> request) {
        getRequestQueue().add(request);
    }
}
