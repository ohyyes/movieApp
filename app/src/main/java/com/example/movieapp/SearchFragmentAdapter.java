package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFragmentAdapter extends RecyclerView.Adapter<SearchFragmentAdapter.CustomViewHolder> {

    //홈액티비티 가져오기 (선언)
    HomeActivity homeActivity;

    //리스트뷰의 아이템들을 담을 리스트
    private ArrayList<SearchFragmentMainData> arrayList;
    //생성자
    public SearchFragmentAdapter(ArrayList<SearchFragmentMainData> arrayList, Context mContext) {
        this.arrayList = arrayList;

        // 어댑터에서는 다른 엑티비티를 가져올 때 Context 로 가져옴 !
        this.homeActivity = ((HomeActivity) mContext);
    }

    @NonNull
    @Override
    //리스트뷰가 처음으로 생성될 때 생명주기
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // mypage_recycleview_items.xml 과 연동하기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recyclerview_items, parent, false);
        //holder로 mypage_recycleview_items.xml 의 뷰를 컨택할 수 있음.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    //실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull SearchFragmentAdapter.CustomViewHolder holder, int position) {

        //arrayList 의 각 MainData 객체의 정보를 holder에 전달해서 해당 정보들이 화면에 보이도록 함
        holder.iv_poster.setImageResource(arrayList.get(position).getIv_poster());
        holder.iv_poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_rate.setText(arrayList.get(position).getTv_rate());
        holder.tv_gerne.setText(arrayList.get(position).getTv_gerne());
        holder.tv_date.setText(arrayList.get(position).getTv_date());
        holder.tv_running_time.setText(arrayList.get(position).getTv_running_time());


        //리스트뷰가 클릭되었을 때,
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 클릭된 아이템의 제목 가져오기
                String movie_title = holder.tv_name.getText().toString();

                //homeActivity.java 에서 선언된 onFragmentChange 메소드에 접근해서 index=0 을 전달해 fragmentReview 로 이동 (영화 상세 페이지)
                homeActivity.onFragmentChange(0, movie_title);
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

    // SearchFragment.java 에서 검색 for문 후 만들어진 search_list 를 어댑터의 data 로 바꿀 때 쓰임.
    // 어댑터의 data 를 newList 로 바꾸고 notifyDataSetChanged()로 리사이클러뷰에게 데이터가 변했다고 알려주는 역할.
    public void setItems(ArrayList<SearchFragmentMainData> newList){
        this.arrayList = newList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_poster;
        protected TextView tv_name, tv_rate, tv_gerne, tv_date, tv_running_time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.tv_gerne = (TextView) itemView.findViewById(R.id.tv_gerne);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_running_time = (TextView) itemView.findViewById(R.id.tv_running_time);

        }
    }
}
