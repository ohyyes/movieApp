package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecoAdapter extends RecyclerView.Adapter<RecoAdapter.CustomViewHolder> {
    private ArrayList<MovieItem> movieList;

    public RecoAdapter(ArrayList<MovieItem> mMovieList) {
        this.movieList = mMovieList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        RecoAdapter.CustomViewHolder holder = new RecoAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecoAdapter.CustomViewHolder holder, int position) {
        holder.img.setImageResource(movieList.get(position).getImg());
        holder.name.setText(movieList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovieList(ArrayList<MovieItem> mMovieList){
        this.movieList = mMovieList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView img;
        protected TextView name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.movieImg);
            this.name = (TextView) itemView.findViewById(R.id.movieName);

        }
    }
}
