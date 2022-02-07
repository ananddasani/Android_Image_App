package com.example.apipixabayimagesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apipixabayimagesapp.Adapter.CustomAdapter;
import com.example.apipixabayimagesapp.Model.ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    CustomAdapter customAdapter;
    ArrayList<ModelClass> arrayList;

    String url = "https://pixabay.com/api/?key=25385195-495838112e96db5dc2cdf0935&q=science&image_type=photo&pretty=true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        //fetch data from API using volley and insert them into ArrayList
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Request the data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        //extract all necessary data form the particular object
                        String imageUrl = jsonArray.getJSONObject(i).getString("webformatURL");
                        String tags = jsonArray.getJSONObject(i).getString("tags");
                        int views = jsonArray.getJSONObject(i).getInt("views");
                        int likes = jsonArray.getJSONObject(i).getInt("likes");
                        int downloads = jsonArray.getJSONObject(i).getInt("downloads");

                        arrayList.add(new ModelClass(imageUrl, tags, likes, downloads, views));
                        progressBar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Seems Like You Are Offline", Toast.LENGTH_SHORT).show();
            }
        });

        //Trigger the request
        requestQueue.add(jsonObjectRequest);

        customAdapter = new CustomAdapter(arrayList, MainActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerView.setAdapter(customAdapter);
    }
}
