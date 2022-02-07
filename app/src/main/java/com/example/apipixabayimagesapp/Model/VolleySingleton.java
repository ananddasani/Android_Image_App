package com.example.apipixabayimagesapp.Model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private RequestQueue requestQueue;
    private static VolleySingleton mInstance;

    public VolleySingleton(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public RequestQueue getRequestQueue(Context context) {
        return requestQueue;
    }

    public static VolleySingleton getmInstance(Context context) {
        if (mInstance == null)
            mInstance = new VolleySingleton(context);

        return mInstance;
    }
}
