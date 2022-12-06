package com.example.movieapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.DataListReady;
import com.example.movieapp.Movie;
import com.example.movieapp.R;
import com.example.movieapp.adapter.HomeFragmentAdapter;
import com.example.movieapp.adapter.RecFragmentAdapter;
import com.example.movieapp.data.HomeFragmentMainData;
import com.example.movieapp.data.RecFragmentMainData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecFragment extends Fragment {

    int [] movie_poster = {R.drawable.m0, R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4, R.drawable.m5,
            R.drawable.m6, R.drawable.m7, R.drawable.m8, R.drawable.m9, R.drawable.m10, R.drawable.m11, R.drawable.m12,
            R.drawable.m13, R.drawable.m14, R.drawable.m15, R.drawable.m16, R.drawable.m17, R.drawable.m18, R.drawable.m19,
            R.drawable.m20, R.drawable.m21, R.drawable.m22, R.drawable.m23, R.drawable.m24, R.drawable.m25, R.drawable.m26,
            R.drawable.m27, R.drawable.m28, R.drawable.m29, R.drawable.m30, R.drawable.m31, R.drawable.m32, R.drawable.m33,
            R.drawable.m34, R.drawable.m35, R.drawable.m36, R.drawable.m37, R.drawable.m38, R.drawable.m39, R.drawable.m40,
            R.drawable.m41, R.drawable.m42, R.drawable.m43, R.drawable.m44, R.drawable.m45, R.drawable.m46, R.drawable.m47,
            R.drawable.m48, R.drawable.m49, R.drawable.m50, R.drawable.m51, R.drawable.m52, R.drawable.m53, R.drawable.m54,
            R.drawable.m55, R.drawable.m56, R.drawable.m57, R.drawable.m58, R.drawable.m59, R.drawable.m60, R.drawable.m61,
            R.drawable.m62};

    //------------------ 규원 -------------------
    RecyclerView recoRecyclerView;
    RecyclerView.Adapter recoAdapter;
    RecyclerView.LayoutManager layoutManager;

    public ArrayList<RecFragmentMainData> movieList;
    public List<List<String>> dataList;
    public ArrayList<String> resultList; // 추천 알고리즘 결과값
    public static ArrayList<String> MBTIList; // 사용자 엠비티아이 리스트
    public ArrayList<String> movieNumList;

    private View view;
    //------------------- 규원 -----------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rec, container, false);
        recoRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewReco);
        recoRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recoRecyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        resultList = new ArrayList<>();
        movieList = new ArrayList<>();
        movieNumList = new ArrayList<>();

        dataList = DataListReady.data_list;

        for (int i = 0; i < dataList.size(); i++) {
            int cnt = 0;
            if (dataList.get(i).get(1).equals(MBTIList.get(0))) cnt++;
            if (dataList.get(i).get(2).equals(MBTIList.get(1))) cnt++;
            if (dataList.get(i).get(3).equals(MBTIList.get(2))) cnt++;
            if (dataList.get(i).get(4).equals(MBTIList.get(3))) cnt++;

            if (cnt >= 3) {
                movieNumList.add(String.valueOf(i));
                resultList.add(dataList.get(i).get(0));
            }
        }

        recoAdapter = new RecFragmentAdapter(movieList, getContext());

        for (int i = 0; i < resultList.size(); i++) {
            int tmpPosNum = movie_poster[Integer.parseInt(movieNumList.get(i))];
            movieList.add(new RecFragmentMainData(tmpPosNum, resultList.get(i)));
        }

        System.out.println("dataList" + dataList);
        System.out.println("resultList" + resultList);
        System.out.println("movieNumList" + movieNumList);
        System.out.println("movieList" + movieList);
        System.out.println("MBTIList" + MBTIList);

        recoRecyclerView.setAdapter(recoAdapter);

        return view;
    }
}