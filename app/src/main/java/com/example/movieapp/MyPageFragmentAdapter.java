package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPageFragmentAdapter extends RecyclerView.Adapter<MyPageFragmentAdapter.CustomViewHolder> {

    //홈액티비티 가져오기 (선언)
    HomeActivity homeActivity;

    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<MyPageFragmentMainData> arrayList;
    //생성자
    public MyPageFragmentAdapter(ArrayList<MyPageFragmentMainData> arrayList, Context mContext) {
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
    public void onBindViewHolder(@NonNull MyPageFragmentAdapter.CustomViewHolder holder, int position) {
        //프로필 사진 가져오기
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());

        //리스트뷰가 클릭되었을 때,
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* searchFragmentAdapter.java 참고해서 데이터 가져와서 전달하는 코드 작성 */

                //homeActivity.java 에서 선언된 onFragmentChange 메소드에 접근해서 index=1 을 전달해 fragmentReview 로 이동 (감상평 상세 페이지)
                homeActivity.onFragmentChange(1);
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
