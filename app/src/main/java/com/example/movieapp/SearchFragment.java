package com.example.movieapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    //홈 엑티비티 선언 (화면 전환시 필요)
    HomeActivity homeActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //홈 엑티비티 생성
        homeActivity = (HomeActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //홈 엑티비티 제거
        homeActivity = null;
    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
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
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    private final static String NAVER_MOVIE_SEARCH = "https://movie.naver.com/movie/search/result.naver?section=movie&query=";
    private final static String NAVER_MOVIE_INFO = "https://movie.naver.com/movie/bi/mi/basic.naver?code=";

    private static final String TAG = "moviesearch";
    public static final int LOAD_SUCCESS = 101;

    private final static String MOVIE_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final static String MOVIEINFO_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
    private final static String KEY = "ac25203913acc2c703a28e44ec239cbf";

    private final static String NAVERMOVIE_URL = "https://openapi.naver.com/v1/search/movie?query=";
    private final String API_ID = "BEOHWoFfrwf9hxeYp1_1";
    private final String API_SECRET = "1SQEUT5QF6";

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rec_search_list;    // 리사이클러 뷰
    private LinearLayout lin_no_result;     // 검색결과 없음 레이아웃
    private EditText et_search;         // 검색창 입력값
    private ArrayList<SearchFragmentMainData> resultMovieList;    // 검색 결과 리스트
    private SearchFragmentAdapter adapter;

    // 로딩중 표시를 위한 다이얼로그
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // rootView 객체에 fragment_search.xml 과 연결한 것을 담기.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        //여기서부터 만짐//
        rec_search_list = (RecyclerView) rootView.findViewById(R.id.rec_search_list);
        lin_no_result = (LinearLayout) rootView.findViewById(R.id.lin_no_result);
        et_search = (EditText) rootView.findViewById(R.id.et_search);
        resultMovieList = new ArrayList<>();
        //어댑터 생성
        adapter = new SearchFragmentAdapter(resultMovieList, homeActivity);
        // 어댑터의 data 를 resultMovieList 로 갱신 (setItems()는 SearchFragmentAdapter.java 에 구현 되어있음)
        adapter.setItems(resultMovieList);

        linearLayoutManager = new LinearLayoutManager(getContext());     // ???
        rec_search_list.setLayoutManager(linearLayoutManager);

        // [돋보기] 버튼을 누르면 해당 키워드를 포함하는 아이템 검색해서 보여줌
        ImageView ib_search = (ImageButton) rootView.findViewById(R.id.ib_search);
        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("검색버튼", "클릭!");

                // 로딩중 표시
                progressDialog = new ProgressDialog(homeActivity);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();      // 시작

                // 검색 결과 리스트 초기화
                resultMovieList.clear();
                // 검색
                String keyword = et_search.getText().toString();    // keyword 변수에 EditText 에 입력된 값 담기
                searchMovie(keyword);               // keyword 로 영화 검색
            }
        });

        return rootView;
    }
    private final MyHandler mHandler = new MyHandler();

    private class MyHandler extends Handler {
        // 메시지 큐의 메시지 처리
        @Override
        public void handleMessage(Message msg) {
            // 어댑터의 data 를 resultMovieList 로 갱신 (setItems()는 SearchFragmentAdapter.java 에 구현되어 있음)
            adapter.setItems(resultMovieList);

            // 로딩중 표시 종료
            progressDialog.dismiss();

            // 검색된 결과가 없을 때 -> "죄송합니다 해당 키워드가 없습니다" 레이아웃을 보이게 !
            if (resultMovieList.isEmpty()) {
                rec_search_list.setVisibility(View.INVISIBLE);  // 리사이클러뷰 잠깐 안 보이게 설정
                lin_no_result.setVisibility(View.VISIBLE);      // lin_no_result 레이아웃을 보이게 설정
            }
            // 있을 땐, 리사이클러뷰가 보이게 !
            else {
                rec_search_list.setVisibility(View.VISIBLE);    // 리사이클러뷰 보이게
                lin_no_result.setVisibility(View.INVISIBLE);    // lin_no_result 레이아웃 안 보이게
            }

            // 리사이클러뷰에게 어댑터 객체를 전송한다.
            // (검색결과가 없을 때도 리사이클러뷰에게 어댑터를 기본적으로 전송하도록 짜둠.)
            rec_search_list.setAdapter(adapter);
        }
    }
    // 영화목록 조회 API 호출 및 응답결과 파싱 함수 호출
    public void searchMovie(final String keyword) {
        if (keyword == null) return;

        // 새로 생성한 스레드에서 영화진흥위원회 영화목록 API 호출
        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(NAVER_MOVIE_SEARCH+keyword).get();	// URL 웹사이트에 있는 html 코드를 다 끌어오기

                    // html 에서 태그 ul, 클래스명 "search_list_1"인 값에서 태그가 dl인 값 가져오기
                    Elements elements = doc.select("ul.search_list_1").select("dl");
                    boolean isEmpty = elements.isEmpty();           // 빼온 값 null 체크
                    Log.d("Tag", "isNull? : " + isEmpty);

                    if (!isEmpty) {          // null 이 아니면 크롤링
                        for (int i=0; i<elements.size(); i++) {
                            Element element = elements.get(i);
                            Element idElement = element.select("a").first();
                            // 영화 객체
                            SearchFragmentMainData movie = new SearchFragmentMainData();

                            // 식별 코드
                            String codeUrl = idElement.attr("href");
                            String code = codeUrl.split("code=")[1];

                            // 기본 정보(제목, 평점, 개봉연도, 장르) 저장
                            movie.setTitle(idElement.select("a").text());        // 제목

                            Elements ratingElements = element.select("dd.point");
                            movie.setUserRating((ratingElements.size()==0)?"0.00": ratingElements.select("em.num").text());    // 평점

                            Elements etcElements = element.select("dd.etc").select("a");
                            String openYear = null;
                            String genres = etcElements.get(0).text();
                            for (int j=1; j<etcElements.size(); j++) {
                                Element etcElement = etcElements.get(j);
                                if (etcElement.attr("href").contains("year"))
                                    openYear = etcElement.text();
                                else if (etcElement.attr("href").contains("genre"))
                                    genres += ", " + etcElement.text();
                            }
                            movie.setOpenYear(openYear);        // 개봉연도
                            movie.setGenre(genres);             // 장르

                            // 결과 리스트에 영화 저장하기
                            resultMovieList.add(movie);

                            // (2) 영화 상세정보 추가 - 포스터, 감독, 출연진, 줄거리
                            searchMovieInfo(code, i);
                        }
                        Message msg = mHandler.obtainMessage(LOAD_SUCCESS);
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }
        });
        thread.start();
    }
    // (2) 영화 코드로 상세정보 (영화 한 개)
    public void searchMovieInfo(final String code, int position) {
        if (code == null) return;

        try {
            Document doc = Jsoup.connect(NAVER_MOVIE_INFO + code).get();    // URL 웹사이트에 있는 html 코드를 다 끌어오기
            // 포스터
            Bitmap posterBitmap;
            Element posterElement = doc.select("div.poster").select("img").first();
            if (posterElement != null) {
                posterBitmap = getBitmapFromURL(posterElement.attr("src"));
                resultMovieList.get(position).setPoster(posterBitmap);
            }

            // 상영 시간
            Elements infoElements = doc.select("dl.info_spec").select("span");
            String runningTime = (infoElements.size()<3)? "": infoElements.get(2).text();

            // 감독 및 출연진
            Elements peoples = doc.select("div.people").select("li");
            String director = "감독: ";
            String actors = "출연: ";
            if (peoples.size() > 0) {
                director += peoples.get(0).select("a").get(1).text();
                if (peoples.size() > 1) {
                    actors += peoples.get(1).select("a").get(1).text();
                    for (int i = 2; i < peoples.size() - 1; i++)
                        actors += ", " + peoples.get(i).select("a").get(1).text();
                }
            }
            // 줄거리
            String story = doc.select("div.story_area").select("p").text();

            resultMovieList.get(position).setRunningTime(runningTime);
            resultMovieList.get(position).setDirector(director);
            resultMovieList.get(position).setActors(actors);
            resultMovieList.get(position).setStory(story);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
    // (3) 이미지 링크를 받아서 비트맵으로 반환
    public static Bitmap getBitmapFromURL(final String imageURL) {
        if (imageURL == null) return null;

        Bitmap posterBitmap = null;

        try {
            URL url = new URL(imageURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();

            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK)
                inputStream = httpURLConnection.getInputStream();
            else
                return null;

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            posterBitmap = BitmapFactory.decodeStream(bufferedInputStream);

            bufferedInputStream.close();
            httpURLConnection.disconnect();

            // 변환 확인용 ---------------------------
            System.out.println(posterBitmap);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return posterBitmap;
    }
}