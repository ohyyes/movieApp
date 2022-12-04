package com.example.movieapp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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


        //데이터 값 설정하기
        MovieDetailFragmentMainData mainData1 = new MovieDetailFragmentMainData(R.drawable.testdata_minari, "미나리", "8.04", "2022", "103분", "드라마", "\"미나리는 어디서든 잘 자라\" 낯선 미국, 아칸소로 떠나온 한국 가족. 가족들에게 뭔가 해내는 걸 보여주고 싶은 아빠 '제이콥'(스티븐 연)은 자신만의 농장을 가꾸기 시작하고 엄마 '모니카'(한예리)도 다시 일자리를 찾는다. 아직 어린 아이들을 위해 ‘모니카’의 엄마 ‘순자’(윤여정)가 함께 살기로 하고 가방 가득 고춧가루, 멸치, 한약 그리고 미나리씨를 담은 할머니가 도착한다. 의젓한 큰딸 '앤'(노엘 케이트 조)과 장난꾸러기 막내아들 '데이빗'(앨런 김)은 여느 그랜마같지 않은 할머니가 영- 못마땅한데… 함께 있다면, 새로 시작할 수 있다는 희망으로 하루하루 뿌리 내리며 살아가는 어느 가족의 아주 특별한 여정이 시작된다!", "정이삭", "스티븐 연, 한예리, 윤여정, 앨런 김, 노엘 조, 윌 패튼");



        //임시 변수 (원래는 여기에 데이터를 담아 확인해야함)
        boolean has_review = true;
        btn_gotoReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //만약 해당 감상평이 있다면, 감상평 상세 프래그먼트로 화면 전환
                if (has_review) {
                    homeActivity.onFragmentChange(1);
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
                            //리뷰 작성 페이지로 이동 ! (일단 리뷰 상세 페이지로 이동하게 짬 리뷰 작성 페이지가 안 보여서)
                            homeActivity.onFragmentChange(3);
                            alertDialog.dismiss();  //팝업창 닫기
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