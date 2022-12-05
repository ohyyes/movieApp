package com.example.movieapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieapp.data.MovieMainData;
import com.example.movieapp.R;
import com.example.movieapp.data.ReviewMainData;
import com.example.movieapp.activity.HomeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    //홈 엑티비티 선언 (화면 전환시 필요)
    HomeActivity homeActivity;
    //뒤로가기 버튼 선언
    private ImageButton ib_back;
    ReviewMainData review_item;
    boolean has_review;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //홈 엑티비티 생성
        homeActivity = (HomeActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        homeActivity = null;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
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

    ArrayList<ReviewMainData> all_review;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_movie_detail.xml 과 연결하기!
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_detail, container, false);

        //뷰 선언
        ImageButton btn_gotoReview;
        ImageView iv_poster;
        TextView tv_name, tv_rate, tv_date, tv_time, tv_gerne, tv_summary, tv_director, tv_actor;

        //뒤로가기버튼 연결
        ib_back = (ImageButton) rootView.findViewById(R.id.ib_back);

        //뒤로가기버튼클릭시
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BackStack 에 저장된 이전 프래그먼트로 이동하기
                FragmentManager homeActivityFM= homeActivity.getSupportFragmentManager(); //프래그먼트 매니저 생성
                homeActivityFM.beginTransaction().addToBackStack(null); // BackStack 에 현재 프래그먼트 저장
                homeActivityFM.beginTransaction().remove(MovieDetailFragment.this).commit(); //현재 프래그먼트 삭제
                homeActivityFM.popBackStack(); //이전 프래그먼트 불러오기
            }
        });
        //뷰 불러오기
        btn_gotoReview = (ImageButton) rootView.findViewById(R.id.ib_gotomyreview);
        iv_poster = (ImageView) rootView.findViewById(R.id.iv_poster);
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_rate = (TextView) rootView.findViewById(R.id.tv_rate);
        tv_date = (TextView) rootView.findViewById(R.id.tv_date);
        tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        tv_gerne = (TextView) rootView.findViewById(R.id.tv_gerne);
        tv_summary = (TextView) rootView.findViewById(R.id.tv_summary);
        tv_director = (TextView) rootView.findViewById(R.id.tv_director);
        tv_actor = (TextView) rootView.findViewById(R.id.tv_actor);

        //이전 프래그먼트에서 전달된 메세지 변수에 담기
        MovieMainData movie_item =  (MovieMainData) this.getArguments().getParcelable("아이템");

        //뷰에 mainData 정보 넣기
        iv_poster.setImageResource(movie_item.getIv_poster());
        iv_poster.setClipToOutline(true); //포스터 둥근테두리 디자인 반영
        tv_name.setText(movie_item.getTv_name());
        tv_rate.setText(movie_item.getTv_rate());
        tv_date.setText(movie_item.getTv_date());
        tv_time.setText(movie_item.getTv_time());
        tv_gerne.setText(movie_item.getTv_gerne());
        tv_summary.setText(movie_item.getTv_summary());
        tv_director.setText(movie_item.getTv_director());
        tv_actor.setText(movie_item.getTv_actor());

        //참조할 리뷰 데이터 리스트
        all_review = new ArrayList<>();
        //MainData 객체 만들기-> back이랑 연결하면 삭제하기
        ReviewMainData mainData1 = new ReviewMainData(R.drawable.movie1, "쥬라기 월드", 5, "2022.02.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData1);
        ReviewMainData mainData2 = new ReviewMainData(R.drawable.movie2, "스파이더맨:노 웨이 홈", 4, "2021.08.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData2);
        ReviewMainData mainData3 = new ReviewMainData(R.drawable.movie3, "소닉 2", 4.5, "2022.09.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData3);
        ReviewMainData mainData4 = new ReviewMainData(R.drawable.movie1, "어메이징 스파이더맨 2", 2.5, "2022.02.20", ""); //아이템 추가하는 코드
        all_review.add(mainData4);
        ReviewMainData mainData5 = new ReviewMainData(R.drawable.movie2, "ㅁ", 0.5, "2015.05.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData5);
        ReviewMainData mainData6 = new ReviewMainData(R.drawable.movie3, "ㄴ", 1, "2008.03.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData6);


        //감상평 상세 프래그먼트로 전환하기 전에 선택한 영화의 감상평데이터가 있는지 확인
        review_item = new ReviewMainData();
        has_review = false;

        for(int i=0;i<all_review.size();i++){
            //감상평데이터가 있다면 전달할 감상평 객체 담기
            if(movie_item.getTv_name() == all_review.get(i).getTv_name()){
                review_item = all_review.get(i);
                has_review = true;
            }
            //찾으면 for문 중지
            if (has_review == true) break;
        }

        // [내 감상평 보러가기] 버튼의 setOnClickListener
        btn_gotoReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //만약 해당 감상평이 있다면 리뷰아이템 객체 그대로 전달하며 감상평 상세 프래그먼트로 화면 전환
                if (has_review) {
                    homeActivity.onFragmentChange(1, review_item);
                }
                //없다면, 팝업창 띄우기
                else {

                    final View popupView = getLayoutInflater().inflate(R.layout.popup_no_review, null);
                    final AlertDialog.Builder AlertBuilder = new AlertDialog.Builder(getContext());
                    AlertBuilder.setView(popupView);

                    final AlertDialog alertDialog = AlertBuilder.create();


                    //팝업창 크기 조정
                    LayoutParams dialogAttribute = alertDialog.getWindow().getAttributes();
                    dialogAttribute.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    dialogAttribute.height = ViewGroup.LayoutParams.MATCH_PARENT;

                    //팝업창 띄우기
                    alertDialog.show();

                    alertDialog.getWindow().setAttributes(dialogAttribute);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //팝업창이 둥글게 나올 수 있도록 기본 팝업 영역을 투명하게 설정


                    //닫기 버튼
                    Button btn_close = popupView.findViewById(R.id.btn_close);
                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();  //팝업창 닫기
                        }
                    });

                    //리뷰작성 버튼
                    Button btn_writeReview = popupView.findViewById(R.id.btn_writeReview);
                    btn_writeReview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //감상평 데이터에 필요한 영화 포스터, 이름 데이터 주기 위해 영화 아이템 객체 전달
                            Log.e("d", movie_item.getTv_name());
                            alertDialog.dismiss();  //팝업창 닫기
                            homeActivity.onFragmentChange(1, movie_item);
                        }
                    });

                    //보이기
                    alertDialog.show();

                }
            }
        });

        return rootView;
    }
}