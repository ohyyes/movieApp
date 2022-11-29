package com.example.movieapp;

import android.content.Context;
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
    private Context context;

    public RecoAdapter(ArrayList<MovieItem> mMovieList, Context context) {
        this.movieList = mMovieList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recyclerview_items, parent, false);
        CustomViewHolder holder = new RecoAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecoAdapter.CustomViewHolder holder, int position) {

//        Glide.with(holder.itemView)
//                .load(movieList.get(position).getImg())
//                .into(holder.img);
        holder.img.setImageResource(movieList.get(position).getImg());
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
            this.img = (ImageView) itemView.findViewById(R.id.movieImg);
            this.name = (TextView) itemView.findViewById(R.id.movieName);

        }
    }
}
