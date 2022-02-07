# Android_Image_App
App that show the random images fetching from the pixabay website by using its website

# Code

### VolleySingleton.java
```
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
}T
```

### ModelClass.java
```
public class ModelClass {

    private String imageUrl = "";
    private String tags = "";
    private int likes = 0;
    private int downloads = 0;
    private int views = 0;

    public ModelClass(String imageUrl, String tags, int likes, int downloads, int views) {
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.likes = likes;
        this.downloads = downloads;
        this.views = views;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
```

### CustomAdapter.java
```
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<ModelClass> arrayList;
    Context context;

    public CustomAdapter(ArrayList<ModelClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

        try {
            //set the dynamic info from API to UI
            holder.tags.setText(arrayList.get(position).getTags());
            holder.likes.setText(String.valueOf(arrayList.get(position).getLikes()));
            holder.views.setText(String.valueOf(arrayList.get(position).getViews()));
            holder.downloads.setText(String.valueOf(arrayList.get(position).getDownloads()));
            Glide.with(context).load(arrayList.get(position).getImageUrl()).into(holder.imageView);

            //set on click listener
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Implementing Soon.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.e("onBindError", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView tags, likes, views, downloads;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            tags = itemView.findViewById(R.id.tagTextView);
            likes = itemView.findViewById(R.id.likesTextView);
            views = itemView.findViewById(R.id.viewsTextView);
            downloads = itemView.findViewById(R.id.downloadsTextView);
        }
    }
}
```

#### MainActivity.java
```
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
```

### custom_layout.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <androidx.cardview.widget.CardView
        android:id="@+id/cardView"
        android:layout_width="0dp"
        android:layout_height="200dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        app:cardElevation="6dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/cardConstraintLayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <ImageView
                android:id="@+id/imageView"
                android:layout_width="200dp"
                android:layout_height="150dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:scaleType="centerCrop"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                tools:srcCompat="@tools:sample/avatars" />

            <TextView
                android:id="@+id/tagTextView"
                android:layout_width="250dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:inputType="textMultiLine"
                android:maxLines="1"
                android:text="orion nebula, emission nebula, constellation orion"
                app:layout_constraintBottom_toBottomOf="@+id/tagTV"
                app:layout_constraintStart_toEndOf="@+id/tagTV"
                app:layout_constraintTop_toTopOf="@+id/tagTV"
                tools:ignore="TouchTargetSizeCheck" />

            <TextView
                android:id="@+id/tagTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:layout_marginBottom="8dp"
                android:text="TAGS:"
                android:textColor="@color/black"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/imageView" />

            <TextView
                android:id="@+id/likeTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="Likes:"
                android:textColor="@color/black"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toTopOf="@+id/viewsTV"
                app:layout_constraintStart_toEndOf="@+id/imageView"
                app:layout_constraintTop_toTopOf="@+id/imageView"
                app:layout_constraintVertical_chainStyle="spread" />

            <TextView
                android:id="@+id/viewsTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="Views:"
                android:textColor="@color/black"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toTopOf="@+id/downloadsTV"
                app:layout_constraintStart_toEndOf="@+id/imageView"
                app:layout_constraintTop_toBottomOf="@+id/likeTV" />

            <TextView
                android:id="@+id/downloadsTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="Downloads:"
                android:textColor="@color/black"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toBottomOf="@+id/imageView"
                app:layout_constraintStart_toEndOf="@+id/imageView"
                app:layout_constraintTop_toBottomOf="@+id/viewsTV" />

            <TextView
                android:id="@+id/likesTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="1000"
                app:layout_constraintBottom_toBottomOf="@+id/likeTV"
                app:layout_constraintStart_toEndOf="@+id/likeTV"
                app:layout_constraintTop_toTopOf="@+id/likeTV" />

            <TextView
                android:id="@+id/viewsTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="10000"
                app:layout_constraintBottom_toBottomOf="@+id/viewsTV"
                app:layout_constraintStart_toEndOf="@+id/viewsTV"
                app:layout_constraintTop_toTopOf="@+id/viewsTV" />

            <TextView
                android:id="@+id/downloadsTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="500"
                app:layout_constraintBottom_toBottomOf="@+id/downloadsTV"
                app:layout_constraintStart_toEndOf="@+id/downloadsTV"
                app:layout_constraintTop_toTopOf="@+id/downloadsTV" />
        </androidx.constraintlayout.widget.ConstraintLayout>

    </androidx.cardview.widget.CardView>
</androidx.constraintlayout.widget.ConstraintLayout>
```
