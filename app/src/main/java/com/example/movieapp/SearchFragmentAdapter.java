package com.example.movieapp;

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

        // mypage_recycleview_items.xml 과 연동하기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycleview_items, parent, false);
        //holder로 mypage_recycleview_items.xml 의 뷰를 컨택할 수 있음.
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    //실제 추가될 때 생명주기
    public void onBindViewHolder(@NonNull SearchFragmentAdapter.CustomViewHolder holder, int position) {
        //프로필 사진 가져오기
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
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
                String curName = holder.tv_name.getText().toString();
                //curName이 토스트 메뉴로 클릭되게
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

    // SearchFragment.java 에서 검색 for문 후 만들어진 search_list 를 어댑터의 data 로 바꿀 때 쓰임.
    // 어댑터의 data 를 newList 로 바꾸고 notifyDataSetChanged()로 리사이클러뷰에게 데이터가 변했다고 알려주는 역할.
    public void setItems(ArrayList<SearchFragmentMainData> newList){
        this.arrayList = newList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView tv_name, tv_rate, tv_gerne, tv_date, tv_running_time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.tv_gerne = (TextView) itemView.findViewById(R.id.tv_gerne);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_running_time = (TextView) itemView.findViewById(R.id.tv_running_time);

        }
    }
}
