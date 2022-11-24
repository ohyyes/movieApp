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

    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<MovieItem> movieList;
    //생성자
    public RecoAdapter(ArrayList<MovieItem> mMovieList) {
        this.movieList = mMovieList;
    }

    @NonNull
    @Override
    //리스트뷰가 처음으로 생성될 때 생명주기
    public RecoAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        RecoAdapter.CustomViewHolder holder = new RecoAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    //실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull RecoAdapter.CustomViewHolder holder, int position) {
        //프로필 사진 가져오기
        holder.img.setImageResource(movieList.get(position).getImg());
        holder.name.setText(movieList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (movieList!=null){
            return movieList.size();
        }
        else return 0;
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
