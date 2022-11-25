package com.example.movieapp;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<MainData> arrayList;

    //생성자
    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    //리스트뷰가 처음으로 생성될 때 생명주기
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // search_recycleview_items.xml 과 연동하기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycleview_items, parent, false);
        // holder로 mypage_recycleview_items.xml 의 뷰를 컨택할 수 있음.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    // 실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        // 해당 위치의 영화 정보를 뷰에 설정
        holder.iv_poster.setImageBitmap(arrayList.get(position).getPoster());
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_userRating.setText(arrayList.get(position).getUserRating());
        holder.tv_genre.setText(arrayList.get(position).getGenre());
        holder.tv_openYear.setText(arrayList.get(position).getOpenYear());
        holder.tv_runningTime.setText(arrayList.get(position).getRunningTime());

        // 리스트뷰가 클릭되었을 때
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.tv_title.getText().toString();
                // curName이 토스트 메뉴로 클릭되게
                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });
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
    public void setItems(ArrayList<MainData> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_poster;
        protected TextView tv_title, tv_userRating, tv_genre, tv_openYear, tv_runningTime;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_userRating = (TextView) itemView.findViewById(R.id.tv_userRating);
            this.tv_genre = (TextView) itemView.findViewById(R.id.tv_genre);
            this.tv_openYear = (TextView) itemView.findViewById(R.id.tv_openYear);
            this.tv_runningTime = (TextView) itemView.findViewById(R.id.tv_runningTime);
        }
    }
}