package com.example.movieapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFragmentAdapter extends RecyclerView.Adapter<SearchFragmentAdapter.CustomViewHolder> {

    //홈액티비티 가져오기 (선언)
    HomeActivity homeActivity;
    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<SearchFragmentMainData> arrayList;

    //생성자
    public SearchFragmentAdapter(ArrayList<SearchFragmentMainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    //리스트뷰가 처음으로 생성될 때 생명주기
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // search_recycleview_items.xml 과 연동하기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recyclerview_items, parent, false);
        // holder로 mypage_recycleview_items.xml 의 뷰를 컨택할 수 있음.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    // 실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull SearchFragmentAdapter.CustomViewHolder holder, int position) {
        // 해당 위치의 영화 정보를 뷰에 설정
        if (arrayList.get(position).getPoster() != null)
            holder.iv_profile.setImageBitmap(arrayList.get(position).getPoster());
        holder.tv_name.setText(arrayList.get(position).getTitle());
        holder.tv_rate.setText(arrayList.get(position).getUserRating());
        holder.tv_genre.setText(arrayList.get(position).getGenre());
        holder.tv_date.setText(arrayList.get(position).getOpenYear());
        holder.tv_running_time.setText(arrayList.get(position).getRunningTime());

        // 리스트뷰가 클릭되었을 때
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 클릭된 아이템의 제목 가져오기
                String curName = holder.tv_name.getText().toString();

                //homeActivity.java 에서 선언된 onFragmentChange 메소드에 접근해서 index=0 을 전달해 fragmentReview 로 이동 (영화 상세 페이지)
                homeActivity.onFragmentChange(0);
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
    public void setItems(ArrayList<SearchFragmentMainData> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView tv_name, tv_rate, tv_genre, tv_date, tv_running_time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.tv_genre = (TextView) itemView.findViewById(R.id.tv_genre);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_running_time = (TextView) itemView.findViewById(R.id.tv_running_time);
        }
    }
}