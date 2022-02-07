package com.example.apipixabayimagesapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apipixabayimagesapp.Model.ModelClass;
import com.example.apipixabayimagesapp.R;

import java.util.ArrayList;

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
