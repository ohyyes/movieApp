package com.example.movieapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements MyRecyclerAdapter.MyRecyclerViewClickListener{

    ViewGroup rootView;
    RecyclerView recyclerView;
    ArrayList<ItemData> dataList;
    // int[] cat = {R.drawable.movie1, R.drawable.movie2,R.drawable.movie3,R.drawable.movie4,R.drawable.movie5};

    private MyRecyclerAdapter adapter;
    static int i=0;

    private String URL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=%EB%B0%95%EC%8A%A4%EC%98%A4%ED%94%BC%EC%8A%A4";
    public static final int LOAD_SUCCESS = 101;

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

        recyclerView = rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        adapter = new MyRecyclerAdapter(dataList);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bundle = new Bundle();
                    Document doc = Jsoup.connect(URL).get();	// URL 웹사이트에 있는 html 코드를 다 끌어오기

                    // html 에서 태그 div, 클래스명 "list_image_box"인 값에서 태그가 img 인 값 가져오기
                    Elements elements = doc.select("div.list_image_box").select("img");
                    boolean isEmpty = elements.isEmpty();           // 빼온 값 null 체크
                    Log.d("Tag", "isNull? : " + isEmpty);

                    if (!isEmpty) {          // null 이 아니면 크롤링
                        for (int i=0; i<5; i++) {
                            String src = elements.get(i).absUrl("src");   // 가져온 i번째 element 에서 src 속성값 가져오기
                            String title = elements.get(i).attr("alt");   // 가져온 i번째 element 에서 alt 속성값(제목) 가져오기
                            bundle.putString("image source "+i, src); // bundle 이라는 자료형에 뽑아낸 결과값 담아서 main Thread로 보내기
                            bundle.putString("title "+i, title);
                            System.out.println(src);        // 이미지 소스 확인
                        }
                        System.out.println("성공!!");
                        Message msg = handler.obtainMessage(LOAD_SUCCESS);
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        recyclerView.setAdapter(adapter);
        return rootView;
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();    // new Thread에서 작업한 결과물 받기
            for (int i=0; i<5; i++) {
                Bitmap poster = SearchFragment.getBitmapFromURL(bundle.getString("image source "+i));
                String title = bundle.getString("title "+i);
                dataList.add(new ItemData(poster, title));
            }
            recyclerView.setAdapter(adapter);
        }
    };

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