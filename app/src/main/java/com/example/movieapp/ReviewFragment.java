package com.example.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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


    //데이터 리스트
    private ArrayList<ReviewFragmentMainData> all_review;

    //어댑터 선언
    private ReviewFragmentAdapter adapter;
    private ArrayAdapter<String> spinnerAdapter;

    //감상평 목록 정렬 기준 스피너 리스트
    String[] sortItems = {"최신순", "이름순", "별점높은순", "별점낮은순"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_review, container, false);

        //스피너 연결 후 어댑터 지정 -> 어댑터는 android 라이브러리에 정의된 것 사용
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, sortItems);
        spinner.setAdapter(spinnerAdapter);


        RecyclerView review_list = (RecyclerView) rootView.findViewById(R.id.review_list);
        LinearLayout lin_no_result = (LinearLayout) rootView.findViewById(R.id.lin_no_result);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()); //???
        review_list.setLayoutManager(layoutManager);




        all_review = new ArrayList<>();



        //임의의 데이터리뷰 추가 -> 나중에 back과 연결시키기
        ReviewFragmentMainData mainData1 = new ReviewFragmentMainData(R.drawable.movie1, "쥬라기 월드", 10, "2022.02.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData1);
        ReviewFragmentMainData mainData2 = new ReviewFragmentMainData(R.drawable.movie2, "스파이더맨:노 웨이 홈", 8, "2021.08.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData2);
        ReviewFragmentMainData mainData3 = new ReviewFragmentMainData(R.drawable.movie3, "소닉 2", 9, "2022.09.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        all_review.add(mainData3);

        adapter = new ReviewFragmentAdapter(all_review);
        review_list.setAdapter(adapter);

        if(all_review.isEmpty()){
            review_list.setVisibility(View.INVISIBLE);  // 리사이클러뷰 잠깐 안 보이게 설정
            lin_no_result.setVisibility(View.VISIBLE);      // lin_no_result 레이아웃을 보이게 설정
        }
        // 있을 땐, 리사이클러뷰가 보이게 !
        else{
            review_list.setVisibility(View.VISIBLE);    // 리사이클러뷰 보이게
            lin_no_result.setVisibility(View.INVISIBLE);    // lin_no_result 레이아웃 안 보이게
        }


        //스피너 아이템 선택했을 때
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //선택된 아이템은 sortItems[i] 사용하면 됨
                if(i == 0){
                    //최신순
                }else if (i == 1){
                    //이름순
                }else if(i==2){
                    //별점높은순
                }else if(i==3){
                    //별점낮은순
                }
            }

            //아무것도 선택되지 않았을 때
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return rootView;
    }
}