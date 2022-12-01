package com.example.movieapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxViewHolder> {

    // 포스터 비트맵을 담을 리스트
    private ArrayList<Bitmap> arrayList;

    //생성자
    public BoxAdapter( ArrayList<Bitmap> arrayList ) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    //리스트뷰가 처음으로 생성될 때 생명주기
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_boxoffice.xml 과 연동하기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boxoffice, parent, false);
        // holder로 item_boxoffice.xml 의 뷰를 컨택할 수 있음.
        BoxViewHolder holder = new BoxViewHolder(view);

        return holder;
    }

    @Override
    // 실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull BoxAdapter.BoxViewHolder holder, int position) {
        // 해당 위치의 영화 정보를 뷰에 설정
        holder.iv_poster.setImageBitmap(arrayList.get(position));
        switch (position) {
            case 1:
                holder.iv_poster.setImageResource((R.drawable.num_one));
                break;
            case 2:
                holder.iv_poster.setImageResource((R.drawable.num_two));
                break;
            case 3:
                holder.iv_poster.setImageResource((R.drawable.num_three));
                break;
            case 4:
                holder.iv_poster.setImageResource((R.drawable.num_four));
                break;
            case 5:
                holder.iv_poster.setImageResource((R.drawable.num_five));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList!=null){
            return arrayList.size();
        }
        else return 0;
    }

    // (검색) 어댑터의 아이템을 setItems 의 인자로 바꾸고 notifyDataSetChanged()로 리사이클러뷰에게 데이터가 변했다고 알려주는 역할.
    @SuppressLint("NotifyDataSetChanged")
    public void setItems(ArrayList<Bitmap> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public static class BoxViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_poster, iv_ranking;

        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            this.iv_ranking = (ImageView) itemView.findViewById(R.id.iv_ranking);
        }
    }
}