package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewFragmentAdapter extends RecyclerView.Adapter<ReviewFragmentAdapter.CustomViewHolder> {

    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<ReviewFragmentMainData> arrayList;

    //생성자
    public ReviewFragmentAdapter(ArrayList<ReviewFragmentMainData> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    //리스트뷰가 처음으로 생성될 때 생명주기
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recyclerview_items, parent, false);
        //holder로 review_recycleview_items.xml 의 뷰를 컨택할 수 있음.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    //실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull ReviewFragmentAdapter.CustomViewHolder holder, int position) {
        //프로필 사진 가져오기
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.iv_profile.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_my_rate.setText(String.valueOf(arrayList.get(position).getTv_my_rate()));
        holder.tv_review_date.setText(arrayList.get(position).getTv_review_date());


        if (arrayList.get(position).getTv_review().length() > 35) {
            holder.tv_review.setText(arrayList.get(position).getTv_review().substring(0, 34) + "...");
        } else {
            holder.tv_review.setText(arrayList.get(position).getTv_review());
        }


        //리스트뷰가 클릭되었을 때,
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.tv_name.getText().toString();
                //curName이 토스트 메뉴로 클릭되게
                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        //편집 버튼 클릭 여부에 따라 체크박스 표시
        if (ck == 1){ //수정모드이므로 체크박스 보이게
            holder.checkbox.setVisibility(View.VISIBLE);
        }
        else {
            holder.checkbox.setVisibility(View.GONE);
            holder.checkbox.setChecked(false);
        }



    }


    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        } else return 0;
    }

    // SearchFragment.java 에서 검색 for문 후 만들어진 search_list 를 어댑터의 data 로 바꿀 때 쓰임.
    // 어댑터의 data 를 newList 로 바꾸고 notifyDataSetChanged()로 리사이클러뷰에게 데이터가 변했다고 알려주는 역할.
    public void setItems(ArrayList<ReviewFragmentMainData> newList) {
        this.arrayList = newList;
        notifyDataSetChanged();
    }

    int ck = 0;

    public void updateCheckbox(int n){
        ck = n;
        notifyDataSetChanged();}


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView tv_name, tv_my_rate, tv_review_date, tv_review;
        protected CheckBox checkbox;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_my_rate = (TextView) itemView.findViewById(R.id.tv_my_rate);
            this.tv_review_date = (TextView) itemView.findViewById(R.id.tv_review_date);
            this.tv_review = (TextView) itemView.findViewById(R.id.tv_review);
            this.checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);


        }
    }
}
