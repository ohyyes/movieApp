package com.example.movieapp.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.DataListReady;
import com.example.movieapp.R;
import com.example.movieapp.adapter.HomeFragmentAdapter;
import com.example.movieapp.adapter.RecFragmentAdapter;
import com.example.movieapp.data.HomeFragmentMainData;
import com.example.movieapp.data.MovieMainData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment{

    ViewGroup rootView;
    ArrayList<HomeFragmentMainData> dataList;

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


    //--------------------해담------------------
    private String URL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=%EB%B0%95%EC%8A%A4%EC%98%A4%ED%94%BC%EC%8A%A4";
    public static final int LOAD_SUCCESS = 101;
    RecyclerView boxRecyclerView;
    //-----------------------------------------


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
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);


        //-----------------------해담----------------------------------
        boxRecyclerView = rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        boxRecyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        adapter = new HomeFragmentAdapter(dataList);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(URL).get();	// URL 웹사이트에 있는 html 코드를 다 끌어오기

                    // html 에서 태그 div, 클래스명 "list_image_box"인 값에서 태그가 img 인 값 가져오기
                    Elements elements = doc.select("div.list_image_box").select("img");
                    boolean isEmpty = elements.isEmpty();           // 빼온 값 null 체크
                    Log.d("Tag", "isNull? : " + isEmpty);

                    if (!isEmpty) {          // null 이 아니면 크롤링
                        for (int i=0; i<5; i++) {
                            String src = elements.get(i).absUrl("src");   // 가져온 i번째 element 에서 src 속성값 가져오기
                            String title = elements.get(i).attr("alt");   // 가져온 i번째 element 에서 alt 속성값(제목) 가져오기
                            Bitmap imgBitmap = SearchFragment.getBitmapFromURL(src);
                            dataList.add(new HomeFragmentMainData(imgBitmap, title));       // 결과 리스트에 추가
                        }
                        Message msg = handler.obtainMessage(LOAD_SUCCESS);
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        //--------------------------해담-----------------------------



        //------------------- 규원 -----------------

        recoRecyclerView = rootView.findViewById(R.id.recyclerViewReco);
        recoRecyclerView.setHasFixedSize(true);
        layoutManagerReco = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recoRecyclerView.setLayoutManager(layoutManagerReco);

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

        for (int i = 0; i < resultList.size(); i++) {
            int tmpPosNum = movie_poster[Integer.parseInt(movieNumList.get(i))];
            movieList.add(new MovieMainData(tmpPosNum, resultList.get(i)));
        }

        recoRecyclerView.setAdapter(recoAdapter);

        //------------------- 규원 -----------------

        return rootView;
    }

    //-------------------------해담--------------------------
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            adapter.setItems(dataList);
            boxRecyclerView.setAdapter(adapter);
        }
    };
    //---------------------------------------------------

}