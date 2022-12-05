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
import com.example.movieapp.data.HomeFragmentMainData;
import com.example.movieapp.data.RecFragmentMainData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecFragment extends Fragment {

    ViewGroup rootView;
    ArrayList<HomeFragmentMainData> dataList = new ArrayList<>();
    int[] cat = {R.drawable.movie1, R.drawable.movie2,R.drawable.movie3,R.drawable.movie4,R.drawable.movie5};

    final HomeFragmentAdapter adapter = new HomeFragmentAdapter(dataList);
    static int i=0;

    //------------------ 규원 -------------------
    RecyclerView recoRecyclerView;
    RecyclerView.Adapter recoAdapter;
    RecyclerView.LayoutManager layoutManager;

    public ArrayList<RecFragmentMainData> movieList;
    public List<List<String>> movie_dataList;
    public ArrayList<String> resultList; // 추천 알고리즘 결과값
    public static ArrayList<String> MBTIList; // 사용자 엠비티아이 리스트

    //firebase로 movieList읽기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();
    private View view;
    //------------------- 규원 -----------------

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecFragment newInstance(String param1, String param2) {
        RecFragment fragment = new RecFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_rec, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        movie_dataList = new ArrayList<>();
        resultList = new ArrayList<>();
        movieList = new ArrayList<>();

        movie_dataList = DataListReady.data_list;

        for (int i = 0; i < dataList.size(); i++) {
            int cnt = 0;
            if (movie_dataList.get(i).get(1).equals(MBTIList.get(0))) cnt++;
            if (movie_dataList.get(i).get(2).equals(MBTIList.get(1))) cnt++;
            if (movie_dataList.get(i).get(3).equals(MBTIList.get(2))) cnt++;
            if (movie_dataList.get(i).get(4).equals(MBTIList.get(3))) cnt++;

            if (cnt >= 3) resultList.add(movie_dataList.get(i).get(0));
        }

        //firebase movie Title 가져옴
        for (int i = 0; i < resultList.size(); i++) {
            getFirebase(resultList.get(i));
        }

        recoRecyclerView.setAdapter(recoAdapter);

        for (int i=0; i<5; i++) {
            dataList.add(new HomeFragmentMainData(cat[i], "movie "+(i+1)));
        }
        recyclerView.setAdapter(adapter);
        return rootView;
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