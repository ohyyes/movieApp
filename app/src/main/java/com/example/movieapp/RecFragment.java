package com.example.movieapp;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class RecFragment extends Fragment {
    //------------------ 규원 -------------------
    RecyclerView recoRecyclerView;
    RecyclerView.Adapter recoAdapter;
    RecyclerView.LayoutManager layoutManager;

    public ArrayList<RecFragmentMainData> movieList;
    public List<List<String>> movie_dataList;
    public ArrayList<String> resultList; // 추천 알고리즘 결과값
    public static ArrayList<String> MBTIList; // 사용자 엠비티아이 리스트
    public ArrayList<String> num_movieList;


    //firebase로 movieList읽기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();

    private View view;
    //------------------- 규원 -----------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_rec, container, false);

        ImageView iv_poster = (ImageView) view.findViewById(R.id.iv_poster);

        recoRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewReco);

        recoRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recoRecyclerView.setLayoutManager(layoutManager);

        movie_dataList = new ArrayList<>();
        resultList = new ArrayList<>();
        movieList = new ArrayList<>();
        num_movieList = new ArrayList<>();

        movie_dataList = DataListReady.data_list;

        for (int i = 0; i < movie_dataList.size(); i++) {
            int cnt = 0;
            if (movie_dataList.get(i).get(1).equals(MBTIList.get(0))) cnt++;
            if (movie_dataList.get(i).get(2).equals(MBTIList.get(1))) cnt++;
            if (movie_dataList.get(i).get(3).equals(MBTIList.get(2))) cnt++;
            if (movie_dataList.get(i).get(4).equals(MBTIList.get(3))) cnt++;

            if (cnt >= 3) {
                num_movieList.add(String.valueOf(i));
                resultList.add(movie_dataList.get(i).get(0));
            }
        }
        recoAdapter = new RecFragmentAdapter(movieList, getContext());

        for(int i=0; i<resultList.size(); i++){
            getFirebase(num_movieList.get(i), resultList.get(i));
        }

        recoRecyclerView.setAdapter(recoAdapter);

        return view;
    }

    private void getFirebase(String movieNum , String name){
        String movieName = name;
        userReference.child("movie").child(movieNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Movie movie = snapshot.getValue(Movie.class);
                String imgUrl = movie.getImg(); // 이미지 링크 따오는 함수 !!

                //ImageView에 이미지 저장하기 위해 byte로 변환
                byte[] b = binaryStringToByteArray(imgUrl);
                ByteArrayInputStream is = new ByteArrayInputStream(b);
                Drawable reviewImage = Drawable.createFromStream(is, "reviewImage");
                iv_poster.setImageDrawable(reviewImage);

                System.out.println("imgUrl" + imgUrl);
                System.out.println("movieName" + movieName);

                movieList.add(new RecFragmentMainData(imgUrl, movieName));

                recoAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // 스트링을 바이너리 바이트 배열로
    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    // 스트링을 바이너리 바이트로
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }
}