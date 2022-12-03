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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
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

    private static final String TAG = "moviesearch";
    public static final int LOAD_SUCCESS = 101;

    private final static String MOVIE_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final static String MOVIEINFO_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
    private final static String KEY = "ac25203913acc2c703a28e44ec239cbf";

    private final static String NAVERMOVIE_URL = "https://openapi.naver.com/v1/search/movie?query=";
    private final String API_ID = "BEOHWoFfrwf9hxeYp1_1";
    private final String API_SECRET = "1SQEUT5QF6";

    // 로딩중 표시
    private Process progressDialog;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rec_search_list;    // 리사이클러 뷰
    private LinearLayout lin_no_result;     // 검색결과 없음 레이아웃
    private EditText et_search;         // 검색창 입력값
    private ArrayList<SearchFragmentMainData> resultMovieList;    // 검색 결과 리스트
    private SearchFragmentAdapter adapter;

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
            // 어댑터의 data 를 resultMovieList 로 갱신 (setItems()는 SearchFragmentAdapter.java 에 구현 되어있음)
            adapter.setItems(resultMovieList);

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
                // 전달받은 키워드로 API 호출하여 얻은 응답을 jsonString에 저장
                String jsonString = null;

                try {
                    Log.d(TAG, MOVIE_URL+"?key="+KEY+"&movieNm="+keyword);
                    URL url = new URL(MOVIE_URL+"?key="+KEY+"&movieNm="+keyword);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("GET");
                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();
                    httpURLConnection.disconnect();

                    jsonString = sb.toString().trim();

                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }

                // 응답 결과 확인용 ---------------
                System.out.println(jsonString);

                // 응답 결과(jsonString) JSON 파싱
                if (parseJSON(jsonString)) {
                    System.out.println("성공!!");
                    Message msg = mHandler.obtainMessage(LOAD_SUCCESS);
                    mHandler.sendMessage(msg);
                }
            }
        });
        thread.start();
    }
    // (1) 영화목록 응답결과 파싱
    public boolean parseJSON(String jsonString) {
        if (jsonString == null) return false;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);     // 응답 결과 {} : JSON ---(1)
            JSONObject result = jsonObject.getJSONObject("movieListResult");   // (1)안에 "movieListResult"에 대응되는 value {} : JSON ---(2)
            JSONArray movies = result.getJSONArray("movieList");        // (2)안에 "movieList"에 대응되는 value [] : JSON 배열 ---(3)

            for (int i=0; i<movies.length(); i++) {
                JSONObject movieObject = movies.getJSONObject(i);       // (3)안에 하나의 영화 정보 {} : JSON
                String code = movieObject.getString("movieCd");

                SearchFragmentMainData movie = new SearchFragmentMainData();
                resultMovieList.add(movie);     // 빈 영화 일단 추가!
                searchMovieInfo(code, i);       // 상세정보 api 호출(2) - 추가한 영화에 정보 업데이트
            }

            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }

        return false;
    }
    // (2) 영화코드로 상세정보 API 호출 (영화 한 개)
    public void searchMovieInfo(final String code, int position) {
        if (code == null) return;

        // 전달받은 영화 코드로 API 호출하여 얻은 응답 저장
        String jsonString = null;

        try {
            Log.d(TAG, MOVIEINFO_URL+"?key="+KEY+"&movieCd="+code);
            URL url = new URL(MOVIEINFO_URL+"?key="+KEY+"&movieCd="+code);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            httpURLConnection.disconnect();

            jsonString = sb.toString().trim();

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        // 응답 결과 확인용 ----------------
        System.out.println(jsonString);

        // 응답 결과(jsonString) JSON 파싱(3)
        parseJSON2(jsonString, position);
    }
    // (3) 영화 상세정보 파싱 및 resultMovieList 에 정보 저장
    public void parseJSON2(String jsonString, int position) {
        if (jsonString == null) return;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);     // 응답 결과 {} : JSON ---(1)
            if (!jsonObject.has("movieInfoResult")) return;     // 검색 결과 없을 시 리턴!!
            JSONObject result = jsonObject.getJSONObject("movieInfoResult");  // (1) 안에 "movieInfo~"에 대응되는 value {}: JSON ---(2)
            JSONObject movies = result.getJSONObject("movieInfo");   // (2)안에 "movieInfo"에 대응되는 value {} : JSON ---(2)

            resultMovieList.get(position).setTitle(movies.getString("movieNm"));     // 제목
            if (!movies.getString("openDt").equals(""))
                resultMovieList.get(position).setOpenYear(movies.getString("openDt").substring(0,4));      // 개봉연도
            resultMovieList.get(position).setRunningTime(movies.getString("showTm"));    // 상영시간

            JSONArray genresArray = movies.getJSONArray("genres");
            if (genresArray.length() != 0)
                resultMovieList.get(position).setGenre(genresArray.getJSONObject(0).getString("genreNm"));    // 장르 (하나만)

            // 상세 페이지에서 추가로 필요한 정보들 - 감독, 출연진
            JSONArray directorsArray = movies.getJSONArray("directors");
            JSONArray actorsArray = movies.getJSONArray("actors");

            resultMovieList.get(position).setDirectors("감독:  " + getPeopleStr(directorsArray));   // 감독
            resultMovieList.get(position).setActors("배우:  " + getPeopleStr(actorsArray));      // 출연진

            // 제목으로 네이버 영화 API 호출(4)
            searchNaver(resultMovieList.get(position).getTitle(), position);       // 포스터, 평점

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
    // JSON 배열에 담긴 정보들 하나의 문자열로
    private String getPeopleStr(JSONArray peoplesArray) throws Exception {
        if (peoplesArray.length() == 0)
            return "정보 없음";
        String peoples = peoplesArray.getJSONObject(0).getString("peopleNm");
        for (int i=1; i< peoplesArray.length(); i++) {
            String people = peoplesArray.getJSONObject(i).getString("peopleNm");
            peoples = peoples.concat(", " + people);
            if (i > 10) {           // 최대 10명까지만
                peoples += " 외 다수";
                break;
            }
        }
        return peoples;
    }
    // (4) 네이버 영화 검색 API 호출 - 포스터, 평점
    public void searchNaver(String title, int position) {
        if (title == null) return;

        String jsonString = null;
        title = title.replace(" ", "_");        // 제목에 띄어쓰기 _로 변환

        try {
            Log.d(TAG, NAVERMOVIE_URL+title);
            URL url = new URL(NAVERMOVIE_URL+title);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("X-Naver-Client-Id", API_ID);
            httpURLConnection.setRequestProperty("X-Naver-Client-Secret", API_SECRET);

            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            httpURLConnection.disconnect();

            jsonString = sb.toString().trim();
            // 응답 결과 확인 -------------------
            System.out.println(jsonString);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        // 네이버 API 응답 결과 파싱(5)
        parseJSON3(jsonString, position);
    }
    // (5) 네이버 API 응답 결과에서 포스터, 평점 문자열 뽑아내기
    public void parseJSON3(String jsonString, int position) {
        if (jsonString == null) return;

        try {
            System.out.println("첫번째 구간 - 네이버 api 호출");       // 1
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray.length() == 0) return;            // 검색 결과 없을 시 리턴
            JSONObject items = jsonArray.getJSONObject(0);     // check! - 맨 처음 나온 결과가 찾던 영화라 가정

            String imageUrl = items.getString("image");
            String userRating = items.getString("userRating");

            if (!imageUrl.equals("")) {
                // 이미지링크를 비트맵으로 변환(6)
                Bitmap poster = getBitmapFromURL(imageUrl);
                // 해당 위치의 영화에 이미지 저장
                resultMovieList.get(position).setPoster(poster);
            }

            // 해당 위치의 영화에 평점 저장
            resultMovieList.get(position).setUserRating(userRating);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // (6) 이미지 링크를 받아서 비트맵으로 반환
    public static Bitmap getBitmapFromURL(final String imageURL) {
        if (imageURL == null) return null;

        Bitmap posterBitmap = null;
        System.out.println("두번째 구간 - 네이버 api 호출 결과 파싱");       // 2

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