package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPageFragmentAdapter extends RecyclerView.Adapter<MyPageFragmentAdapter.CustomViewHolder> {

    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<MyPageFragmentMainData> arrayList;
    //생성자
    public MyPageFragmentAdapter(ArrayList<MyPageFragmentMainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    //리스트뷰가 처음으로 생성될 때 생명주기
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // mypage_recycleview_items.xml 과 연동하기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_recycleview_items, parent, false);
        //holder로 mypage_recycleview_items.xml 의 뷰를 컨택할 수 있음.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    //실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull MyPageFragmentAdapter.CustomViewHolder holder, int position) {
        //프로필 사진 가져오기
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());

        //리스트뷰가 클릭되었을 때,
        holder.itemView.setTag(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String curName = holder.tv_name.getText().toString();
//                //curName이 토스트 메뉴로 클릭되게
//                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
//            }
//        });

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
