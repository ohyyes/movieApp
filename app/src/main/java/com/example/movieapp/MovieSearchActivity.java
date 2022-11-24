package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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

public class MovieSearchActivity extends AppCompatActivity {

    private static final String TAG = "moviesearch";
    public static final int LOAD_SUCCESS = 101;

    private final static String MOVIE_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final static String MOVIEINFO_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
    private final static String KEY = "ac25203913acc2c703a28e44ec239cbf";

    private final static String NAVERMOVIE_URL = "https://openapi.naver.com/v1/search/movie?query=";
    private final String API_ID = "BEOHWoFfrwf9hxeYp1_1";
    private final String API_SECRET = "1SQEUT5QF6";

    private ProgressDialog progressDialog;
    private EditText et_search;
    private MainAdapter adapter;
    private ArrayList<MainData> resultMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frag_search, new SearchFragment());
        fragmentTransaction.commit();

        et_search = (EditText) findViewById(R.id.et_search);
        resultMovieList = new ArrayList<MainData>();        // 검색 결과(데이터)를 저장할 리스트
        RecyclerView rec_movieList = (RecyclerView) findViewById(R.id.rec_searchList);  // 검색 결과를 보여줄 리사이클러 뷰

        adapter = new MainAdapter(resultMovieList);
        rec_movieList.setAdapter(adapter);

        // 버튼 클릭!
        ImageButton ib_search = (ImageButton) findViewById(R.id.ib_search);
        ImageButton ib_back = (ImageButton) findViewById(R.id.ib_back);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ib_search:
                        Log.v("검색버튼", "클릭!");

                        // 로딩중
                        progressDialog = new ProgressDialog(MovieSearchActivity.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();      // 시작!

                        String keyword = et_search.getText().toString();     // 검색 키워드 입력 받기
                        searchMovie(keyword);       // 키워드로 영화 검색
                        break;
                    case R.id.ib_back:
                        // 뒤로 가기
                        break;
                }
            }
        };
        ib_search.setOnClickListener(listener);
        ib_back.setOnClickListener(listener);

    }
    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<MovieSearchActivity> weakReference;

        public MyHandler(MovieSearchActivity activity) {
            weakReference = new WeakReference<MovieSearchActivity>(activity);
        }

        // 메시지 큐의 메시지 처리
        @Override
        public void handleMessage(Message msg) {
            MovieSearchActivity movieSearchActivity = weakReference.get();

            if (movieSearchActivity != null) {
                if (msg.what == LOAD_SUCCESS) {         // json 파싱 성공 시
                    movieSearchActivity.progressDialog.dismiss();      // 로딩 스피너 종료!
                    movieSearchActivity.adapter.notifyDataSetChanged();    // 리사이클러뷰 새로고침
                }
            }
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
                    jsonString = e.toString();
                }

                // 응답 결과 확인용 ----------------
                System.out.println(jsonString);

                // 응답 결과(jsonString) JSON 파싱
                if (parseJSON(jsonString)) {
                    Message message = mHandler.obtainMessage(LOAD_SUCCESS);
                    mHandler.sendMessage(message);
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

            resultMovieList.clear();

            for (int i=0; i<movies.length(); i++) {
                JSONObject movieObject = movies.getJSONObject(i);       // (3)안에 하나의 영화 정보 {} : JSON
                String code = movieObject.getString("movieCd");

                MainData movie = new MainData();
                resultMovieList.add(movie);     // 빈 영화 일단 추가!
                searchMovieInfo(code, i);       // 상세정보 api 호출(2) - 추가한 영화에 정보 업데이트
            }

            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }

        return false;
    }
    // (2) 영화코드로 상세정보 API 호출
    public void searchMovieInfo(final String code, int position) {
        if (code == null) return;

        // 전달받은 키워드로 API 호출하여 얻은 응답을 jsonString에 저장
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
            jsonString = e.toString();
        }

        // 응답 결과 확인용 ----------------
        System.out.println(jsonString);

        // 응답 결과(jsonString) JSON 파싱(3)
        parseJSON2(jsonString, position);
    }
    // (3) 영화 상세정보 파싱 및 resultMovieList에 정보 저장
    public void parseJSON2(String jsonString, int position) {

        if (jsonString == null) return;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);     // 응답 결과 {} : JSON ---(1)
            JSONObject result = jsonObject.getJSONObject("movieInfoResult");  // (1) 안에 "movieInfo~"에 대응되는 value {}: JSON ---(2)
            JSONObject movies = result.getJSONObject("movieInfo");   // (2)안에 "movieInfo"에 대응되는 value {} : JSON ---(2)

            String title = movies.getString("movieNm");
            String openYear = movies.getString("openDt");
            String runningTime = movies.getString("showTm");
            // 상세 페이지에서 추가로 필요한 정보들
            String directors = movies.getString("directors");
            String actors = movies.getString("actors");

            MainData mv = resultMovieList.get(position);
            mv.setOpenYear(openYear);
            mv.setRunningTime(runningTime);
            mv.setDirectors(directors);
            mv.setActors(actors);

            // 네이버 영화 API 호출(4)
            searchNaver(mv.getTitle(), position);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
    // (4) 네이버 영화 검색 API 호출
    public void searchNaver(String title, int position) {
        String jsonString = null;

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

        } catch (Exception e) {
            jsonString = e.toString();
        }
        // 네이버 API 응답 결과 파싱(5)
        parseJSON3(jsonString, position);
    }
    // (5) 네이버 API 에서 이미지, 평점 문자열 뽑아내기
    public void parseJSON3(String jsonString, int position) {
        MainData mv = resultMovieList.get(position);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject items = jsonArray.getJSONObject(0);     // check! - 맨 처음 나온 결과가 찾던 영화라 가정

            String imageUrl = items.getString("image");
            String userRating = items.getString("userRating");

            // 이미지링크를 비트맵으로 변환(6)
            Bitmap poster = getPosterFromURL(imageUrl);

            // 해당 위치의 영화에 이미지와 평점 정보 저장
            mv.setPoster(poster);
            mv.setUserRating(userRating);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // (6) 이미지 링크를 받아서 비트맵으로 반환
    public Bitmap getPosterFromURL(final String imageURL) {
        Bitmap posterBitmap = null;

        if (imageURL == null) return null;

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
            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);

            bufferedInputStream.close();
            httpURLConnection.disconnect();

            posterBitmap = bitmap;
            // 변환 확인용 ---------------------------
            System.out.println(bitmap);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return posterBitmap;
    }
}