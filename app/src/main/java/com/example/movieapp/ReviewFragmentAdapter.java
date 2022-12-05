package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewFragmentAdapter extends RecyclerView.Adapter<ReviewFragmentAdapter.CustomViewHolder> {


    HomeActivity homeActivity;
    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<ReviewMainData> arrayList;

    //생성자
    public ReviewFragmentAdapter(ArrayList<ReviewMainData> arrayList, Context mContext) {
        this.arrayList = arrayList;

        // 어댑터에서는 다른 엑티비티를 가져올 때 Context 로 가져옴 !
        this.homeActivity = ((HomeActivity) mContext);
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
    public void onBindViewHolder(@NonNull ReviewFragmentAdapter.CustomViewHolder holder,  int position) {

        final int pos = position;
        //프로필 사진 가져오기
        holder.iv_poster.setImageResource(arrayList.get(position).getIv_poster());
        holder.iv_poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_my_rate.setText(String.valueOf(arrayList.get(position).getTv_my_rate()));
        holder.tv_review_date.setText(arrayList.get(position).getTv_review_date());


        if (arrayList.get(position).getTv_review().length() > 35) {
            holder.tv_review.setText(arrayList.get(position).getTv_review().substring(0, 34) + "...");
        } else {
            holder.tv_review.setText(arrayList.get(position).getTv_review());
        }


        //감상평 아이템 클릭시
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ck == 0) { //일반모드일 때
                    // 현재 눌린 아이템 arraylist의 position번째 아이템 객체 가져오기
                    ReviewMainData review_item = arrayList.get(position);
                    //이동할 프래그먼트에 클릭된 아이템 객체 전달
                    homeActivity.onFragmentChange(1, review_item);
                } else { //편집모드일 땐 이동 X
                }
            }
        });

        //편집 버튼 클릭 여부에 따라 체크박스 표시
        if (ck == 1) { //편집모드이므로 체크박스 보이게
            holder.checkbox.setVisibility(View.VISIBLE);
        } else {
            holder.checkbox.setVisibility(View.GONE);
            //일반모드일 때 체크박스 초기화
            arrayList.get(position).setSelected(false);
        }

        //체크 여부 설정
        holder.checkbox.setChecked(arrayList.get(position).isSelected());
        holder.checkbox.setTag(arrayList.get(position));

        //체크박스 클릭시
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                ReviewMainData contact = (ReviewMainData) cb.getTag();

                contact.setSelected(cb.isChecked());
                //체크박스와 객체의 체크유무 변수 동기화
                arrayList.get(pos).setSelected(cb.isChecked());
            }
        });

    }


    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        } else return 0;
    }

    public int getSelectedItemCount() {
        if (arrayList != null) {
            int count = 0;
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).isSelected() == true) count++;
            }
            return count;
        } else return 0;
    }

    public ArrayList<ReviewMainData> getArrayList() {
        return arrayList;
    }

    // SearchFragment.java 에서 검색 for문 후 만들어진 search_list 를 어댑터의 data 로 바꿀 때 쓰임.
    // 어댑터의 data 를 newList 로 바꾸고 notifyDataSetChanged()로 리사이클러뷰에게 데이터가 변했다고 알려주는 역할.
    public void setItems(ArrayList<ReviewMainData> newList) {
        this.arrayList = newList;
        notifyDataSetChanged();
    }

    int ck = 0;

    public void updateCheckbox(int n) {
        ck = n;
        notifyDataSetChanged();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_poster;
        protected TextView tv_name, tv_my_rate, tv_review_date, tv_review;
        protected CheckBox checkbox;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_my_rate = (TextView) itemView.findViewById(R.id.tv_my_rate);
            this.tv_review_date = (TextView) itemView.findViewById(R.id.tv_review_date);
            this.tv_review = (TextView) itemView.findViewById(R.id.tv_review);
            this.checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);


        }
    }
}
