package com.example.movieapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieapp.R;
import com.example.movieapp.UserAccount;
import com.example.movieapp.activity.HomeActivity;
import com.example.movieapp.activity.RegisterActivity;
import com.example.movieapp.data.MovieMainData;
import com.example.movieapp.data.ReviewMainData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewDetailFragment extends Fragment {

    private ImageButton ib_back;

    HomeActivity homeActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //홈 엑티비티 생성
        homeActivity = (HomeActivity) getActivity();
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

    public ReviewDetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewDetailFragment newInstance(String param1, String param2) {
        ReviewDetailFragment fragment = new ReviewDetailFragment();
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

    //뷰 선언
    private ImageView iv_poster;
    private TextView tv_name, tv_review;
    private RatingBar ratingbar1, ratingbar2;
    private EditText et_review;
    public String movieTitle, review=null, rating;
    public String poster_db, review_db, rating_db;
    public Bitmap poster, bitmap_poster_db;

    //버튼 선언
    private Button btn_amend, btn_write;

    ArrayList<ReviewMainData> all_review;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_review_detail, container, false);

        //뷰 불러오기
        iv_poster = rootView.findViewById(R.id.iv_poster);
        iv_poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_review = rootView.findViewById(R.id.tv_review);
        tv_review.setMovementMethod(new ScrollingMovementMethod());
        ratingbar1 = rootView.findViewById(R.id.ratingbar1);
        ratingbar2 = rootView.findViewById(R.id.ratingbar2);
        et_review = rootView.findViewById(R.id.et_review);

        //버튼 연결
        btn_amend = rootView.findViewById(R.id.btn_amend);
        btn_write = rootView.findViewById(R.id.btn_write);

        //레이아웃 연결
        LinearLayout lin_review = (LinearLayout) rootView.findViewById(R.id.lin_review);
        LinearLayout lin_no_review = (LinearLayout) rootView.findViewById(R.id.lin_no_review);

        //user Token 받아오기
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();

        Serializable item = this.getArguments().getSerializable("아이템");

        //이전 프래그먼트에서 전달된 영화 객체에 담기
        if(item.getClass().getName().contains("ReviewMainData")){
            //리뷰 아이템을 전달받았다면
            ReviewMainData review_item = (ReviewMainData) item;
            iv_poster.setImageBitmap(review_item.getIv_posterBitmap());
            iv_poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
            tv_name.setText(review_item.getTv_name());

            //영화제목 - firebase
            poster = review_item.getIv_posterBitmap();
            movieTitle = review_item.getTv_name();

            //title이 존재하는지 확인 (없으면 -> 리뷰도 없음)
            readReview(userUid, movieTitle);

        }else if(item.getClass().getName().contains("MovieMainData")) {
            //이전 프래그먼트가 영화 상세화면일 때 감상평 데이터가 없으면 영화 아이템을 전달 받음
            //새로 감상평 데이터를 추가하기 위해 영화 포스터와 이름이 필요하므로 영화 객체 생성해서 전달받음
            MovieMainData movie_item =  (MovieMainData) item;
            changeMode(1);
            iv_poster.setImageBitmap(movie_item.getPosterBitmap());
            iv_poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
            tv_name.setText(movie_item.getTitle());

            //영화제목 - firebase
            poster = movie_item.getPosterBitmap();
            movieTitle = movie_item.getTitle();
//            ratingbar2.setRating(0);

            //감상평 등록 레이아웃 띄우기
            lin_review.setVisibility(View.GONE);
            lin_no_review.setVisibility(View.VISIBLE);
            changeMode(1);

            ////firebase에서 해당 TITLE의 리뷰 있으면 불러오기
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference userReference = database.getReference();
//
//            //title이 존재하는지 확인 (없으면 -> 리뷰도 없음)
//            System.out.println("moviteTitle" + movieTitle);
            readReview(userUid, movieTitle);
        }

        //수정 버튼 클릭시
        btn_amend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode(1);
                lin_review.setVisibility(View.GONE);
                lin_no_review.setVisibility(View.VISIBLE);
            }
        });

        //등록버튼 클릭시
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //사용자가 쓴 리뷰 저장
                review = et_review.getText().toString();
                rating = String.valueOf(ratingbar2.getRating());

                //현재 날짜 구하기
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                String now_dt = format.format(now);

                //DB에는 String으로 저장해야 해서 변경
                String str_poster = BitmapToString(poster);

                //firebase에 리뷰 저장 함수
                saveReview(userUid, str_poster, movieTitle, review, rating, now_dt);

                changeMode(0);
                lin_review.setVisibility(View.VISIBLE);
                lin_no_review.setVisibility(View.INVISIBLE);
            }
        });

        //뒤로가기버튼 연결
        ib_back = (ImageButton) rootView.findViewById(R.id.ib_back);

        //뒤로가기버튼클릭시
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BackStack 에 저장된 이전 프래그먼트로 이동하기
                FragmentManager homeActivityFM = homeActivity.getSupportFragmentManager(); //프래그먼트 매니저 생성
                homeActivityFM.beginTransaction().addToBackStack(null); // BackStack 에 현재 프래그먼트 저장
                homeActivityFM.beginTransaction().remove(ReviewDetailFragment.this).commit(); //현재 프래그먼트 삭제
                homeActivityFM.popBackStack(); //이전 프래그먼트 불러오기
            }
        });
        return rootView;
    }

    //일반모드or수정모드 변경
    private void changeMode(int n) {
        if (n == 1) { //수정 모드
            btn_amend.setVisibility(View.INVISIBLE);
            btn_write.setVisibility(View.VISIBLE);
        } else {
            btn_amend.setVisibility(View.VISIBLE);
            btn_write.setVisibility(View.INVISIBLE);
        }
    }

    //firebase에 영화 리뷰 저장하기
    private void saveReview(String userUid, String poster, String title, String review, String rating, String now_dt){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference();

        userReference.child("user").child(userUid).child(title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //값 수정
                userReference.child("user").child(userUid).child(title).child("poster").setValue(poster);
                userReference.child("user").child(userUid).child(title).child("review").setValue(review);
                userReference.child("user").child(userUid).child(title).child("rating").setValue(rating);
                userReference.child("user").child(userUid).child(title).child("date").setValue(now_dt);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readReview(String userUid, String title){
        ////firebase에서 해당 TITLE의 리뷰 있으면 불러오기
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference();

        userReference.child("user").child(userUid).child(title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    poster_db = snapshot.child("poster").getValue().toString();
                    review_db = snapshot.child("review").getValue().toString();
                    rating_db = snapshot.child("rating").getValue().toString();
                    bitmap_poster_db = StringToBitmap(poster_db);
                    reviewDetail();
                }
                //리뷰데이터가 없으면 감상평 등록 레이아웃
                catch (Exception e){
                    changeMode(1);
//                    lin_review.setVisibility(View.GONE);
//                    lin_no_review.setVisibility(View.VISIBLE);
                    et_review.setText("");
                    ratingbar2.setRating(0);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void reviewDetail(){
        iv_poster.setImageBitmap(bitmap_poster_db);
        tv_review.setText(review_db);
        //리뷰데이터 있으면 리뷰아이템 객체 바로 보여줌
        ratingbar1.setRating(Float.valueOf(rating_db));
    }

        /* * String형을 BitMap으로 변환시켜주는 함수 * */
        public static Bitmap StringToBitmap(String encodedString) {
            try {
                byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }

    /* * Bitmap을 String형으로 변환 * */
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }
}