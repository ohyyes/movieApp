package com.example.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.MovieMainData;

import java.util.ArrayList;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {

    private ArrayList<MovieMainData> movieMainData;
    public HomeFragmentAdapter(ArrayList<MovieMainData> movieMainData) {
        this.movieMainData = movieMainData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_recyclerview_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MovieMainData item = movieMainData.get(position);
        holder.name.setText(item.getTv_name());
        holder.poster.setImageResource(item.getIv_poster());
        holder.poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영


    }

    @Override
    public int getItemCount() {
        return movieMainData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            poster = itemView.findViewById(R.id.iv_poster);

            //이미지뷰 원형으로 표시
            poster.setClipToOutline(true);
        }
    }

}
