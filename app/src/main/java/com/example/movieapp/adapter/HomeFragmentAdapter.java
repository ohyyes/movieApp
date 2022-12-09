package com.example.movieapp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.HomeFragmentMainData;

import java.util.ArrayList;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {

    private ArrayList<HomeFragmentMainData> homeFragmentMainData;
    public HomeFragmentAdapter(ArrayList<HomeFragmentMainData> homeFragmentMainData) {
        this.homeFragmentMainData = homeFragmentMainData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.boxoffice_recyclerview_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        HomeFragmentMainData item = homeFragmentMainData.get(position);
        holder.name.setText(item.getmName());
        holder.poster.setImageBitmap(item.getmPosterBitmap());
        holder.poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
        // 박스오피스 순위 출력
        switch (position) {
            case 0:     // 첫 번째 - 1위
                holder.grade.setImageResource(R.drawable.num_one);
                break;
            case 1:     // 두 번째 - 2위
                holder.grade.setImageResource(R.drawable.num_two);
                break;
            case 2:     // 세 번째 - 3위
                holder.grade.setImageResource(R.drawable.num_three);
                break;
            case 3:     // 네 번째 - 4위
                holder.grade.setImageResource(R.drawable.num_four);
                break;
            case 4:     // 다섯 번째 - 5위
                holder.grade.setImageResource(R.drawable.num_five);
                break;
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(ArrayList<HomeFragmentMainData> arrayList){
        this.homeFragmentMainData = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return homeFragmentMainData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView poster, grade;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            poster = itemView.findViewById(R.id.iv_poster);
            grade = itemView.findViewById(R.id.iv_grade);

            //이미지뷰 테두리 원형으로 표시
            poster.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0, view.getWidth(), view.getHeight(), 40);
                }
            });
            poster.setClipToOutline(true);
        }
    }

}
