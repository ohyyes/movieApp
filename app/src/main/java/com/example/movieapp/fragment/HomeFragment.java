package com.example.movieapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.DataListReady;
import com.example.movieapp.R;
import com.example.movieapp.adapter.HomeFragmentAdapter;
import com.example.movieapp.adapter.RecFragmentAdapter;
import com.example.movieapp.data.HomeFragmentMainData;
import com.example.movieapp.data.MovieMainData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements HomeFragmentAdapter.MyRecyclerViewClickListener{

    ViewGroup rootView;
    ArrayList<HomeFragmentMainData> dataList;
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
            dataList.add(new HomeFragmentMainData(cat[i], "movie "+(i+1)));
        }


        recyclerView.setAdapter(adapter);

        //------------------- 규원 -----------------

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        recoRecyclerView = rootView.findViewById(R.id.recyclerViewReco);
        recoRecyclerView.setHasFixedSize(true);
        layoutManagerReco = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recoRecyclerView.setLayoutManager(layoutManagerReco);

        dataList = new ArrayList<>();
        resultList = new ArrayList<>();
        movieList = new ArrayList<>();
        movieNumList = new ArrayList<>();

        movieDataList = DataListReady.data_list;

        for (int i = 0; i < movieDataList.size(); i++) {
            int cnt = 0;
            if (movieDataList.get(i).get(1).equals(MBTIList.get(0))) cnt++;
            if (movieDataList.get(i).get(2).equals(MBTIList.get(1))) cnt++;
            if (movieDataList.get(i).get(3).equals(MBTIList.get(2))) cnt++;
            if (movieDataList.get(i).get(4).equals(MBTIList.get(3))) cnt++;

            if (cnt >= 3) {
                movieNumList.add(String.valueOf(i));
                resultList.add(movieDataList.get(i).get(0));
            }
        }

        recoAdapter = new RecFragmentAdapter(movieList, getContext());

        String str = "메멘토, 플립, 아멜리에, 어린왕자, 이터널 선샤인, 500일의 썸머, 비포 선셋, 미드나잇 인 파리, 매트릭스 : 리저렉션, 반지의 제왕 : 왕의 귀환, 스타워즈 : 라이즈 오브 스카이워커, 인생은 아름다워, 미녀와 야수, 월터의 상상은 현실이 된다, 록산느, 꼬마 돼지 베이브, 컨택트, 페인 앤 글로리, 프로메테우스, 그레이트 뷰티";
        resultList = new ArrayList<>(Arrays.asList(str.split(", ")));

        String str2 = "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20";
        movieNumList = new ArrayList<>(Arrays.asList(str2.split(", ")));

        for (int i = 0; i < resultList.size(); i++) {
            int tmpPosNum = movie_poster[Integer.parseInt(movieNumList.get(i))];
            movieList.add(new MovieMainData(tmpPosNum, resultList.get(i)));
        }

        recoRecyclerView.setAdapter(recoAdapter);

        //------------------- 규원 -----------------

        return rootView;
    }
    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getActivity().getApplicationContext(), "Item : "+position, Toast.LENGTH_SHORT).show();
    }

    public void onTitleClicked(int position) {
        Toast.makeText(getActivity().getApplicationContext(), "Title : "+position, Toast.LENGTH_SHORT).show();
    }

    public void onContentClicked(int position) {
        Toast.makeText(getActivity().getApplicationContext(), "Content : "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageViewClicked(int position) {
        Toast.makeText(getActivity().getApplicationContext(), "Image : "+position, Toast.LENGTH_SHORT).show();
    }

    public void onItemLongClicked(int position) {
        adapter.remove(position);
        Toast.makeText(getActivity().getApplicationContext(),
                dataList.get(position).getmName()+" is removed",Toast.LENGTH_SHORT).show();
    }



}