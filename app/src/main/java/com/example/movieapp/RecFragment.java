package com.example.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.examaple.movieapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecFragment extends Fragment {
    RecyclerView recoRecyclerView;
    RecyclerView.Adapter recoAdapter;
    RecyclerView.LayoutManager layoutManager;

    public ArrayList<RecFragmentMainData> movieList;
    public List<List<String>> dataList;
    public ArrayList<String> resultList; // 추천 알고리즘 결과값
    public static ArrayList<String> MBTIList; // 사용자 엠비티아이 리스트

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();
    private View view;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_reco, container, false);
        recoRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewReco);
        recoRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recoRecyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        resultList = new ArrayList<>();
        movieList = new ArrayList<>();

        dataList = DataListReady.data_list;

        for (int i = 0; i < dataList.size(); i++) {
            int cnt = 0;
            if (dataList.get(i).get(1).equals(MBTIList.get(0))) cnt++;
            if (dataList.get(i).get(2).equals(MBTIList.get(1))) cnt++;
            if (dataList.get(i).get(3).equals(MBTIList.get(2))) cnt++;
            if (dataList.get(i).get(4).equals(MBTIList.get(3))) cnt++;

            if (cnt >= 3) resultList.add(dataList.get(i).get(0));
        }

        System.out.println("1 resultList" + resultList);

        // 파이어베이스 구현부
        for (int i = 0; i < resultList.size(); i++) {
            getFirebase(resultList.get(i));
        }

//        String str = "메멘토, 플립, 아멜리에, 어린왕자, 이터널 선샤인, 500일의 썸머, 비포 선셋, 미드나잇 인 파리, 매트릭스 : 리저렉션, 반지의 제왕 : 왕의 귀환, 스타워즈 : 라이즈 오브 스카이워커, 인생은 아름다워, 미녀와 야수, 월터의 상상은 현실이 된다, 록산느, 꼬마 돼지 베이브, 컨택트, 페인 앤 글로리, 프로메테우스, 그레이트 뷰티";
//        resultList = new ArrayList<>(Arrays.asList(str.split(", ")));
//
//        recoAdapter = new RecoAdapter(movieList, getContext());
//
//        System.out.println("2 resultList" + resultList);
//        for (int i = 0; i < resultList.size(); i++) {
//            movieList.add(new MovieItem(R.drawable.movie_black, resultList.get(i)));
//        }

        System.out.println("3 movieList" + movieList);
        recoRecyclerView.setAdapter(recoAdapter);

        return view;
    }

    private void getFirebase(String name){
        String movieName = name;
        userReference.child("movie").child("Title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Movie movie = snapshot.getValue(Movie.class);
                String imgUrl = movie.getImg(); // 이미지 링크 따오는 함수 !!

                movieList.add(new RecFragmentMainData(imgUrl, movieName));
            }

            //            RecFragmentAdapter.notifyDataSetChanged();
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}


