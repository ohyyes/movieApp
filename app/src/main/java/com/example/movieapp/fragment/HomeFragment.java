package com.example.movieapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.movieapp.DataListReady;
import com.example.movieapp.R;
import com.example.movieapp.adapter.HomeFragmentAdapter;
import com.example.movieapp.adapter.RecFragmentAdapter;
import com.example.movieapp.data.MovieMainData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ViewGroup rootView;
    ArrayList<MovieMainData> dataList;
    int[] cat = {R.drawable.movie1, R.drawable.movie2,R.drawable.movie3,R.drawable.movie4,R.drawable.movie5};

    private HomeFragmentAdapter adapter;
    static int i=0;

    //------------------ 규원 -------------------
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

    RecyclerView recoRecyclerView;
    RecyclerView.Adapter recoAdapter;
    RecyclerView.LayoutManager layoutManagerReco;

    public ArrayList<MovieMainData> movieList;
    public List<List<String>> movieDataList;
    public ArrayList<String> resultList; // 추천 알고리즘 결과값
    public static ArrayList<String> MBTIList; // 사용자 엠비티아이 리스트
    public ArrayList<String> movieNumList;
    //------------------- 규원 -----------------



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.replace(R.id.rec_frame_layout, fragmentRec).commitAllowingStateLoss();

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        adapter = new HomeFragmentAdapter(dataList);
        for (int i=0; i<5; i++) {
            dataList.add(new MovieMainData(cat[i], "movie "+(i+1)));
        }


        recyclerView.setAdapter(adapter);

        //------------------- 규원 -----------------

        recoRecyclerView = rootView.findViewById(R.id.recyclerViewReco);
        recoRecyclerView.setHasFixedSize(true);
        layoutManagerReco = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recoRecyclerView.setLayoutManager(layoutManagerReco);

        dataList = new ArrayList<>();
        resultList = new ArrayList<>();
        movieList = new ArrayList<>();
        movieNumList = new ArrayList<>();

        //movieDataList = DataListReady.data_list;

        recoAdapter = new RecFragmentAdapter(dataList, getContext());

        recoRecyclerView.setAdapter(recoAdapter);

        //------------------- 규원 -----------------

        return rootView;
    }

}