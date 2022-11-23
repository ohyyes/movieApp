package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemSheetAdapter extends RecyclerView.Adapter<ItemSheetAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ItemSheetList> datas;
    ProductInterface listener;

    //인터페이스
    public interface ProductInterface{
        void onItemClick(ItemSheetList data);
        void onFavoriteClick(ItemSheetList data, int position);
    }


    //사용할 생성자
    public ItemSheetAdapter(Context mContext, ArrayList<ItemSheetList> datas, ProductInterface listener) {
        this.mContext = mContext;
        this.datas = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //어댑터에서 바인딩 쓰는 법
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(holder ,datas.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemProductBinding binding; //바인딩1

        public ViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding; //바인딩2
        }

        public void bind(ViewHolder holder, final ProductData productData) {

            try{
                Glide.with(holder.itemView.getContext())
                        .load(productData.getUrls().get(0))
                        .error(R.mipmap.ic_launcher)
                        .thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loading_products)) //로딩 이미지에 gif 넣는 법
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))) //라운드를 줄 수 있다
                        .into(binding.ivProduct);
            }catch (Exception e){
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.swuri_2)
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.drawable.loading_example)//
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(binding.ivProduct);
            }

            binding.tvName.setText(productData.getName());
            binding.tvPrice.setText(
                    new DecimalFormat("###,###,###,###")
                            .format(productData.getPrice()) + "원"
            );
}