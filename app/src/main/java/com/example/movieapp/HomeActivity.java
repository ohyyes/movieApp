package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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

    public void onFragmentChange(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        if(index == 0) { //감상평 상세 화면으로 이동
            bottomNavigationView.setSelectedItemId(R.id.menu_review);
            transaction.replace(R.id.menu_frame_layout, fragmentMovieDetail).commitAllowingStateLoss();
        }else if(index == 1) {
            transaction.replace(R.id.menu_frame_layout, fragmentReviewDetail).commitAllowingStateLoss();
        }

    }
}

