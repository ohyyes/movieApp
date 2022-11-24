package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private SearchFragment fragmentSearch = new SearchFragment();
    private ReviewFragment fragmentReview = new ReviewFragment();
    private MypageFragment fragmentMypage = new MypageFragment();
    private HomeFragment fragmentHome = new HomeFragment();
    private MovieDetailFragment fragmentMovieDetail = new MovieDetailFragment();
    private ReviewDetailFragment fragmentReviewDetail = new ReviewDetailFragment();
    private MovieRecoFragment fragmentMovieReco = new MovieRecoFragment();

    public List<List<String>> data_list = new ArrayList<>();
    public List<List<String>> da = new ArrayList<>();

    //firebase에서 데이터 추가/조회 하기 위한 instance
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_frame_layout, fragmentMovieReco).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        da = readMovie();
//        Log.d("da = ", String.valueOf(da));
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
                        transaction.replace(R.id.menu_frame_layout, fragmentMovieReco).commitAllowingStateLoss();
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

    //
    private ArrayList readMovie() {
        Log.d("readMovie = ", "readmovie");
        for (int i = 0; i < 63; i++) {
            userReference.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Movie movie = snapshot.getValue(Movie.class);
                    List<String> data = new ArrayList<>();
                    String title = movie.getTitle();
                    String IE = movie.getIE();
                    String NS = movie.getNS();
                    String TF = movie.getTF();
                    String JP = movie.getJP();
                    String Img = movie.getImg();
                    Log.d("title = ", title);

                    data.add(title);
                    data.add(IE);
                    data.add(NS);
                    data.add(TF);
                    data.add(JP);
                    data.add(Img);

                    data_list.add(data);
                    Log.d("data_list = ", String.valueOf(data_list));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return (ArrayList) data_list;
    }

}



