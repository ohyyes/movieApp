package com.example.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.ReviewMainData;
import com.example.movieapp.activity.HomeActivity;

import java.util.ArrayList;

public class MyPageFragmentAdapter extends RecyclerView.Adapter<MyPageFragmentAdapter.CustomViewHolder> {

    //홈액티비티 가져오기 (선언)
    HomeActivity homeActivity;

    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<ReviewMainData> arrayList;
    //생성자
    public MyPageFragmentAdapter(ArrayList<ReviewMainData> arrayList, Context mContext) {
        this.arrayList = arrayList;

        // 어댑터에서는 다른 엑티비티를 가져올 때 Context 로 가져옴 !
        this.homeActivity = ((HomeActivity) mContext);
    }



    @NonNull
    @Override
    //리사이클러뷰가 처음으로 생성될 때 생명주기
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // mypage_recycleview_items.xml 과 연동하기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_recyclerview_items, parent, false);
        //holder로 mypage_recycleview_items.xml 의 뷰를 컨택할 수 있음.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    //실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull MyPageFragmentAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //데이터 가져와서 화면에 보여주기
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_poster());


        //리스트뷰가 클릭되었을 때,
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 눌린 아이템 arraylist의 position번째 아이템 객체 가져오기
                ReviewMainData review_item = arrayList.get(position);
                //이동할 프래그먼트에 클릭된 아이템 객체 전달
                homeActivity.onFragmentChange(1, review_item);
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


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
        }
    }
}
