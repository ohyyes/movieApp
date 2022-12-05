package com.example.movieapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private SearchFragment fragmentSearch = new SearchFragment();
    private ReviewFragment fragmentReview = new ReviewFragment();
    private MypageFragment fragmentMypage = new MypageFragment();
    private HomeFragment fragmentHome = new HomeFragment();
    private MovieDetailFragment fragmentMovieDetail = new MovieDetailFragment();
    private ReviewDetailFragment fragmentReviewDetail = new ReviewDetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_frame_layout, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.menu_review:
                        transaction.replace(R.id.menu_frame_layout, fragmentReview).commitAllowingStateLoss();
                        break;
                    case R.id.menu_search:
                        transaction.replace(R.id.menu_frame_layout, fragmentSearch).commitAllowingStateLoss();
                        break;
                    case R.id.menu_mypage:
                        transaction.replace(R.id.menu_frame_layout, fragmentMypage).commitAllowingStateLoss();
                        break;
                    case R.id.menu_home:
                        transaction.replace(R.id.menu_frame_layout, fragmentHome).commitAllowingStateLoss();
                        break;

                }

                return true;
            }
        });

    }

    //프래그먼트 to 프래그먼트 화면 전환 메소드
    public void onFragmentChange(int index, Parcelable movie_item) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // index = 0 : fragmentMovieDetail 로 이동 (영화 상세 페이지)
        // index = 1 : fragmentReviewDetail 로 이동 (감상평 상세 페이지)
        // index = 2 : fragmentReview 로 이동 (감상평 목록 페이지)

        //인덱스 0, 1 인 영화 상세 페이지, 감상평 상세 페이지는 뒤로가기 버튼 때문에 replace 대신 add 를 사용해야했음

        //프래그먼트로 넘길 데이터
        Bundle bundle = new Bundle();


        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        //index
        if(index == 0) {
            //bottomNavigationView 값이 설정되어있으면 영화상세페이지의 [<] 버튼 눌렀을 때, 해당 페이지로 자꾸 이동해서 삭제헀더니 문제 해결됨
            //이미 이 프래그먼트가 BackStack 에 추가 되어있다면 삭제 후 다시 추가
            if(fragmentMovieDetail.isAdded()){
                transaction.remove(fragmentMovieDetail);
            }
            //데이터 담기
            bundle.putParcelable("아이템", movie_item);
            //프래그먼트에 데이터 넘기기
            fragmentMovieDetail.setArguments(bundle);
            //프래그먼트로 이동
            transaction.add(R.id.menu_frame_layout, fragmentMovieDetail).commitAllowingStateLoss();
        }
        else if(index == 1) {
            //bottomNavigationView 값이 설정되어있으면 리뷰상세페이지의 [<] 버튼 눌렀을 때, 해당 페이지로 자꾸 이동해서 삭제헀더니 문제 해결됨
            //이미 이 프래그먼트가 BackStack 에 추가 되어있다면 삭제 후 다시 추가
            if(fragmentReviewDetail.isAdded()){
                transaction.remove(fragmentReviewDetail);
            }
            //데이터 담기
            //bundle.putString("영화 제목",movie_title);
            bundle.putParcelable("아이템", movie_item);
            //프래그먼트에 데이터 넘기기
            fragmentReviewDetail.setArguments(bundle);
            transaction.add(R.id.menu_frame_layout, fragmentReviewDetail).commitAllowingStateLoss();
        }
        else if(index == 2) {
            bottomNavigationView.setSelectedItemId(R.id.menu_review);
            transaction.replace(R.id.menu_frame_layout, fragmentReview).commitAllowingStateLoss();
        }
    }
}

