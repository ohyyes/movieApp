package com.example.movieapp;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

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

    //버튼 선언
    private Button btn_amend, btn_write;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_review_detail, container, false);

        //뷰 불러오기
        iv_poster = rootView.findViewById(R.id.iv_poster);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_review = rootView.findViewById(R.id.tv_review);
        tv_review.setMovementMethod(new ScrollingMovementMethod());

        //버튼 연결
        btn_amend = rootView.findViewById(R.id.btn_amend);
        btn_write = rootView.findViewById(R.id.btn_write);

        //레이아웃 연결
        LinearLayout lin_review = (LinearLayout) rootView.findViewById(R.id.lin_review);
        LinearLayout lin_no_review = (LinearLayout) rootView.findViewById(R.id.lin_no_review);

        //수정 버튼 클릭시
        btn_amend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    btn_amend.setVisibility(View.INVISIBLE);
                    btn_write.setVisibility(View.VISIBLE);
                    lin_review.setVisibility(View.GONE);
                    lin_no_review.setVisibility(View.VISIBLE);
            }
        });

        //등록버튼 클릭시
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_amend.setVisibility(View.VISIBLE);
                btn_write.setVisibility(View.INVISIBLE);
                lin_review.setVisibility(View.VISIBLE);
                lin_no_review.setVisibility(View.INVISIBLE);
            }
        });


        //이전 프래그먼트에서 전달된 메세지 변수에 담기
        String movie_title = this.getArguments().getString("영화 제목");

        //MainData 객체 만들기
        ReviewDetailFragmentMainData mainData1 = new ReviewDetailFragmentMainData();

        //데이터 값 설정하기
        mainData1.setIv_poster(R.drawable.testdata_minari);
        mainData1.setTv_name(movie_title);
        mainData1.setTv_review("여기는 줄거리가 들어갈 공간입니다. 데베에 저장된 줄거리를 movie_title 변수를 통해 찾아와서 줄거리를 보여주는 코드를 ReviewDetailFragement.java에 작성하면 될 것 같습니다.");

        //뷰에 mainData 정보 넣기
        iv_poster.setImageResource(mainData1.getIv_poster());
        tv_name.setText(mainData1.getTv_name());
        tv_review.setText(mainData1.getTv_review());


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
}