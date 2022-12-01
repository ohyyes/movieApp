package com.example.movieapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BoxOfficeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoxOfficeFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoxOfficeFragment newInstance(String param1, String param2) {
        BoxOfficeFragment fragment = new BoxOfficeFragment();
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

    private String URL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=%EB%B0%95%EC%8A%A4%EC%98%A4%ED%94%BC%EC%8A%A4";
    public static final int LOAD_SUCCESS = 101;

//    private final static String BOXOFFICE_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
//    private final static String KEY = "ac25203913acc2c703a28e44ec239cbf";
//
//    private final static String NAVERMOVIE_URL = "https://openapi.naver.com/v1/search/movie?query=";
//    private final String API_ID = "BEOHWoFfrwf9hxeYp1_1";
//    private final String API_SECRET = "1SQEUT5QF6";

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rec_boxoffice;
    private ArrayList<Bitmap> boxOfficeList;
    private BoxAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        rec_boxoffice = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        boxOfficeList = new ArrayList<>();
        adapter = new BoxAdapter(boxOfficeList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rec_boxoffice.setLayoutManager(linearLayoutManager);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, -1);
//        String today = sdf.format(calendar.getTime());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bundle = new Bundle();
                    Document doc = Jsoup.connect(URL).get();	//URL 웹사이트에 있는 html 코드를 다 끌어오기
                    Elements elements = doc.select("div.list_image_box").select("img");	//끌어온 html에서 클래스네임이 "mask" 인 값만 선택해서 빼오기
                    boolean isEmpty = elements.isEmpty();           // 빼온 값 null 체크
                    Log.d("Tag", "isNull? : " + isEmpty);
                    if (!isEmpty) {          // null 이 아니면 크롤링
                        for (int i=0; i<5; i++) {
                            String src = elements.get(i).absUrl("src"); //크롤링 하면 "현재온도30'c" 이런식으로 뽑아와지기 때문에, 현재온도를 잘라내고 30'c만 뽑아내는 코드
                            bundle.putString("image source"+i, src); //bundle 이라는 자료형에 뽑아낸 결과값 담아서 main Thread로 보내기
                            System.out.println(src);
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

        return rootView;
    }
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            // 어댑터의 data 를 boxOfficeList 로 갱신 (setItems()는 BoxAdapter.java 에 구현 되어있음)
            adapter.setItems(boxOfficeList);

            Bundle bundle = msg.getData();    // new Thread에서 작업한 결과물 받기
            for (int i=0; i<5; i++) {
                boxOfficeList.add(SearchFragment.getBitmapFromURL(bundle.getString("image source"+i)));
            }
            rec_boxoffice.setAdapter(adapter);
        }
    };
}
