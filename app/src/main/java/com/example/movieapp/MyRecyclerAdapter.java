package com.example.movieapp;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<ItemData> itemData;
    public MyRecyclerAdapter(ArrayList<ItemData> itemData) {
        this.itemData = itemData;
    }

    public interface MyRecyclerViewClickListener{
        void onItemClicked(int position);
        void onTitleClicked(int position);
        void onItemLongClicked(int position);
        void onImageViewClicked(int position);
    }


    private MyRecyclerViewClickListener mListener;

    public void setOnClickListener(MyRecyclerViewClickListener listener) {
        this.mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ItemData item = itemData.get(position);
        holder.name.setText(item.getmName());
        holder.poster.setImageResource(item.getmPoster());

        if (mListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos);
                }
            });
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onTitleClicked(pos);
                }
            });
            holder.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onImageViewClicked(pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClicked(holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            poster = itemView.findViewById(R.id.iv_poster);

            //이미지뷰 원형으로 표시
            poster.setClipToOutline(true);
        }
    }

    //리스트 삭제 이벤트
    public void remove(int position){
        try {
            itemData.remove(position);
            notifyDataSetChanged();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
