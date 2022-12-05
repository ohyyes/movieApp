package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;

public class RecFragmentAdapter extends RecyclerView.Adapter<RecFragmentAdapter.CustomViewHolder> {
    private ArrayList<RecFragmentMainData> movieList;
    private Context context;

    public RecFragmentAdapter(ArrayList<RecFragmentMainData> mMovieList, Context context) {
        this.movieList = mMovieList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recyclerview_items, parent, false);
        CustomViewHolder holder = new RecFragmentAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecFragmentAdapter.CustomViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(movieList.get(position).getImg())
                .into(holder.img);
        //holder.img.setImageResource(movieList.get(position).getImg());
        holder.name.setText(movieList.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return (movieList != null ? movieList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView img;
        protected TextView name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.iv_poster);
            this.name = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }
}
