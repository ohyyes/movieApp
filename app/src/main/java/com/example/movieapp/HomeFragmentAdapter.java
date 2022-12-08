//package com.example.movieapp;
//
//import android.annotation.SuppressLint;
//import android.graphics.Outline;
//import android.graphics.drawable.ShapeDrawable;
//import android.graphics.drawable.shapes.OvalShape;
//import android.os.Build;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewOutlineProvider;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
//
//    private ArrayList<ItemData> dataList;
//    public MyRecyclerAdapter(ArrayList<ItemData> dataList) {
//        this.dataList = dataList;
//    }
//
//    public interface MyRecyclerViewClickListener{
//        void onItemClicked(int position);
//        void onTitleClicked(int position);
//        void onItemLongClicked(int position);
//        void onImageViewClicked(int position);
//    }
//
//
//    private MyRecyclerViewClickListener mListener;
//
//    public void setOnClickListener(MyRecyclerViewClickListener listener) {
//        this.mListener = listener;
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_list, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//        ItemData item = dataList.get(position);
//        holder.name.setText(item.getmName());
//        holder.poster.setImageBitmap(item.getmPoster());
//
//        if (mListener != null) {
//            final int pos = position;
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mListener.onItemClicked(pos);
//                }
//            });
//            holder.name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mListener.onTitleClicked(pos);
//                }
//            });
//            holder.poster.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mListener.onImageViewClicked(pos);
//                }
//            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mListener.onItemLongClicked(holder.getAdapterPosition());
//                    return true;
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    public void setItems(ArrayList<ItemData> arrayList){
//        this.dataList = arrayList;
//        notifyDataSetChanged();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView name;
//        ImageView poster;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.tv_name);
//            poster = itemView.findViewById(R.id.iv_poster);
//
//            //이미지뷰 원형으로 표시
//            poster.setOutlineProvider(new ViewOutlineProvider() {
//                @Override
//                public void getOutline(View view, Outline outline) {
//                    outline.setRoundRect(0,0, view.getWidth(), view.getHeight(), 40);
//                }
//            });
//            poster.setClipToOutline(true);
//        }
//    }
//
//    //리스트 삭제 이벤트
//    public void remove(int position){
//        try {
//            dataList.remove(position);
//            notifyDataSetChanged();
//        } catch (IndexOutOfBoundsException e){
//            e.printStackTrace();
//        }
//    }
//}